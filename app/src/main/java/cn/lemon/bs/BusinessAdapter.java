package cn.lemon.bs;

import android.app.Dialog;
import android.content.Context;
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
import cn.lemon.bs.data.bean.Business;
import cn.lemon.common.net.ServiceResponse;
import cn.lemon.view.adapter.BaseViewHolder;
import cn.lemon.view.adapter.RecyclerAdapter;

/**
 * Created by linlongxin on 2017.5.16.
 */

public class BusinessAdapter extends RecyclerAdapter<Business> {

    public BusinessAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder<Business> onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        return new BusinessViewHolder(parent);
    }

    class BusinessViewHolder extends BaseViewHolder<Business> {

        private HttpImageView image;
        private TextView name, intro, price, book;

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
                    showBuyDialog(v, data);
                }
            });
        }

        public void showBuyDialog(View v, final Business data) {

            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

            LinearLayout linearLayout = new LinearLayout(v.getContext());
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            final TextView title = new TextView(v.getContext());
            title.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
            title.setGravity(Gravity.CENTER);
            title.setTextColor(v.getContext().getResources().getColor(R.color.colorPrimary));
            title.setTextSize(18);
            title.setPadding(Utils.dip2px(16), Utils.dip2px(16), Utils.dip2px(16), Utils.dip2px(8));
            title.setText("请输入您的电话");
            linearLayout.addView(title);

            final EditText editText = new EditText(v.getContext());
            final LinearLayout.LayoutParams editParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            editParams.setMargins(Utils.dip2px(16), 0, Utils.dip2px(16), 0);
            editText.setLayoutParams(editParams);
            editText.setHint("电话");
            linearLayout.addView(editText);

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
                    if (TextUtils.isEmpty(editText.getText())) {
                        Utils.Toast("不能为空");
                        return;
                    }
                    DataModel.getInstance().book(data.id, editText.getText().toString(), new ServiceResponse<ResponseStatus>() {
                        @Override
                        public void onNext(ResponseStatus responseStatus) {
                            super.onNext(responseStatus);
                            Utils.Toast("预定成功");
                            alertDialog.dismiss();
                        }

                        @Override
                        public void onError(Throwable e) {
                            super.onError(e);
                            Utils.Toast("预定失败");
                            alertDialog.dismiss();
                        }
                    });


                }
            });
        }

    }
}
