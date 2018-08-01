package com.example.weimingzeng.maxutils.lab;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.weimingzeng.maxutils.R;

import java.util.List;

/**
 * Date: 2018/7/11
 * author:Weiming Max Zeng
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {
    private static final String TAG = "MyRecyclerAdapter";

    private List<String> mData;
    private Context mContext;
    private LayoutInflater inflater;

    public MyRecyclerAdapter(Context context, List<String> data) {
        this.mContext = context;
        this.mData = data;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onViewRecycled(MyViewHolder holder) {
        super.onViewRecycled(holder);
//        Log.d(TAG, "onViewRecycled: " + holder.tv.getText().toString() + ", position: " + holder.getAdapterPosition());
    }

    //填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
//        Log.d(TAG, "onBindViewHolder: 验证是否重用了");
//        Log.d(TAG, "onBindViewHolder: 重用了" + holder.tv.getTag());
//        Log.d(TAG, "onBindViewHolder: 放到了---" + mData.get(position));
        Log.d(TAG, "onBindViewHolder: 放到了---" + position);
        holder.tv.setText(mData.get(position));
        holder.tv.setTag(mData.get(position));
    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        Log.d(TAG, "onCreateViewHolder");
        View view = inflater.inflate(R.layout.item_layout, parent, false);
        return new MyViewHolder(view);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv;

        public MyViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.id_num);
        }
    }
}
