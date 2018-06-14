package com.example.weimingzeng.maxutils.RecyclerView.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Date: 2018/5/18
 * author:Weiming Max Zeng
 */
public class MultipleTypeAdapter<T> extends RecyclerView.Adapter {

    private final int EMPTY_VIEW = 1;
    private final int PROGRESS_VIEW = 2;
    private final int IMAGE_VIEW = 3;

    private Context mContext;
    private List<T> list;

    public MultipleTypeAdapter(Context context, List<T> list) {
        this.mContext = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * 根据数据类型返回type
     */
    @Override
    public int getItemViewType(int position) {
//        if(list.size() == 0){
//            return EMPTY_VIEW;
//        } else if(list.get(position) == null){
//            return PROGRESS_VIEW;
//        } else if(list.get(position).getType().equals(News.IMAGE_NEWS)){
//            return IMAGE_VIEW;
//        } else {
            return super.getItemViewType(position);
//        }
    }
}
