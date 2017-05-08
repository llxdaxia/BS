package cn.lemon.bs.data;

import java.io.File;

import cn.lemon.common.base.model.SuperModel;
import cn.lemon.common.net.SchedulersTransformer;
import cn.lemon.common.net.ServiceResponse;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by linlongxin on 2017.5.5.
 */

public class DeviceModel extends SuperModel {

    public static DeviceModel getInstance() {
        return getInstance(DeviceModel.class);
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
}
