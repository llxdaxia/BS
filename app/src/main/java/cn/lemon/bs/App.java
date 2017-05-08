package cn.lemon.bs;

import android.app.Application;

import cn.alien95.resthttp.request.RestHttp;
import cn.alien95.util.Utils;
import cn.lemon.common.base.model.SuperModel;

/**
 * Created by linlongxin on 2017.5.6.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RestHttp.initialize(this);
        Utils.initialize(this);
        SuperModel.initialize(this);
        if(BuildConfig.DEBUG){
            Utils.setDebug(true,"debug_bs");
        }
    }
}
