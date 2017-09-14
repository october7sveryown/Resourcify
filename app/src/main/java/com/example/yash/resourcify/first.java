package com.example.yash.resourcify;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class first extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get the view from fragmenttab2.xml
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        TextView tvexplore=(TextView)view.findViewById(R.id.exploresales);
        Typeface tfexplore=Typeface.createFromAsset(getActivity().getAssets(),"fonts/Montserrat-Bold.otf");
        tvexplore.setTypeface(tfexplore);
        TextView tvdesc=(TextView)view.findViewById(R.id.desc);
        //TextView tvloginclick=(TextView)view.findViewById(R.id.loginclick);
        Typeface tfdesc=Typeface.createFromAsset(getActivity().getAssets(),"fonts/Montserrat-Light.otf");
        tvdesc.setTypeface(tfdesc);
        Typeface tfloginclock=Typeface.createFromAsset(getActivity().getAssets(),"fonts/Montserrat-SemiBold.otf");
        //tvloginclick.setTypeface(tfloginclock);


        return view;
    }
}
