package com.tug.tugiloc.adapter;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tug.tugiloc.R;

import java.util.ArrayList;

public class ScanResultAdapter extends RecyclerView.Adapter<ScanResultAdapter.ScanResultViewHolder> {
    private ArrayList<ScanResult> mItems;

    public static class ScanResultViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTxt_ssid;
        public TextView mTxt_bssid;
        public TextView mTxt_rss;

        public ScanResultViewHolder(@NonNull View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.img_wifi);
            mTxt_ssid = itemView.findViewById(R.id.txt_SSID);
            mTxt_bssid = itemView.findViewById(R.id.txt_BSSID);
            mTxt_rss = itemView.findViewById(R.id.txt_RSS);
        }
    }

    public ScanResultAdapter(ArrayList<ScanResult> items){
        this.mItems = items;
    }

    public void setItems(ArrayList<ScanResult> items){
        this.mItems = items;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ScanResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_scanresult, parent, false);
        ScanResultViewHolder svh = new ScanResultViewHolder(v);
        return svh;
    }

    @Override
    public void onBindViewHolder(@NonNull ScanResultViewHolder holder, int position) {
        ScanResult cur = mItems.get(position);
        int irss = WifiManager.calculateSignalLevel(cur.level, 100);
        String rss_str = irss + "%";

        holder.mTxt_ssid.setText(cur.SSID);
        holder.mTxt_bssid.setText(cur.BSSID);
        holder.mTxt_rss.setText(rss_str);
    }

    @Override
    public int getItemCount() {
        if (mItems == null) return 0;

        return mItems.size();
    }
}
