package com.example.harry.customandroid.tabs.develop.service;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.widget.Button;

import com.example.harry.customandroid.R;
import com.example.harry.customandroid.base.BaseActivity;

import butterknife.BindView;

import static com.example.harry.customandroid.tabs.develop.service.UploadService.PARAM_TASK_CLASS;

/**
 * created by panqiang at 18-12-27
 */
public class ServiceDemoActivity extends BaseActivity {

    @BindView(R.id.start_service)
    Button btStartService;
    @BindView(R.id.stop_service)
    Button btStopService;
    @BindView(R.id.un_bind_service)
    Button btUnBindService;
    @BindView(R.id.re_bind_service)
    Button btReBindService;

    @Override
    public int getLayoutId() {
        return R.layout.activity_service_usage;
    }

    @Override
    public int getTitleId() {
        return R.string.service_usage;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(getApplicationContext(), UploadService.class);
        intent.putExtra(PARAM_TASK_CLASS, UploadTask.class.getName());

        btStartService.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent);
            } else {
                startService(intent);
            }
        });
    }
}
