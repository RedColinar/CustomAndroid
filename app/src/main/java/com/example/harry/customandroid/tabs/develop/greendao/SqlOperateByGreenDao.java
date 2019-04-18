package com.example.harry.customandroid.tabs.develop.greendao;

import com.example.harry.customandroid.application.MyApplication;
import com.example.harry.customandroid.tabs.develop.greendao.entity.DaoMaster;
import com.example.harry.customandroid.tabs.develop.greendao.entity.DaoSession;

/**
 * @author panqiang
 * @version 1.0
 * @date 2018/7/22 19:30
 * @description
 */
public class SqlOperateByGreenDao implements ISqlOperate {

    private static DaoMaster mDaoMaster;
    private static DaoSession mDaoSession;

    public static SqlOperateByGreenDao getInstance() {
        return ProxyHolder.instance;
    }

    @Override
    public DaoSession getDaoSession() {
        return null;
    }

    private static class ProxyHolder {
        private static final SqlOperateByGreenDao instance = new SqlOperateByGreenDao();
    }

    private SqlOperateByGreenDao() {
        DBOpenHelper helper = new DBOpenHelper(MyApplication.getAppContext(), "pq.db", DaoMaster.SCHEMA_VERSION);
        mDaoMaster = new DaoMaster(helper.getWritableDatabase());
        mDaoSession  = mDaoMaster.newSession();
    }

    public static DaoSession getmDaoSession() {
        return mDaoSession;
    }
}
