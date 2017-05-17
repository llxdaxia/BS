package cn.lemon.bs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import cn.lemon.bs.data.DataModel;
import cn.lemon.bs.data.bean.Notice;
import cn.lemon.common.base.fragment.SuperFragment;
import cn.lemon.common.net.ServiceResponse;
import cn.lemon.view.RefreshRecyclerView;
import cn.lemon.view.adapter.Action;

/**
 * Created by linlongxin on 2017.5.16.
 */

public class PublicNoticeFragment extends SuperFragment {

    private RefreshRecyclerView mRecyclerView;
    private NoticeAdapter mAdapter;
    private int page = 0;

    public PublicNoticeFragment() {
        super(R.layout.fragment_public_notice, true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData(0);
    }

    @Override
    public void onInitialView() {
        super.onInitialView();
        mRecyclerView = (RefreshRecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter = new NoticeAdapter(getContext()));
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

    @Override
    public void onClickErrorLoadData(View v) {
        super.onClickErrorLoadData(v);
        getData(0);
    }

    public void getData(final int page) {
        DataModel.getInstance().getPageNoticeList(page, new ServiceResponse<Notice[]>() {
            @Override
            public void onNext(Notice[] notices) {
                super.onNext(notices);
                showContent();
                if (page == 0) {
                    mAdapter.clear();
                    if (notices.length == 0) {
                        showEmpty();
                    }
                    mAdapter.addAll(notices);
                    mRecyclerView.dismissSwipeRefresh();
                } else {
                    mAdapter.addAll(notices);
                }
                if (notices.length < 10) {
                    mAdapter.showNoMore();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                showError();
            }
        });
    }
}
