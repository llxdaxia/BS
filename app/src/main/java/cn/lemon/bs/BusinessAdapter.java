package cn.lemon.bs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.alien95.resthttp.view.HttpImageView;
import cn.alien95.util.Utils;
import cn.lemon.bs.data.DataModel;
import cn.lemon.bs.data.ResponseStatus;
import cn.lemon.bs.data.bean.Account;
import cn.lemon.bs.data.bean.Business;
import cn.lemon.common.net.ServiceResponse;
import cn.lemon.view.adapter.BaseViewHolder;
import cn.lemon.view.adapter.RecyclerAdapter;

/**
 * Created by linlongxin on 2017.5.16.
 */

public class BusinessAdapter extends RecyclerAdapter<Business> {

    public final static String PAY_MONEY = "PAY_MONEY";

    private enum BuyType {
        BOOK, PAY;
    }

    public BusinessAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder<Business> onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        return new BusinessViewHolder(parent);
    }

    class BusinessViewHolder extends BaseViewHolder<Business> {

        private HttpImageView image;
        private TextView name, intro, price, book, pay;

        public BusinessViewHolder(ViewGroup parent) {
            super(parent, R.layout.holder_business);
        }

        @Override
        public void onInitializeView() {
            super.onInitializeView();
            image = findViewById(R.id.image);
            name = findViewById(R.id.name);
            intro = findViewById(R.id.intro);
            price = findViewById(R.id.price);
            book = findViewById(R.id.book);
            pay = findViewById(R.id.pay);
        }

        @Override
        public void setData(final Business data) {
            super.setData(data);
            image.setImageUrl(data.image);
            name.setText(data.name);
            intro.setText(data.intro);
            price.setText(data.price + "元");

            book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (DataModel.getInstance().getAccount() == null) {
                        completeInfo(v, data, BuyType.BOOK);
                    } else {
                        book.setText("已预订");
                        Utils.Toast("预订成功，请到指定地点自取");
                    }
                }
            });
            pay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (DataModel.getInstance().getAccount() == null) {
                        completeInfo(v, data, BuyType.PAY);
                    } else {
                        Intent intent = new Intent(v.getContext(), PayActivity.class);
                        intent.putExtra(CommunityServiceFragment.PAY_TEXT, data.name);
                        intent.putExtra(PAY_MONEY, data.price);
                        v.getContext().startActivity(intent);
                    }
                }
            });
        }

        public void completeInfo(View v, final Business data, final BuyType type) {

            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

            LinearLayout linearLayout = new LinearLayout(v.getContext());
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            final TextView title = new TextView(v.getContext());
            title.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
            title.setGravity(Gravity.CENTER);
            title.setTextColor(v.getContext().getResources().getColor(R.color.colorPrimary));
            title.setTextSize(18);
            title.setPadding(Utils.dip2px(16), Utils.dip2px(16), Utils.dip2px(16), Utils.dip2px(8));
            title.setText("请填写电话和地址");
            linearLayout.addView(title);

            final EditText phoneText = new EditText(v.getContext());
            final LinearLayout.LayoutParams editParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            editParams.setMargins(Utils.dip2px(16), 0, Utils.dip2px(16), 0);
            phoneText.setLayoutParams(editParams);
            phoneText.setHint("电话");
            linearLayout.addView(phoneText);

            final EditText addressText = new EditText(v.getContext());
            final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(Utils.dip2px(16), 0, Utils.dip2px(16), 0);
            addressText.setLayoutParams(layoutParams);
            addressText.setHint("地址");
            linearLayout.addView(addressText);

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
                public void onClick(final View v) {
                    String phoneNum = phoneText.getText().toString();
                    String address = addressText.getText().toString();
                    if (TextUtils.isEmpty(phoneNum) || TextUtils.isEmpty(address)) {
                        Utils.Toast("内容不能为空");
                        return;
                    }
                    DataModel.getInstance().saveAccount(new Account(phoneNum, address));
                    if (type == BuyType.BOOK) {

                        DataModel.getInstance().book(data.id, phoneText.getText().toString(), new ServiceResponse<ResponseStatus>() {
                            @Override
                            public void onNext(ResponseStatus responseStatus) {
                                super.onNext(responseStatus);
                                Utils.Toast("预订成功，请到指定地点自取");
                                alertDialog.dismiss();
                                book.setText("已预订");
                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                Utils.Toast("预定失败");
                                alertDialog.dismiss();
                            }
                        });
                    } else {
                        alertDialog.dismiss();
                        Intent intent = new Intent(v.getContext(), PayActivity.class);
                        intent.putExtra(CommunityServiceFragment.PAY_TEXT, data.name);
                        intent.putExtra(PAY_MONEY, data.price);
                        v.getContext().startActivity(intent);
                    }
                }
            });
        }

    }
}
