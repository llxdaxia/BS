package cn.lemon.bs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.lemon.common.base.SuperActivity;

public class MainActivity extends SuperActivity implements View.OnClickListener {

    private DeviceFragment mDeviceFragment;
    private PublishFragment mPublishFragment;
    private BusinessFragment mBusinessFragment;
    private PublicNoticeFragment mPublicNoticeFragment;
    private CommunityServiceFragment mCommunityServiceFragment;
    private FragmentManager mFragmentManager;
    private LinearLayout mDeviceTab, mPublishTab, mNoticeTab, mBusinessTab, mServiceTab;
    private ImageView mDeviceIcon, mPublishIcon, mNoticeIcon, mBusinessIcon, mServiceIcon;
    private TextView mDeviceText, mPublishText, mNoticeText, mBusinessText, mServiceText;
    private TextView mTitle;
    private int mCurrentTabPosition = 0;
    private Fragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        useStatusPages(false);
        setContentView(R.layout.activity_main);

        mFragmentManager = getSupportFragmentManager();
        mDeviceFragment = new DeviceFragment();
        mPublishFragment = new PublishFragment();
        mBusinessFragment = new BusinessFragment();
        mPublicNoticeFragment = new PublicNoticeFragment();
        mCommunityServiceFragment = new CommunityServiceFragment();
        mCurrentFragment = mDeviceFragment;
        initView();

    }

    public void setTitle(String title) {
        mTitle.setText(title);
    }

    public void initView() {
        mTitle = (TextView) findViewById(R.id.title);
        mDeviceTab = (LinearLayout) findViewById(R.id.device_tab);
        mPublishTab = (LinearLayout) findViewById(R.id.publish_tab);
        mNoticeTab = (LinearLayout) findViewById(R.id.public_notice_tab);
        mBusinessTab = (LinearLayout) findViewById(R.id.business_tab);
        mServiceTab = (LinearLayout) findViewById(R.id.community_service_tab);

        mDeviceIcon = (ImageView) findViewById(R.id.device_icon);
        mPublishIcon = (ImageView) findViewById(R.id.publish_icon);
        mNoticeIcon = (ImageView) findViewById(R.id.public_notice_icon);
        mBusinessIcon = (ImageView) findViewById(R.id.business_icon);
        mServiceIcon = (ImageView) findViewById(R.id.community_service_icon);

        mDeviceText = (TextView) findViewById(R.id.device_text);
        mPublishText = (TextView) findViewById(R.id.publish_text);
        mNoticeText = (TextView) findViewById(R.id.public_notice_text);
        mBusinessText = (TextView) $(R.id.business_text);
        mServiceText = (TextView) $(R.id.community_service_text);

        mDeviceTab.setOnClickListener(this);
        mPublishTab.setOnClickListener(this);
        mNoticeTab.setOnClickListener(this);
        mBusinessTab.setOnClickListener(this);
        mServiceTab.setOnClickListener(this);

        mFragmentManager.beginTransaction().add(R.id.fragment, mDeviceFragment).commit();
    }

    public void selectTab(int currentPosition) {
        int deviceResId, publishResId, noticeResId, serviceResId, BusinessResId;
        int deviceColor, publishColor, noticeColor, serviceColor, BusinessColor;
        if (currentPosition == 0) {
            deviceResId = R.drawable.ic_select_device;
            publishResId = R.drawable.ic_unselect_publish;
            noticeResId = R.drawable.ic_unselect_notice;
            serviceResId = R.drawable.ic_unselect_service;
            BusinessResId = R.drawable.ic_unselect_eb;
            deviceColor = R.color.blueLight;
            publishColor = R.color.gray;
            noticeColor = R.color.gray;
            serviceColor = R.color.gray;
            BusinessColor = R.color.gray;
            setTitle("智能设备");
        } else if (currentPosition == 1) {
            deviceResId = R.drawable.ic_unselect_device;
            BusinessResId = R.drawable.ic_select_eb;
            serviceResId = R.drawable.ic_unselect_service;
            noticeResId = R.drawable.ic_unselect_notice;
            publishResId = R.drawable.ic_unselect_publish;
            deviceColor = R.color.gray;
            BusinessColor = R.color.blueLight;
            serviceColor = R.color.gray;
            noticeColor = R.color.gray;
            publishColor = R.color.gray;
            setTitle("预定购物");
        } else if (currentPosition == 2) {
            deviceResId = R.drawable.ic_unselect_device;
            BusinessResId = R.drawable.ic_unselect_eb;
            serviceResId = R.drawable.ic_select_service;
            noticeResId = R.drawable.ic_unselect_notice;
            publishResId = R.drawable.ic_unselect_publish;
            deviceColor = R.color.gray;
            BusinessColor = R.color.gray;
            serviceColor = R.color.blueLight;
            noticeColor = R.color.gray;
            publishColor = R.color.gray;
            setTitle("社区服务");
        } else if (currentPosition == 3) {
            deviceResId = R.drawable.ic_unselect_device;
            BusinessResId = R.drawable.ic_unselect_eb;
            serviceResId = R.drawable.ic_unselect_service;
            noticeResId = R.drawable.ic_select_notice;
            publishResId = R.drawable.ic_unselect_publish;
            deviceColor = R.color.gray;
            BusinessColor = R.color.gray;
            serviceColor = R.color.gray;
            noticeColor = R.color.blueLight;
            publishColor = R.color.gray;
            setTitle("公告通知");
        } else {
            deviceResId = R.drawable.ic_unselect_device;
            BusinessResId = R.drawable.ic_unselect_eb;
            serviceResId = R.drawable.ic_unselect_service;
            noticeResId = R.drawable.ic_unselect_notice;
            publishResId = R.drawable.ic_select_publish;
            deviceColor = R.color.gray;
            BusinessColor = R.color.gray;
            serviceColor = R.color.gray;
            noticeColor = R.color.gray;
            publishColor = R.color.blueLight;
            setTitle("发布");
        }
        mDeviceIcon.setImageResource(deviceResId);
        mPublishIcon.setImageResource(publishResId);
        mNoticeIcon.setImageResource(noticeResId);
        mServiceIcon.setImageResource(serviceResId);
        mBusinessIcon.setImageResource(BusinessResId);
        mDeviceText.setTextColor(getResources().getColor(deviceColor));
        mPublishText.setTextColor(getResources().getColor(publishColor));
        mNoticeText.setTextColor(getResources().getColor(noticeColor));
        mBusinessText.setTextColor(getResources().getColor(BusinessColor));
        mServiceText.setTextColor(getResources().getColor(serviceColor));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.device_tab:
                if (mCurrentTabPosition == 0) {
                    return;
                } else {
                    selectTab(0);
                    mCurrentTabPosition = 0;
                    mFragmentManager.beginTransaction()
                            .show(mDeviceFragment)
                            .hide(mCurrentFragment)
                            .commit();
                    mCurrentFragment = mDeviceFragment;
                }
                break;
            case R.id.business_tab:
                if (mCurrentTabPosition == 1) {
                    return;
                } else {
                    selectTab(1);
                    mCurrentTabPosition = 1;
                    if (!mBusinessFragment.isAdded()) {
                        mFragmentManager.beginTransaction()
                                .add(R.id.fragment, mBusinessFragment)
                                .commit();
                    }
                    mFragmentManager.beginTransaction()
                            .show(mBusinessFragment)
                            .hide(mCurrentFragment)
                            .commit();
                    mCurrentFragment = mBusinessFragment;
                }
                break;
            case R.id.community_service_tab:
                if (mCurrentTabPosition == 2) {
                    return;
                } else {
                    selectTab(2);
                    mCurrentTabPosition = 2;
                    if (!mCommunityServiceFragment.isAdded()) {
                        mFragmentManager.beginTransaction()
                                .add(R.id.fragment, mCommunityServiceFragment)
                                .commit();
                    }
                    mFragmentManager.beginTransaction()
                            .show(mCommunityServiceFragment)
                            .hide(mCurrentFragment)
                            .commit();
                    mCurrentFragment = mCommunityServiceFragment;
                }
                break;
            case R.id.public_notice_tab:
                if (mCurrentTabPosition == 3) {
                    return;
                } else {
                    selectTab(3);
                    mCurrentTabPosition = 3;
                    if (!mPublicNoticeFragment.isAdded()) {
                        mFragmentManager.beginTransaction()
                                .add(R.id.fragment, mPublicNoticeFragment)
                                .commit();
                    }
                    mFragmentManager.beginTransaction()
                            .show(mPublicNoticeFragment)
                            .hide(mCurrentFragment)
                            .commit();
                    mCurrentFragment = mPublicNoticeFragment;
                }
                break;
            case R.id.publish_tab:
                if (mCurrentTabPosition == 4) {
                    return;
                } else {
                    selectTab(4);
                    mCurrentTabPosition = 4;
                    if (!mPublishFragment.isAdded()) {
                        mFragmentManager.beginTransaction()
                                .add(R.id.fragment, mPublishFragment)
                                .commit();
                    }
                    mFragmentManager.beginTransaction()
                            .show(mPublishFragment)
                            .hide(mCurrentFragment)
                            .commit();
                    mCurrentFragment = mPublishFragment;
                }
                break;
        }
    }
}

