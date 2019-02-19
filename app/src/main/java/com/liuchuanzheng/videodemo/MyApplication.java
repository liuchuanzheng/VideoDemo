package com.liuchuanzheng.videodemo;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;
import java.util.List;

import cn.rongcloud.rtc.RongRTCEngine;
import cn.rongcloud.rtc.util.Utils;
import io.rong.imkit.RongIM;

/**
 * @author 刘传政
 * @date 2019/2/12 0012 13:51
 * QQ:1052374416
 * 电话:18501231486
 * 作用:
 * 注意事项:
 */
public class MyApplication extends Application {
    public static Doctor currentDoctor;
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        // 内测时设置为true ， 发布时修改为false
        CrashReport.initCrashReport(getApplicationContext(), "ef48d6a01a", true);


        String[] names = {"张三","李四","王五","50002"};
        String[] ids = {"10000","10001","10002","50002"};
        String[] tokens = {"mz/7mjC+eZzvL5qMB2hnRJPAyyJN3AEs5mavcOXqOeBr5T/5iQDkM///Q6kAbYhPBponaA5H3HO4RLozIfTtCg==",
                "yxi5+1bwqBcf2HI0ziLHuZPAyyJN3AEs5mavcOXqOeBr5T/5iQDkM3ta4uq8NlkZgveManeqVp9kFyck6C+z0w==",
                "fU5/MlveJHVAxmIFT16hzodK0HMFgbWeEmW7KchUgPd8K/sCSa57Wofg1/ThoKaxSh5iqqOlCxOFyGKUTrfBjA==",
                "PuLyVD9d+NImbUY7ML9ZModK0HMFgbWeEmW7KchUgPcOPOik1jvb2WG8T5PGkWx7hW4qVif6HrqFyGKUTrfBjA=="};
        List<Doctor> dataList = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            Doctor doctor = new Doctor();
            doctor.setName(names[i]);
            doctor.setId(ids[i]);
            doctor.setToken(tokens[i]);
            dataList.add(doctor);
        }
        currentDoctor = dataList.get(0);
        //初始化视频呼叫SDK
        RongIM.init(this);
        //初始化视频会议模式SDK
        String cmpServer = "rtccmp.ronghub.com:80";
        RongRTCEngine.init(getApplicationContext(),cmpServer);
    }

    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

}
