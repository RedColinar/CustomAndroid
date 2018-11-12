package com.example.harry.customandroid.tabs.develop.changeDeskIcon;

import android.content.ComponentName;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.view.View;

import com.example.harry.customandroid.R;
import com.example.harry.customandroid.base.BaseActivity;

import butterknife.OnClick;

/**
 * created by panqiang at 18-11-12
 */
public class ChangeDeskIconActivity extends BaseActivity {

    private static final String ACTIVITY_ALIAS = "com.example.harry.customandroid.IconChangedActivity";
    private static final String ACTIVITY_NORMAL = "com.example.harry.customandroid.IconNormalActivity";

    @Override
    public int getLayoutId() {
        return R.layout.activity_change_desk_icon;
    }

    @Override
    public int getTitleId() {
        return R.string.change_desk_icon;
    }

    @OnClick({
            R.id.btn_change_desk_icon,
            R.id.btn_desk_icon
    })
    public void changeDeskIcon(View view) {
       boolean useAliasActivity = view.getId() == R.id.btn_change_desk_icon;
        // 关闭一个组件， 开启另一个组件
        PackageManager pm = getPackageManager();
        pm.setComponentEnabledSetting(new ComponentName(this, ACTIVITY_NORMAL),
                useAliasActivity ? PackageManager.COMPONENT_ENABLED_STATE_DISABLED : PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

        pm.setComponentEnabledSetting(new ComponentName(this, ACTIVITY_ALIAS),
                useAliasActivity ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED : PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }
}
