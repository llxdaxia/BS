package cn.lemon.bs;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import cn.lemon.bs.data.Device;
import cn.lemon.bs.data.DeviceModel;
import cn.lemon.common.base.fragment.SuperFragment;
import cn.lemon.common.net.ServiceResponse;
import cn.lemon.view.RefreshRecyclerView;
import cn.lemon.view.adapter.Action;


@SuppressLint("ValidFragment")
public class DeviceFragment extends SuperFragment {

    private RefreshRecyclerView mRecyclerView;
    private DeviceAdapter mAdapter;
    private int page = 0;

    @SuppressLint("ValidFragment")
    public DeviceFragment() {
        super(R.layout.fragment_device, true);
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
        mRecyclerView.setAdapter(mAdapter = new DeviceAdapter(getContext()));
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
        DeviceModel.getInstance().getPageDeviceList(page, new ServiceResponse<Device[]>() {
            @Override
            public void onNext(Device[] devices) {
                super.onNext(devices);
                showContent();
                if (page == 0) {
                    mAdapter.clear();
                    if (devices.length == 0) {
                        showEmpty();
                    }
                    mAdapter.addAll(devices);
                    mRecyclerView.dismissSwipeRefresh();
                } else {
                    mAdapter.addAll(devices);
                }
                if (devices.length < 10) {
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
