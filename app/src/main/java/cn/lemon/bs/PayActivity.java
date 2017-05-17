package cn.lemon.bs;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        useStatusPages(false);
        setContentView(R.layout.activity_pay);

        mHandler = new Handler();

        String text = getIntent().getStringExtra(CommunityServiceFragment.PAY_TEXT);

        mMoneyNum = (TextView) $(R.id.money_num);
        mPay = (Button) $(R.id.pay);

        mMoneyNum.setText("需缴纳" + text + String.format("%.2f", (Math.random() * 100)) + "元");

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
