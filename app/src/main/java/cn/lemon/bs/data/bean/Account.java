package cn.lemon.bs.data.bean;

/**
 * Created by linlongxin on 2017.5.24.
 */

public class Account {
    public int id;
    public String phoneNum;
    public String address;

    public Account(String phoneNum, String address) {
        this.phoneNum = phoneNum;
        this.address = address;
    }
}
