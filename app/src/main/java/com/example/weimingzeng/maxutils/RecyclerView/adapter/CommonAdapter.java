package com.example.weimingzeng.maxutils.RecyclerView.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weimingzeng.maxutils.RecyclerView.viewholder.CommonViewHolder;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 *
 * @author weiming.zeng
 * @date 2017/9/5
 */

public class CommonAdapter<T, K extends CommonViewHolder> extends RecyclerView.Adapter {
    private Context context;
    private List<T> model;
    private Class<K> clazz;
    private RecyclerView.LayoutManager layoutManager;
    private OnClickItemListener mListener;
    private View itemView;
    private LoadCallBack loadCallBack;

    @LayoutRes
    int itemResource;
    private boolean inflateParent = true;

    public CommonAdapter(Context context, List<T> model, @LayoutRes int itemResource, Class<K> clazz) {
        this.context = context;
        this.model = model;
        this.clazz = clazz;
        this.itemResource = itemResource;
    }

    public CommonAdapter(Context context, List<T> model, @LayoutRes int itemResource, Class<K> clazz, RecyclerView.LayoutManager layoutManager) {
        this.context = context;
        this.model = model;
        this.clazz = clazz;
        this.layoutManager = layoutManager;
        this.itemResource = itemResource;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        K viewHolder = null;
        final View itemView;
        if (inflateParent) {
            itemView = LayoutInflater.from(context).inflate(itemResource, parent, false);
        } else {
            itemView = LayoutInflater.from(context).inflate(itemResource, null);
        }
        this.itemView = itemView;
        if (mListener != null) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //暂时还没用到，先不实现
//                    mListener.onItemClick(CommonAdapter.this, itemView, get);
                }
            });
        }
        try {
            Constructor<K> constructor = clazz.getConstructor(Context.class, View.class);
            viewHolder = constructor.newInstance(context, itemView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (loadCallBack != null) {
            loadCallBack.initEnd();
        }
        ((K) holder).bindView(this, model.get(position));
    }


    @Override
    public int getItemCount() {
        if (model == null) {
            return 0;
        }
        return model.size();
    }

    public void refreshData(List<T> data) {
        model = data;
        notifyDataSetChanged();
    }

    public float getItemHeight() {
        //返回itemView的高度，用来动态计算recyclerView的高度
        //目前只适用于所有item都一样的情况
        if (itemView == null) {
            return 0;
        }
        return itemView.getHeight();
    }

    public View getItemViewByPostion(int position) {
        if (layoutManager == null) {
            return null;
        }
        return layoutManager.findViewByPosition(position);
    }

    public void setInflateParent(boolean bool) {
        this.inflateParent = bool;
    }

    public interface OnClickItemListener {
        void onItemClick(CommonAdapter adapter, View view, int position);
    }

    public void setListener(OnClickItemListener mListener) {
        this.mListener = mListener;
    }

    public void removeData(int position) {
        //  播放动画延迟，会导致有position出现-1的情况导致数组越界
        if (position < 0) {
            return;
        }
        model.remove(position);
        notifyItemRemoved(position);
    }

    public interface LoadCallBack {
        void initEnd();
    }

    public void setLoadCallBack(LoadCallBack callBack) {
        this.loadCallBack = callBack;
    }

    public List<T> getModel() {
        return this.model;
    }
}
