package com.example.yash.resourcify;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AboutFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);
        TextView title=(TextView)rootView.findViewById(R.id.textTitle);
        Typeface mytypeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/GrandHotel-Regular.ttf");
        title.setTypeface(mytypeface);
        TextView version=(TextView)rootView.findViewById(R.id.textVersion);
        Typeface typeface1=Typeface.createFromAsset(getActivity().getAssets(),"fonts/RobotoCondensed-Regular.ttf");
        version.setTypeface(typeface1);
        TextView textView1=(TextView)rootView.findViewById(R.id.textSub);
        TextView textView=(TextView)rootView.findViewById(R.id.textFor);
        textView.setTypeface(typeface1);
        textView1.setTypeface(typeface1);

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}