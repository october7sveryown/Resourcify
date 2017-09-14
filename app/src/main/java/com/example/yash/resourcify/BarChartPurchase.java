package com.example.yash.resourcify;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.yash.resourcify.model.PurchaseOrder;
import com.example.yash.resourcify.model.SalesOrder;
import com.example.yash.resourcify.model.Transactions;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.realm.implementation.RealmBarData;
import com.github.mikephil.charting.data.realm.implementation.RealmBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class BarChartPurchase extends Activity {
    public String Start, End,URL;
    public Date StartDate, EndDate;
    public DateFormat dateFormat;
    public RealmConfiguration realmConfig;
    public Realm realm;
    public Typeface typeface;
    public int count,id;
    public BarChart barChart;
    public CoordinatorLayout coordinatorLayout;
public TextView imageView;
    public JSONArray jsonArray;
    public JSONObject jsonObject;
    public String transactiondate;
    public Date final1;
    public float Purchase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.content_bar_chart);
        barChart = (BarChart) findViewById(R.id.chart);
        Start = getIntent().getStringExtra("Start Date");
        End = getIntent().getStringExtra("End Date");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        imageView=(TextView)findViewById(R.id.mydownload);
        coordinatorLayout=(CoordinatorLayout)findViewById(R.id.mycoordinatorLayout);
        URL ="http://192.168.12.52:34/MyTaskManager/v1/getPurchase";
        try {
            StartDate = dateFormat.parse(Start);
            EndDate = dateFormat.parse(End);

        } catch (ParseException e) {
            e.printStackTrace();
        }

purchaseRequest(URL);
        realm = Realm.getDefaultInstance();
        //setRealmData();
        setData();
        typeface=Typeface.createFromAsset(getAssets(),"fonts/RobotoCondensed-Italic.ttf");
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                barChart.saveToGallery("mpandroidchart-purchase",View.DRAWING_CACHE_QUALITY_HIGH);
                Toast.makeText(getApplicationContext(),"Chart saved to gallery",Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        setData();
    }
    void purchaseRequest(String url){
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        verify(response);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        //Snackbar mysnackbar=Snackbar.make(coordinatorLayout,error.toString(),Snackbar.LENGTH_LONG);
                        //mysnackbar.show();
                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("start", Start);
                params.put("end", End);
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map header=new HashMap();
                header.put("Content-type","application/x-www-form-urlencoded");
                return header;
            }

        };
        postRequest.setShouldCache(false);
        ApplicationController.getInstance().getRequestQueue().getCache().remove(url);
        ApplicationController.getInstance().getRequestQueue().getCache().invalidate(url,true);
        ApplicationController.getInstance().getRequestQueue().add(postRequest);

    }
    void verify(String response) {
        String response1=response.substring(9,13);
        switch(response1) {
            case "true":
                Snackbar snackbar=Snackbar.make(coordinatorLayout,"Something went wrong.Try again.",Snackbar.LENGTH_LONG);
                snackbar.show();
                break;
            case "fals":
                //Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                //setJsonData(response);
                try {
                    realm.beginTransaction();
                    String jsonString = response.substring(15);
                    jsonObject = new JSONObject(response);
                    jsonArray = jsonObject.getJSONArray("tasks");
                    int n=jsonArray.length();
                    for (int i = 0; i < n; i++) {
                        final JSONObject tasks = jsonArray.getJSONObject(i);
                        id=tasks.getInt("id");
                        transactiondate=tasks.getString("Date");
                        Purchase=tasks.getLong("purchase_order");
                        final1=dateFormat.parse(transactiondate);
                        PurchaseOrder purchaseOrder=new PurchaseOrder(i,Purchase,final1,transactiondate);
                        realm.copyToRealmOrUpdate(purchaseOrder);
                    }
                }
                catch(ParseException E){E.printStackTrace();}
                catch(JSONException E){E.printStackTrace();}
                realm.commitTransaction();
                setData();
                break;
            default:
                Snackbar snackbar2=Snackbar.make(coordinatorLayout,"Something went wrong.Try again.",Snackbar.LENGTH_LONG);
                snackbar2.show();
                break;
        }
    }


    private void setData(){

        RealmResults<PurchaseOrder> results = realm.where(PurchaseOrder.class).between("mydate", StartDate,EndDate).findAll();
        RealmBarDataSet<PurchaseOrder> barDataSet = new RealmBarDataSet<PurchaseOrder>(results, "Purchase_order", "id");
        //RealmBarDataSet<Transactions> barDataSet1 = new RealmBarDataSet<Transactions>(results, "Sales_order", "id");
        //barDataSet1.setColor(ColorTemplate.rgb("#2980b9"));
        //barDataSet1.setLabel("Sales Order");
        barDataSet.setColor(ColorTemplate.rgb("#d35400"));
        barDataSet.setLabel("Purchase Order");
        barDataSet.setValueFormatter(new MyCustomFormatter());
        ArrayList<IBarDataSet> barDataSets = new ArrayList<IBarDataSet>();
        barDataSets.add(barDataSet);
        //barDataSets.add(barDataSet1);
        RealmBarData barData = new RealmBarData(results, "date", barDataSets);
        barChart.setData(barData);
        barChart.invalidate();
        Legend legend=barChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setFormSize(10f);
        legend.setTextSize(12f);
        legend.setTextColor(Color.rgb(52, 73, 94));
        legend.setXEntrySpace(5f);
        legend.setYEntrySpace(5f);
        XAxis xAxis=barChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);
        xAxis.setTextColor(R.color.colorAccent);
        YAxis left=barChart.getAxisLeft();
        left.setDrawAxisLine(true);
        left.setDrawGridLines(true);
        barChart.getAxisRight().setEnabled(false);

        barChart.setMaxVisibleValueCount(7);
  barChart.setDescription("Hitachi Hirel Power Electronics Pvt. Ltd");
        barChart.setDescriptionPosition(720f, 425f);
        barChart.setDescriptionTextSize(12f);
        barChart.setDescriptionColor(Color.rgb(52, 73, 94));
        barChart.setDescriptionTypeface(typeface);

        barChart.setNoDataTextDescription("No chart data available to display.");
        //barChart.setDescriptionPosition(20, 35);
        // barChart.setDescription("SALES ORDER");
        barChart.animateY(1400, Easing.EasingOption.EaseInOutQuart);
        barChart.notifyDataSetChanged();
        barChart.invalidate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

}

