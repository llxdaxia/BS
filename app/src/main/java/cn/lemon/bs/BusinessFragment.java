package cn.lemon.bs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import cn.alien95.util.Utils;
import cn.lemon.bs.data.DataModel;
import cn.lemon.bs.data.bean.Business;
import cn.lemon.common.base.fragment.SuperFragment;
import cn.lemon.common.net.ServiceResponse;
import cn.lemon.view.RefreshRecyclerView;
import cn.lemon.view.adapter.Action;


public class BusinessFragment extends SuperFragment {

    private RefreshRecyclerView mRecyclerView;
    private BusinessAdapter mAdapter;
    private int page = 0;

    public BusinessFragment(){
        super(R.layout.fragment_business,true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new BusinessAdapter(getContext());
        getData(0);
    }

    @Override
    public void onClickErrorLoadData(View v) {
        super.onClickErrorLoadData(v);
        getData(0);
    }

    @Override
    public void onInitialView() {
        super.onInitialView();
        mRecyclerView = (RefreshRecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setRefreshAction(new Action() {
            @Override
            public void onAction() {
                getData(0);
            }
        });
        mRecyclerView.setLoadMoreAction(new Action() {
            @Override
            public void onAction() {
                getData(page);
                page++;
            }
        });
    }

    public void getData(final int page){
        DataModel.getInstance().getPageBusinessList(page, new ServiceResponse<Business[]>() {
            @Override
            public void onNext(Business[] data) {
                super.onNext(data);
                Utils.Log("businessfragment : onNext " + data.length);
                showContent();
                if (page == 0) {
                    mAdapter.clear();
                    if (data.length == 0) {
                        showEmpty();
                    }
                    mAdapter.addAll(data);
                    mRecyclerView.dismissSwipeRefresh();
                } else {
                    mAdapter.addAll(data);
                }
                if (data.length < 10) {
                    mAdapter.showNoMore();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                Utils.Log("businessfragment : onError " + e.getMessage());
                showError();
            }
        });
    }

}
