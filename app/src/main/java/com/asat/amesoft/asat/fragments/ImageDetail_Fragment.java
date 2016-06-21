package com.asat.amesoft.asat.fragments;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.asat.amesoft.asat.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageDetail_Fragment extends Fragment {

    String imagen;

    public ImageDetail_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imagen=getArguments().getString("imagen");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_image_detail_, container, false);
        ImageView image = (ImageView) view.findViewById(R.id.image_detail);
        image.setImageBitmap(decodeImage(this.imagen));
        RelativeLayout x = (RelativeLayout) view.findViewById(R.id.image_layout);
        Drawable d = new ColorDrawable(getResources().getColor(R.color.colorAccent));
        d.setAlpha(200);
        x.setBackground(d);
        return view;
    }

    private Bitmap decodeImage(String encoded){
        byte[] decodedImage = Base64.decode(encoded, Base64.CRLF);
        return BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);
    }

}
