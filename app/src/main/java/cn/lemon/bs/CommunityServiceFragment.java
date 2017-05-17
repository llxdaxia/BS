package cn.lemon.bs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.alien95.resthttp.view.HttpImageView;
import cn.alien95.util.Utils;
import cn.lemon.bs.data.DataModel;
import cn.lemon.bs.data.bean.Device;
import cn.lemon.common.base.fragment.SuperFragment;
import cn.lemon.common.net.ServiceResponse;
import cn.lemon.view.RefreshRecyclerView;
import cn.lemon.view.adapter.Action;
import cn.lemon.view.adapter.BaseViewHolder;
import cn.lemon.view.adapter.MultiTypeAdapter;

/**
 * Created by linlongxin on 2017.5.16.
 */

public class CommunityServiceFragment extends SuperFragment {

    public static final String PAY_TEXT = "pay_text";
    private RefreshRecyclerView mRecyclerView;
    private MultiTypeAdapter mAdapter;
    private int page = 0;

    public CommunityServiceFragment() {
        super(R.layout.fragment_community_service, true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new MultiTypeAdapter(getContext());
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

    public void getData(final int page) {
        DataModel.getInstance().getPageCommunityServiceList(page, new ServiceResponse<Device[]>() {
            @Override
            public void onNext(Device[] devices) {
                super.onNext(devices);
                Utils.Log("CommunityServiceFragment onNext : " + devices.length);
                Device[] troubleDevices = getTroubleDevices(devices);
                Device[] fixDevices = getFixDevices(devices);
                showContent();
                if (page == 0) {
                    mAdapter.clear();
                    if (devices.length == 0) {
                        showEmpty();
                    }
                    mAdapter.add(TextViewHolder.class, "故障");
                    mAdapter.addAll(ServiceViewHolder.class, troubleDevices);
                    mAdapter.add(TextViewHolder.class, "修复中");
                    mAdapter.addAll(ServiceViewHolder.class, fixDevices);
                    mAdapter.add(TextViewHolder.class, "其他");
                    String[] strs = {"水费缴纳", "电费缴纳", "气费缴纳"};
                    mAdapter.addAll(PayHolder.class, strs);

                    mRecyclerView.dismissSwipeRefresh();
                } else {
                    mAdapter.addAll(DeviceAdapter.DeviceViewHolder.class, devices);
                }
                if (devices.length < 10) {
                    mAdapter.showNoMore();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                Utils.Log("CommunityServiceFragment onError : " + e.getMessage());
                showError();
            }
        });
    }

    public static class TextViewHolder extends BaseViewHolder<String> {

        private TextView mTitle;

        public TextViewHolder(ViewGroup parent) {
            super(parent, R.layout.holder_text);
        }

        @Override
        public void setData(String data) {
            super.setData(data);
            mTitle.setText(data);
        }

        @Override
        public void onInitializeView() {
            super.onInitializeView();
            mTitle = findViewById(R.id.title);
        }
    }

    public static class ServiceViewHolder extends BaseViewHolder<Device> {

        private TextView name, intro;
        private HttpImageView image;

        public ServiceViewHolder(ViewGroup parent) {
            super(parent, R.layout.holder_service);
        }

        @Override
        public void onInitializeView() {
            super.onInitializeView();
            name = findViewById(R.id.name);
            intro = findViewById(R.id.intro);
            image = findViewById(R.id.image);
        }

        @Override
        public void setData(Device data) {
            super.setData(data);
            name.setText(data.name);
            intro.setText(data.intro);
            image.setImageUrl(data.image);
        }
    }


    public static class PayHolder extends BaseViewHolder<String> {

        private TextView mTitle;
        private Context mContext;

        public PayHolder(ViewGroup parent) {
            super(parent, R.layout.holder_pay);
            mContext = parent.getContext();
        }

        @Override
        public void setData(String data) {
            super.setData(data);
            mTitle.setText(data);
        }

        @Override
        public void onInitializeView() {
            super.onInitializeView();
            mTitle = findViewById(R.id.title);
        }

        @Override
        public void onItemViewClick(String data) {
            super.onItemViewClick(data);
            Intent intent = new Intent(mContext, PayActivity.class);
            if (TextUtils.equals(data, "水费缴纳")) {
                intent.putExtra(PAY_TEXT, "水费");
            } else if (TextUtils.equals(data, "电费缴纳")) {
                intent.putExtra(PAY_TEXT, "电费");
            } else {
                intent.putExtra(PAY_TEXT, "气费");
            }
            mContext.startActivity(intent);
        }
    }

    public Device[] getTroubleDevices(Device[] data) {
        int length = data.length;
        int temp = 0;
        for (int i = 0; i < length; i++) {
            if (data[i].status == 2) {
                temp = i;
            }
        }
        if (temp == 0) {
            return null;
        }
        Device[] result = new Device[temp];
        for (int i = 0; i < temp; i++) {
            result[i] = data[i];
        }
        return result;
    }

    public Device[] getFixDevices(Device[] data) {
        int length = data.length;
        int start = 0;
        for (int i = 0; i < length; i++) {
            if (data[i].status == 2) {
                start = i;
            }
        }
        if (start == 0) {
            return null;
        }
        Device[] result = new Device[length - start];
        for (int i = start; i < length; i++) {
            result[i - start] = data[i];
        }
        return result;
    }
}
