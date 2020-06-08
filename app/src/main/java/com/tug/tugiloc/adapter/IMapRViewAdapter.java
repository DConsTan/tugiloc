package com.tug.tugiloc.adapter;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.tug.tugiloc.R;
import com.tug.tugiloc.type.IndoorMap;

import java.util.ArrayList;

public class IMapRViewAdapter extends RecyclerView.Adapter<IMapRViewAdapter.IMapViewHolder>{

    private ArrayList<IndoorMap> mIndoorMaps = new ArrayList<>();

    @NonNull
    @Override
    public IMapViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_imap, parent, false);
        IMapViewHolder holder = new IMapViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull IMapViewHolder holder, int position) {
        final IndoorMap item = mIndoorMaps.get(position);

        holder.aTitle.setText(item.getMapName());
        holder.aImg.setImageBitmap(item.getImage());
/*        holder.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton group, boolean isChecked) {
                item.setActive(isChecked);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return mIndoorMaps.size();
    }

    public class IMapViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout    aParentLayout;
        ImageView           aImg;
        TextView            aTitle;
        Switch              aSwitch;

        public IMapViewHolder(@NonNull View itemView) {
            super(itemView);

            aParentLayout = itemView.findViewById(R.id.item_imap_parent);
            aImg =          itemView.findViewById(R.id.item_imap_img);
            aTitle =        itemView.findViewById(R.id.item_imap_title);
            aSwitch =       itemView.findViewById(R.id.item_imap_switch);
        }
    }
}
