package com.example.harry.customandroid.tabs.develop.greendao;

import android.content.Context;

import com.example.harry.customandroid.application.MyApplication;
import com.example.harry.customandroid.tabs.develop.greendao.entity.DaoSession;

/**
 * @author panqiang
 * @version 1.0
 * @date 2018/7/22 19:24
 * @description
 */
public class DbManager {
    private Context context;
    private ISqlOperate sqlOperate;

    public DbManager() {
        context = MyApplication.getAppContext();
        sqlOperate = SqlOperateByGreenDao.getInstance();
    }

    public DaoSession getDaoSession() {
        return sqlOperate.getDaoSession();
    }
}
