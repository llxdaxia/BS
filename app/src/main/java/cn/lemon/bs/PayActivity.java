package cn.lemon.bs;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cn.alien95.util.Utils;
import cn.lemon.common.base.ToolbarActivity;

public class PayActivity extends ToolbarActivity {

    private TextView mMoneyNum;
    private Button mPay;
    private Handler mHandler;

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        useStatusPages(false);
        setContentView(R.layout.activity_pay);

        mHandler = new Handler();

        String text = getIntent().getStringExtra(CommunityServiceFragment.PAY_TEXT);
        int payMoney = getIntent().getIntExtra(BusinessAdapter.PAY_MONEY, -1);

        mMoneyNum = (TextView) $(R.id.money_num);
        mPay = (Button) $(R.id.pay);
        if (payMoney == -1) {
            mMoneyNum.setText("需缴纳" + text + String.format("%.2f", (Math.random() * 100)) + "元");
        } else {
            mMoneyNum.setText("需缴纳 " + text + " " + payMoney + "元");
        }

        mPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingDialog();
                vitualPay();
            }
        });
    }

    public void vitualPay() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dismissLoadingDialog();
                Utils.Toast("支付成功");
                mPay.setClickable(false);
                mMoneyNum.setText("支付完成");
            }
        }, 1500);
    }


}
