package cn.lemon.bs;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import cn.alien95.resthttp.view.HttpImageView;
import cn.alien95.util.Utils;
import cn.lemon.bs.data.Device;
import cn.lemon.bs.data.DeviceModel;
import cn.lemon.bs.data.ResponseStatus;
import cn.lemon.common.net.ServiceResponse;
import cn.lemon.view.adapter.BaseViewHolder;
import cn.lemon.view.adapter.RecyclerAdapter;

/**
 * Created by linlongxin on 2017.5.5.
 */

public class DeviceAdapter extends RecyclerAdapter<Device> {

    public DeviceAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder<Device> onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        return new DeviceViewHolder(parent, R.layout.holder_device);
    }

    class DeviceViewHolder extends BaseViewHolder<Device> {

        private HttpImageView imageView;
        private TextView name, status, intro;
        private Button modifyStatus;

        public DeviceViewHolder(ViewGroup parent, int layoutId) {
            super(parent, layoutId);
        }

        @Override
        public void onInitializeView() {
            super.onInitializeView();
            imageView = findViewById(R.id.image);
            name = findViewById(R.id.name);
            intro = findViewById(R.id.intro);
            status = findViewById(R.id.status);
            modifyStatus = findViewById(R.id.modify_status);
        }

        @Override
        public void setData(final Device data) {
            super.setData(data);
            name.setText(data.name);
            intro.setText(data.intro);
            imageView.setImageUrl(data.image);
            setStatusText(data.status);
            modifyStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showModifyStatus(v,data);
                }
            });
        }
        public void setStatusText(int s){
            if(s == 0){
                status.setText("正常");
            }else if(s == 1){
                status.setText("故障");
            }else {
                status.setText("修复中");
            }
        }

        public void showModifyStatus(View v, final Device device) {
            final int tempStatus = device.status;
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

            LinearLayout linearLayout = new LinearLayout(v.getContext());
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            final TextView title = new TextView(v.getContext());
            title.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
            title.setGravity(Gravity.CENTER);
            title.setTextColor(v.getContext().getResources().getColor(R.color.colorPrimary));
            title.setTextSize(18);
            title.setPadding(Utils.dip2px(16), Utils.dip2px(16), Utils.dip2px(16), Utils.dip2px(8));
            title.setText("请选择修改后的状态");
            linearLayout.addView(title);

            RadioGroup radioGroup = new RadioGroup(v.getContext());
            LinearLayout.LayoutParams checkBoxParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            checkBoxParams.setMargins(Utils.dip2px(16), 0, Utils.dip2px(16), 0);
            radioGroup.setLayoutParams(checkBoxParams);
            final RadioButton radioNormal = new RadioButton(v.getContext());
            final RadioButton radioTrouble = new RadioButton(v.getContext());
            RadioButton radioFix = new RadioButton(v.getContext());
            radioNormal.setTextSize(16);
            radioNormal.setText("正常");
            radioTrouble.setTextSize(16);
            radioTrouble.setText("故障");
            radioFix.setTextSize(16);
            radioFix.setText("修复中");
            radioGroup.addView(radioNormal);
            radioGroup.addView(radioTrouble);
            radioGroup.addView(radioFix);
            radioNormal.setChecked(true);
            switch (device.status){
                case 0:
                    radioNormal.setChecked(true);
                    break;
                case 1:
                    radioTrouble.setChecked(true);
                    break;
                case 2:
                    radioFix.setChecked(true);
                    break;
            }
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                    if (radioNormal.isChecked()) {
                        device.status = 0;
                    } else if (radioTrouble.isChecked()) {
                        device.status = 1;
                    } else {
                        device.status = 2;
                    }
                }
            });
            linearLayout.addView(radioGroup);

            Button button = new Button(v.getContext());
            button.setText("确定");
            LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            buttonParams.setMargins(Utils.dip2px(16), 0, Utils.dip2px(16), Utils.dip2px(16));
            button.setLayoutParams(buttonParams);
            linearLayout.addView(button);

            builder.setView(linearLayout);
            final Dialog alertDialog = builder.create();
            alertDialog.show();

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(device.status == tempStatus){
                        alertDialog.dismiss();
                        return;
                    }
                    DeviceModel.getInstance().updateStatus(device.id,device.status,
                            new ServiceResponse<ResponseStatus>(){
                                @Override
                                public void onNext(ResponseStatus responseStatus) {
                                    super.onNext(responseStatus);
                                    Utils.Toast("修改成功");
                                    setStatusText(device.status);
                                    alertDialog.dismiss();
                                }

                                @Override
                                public void onError(Throwable e) {
                                    super.onError(e);
                                    Utils.Toast("修改失败");
                                    alertDialog.dismiss();
                                }
                            });
                }
            });
        }


    }
}
