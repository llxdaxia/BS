package cn.lemon.bs.data;

import java.io.File;

import cn.lemon.bs.data.bean.Business;
import cn.lemon.bs.data.bean.Device;
import cn.lemon.bs.data.bean.Notice;
import cn.lemon.common.base.model.SuperModel;
import cn.lemon.common.net.SchedulersTransformer;
import cn.lemon.common.net.ServiceResponse;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by linlongxin on 2017.5.5.
 */

public class DataModel extends SuperModel {

    public static DataModel getInstance() {
        return getInstance(DataModel.class);
    }

    public void getPageDeviceList(int page, ServiceResponse<Device[]> response) {
        RetrofitModel.getNetService().getPageDeviceList(page)
                .compose(new SchedulersTransformer<Device[]>())
                .subscribe(response);
    }

    public void updateStatus(int id, int status, ServiceResponse<ResponseStatus> response) {
        RetrofitModel.getNetService().updateStatus(id, status)
                .compose(new SchedulersTransformer<ResponseStatus>())
                .subscribe(response);
    }

    public void addDevice(String name, String intro, int status, File image, ServiceResponse<ResponseStatus> response) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/type"), image);
        MultipartBody.Part part = MultipartBody.Part.createFormData("image", image.getName(), requestBody);
        RetrofitModel.getNetService().addDevice(name, intro, status, part)
                .compose(new SchedulersTransformer<ResponseStatus>())
                .subscribe(response);
    }

    public void getPageBusinessList(int page, ServiceResponse<Business[]> response){
        RetrofitModel.getNetService().getPageBusinessList(page)
                .compose(new SchedulersTransformer<Business[]>())
                .subscribe(response);
    }

    public void getPageCommunityServiceList(int page, ServiceResponse<Device[]> response) {
        RetrofitModel.getNetService().getCommunityServiceList(page)
                .compose(new SchedulersTransformer<Device[]>())
                .subscribe(response);
    }

    public void getPageNoticeList(int page, ServiceResponse<Notice[]> response) {
        RetrofitModel.getNetService().getNoticeList(page)
                .compose(new SchedulersTransformer<Notice[]>())
                .subscribe(response);
    }

    public void book(int id, String phoneNum, ServiceResponse<ResponseStatus> response) {
        RetrofitModel.getNetService().book(id, phoneNum)
                .compose(new SchedulersTransformer<ResponseStatus>())
                .subscribe(response);
    }
}
