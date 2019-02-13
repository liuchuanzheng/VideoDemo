package com.liuchuanzheng.videodemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * @author 刘传政
 * @date 2019/2/12 0012 9:57
 * QQ:1052374416
 * 电话:18501231486
 * 作用:
 * 注意事项:
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    //当前上下文对象
    Context context;
    //RecyclerView填充Item数据的List对象
    List<Doctor> dataList;
    private OnItemClickListener mItemClickListener;

    public MyAdapter(Context context, List<Doctor> dataList) {
        this.context = context;
        this.dataList = dataList;
    }
    public void setOnItemClickListener(OnItemClickListener itemClickListener){
        this.mItemClickListener = itemClickListener ;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //实例化得到Item布局文件的View对象
        View v = View.inflate(context, R.layout.item,null);
        //返回MyViewHolder的对象
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.textView.setText(dataList.get(i).getName());
        myViewHolder.btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mItemClickListener != null){
                    mItemClickListener.onItemClick(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(dataList == null){
            return 0;
        }else {
            return dataList.size();
        }

    }

    //继承RecyclerView.ViewHolder抽象类的自定义ViewHolder
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        Button btn_call;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_name);
            btn_call = itemView.findViewById(R.id.btn_call);
        }
    }
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
}
