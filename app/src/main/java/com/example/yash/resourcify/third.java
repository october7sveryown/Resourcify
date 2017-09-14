package com.example.yash.resourcify;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class third extends Fragment {
    ProgressDialog logact;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get the view from fragmenttab2.xml
        View view = inflater.inflate(R.layout.fragment_third, container, false);
        TextView tvenroll=(TextView)view.findViewById(R.id.enroll);
        Typeface tfenroll=Typeface.createFromAsset(getActivity().getAssets(),"fonts/Montserrat-Bold.otf");
        tvenroll.setTypeface(tfenroll);
        TextView tvdescenroll=(TextView)view.findViewById(R.id.descenroll);
        Button tvloginclick2=(Button) view.findViewById(R.id.loginclick2);
        Typeface tfdesc=Typeface.createFromAsset(getActivity().getAssets(),"fonts/Montserrat-Light.otf");
        tvdescenroll.setTypeface(tfdesc);
        Typeface tfloginclock2=Typeface.createFromAsset(getActivity().getAssets(),"fonts/Montserrat-SemiBold.otf");
        tvloginclick2.setTypeface(tfloginclock2);
        tvloginclick2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
         Intent i=new Intent(getActivity(),LogActivity.class);
        startActivity(i);
        getActivity().finish();

    }
});

        return view;
    }
}
