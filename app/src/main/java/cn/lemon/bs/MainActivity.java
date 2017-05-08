package cn.lemon.bs;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.lemon.common.base.SuperActivity;

public class MainActivity extends SuperActivity implements View.OnClickListener {

    private DeviceFragment mDeviceFragment;
    private PublishFragment mPublishFragment;
    private FragmentManager mFragmentManager;
    private LinearLayout mDeviceTab, mPublishTab;
    private ImageView mDeviceIcon, mPublishIcon;
    private TextView mDeviceText, mPublishText;
    private int mCurrentTabPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        useStatusPages(false);
        setContentView(R.layout.activity_main);

        mFragmentManager = getSupportFragmentManager();
        mDeviceFragment = new DeviceFragment();
        mPublishFragment = new PublishFragment();
        initView();

    }

    public void initView() {
        mDeviceTab = (LinearLayout) findViewById(R.id.device_tab);
        mPublishTab = (LinearLayout) findViewById(R.id.publish_tab);
        mDeviceIcon = (ImageView) findViewById(R.id.device_icon);
        mPublishIcon = (ImageView) findViewById(R.id.publish_icon);
        mDeviceText = (TextView) findViewById(R.id.device_text);
        mPublishText = (TextView) findViewById(R.id.publish_text);
        mDeviceTab.setOnClickListener(this);
        mPublishTab.setOnClickListener(this);
        mFragmentManager.beginTransaction().add(R.id.fragment, mDeviceFragment).commit();
    }

    public void selectTab(int currentPosition) {
        int deviceResId, publishResId;
        int deviceColor, publishColor;
        if (currentPosition == 0) {
            deviceResId = R.drawable.ic_select_device;
            deviceColor = R.color.blueLight;
            publishResId = R.drawable.ic_unselect_publish;
            publishColor = R.color.gray;
        } else {
            deviceResId = R.drawable.ic_unselect_device;
            deviceColor = R.color.gray;
            publishResId = R.drawable.ic_select_publish;
            publishColor = R.color.blueLight;
        }
        mDeviceIcon.setImageResource(deviceResId);
        mPublishIcon.setImageResource(publishResId);
        mDeviceText.setTextColor(getResources().getColor(deviceColor));
        mPublishText.setTextColor(getResources().getColor(publishColor));
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
                            .hide(mPublishFragment)
                            .show(mDeviceFragment)
                            .commit();
                }
                break;
            case R.id.publish_tab:
                if (mCurrentTabPosition == 1) {
                    return;
                } else {
                    selectTab(1);
                    mCurrentTabPosition = 1;
                    if(!mPublishFragment.isAdded()){
                        mFragmentManager.beginTransaction()
                                .add(R.id.fragment,mPublishFragment)
                                .commit();
                    }
                    mFragmentManager.beginTransaction()
                            .hide(mDeviceFragment)
                            .show(mPublishFragment)
                            .commit();
                }
                break;
        }
    }
}

