package cn.lemon.bs;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.IdRes;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.io.File;
import java.util.List;

import cn.alien95.util.ImageUtil;
import cn.alien95.util.Utils;
import cn.lemon.bs.data.DeviceModel;
import cn.lemon.bs.data.ResponseStatus;
import cn.lemon.common.base.fragment.SuperFragment;
import cn.lemon.common.net.ServiceResponse;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

import static android.app.Activity.RESULT_OK;


public class PublishFragment extends SuperFragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private ImageView mImage;
    private TextInputLayout mName, mIntro;
    private Button mPublish;
    private RadioGroup mCheckBox;
    private File mFile;
    public final int REQUEST_IMAGE_CODE = 111;
    private int status = 0;

    public PublishFragment() {
        super(R.layout.fragment_publish, false);
    }

    @Override
    public void onInitialView() {
        super.onInitialView();
        mImage = (ImageView) findViewById(R.id.upload);
        mName = (TextInputLayout) findViewById(R.id.name);
        mIntro = (TextInputLayout) findViewById(R.id.intro);
        mPublish = (Button) findViewById(R.id.publish);
        mCheckBox = (RadioGroup) findViewById(R.id.check_box);
        mImage.setOnClickListener(this);
        mPublish.setOnClickListener(this);
        mCheckBox.setOnCheckedChangeListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.upload:
                uploadImage();
                break;
            case R.id.publish:
                publish();
                break;
        }
    }

    public void uploadImage() {
        showDialog("请选择图片", null, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MultiImageSelector.create()
                        .single()
                        .start(PublishFragment.this, REQUEST_IMAGE_CODE);
                dismissDialog();
            }
        }, null);
    }

    public void publish() {
        String nameStr = mName.getEditText().getText().toString();
        String introStr = mIntro.getEditText().getText().toString();
        Utils.Log("nameStr : " + nameStr);
        Utils.Log("introStr : " + introStr);
        Utils.Log("status : " + status);
        if (TextUtils.isEmpty(nameStr) || TextUtils.isEmpty(introStr)) {
            Utils.Toast("信息不能为空");
            return;
        }
        if(mFile == null){
            Utils.Toast("请选择图片");
            return;
        }
        DeviceModel.getInstance().addDevice(nameStr,introStr,status,mFile,new ServiceResponse<ResponseStatus>(){
            @Override
            public void onNext(ResponseStatus responseStatus) {
                super.onNext(responseStatus);
                Utils.Toast("添加成功");
                clear();
            }
        });
    }

    public void clear(){
        mIntro.getEditText().setText("");
        mName.getEditText().setText("");
        mImage.setImageResource(R.drawable.ic_upload);
        mFile = null;
        status = 0;
        ((RadioButton)mCheckBox.getChildAt(0)).setChecked(true);
        mImage.setFocusable(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CODE && resultCode == RESULT_OK) {
            List<String> paths = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
            ImageUtil.compress(paths, 800, 800, new ImageUtil.ListCallback() {
                @Override
                public void callback(List<Bitmap> bitmaps) {
                    mImage.setImageBitmap(bitmaps.get(0));
                }
            }, new ImageUtil.ListPathCallback() {
                @Override
                public void callback(List<File> list) {
                    mFile = list.get(0);
                }
            });
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.normal:
                status = 0;
                break;
            case R.id.trouble:
                status = 1;
                break;
            case R.id.fixing:
                status = 2;
                break;
        }
    }
}
