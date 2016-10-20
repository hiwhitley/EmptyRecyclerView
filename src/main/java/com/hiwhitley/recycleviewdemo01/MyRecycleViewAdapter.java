package com.hiwhitley.recycleviewdemo01;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hiwhitley on 2016/4/21.
 */
public class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.ViewHolder> {

    public static final String TAG = "hiwhitley";
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<ItemData> mData;


    public MyRecycleViewAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
        mData = new ArrayList<>();
    }

    public void addData() {
        mData.add(new ItemData(R.drawable.img1, "CardView还是蛮好看的"));
        notifyItemInserted(mData.size());
    }

    public void deleteData() {
        if (mData.size() == 0) {
            Toast.makeText(mContext, "没有数据了", Toast.LENGTH_SHORT).show();
            return;
        }
        mData.remove(mData.size() - 1);
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(mData.get(position).getTitle());
        holder.mImageView.setBackgroundResource(mData.get(position).getImgResId());
    }

    @Override
    public int getItemCount() {
        return mData.size() == 0 ? 0 : mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;
        private ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv_item_content);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_item_icon);
        }
    }
}
