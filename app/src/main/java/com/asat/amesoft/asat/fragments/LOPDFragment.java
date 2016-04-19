package com.asat.amesoft.asat.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.asat.amesoft.asat.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LOPDFragment extends Fragment {


    private boolean accept;
    public LOPDFragment() {
        accept=false;
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments().getBoolean("accept")){

            this.accept=getArguments().getBoolean("accept");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lopd, container, false);
        if(this.accept){
            Button accept = (Button) view.findViewById(R.id.lopd_cancel);
            CheckBox checkBox = (CheckBox) view.findViewById(R.id.lopd_checkbox);
            accept.setVisibility(View.VISIBLE);
            checkBox.setVisibility(View.VISIBLE);

        }

        return view;
    }

}
