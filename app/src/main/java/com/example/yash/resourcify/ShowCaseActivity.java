package com.example.yash.resourcify;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.pixelcan.inkpageindicator.InkPageIndicator;

public class ShowCaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_case);
        View decorView = getWindow().getDecorView();
// Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        TextView textView=(TextView)findViewById(R.id.Features);
        Typeface tf=Typeface.createFromAsset(getAssets(),"fonts/Montserrat-Regular.otf");
        textView.setTypeface(tf);
        ViewPager viewPager=(ViewPager)findViewById(R.id.pager);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        InkPageIndicator inkPageIndicator=(InkPageIndicator)findViewById(R.id.indicator);
        inkPageIndicator.setViewPager(viewPager);
    }

    @Override
    protected void onResume() {
        View decorView = getWindow().getDecorView();

        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        super.onResume();
    }
}
