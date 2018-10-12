package com.example.harry.customandroid.tabs.develop.ping;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.harry.customandroid.R;
import com.example.harry.customandroid.base.BaseActivity;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Locale;

import butterknife.BindView;

/**
 * created by panqiang at 18-10-11
 */
public class PingActivity extends BaseActivity {

    final String address = "www.baidu.com";

    @BindView(R.id.start)
    Button btStart;
    @BindView(R.id.text)
    TextView textView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    int network;
    int ping;
    int pingByPacketLoss;

    private Consume consume;
    private interface Consume {
        void accept(long time);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_ping;
    }

    @Override
    public int getTitleId() {
        return R.string.usage_of_ping;
    }

    @Override
    protected void onStart() {
        super.onStart();
        btStart.setOnClickListener(v -> startTest());
        consume = (long time) -> {
            textView.setText(String.format(Locale.CHINA,
                    "checkNetValidated 网络连接失败:%d次\n" +
                            "ping 网络连接失败:%d次\n" +
                            "pingByPacketLoss 网络连接失败:%d次" +
                            "程序运行时间：%dms",
                    network, ping, pingByPacketLoss, time));
            progressBar.setVisibility(View.GONE);
        };
    }

    private void startTest() {
        progressBar.setVisibility(View.VISIBLE);
        network = 0;
        ping = 0;
        pingByPacketLoss = 0;
        new Thread(() -> {
            long start = System.currentTimeMillis();
            for (int i = 0; i < 10; i++) {
                if (!checkNetValidated()) {
                    network++;
                }
            }
            long s1 = System.currentTimeMillis();
            Log.d(TAG, "checkNetValidated: " + (s1 - start) + "ms");

            for (int i = 0; i < 10; i++) {
                if (!ping()) {
                    ping++;
                }
            }
            long s2 = System.currentTimeMillis();
            Log.d(TAG, "ping: " + (s2 - s1) + "ms");

            for (int i = 0; i < 10; i++) {
                if (!pingByPacketLoss()) {
                    pingByPacketLoss++;
                }
            }
            long end = System.currentTimeMillis();
            Log.d(TAG, "pingByPacketLoss: " + (end - s2) + "ms");

            runOnUiThread(() -> consume.accept(end - start));
        }).start();
    }

    private boolean checkNetValidated() {
        final ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            Network network = connectivityManager.getActiveNetwork();
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);

            return capabilities != null
                    && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
        } else {
            return false;
        }
    }

    private boolean ping() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process p = runtime.exec("ping -c 2 -i 0.5 -w 2" + address);
            int ret = p.waitFor();
            return ret == 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean pingByPacketLoss() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process p = runtime.exec("ping -c 2 -i 0.5 -w 2" + address);
            InputStreamReader r = new InputStreamReader(p.getInputStream());
            LineNumberReader returnData = new LineNumberReader(r);

            StringBuilder returnMsg = new StringBuilder();
            String line;
            while ((line = returnData.readLine()) != null) {
                returnMsg.append(line);
                returnMsg.append("\n");
            }

            Log.d(TAG, "startTest: " + returnMsg);

            returnData.close();
            r.close();
            return !returnMsg.toString().contains("100% loss");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
