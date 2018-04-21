package com.wind.yuanbin.daily;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wind.yuanbin.daily.mvp.Contract;
import com.wind.yuanbin.daily.mvp.M.Daily_details;
import com.wind.yuanbin.daily.mvp.P.Presenter_Details;
import com.wind.yuanbin.daily.mvp.BaseActivity;
import com.wind.yuanbin.daily.utils.L;
import com.wind.yuanbin.daily.utils.ShareUtils;

public class DetailsActivity extends BaseActivity<Presenter_Details> implements Contract.UIDetailsView{

    WebView webView;
    ImageView iv_title;
    TextView tv_imgSource;
    RelativeLayout rl_imagesource;
    String title;
    String id;
    String imgUrl;
    String imgSource;
    String shareUrl;
    int height;
    public static final String INTENT_ID = "details_id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        id = intent.getStringExtra(INTENT_ID);

        webView = findViewById(R.id.wb_details);
        rl_imagesource = findViewById(R.id.rl_imagesource);
        iv_title = findViewById(R.id.iv_title);
        tv_imgSource = findViewById(R.id.tv_imgSource);

        initImageLayout();
        getDetails();
    }

    @Override
    public Presenter_Details initPersenter() {
        return new Presenter_Details(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_share,menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
        准备监听器，测量顶部布局的高度；
        在滑动的时候，改变顶部布局的高度，达到缩放的效果；
     */
    private void initImageLayout() {
        ViewTreeObserver vto = rl_imagesource.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rl_imagesource.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                height = rl_imagesource.getHeight();
                L.o("onGlobalLayout: " + height);
            }
        });
        webView.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
//            L.o("v:" + v + " scrollX: " + scrollX
//                    + " scrollY: " + scrollY + " oldScrollX:" + oldScrollX
//                    + " oldScrollY: " + oldScrollY);//old是指上一次的值

            int newHei = height - scrollY;
//            L.o("height: " + height
//                    + " newhei: " + newHei);
            if (newHei <= height){
                if (newHei > 0){
//                    iv_title.setMaxHeight(newHei);
                    changeimageHei(newHei);
                }else {
                    changeimageHei(0);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void changeimageHei(int newHei) {
        ViewGroup.LayoutParams layoutParams = rl_imagesource.getLayoutParams();
        layoutParams.height = newHei;
        rl_imagesource.setLayoutParams(layoutParams);
    }

    public static void toDetail(Activity activity, String id){
        Intent intent = new Intent(activity,DetailsActivity.class);
        intent.putExtra(INTENT_ID,id);
        activity.startActivity(intent);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_item_share:
                ShareUtils.shareText(this,"this is a text" + title + "  " + shareUrl);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void getDetails() {
        mPersenter.getDetails(id);
    }

    @Override
    public void show(String msg) {

    }

    @Override
    public void showWeb(Daily_details model) {
        this.title = model.getTitle();

        setTitle();

        showHtml(model);

        showImg(model);

        shareUrl = model.getShare_url();
    }

    private void showImg(Daily_details model) {
        imgUrl = model.getImage();
        imgSource = model.getImage_source();
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        Glide.with(this).load(imgUrl).apply(options).into(iv_title);
        tv_imgSource.setText("by: " + imgSource);
    }

    private void showHtml(Daily_details model) {
        String html = model.getBody();
//        String css = model.getCss().get(0);

        StringBuilder sb = new StringBuilder(html);
        int index = sb.lastIndexOf("</div>");

        html = sb.insert(index + 6, Contract.CSS).toString();

//        webView.loadData(html,"text/html","utf-8");
//      在Android版本为7.1.1的模拟器中出现了乱码，需要将加载方法做出如下修改
        webView.loadData(html,"text/html;charset=UTF-8",null);
    }

    private void setTitle() {
        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle(title);

        actionBar.setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public void showErr(String msg) {

    }
}
