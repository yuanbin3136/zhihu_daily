package com.wind.yuanbin.daily.mvp.V;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wind.yuanbin.daily.DetailsActivity;
import com.wind.yuanbin.daily.R;
import com.wind.yuanbin.daily.mvp.BaseFragment;
import com.wind.yuanbin.daily.mvp.Contract;
import com.wind.yuanbin.daily.mvp.M.DailyModel;
import com.wind.yuanbin.daily.mvp.P.Presenter_Home;
import com.wind.yuanbin.daily.retrofit.HomeAdapter;
import com.wind.yuanbin.daily.utils.L;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends BaseFragment<Presenter_Home> implements Contract.UIView, SwipeRefreshLayout.OnRefreshListener, HomeAdapter.OnItemClickLitener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

//    private Contract.IPersenter_Home IPersenterHome;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
//        IPersenterHome = new Presenter_Home(this);
        L.o(this.toString() + "onCreate");
    }

    @Override
    public Presenter_Home initPersenter() {
        return new Presenter_Home(this);
    }

    SwipeRefreshLayout swl_home;
    RecyclerView rv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        L.o(this.toString() + "onCreateView");

        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        swl_home = view.findViewById(R.id.swl_home);
        swl_home.setOnRefreshListener(this);

        rv = view.findViewById(R.id.rv_list);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        getToday();
        super.onViewCreated(view, savedInstanceState);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
        L.o(this.toString() + " onButtonPressed");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        L.o(this.toString() + " onAttach");
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        L.o(this.toString() + " onDetach");

    }

    @Override
    public void getToday() {
        if (mPersenter != null){
            mPersenter.getToday();
            swl_home.setRefreshing(true);
//            IPersenterHome.getDaily_details();/
        }
    }
    @Override
    public void onRefresh() {
        getToday();
    }

    HomeAdapter adapter;
    @Override
    public void show(String msg) {
        L.o(msg);
    }

    @Override
    public void showList(DailyModel model) {
        if (adapter == null){
            adapter = new HomeAdapter(model);
            rv.setAdapter(adapter);
            //设置分隔线
            rv.addItemDecoration(new DividerItemDecoration(
                    getActivity(), DividerItemDecoration.VERTICAL));
            adapter.setOnItemClickLitener(this);

            getActivity().setTitle(getResources().getString(R.string.app_introduce)+ model.getDate());
        }else {
            adapter.notifyDataSetChanged();
        }
        swl_home.setRefreshing(false);
    }

    @Override
    public void showErr(String msg) {
        L.o("错误：" + msg);
    }

    @Override
    public void onItemClick(View view, String id) {
        Toast.makeText(getContext(),"onItemClick " + id,Toast.LENGTH_SHORT).show();
        DetailsActivity.toDetail(getActivity(),id);
    }

    @Override
    public void onItemLongClick(View view, int position) {
//        Toast.makeText(getContext(),"onItemLongClick " + position,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPagerClick(String id) {
        Toast.makeText(getContext(),"onPagerClick " + id,Toast.LENGTH_SHORT).show();
        DetailsActivity.toDetail(getActivity(),id);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
