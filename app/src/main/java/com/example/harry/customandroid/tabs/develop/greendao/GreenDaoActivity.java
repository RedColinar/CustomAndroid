package com.example.harry.customandroid.tabs.develop.greendao;

import com.example.harry.customandroid.application.MyApplication;
import com.example.harry.customandroid.R;
import com.example.harry.customandroid.base.BaseActivity;
import com.example.harry.customandroid.tabs.develop.greendao.entity.DaoSession;
import com.example.harry.customandroid.tabs.develop.greendao.entity.UserEntity;
import com.example.harry.customandroid.tabs.develop.greendao.entity.UserEntityDao;

import java.util.UUID;

/**
 * CustomAndroid
 * Created by Harry on 2018/7/21.
 */

public class GreenDaoActivity extends BaseActivity {
    DaoSession daoSession = MyApplication.getDaoSession();

    @Override
    public int getLayoutId() {
        return R.layout.greendao_activity;
    }

    @Override
    public int getTitleId() {
        return R.string.greendao_title;
    }

    @Override
    protected void onStart() {
        super.onStart();
        for (int i = 0; i < 10; i++) {
            UserEntity entity = new UserEntity();
            entity.setMarried(i % 2 == 0);
            entity.setName("pq: " + i);
            entity.setPkUser(UUID.randomUUID());
            daoSession.insertOrReplace(entity);

            daoSession.queryBuilder(UserEntity.class)
                    .where(UserEntityDao.Properties.Name.eq("pq: 2"))
                    .list();
        }
    }
}
