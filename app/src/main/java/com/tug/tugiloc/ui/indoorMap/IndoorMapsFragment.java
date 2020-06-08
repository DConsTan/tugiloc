package com.tug.tugiloc.ui.indoorMap;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tug.tugiloc.R;
import com.tug.tugiloc.run.IndoorMapManager;
import com.tug.tugiloc.type.IndoorMap;

import java.util.ArrayList;

public class IndoorMapsFragment extends Fragment {
    private IndoorMapsViewModel mViewModel;
    private RecyclerView rv;
    private ArrayList<IndoorMap> indoorMaps = null;
    private IndoorMapManager imm = null;
    private final String mapDirPath = "/storage/D340-1035/TUGILoc/maps";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_indoormaps, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(IndoorMapsViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        Context ctx = getContext();
        //imm = new IndoorMapManager(ctx, mapDirPath);
        //Log.d("IndoorMapsFragment","onViewCreated ...");
        //imm.saveMap("inffeldgasse16", IndoorMap.getSampleMap());

        rv = (RecyclerView) getActivity().findViewById(R.id.storedIMaps_view);

    }
}
