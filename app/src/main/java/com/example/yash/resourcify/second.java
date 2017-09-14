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


public class second extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_second, container, false);
        TextView tvexplorepurchase=(TextView)view.findViewById(R.id.explorepurchase);
        Typeface tfexplorepurchase=Typeface.createFromAsset(getActivity().getAssets(),"fonts/Montserrat-Bold.otf");
        tvexplorepurchase.setTypeface(tfexplorepurchase);
        TextView tvdescpurchase=(TextView)view.findViewById(R.id.descpurchase);
       // TextView tvloginclick1=(TextView)view.findViewById(R.id.loginclick1);
        Typeface tfdescpurchase=Typeface.createFromAsset(getActivity().getAssets(),"fonts/Montserrat-Light.otf");
        tvdescpurchase.setTypeface(tfdescpurchase);
        Typeface tfloginclock1=Typeface.createFromAsset(getActivity().getAssets(),"fonts/Montserrat-SemiBold.otf");
       // tvloginclick1.setTypeface(tfloginclock1);
        return view;
    }
}
