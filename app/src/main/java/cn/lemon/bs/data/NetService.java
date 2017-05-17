package cn.lemon.bs.data;

import cn.lemon.bs.data.bean.Business;
import cn.lemon.bs.data.bean.Device;
import cn.lemon.bs.data.bean.Notice;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by linlongxin on 2017.5.5.
 */

public interface NetService {

    @POST("deviceList.php")
    @FormUrlEncoded
    Observable<Device[]> getPageDeviceList(@Field("page") int page);

    @POST("updateStatus.php")
    @FormUrlEncoded
    Observable<ResponseStatus> updateStatus(@Field("id") int id, @Field("status") int status);

    @Multipart
    @POST("addDevice.php")
    Observable<ResponseStatus> addDevice(@Part("name") String name, @Part("intro") String intro, @Part("status") int status, @Part MultipartBody.Part image);

    @POST("businessList.php")
    @FormUrlEncoded
    Observable<Business[]> getPageBusinessList(@Field("page") int page);

    @POST("communityServiceList.php")
    @FormUrlEncoded
    Observable<Device[]> getCommunityServiceList(@Field("page") int page);

    @POST("noticeList.php")
    @FormUrlEncoded
    Observable<Notice[]> getNoticeList(@Field("page") int page);

    @POST("book.php")
    @FormUrlEncoded
    Observable<ResponseStatus> book(@Field("id") int id,@Field("phoneNum") String phoneNum);
}
