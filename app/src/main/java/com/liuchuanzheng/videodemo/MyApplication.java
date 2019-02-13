package com.liuchuanzheng.videodemo;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

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
        String[] names = {"张三","李四","王五"};
        String[] ids = {"10000","10001","10002"};
        String[] tokens = {"mz/7mjC+eZzvL5qMB2hnRJPAyyJN3AEs5mavcOXqOeBr5T/5iQDkM///Q6kAbYhPBponaA5H3HO4RLozIfTtCg==",
                "yxi5+1bwqBcf2HI0ziLHuZPAyyJN3AEs5mavcOXqOeBr5T/5iQDkM3ta4uq8NlkZgveManeqVp9kFyck6C+z0w==",
                "fU5/MlveJHVAxmIFT16hzodK0HMFgbWeEmW7KchUgPd8K/sCSa57Wofg1/ThoKaxSh5iqqOlCxOFyGKUTrfBjA=="};
        List<Doctor> dataList = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            Doctor doctor = new Doctor();
            doctor.setName(names[i]);
            doctor.setId(ids[i]);
            doctor.setToken(tokens[i]);
            dataList.add(doctor);
        }
        currentDoctor = dataList.get(0);
        RongIM.init(this);
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
