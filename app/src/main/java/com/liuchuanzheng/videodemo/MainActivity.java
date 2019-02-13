package com.liuchuanzheng.videodemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.rong.callkit.RongCallKit;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Button btn_onToMore;
    Button btn_moreToMore;
    TextView tv_currentName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initRongYun();
    }

    private void initRongYun() {
        //`10000
//        String token = "mz/7mjC+eZzvL5qMB2hnRJPAyyJN3AEs5mavcOXqOeBr5T/5iQDkM///Q6kAbYhPBponaA5H3HO4RLozIfTtCg==";
        // 10001
//        String token = "yxi5+1bwqBcf2HI0ziLHuZPAyyJN3AEs5mavcOXqOeBr5T/5iQDkM3ta4uq8NlkZgveManeqVp9kFyck6C+z0w==";
        //50001
//        String token = "WqtVc0wEYUUcyxYiyFGVNIdK0HMFgbWeEmW7KchUgPcOPOik1jvb2aJ2f8jSPRKxhZrNCtCp9+aXMYFir8DiPQ==";
        //50002
//        String token = "PuLyVD9d+NImbUY7ML9ZModK0HMFgbWeEmW7KchUgPcOPOik1jvb2WG8T5PGkWx7hW4qVif6HrqFyGKUTrfBjA==";

        connect(MyApplication.currentDoctor.getToken());
    }



    private void initView() {
        recyclerView = findViewById(R.id.rv);
        btn_onToMore = findViewById(R.id.btn_onToMore);
        btn_moreToMore = findViewById(R.id.btn_moreToMore);
        tv_currentName =  findViewById(R.id.tv_currentName);
        tv_currentName.setText("当前医生:"+MyApplication.currentDoctor.getName());
        //初始化数据
        String[] names = {"张三","李四","王五"};
        String[] ids = {"10000","10001","10002"};
        String[] tokens = {"mz/7mjC+eZzvL5qMB2hnRJPAyyJN3AEs5mavcOXqOeBr5T/5iQDkM///Q6kAbYhPBponaA5H3HO4RLozIfTtCg==",
                "yxi5+1bwqBcf2HI0ziLHuZPAyyJN3AEs5mavcOXqOeBr5T/5iQDkM3ta4uq8NlkZgveManeqVp9kFyck6C+z0w==",
                "fU5/MlveJHVAxmIFT16hzodK0HMFgbWeEmW7KchUgPd8K/sCSa57Wofg1/ThoKaxSh5iqqOlCxOFyGKUTrfBjA=="};
        final List<Doctor> dataList = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            Doctor doctor = new Doctor();
            doctor.setName(names[i%names.length]);
            doctor.setId(ids[i%names.length]);
            doctor.setToken(tokens[i%names.length]);
            dataList.add(doctor);
        }
        Iterator<Doctor> iterator = dataList.iterator();
        while (iterator.hasNext()){
            Doctor doctor = iterator.next();
            if (doctor.getName().equals(MyApplication.currentDoctor.getName())){
                iterator.remove();
            }
        }
        //设置LayoutManager为LinearLayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        //设置Adapter
        MyAdapter myAdapter = new MyAdapter(this, dataList);
        recyclerView.setAdapter(myAdapter);
        myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                /**
                 * 发起单人通话。
                 *
                 * @param context   上下文
                 * @param targetId  会话 id
                 * @param mediaType 会话媒体类型
                 */
                RongCallKit.startSingleCall(MainActivity.this, dataList.get(position).getId(), RongCallKit.CallMediaType.CALL_MEDIA_TYPE_VIDEO);

            }
        });
        onToMore();
        moreToMore();
    }

    private void moreToMore() {
        //多人视频
        btn_moreToMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> userIds = new ArrayList<>();
                userIds.add("10001");
                userIds.add("10002");
                RongCallKit.startMultiCall(MainActivity.this,userIds,
                        RongCallKit.CallMediaType.CALL_MEDIA_TYPE_VIDEO);
            }
        });

    }

    private void onToMore() {
        //视频教学
    }

    /**
     * <p>连接服务器，在整个应用程序全局，只需要调用一次，需在 {@link } 之后调用。</p>
     * <p>如果调用此接口遇到连接失败，SDK 会自动启动重连机制进行最多10次重连，分别是1, 2, 4, 8, 16, 32, 64, 128, 256, 512秒后。
     * 在这之后如果仍没有连接成功，还会在当检测到设备网络状态变化时再次进行重连。</p>
     *
     * @param token    从服务端获取的用户身份令牌（Token）。
     * @return RongIM  客户端核心类的实例。
     */
    private void connect(String token) {

        if (getApplicationInfo().packageName.equals(MyApplication.getCurProcessName(getApplicationContext()))) {

            RongIM.connect(token, new RongIMClient.ConnectCallback() {
                /**
                 * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
                 *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
                 */
                @Override
                public void onTokenIncorrect() {
                    Log.i("liuchuanzheng", "--onTokenIncorrect" );
                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token 对应的用户 id
                 */
                @Override
                public void onSuccess(String userid) {
                    Log.i("liuchuanzheng", "--onSuccess" + userid);
                }
                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    Log.i("liuchuanzheng", "--onError" + errorCode);
                }
            });
        }
    }
}
