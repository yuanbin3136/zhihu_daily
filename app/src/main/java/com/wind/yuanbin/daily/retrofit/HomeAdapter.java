package com.wind.yuanbin.daily.retrofit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wind.yuanbin.daily.mvp.M.DailyModel;
import com.wind.yuanbin.daily.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuanb on 2018/2/18.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.BaseViewHolder>{

    private List<DailyModel.Stories> stories;
    private List<DailyModel.Top_stories> top_stories;
    public interface OnItemClickLitener
    {
        void onItemClick(View view, String id);
        void onItemLongClick(View view, int position);
        void onPagerClick(String id);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
    public HomeAdapter(DailyModel model){
        this.stories = model.getStories();
        this.top_stories = model.getTop_stories();
    }
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder viewHolder = null;
        switch (viewType){
            case Type_header:
                viewHolder = new ViewHolder_Header(LayoutInflater.from(
                        parent.getContext()).inflate(R.layout.layout_home_header, parent,
                        false));
                break;
            case Type_item:
                viewHolder = new ViewHolder_Item(LayoutInflater.from(
                    parent.getContext()).inflate(R.layout.layout_home_item, parent,
                    false));
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (getItemViewType(position) == Type_header){
            ViewHolder_Header holder_header = (ViewHolder_Header)holder;
            holder_header.vp.setAdapter(new HeaderAdapter(((ViewHolder_Header) holder).vp.getContext()
                    , top_stories, position_pagerItem ->
                    mOnItemClickLitener.onPagerClick("" + top_stories.get(position_pagerItem).getId())));
            return;
        }else {
            ViewHolder_Item holder_item = (ViewHolder_Item)holder;
            holder_item.tv.setText(stories.get(position - 1).getTitle());
            List<String> list = stories.get(position - 1).getImages();
            Glide.with(holder_item.iv.getContext())
                    .load(list.get(0))
                    .into(holder_item.iv);
            if (list.size() > 1){
                holder_item.tv_isimgs.setText("多图");
                holder_item.tv_isimgs.setVisibility(View.VISIBLE);
            }else {
                holder_item.tv_isimgs.setVisibility(View.INVISIBLE);
            }
            // 如果设置了回调，则设置点击事件
            if (mOnItemClickLitener != null)
            {
                holder.itemView.setOnClickListener(v -> {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView,
                            stories.get(pos - 1).getId()+"");
                });

                holder.itemView.setOnLongClickListener(v -> {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos - 1);
                    return false;
                });
        }

        }
    }

    static class HeaderAdapter extends PagerAdapter implements View.OnClickListener {

        List<View> views = new ArrayList<>();
        Context context;
        List<DailyModel.Top_stories> top_stories;
        OnPagerClick onPagerClick;

        @Override
        public void onClick(View v) {
            for (int k = 0;k < views.size();k++) {
                if(views.get(k)==v){
                    if (onPagerClick != null){
                        onPagerClick.onPagerclick(k);
                        return;
                    }
                }
            }
            onPagerClick.onPagerclick(-1);
        }

        interface OnPagerClick{
            void onPagerclick(int position);
        }
        public HeaderAdapter(Context context, List<DailyModel.Top_stories> top_stories,OnPagerClick onPagerClick){
            this.context = context;
            this.top_stories = top_stories;
            this.onPagerClick = onPagerClick;
            LayoutInflater inflater = LayoutInflater.from(context);
            for (DailyModel.Top_stories s:
                 top_stories) {
                views.add(inflater.inflate(R.layout.layout_home_header_item,null));
            }
        }
        @Override
        public int getCount() {
            return top_stories.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(views.get(position));
//            super.destroyItem(container, position, object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = views.get(position);
            ImageView iv = view.findViewById(R.id.iv_header_item);
            TextView tv = view.findViewById(R.id.tv_header_item);
            RequestOptions options = new RequestOptions();
            options.centerCrop();
            Glide.with(context)
                    .load(top_stories.get(position).getImage())
                    .apply(options)
                    .into(iv);
            tv.setText(top_stories.get(position).getTitle());
            view.setOnClickListener(this);

            container.addView(views.get(position));
//            return super.instantiateItem(container, position);
            return views.get(position);
        }
    }

    @Override
    public int getItemCount() {
        return stories.size() + 1;
    }
    final int Type_header = 0;
    final int Type_item= 1;
    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return Type_header;
        }else {
            return Type_item;
        }
    }

    class BaseViewHolder extends RecyclerView.ViewHolder{

        public BaseViewHolder(View itemView) {
            super(itemView);
        }
    }
    class ViewHolder_Item extends BaseViewHolder{

        ImageView iv;
        TextView tv;
        TextView tv_isimgs;
        public ViewHolder_Item(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.item_iv);
            tv = itemView.findViewById(R.id.item_text);
            tv_isimgs = itemView.findViewById(R.id.item_iv_isimgs);
        }
    }
    class ViewHolder_Header extends BaseViewHolder{
        ViewPager vp;
        public ViewHolder_Header(View itemView) {
            super(itemView);
            vp = itemView.findViewById(R.id.vp_header);
        }
    }
}
