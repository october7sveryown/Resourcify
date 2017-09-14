package com.example.yash.resourcify;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
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

public class BarChartSales extends Activity {
    public String Start, End;
    public Date StartDate, EndDate;
    public DateFormat dateFormat;
    public RealmConfiguration realmConfig;
    public Realm realm;
    public Typeface typeface;
    public int id;
    public String transactiondate;
    public float SALES;
public Date final1;
public BarChart barChart;
    public TextView imageView;
    public JSONArray jsonArray;
    public JSONObject jsonObject;
    String URL;
    ProgressDialog pDialog;
    CoordinatorLayout coordinatorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.content_bar_chart);
        coordinatorLayout=(CoordinatorLayout)findViewById(R.id.mycoordinatorLayout);
        URL ="http://192.168.12.52:34/MyTaskManager/v1/getSales";
        try{
        barChart = (BarChart) findViewById(R.id.chart);
imageView=(TextView)findViewById(R.id.mydownload);
        salesRequest(URL);
        Start = getIntent().getStringExtra("Start Date");
        End = getIntent().getStringExtra("End Date");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            StartDate = dateFormat.parse(Start);
            EndDate = dateFormat.parse(End);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        realm = Realm.getDefaultInstance();
               //setData();
        typeface=Typeface.createFromAsset(getAssets(),"fonts/RobotoCondensed-Italic.ttf");
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                barChart.saveToGallery("mpandroidchart-sales",View.DRAWING_CACHE_QUALITY_HIGH);
                Toast.makeText(getApplicationContext(), "Chart saved to gallery", Toast.LENGTH_SHORT).show();
            }
        });

    }
    /* volley request for sales order*/
    void salesRequest(String url){
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
                        SALES=tasks.getLong("sales_order");
                        final1=dateFormat.parse(transactiondate);
                        SalesOrder salesOrder=new SalesOrder(i,SALES,final1,transactiondate);
                        realm.copyToRealmOrUpdate(salesOrder);
                    }
                }
                catch(ParseException E){E.printStackTrace();}
       catch(JSONException E){E.printStackTrace();}
                 realm.commitTransaction();
                setRealmData();
                break;
            default:
                Snackbar snackbar2=Snackbar.make(coordinatorLayout,"Something went wrong.Try again.",Snackbar.LENGTH_LONG);
                snackbar2.show();
                break;
        }
    }
void setJsonData(String response){

}
    @Override
 protected void onResume() {
        super.onResume();
        setRealmData();
    }
    /*private void setRealmData(){
      realm.beginTransaction();

      // write some demo-data into the realm.io database
      Transactions t1 = new Transactions(0, 1000f, 2000f, "01-03-2016");
      realm.copyToRealmOrUpdate(t1);
      Transactions t2 = new Transactions(1, 2000f, 3000f, "02-03-2016");
      realm.copyToRealmOrUpdate(t2);
      Transactions t3 = new Transactions(2, 4000f, 5000f, "03-03-2016");
      realm.copyToRealmOrUpdate(t3);
      Transactions t4 = new Transactions(3, 12000f, 6000f, "04-03-2016");
      realm.copyToRealmOrUpdate(t4);
      Transactions t5 = new Transactions(4, 8000f, 7000f, "05-03-2016");
      realm.copyToRealmOrUpdate(t5);
      Transactions t6 = new Transactions(5, 9000f, 3000f, "06-03-2016");
      realm.copyToRealmOrUpdate(t6);
      Transactions t7 = new Transactions(6, 9700f, 3600f, "07-03-2016");
      realm.copyToRealmOrUpdate(t7);
      Transactions t8 = new Transactions(7, 5500f, 2300f, "08-03-2016");
      realm.copyToRealmOrUpdate(t8);
      Transactions t9 = new Transactions(8, 4300f, 12000f, "09-03-2016");
      realm.copyToRealmOrUpdate(t9);
      Transactions t10 = new Transactions(9, 1000f, 3560f, "10-03-2016");
      realm.copyToRealmOrUpdate(t10);
        realm.commitTransaction();

      // add data to the chart

  }*/
    private void setRealmData(){
try {
    RealmResults<SalesOrder> results = realm.where(SalesOrder.class).greaterThanOrEqualTo("Currdate",StartDate).lessThanOrEqualTo("Currdate",final1).findAll();
    RealmBarDataSet<SalesOrder> barDataSet = new RealmBarDataSet<SalesOrder>(results, "Sales_order", "id");
    barDataSet.setColor(ColorTemplate.rgb("#8e44ad"));
    barDataSet.setLabel("Sales Order");
    ArrayList<IBarDataSet> barDataSets = new ArrayList<IBarDataSet>();
    barDataSets.add(barDataSet);
    barDataSet.setValueFormatter(new MyCustomFormatter());
    RealmBarData barData = new RealmBarData(results, "date", barDataSets);
    barChart.setData(barData);
    barChart.invalidate();
    Legend legend = barChart.getLegend();
    legend.setForm(Legend.LegendForm.CIRCLE);
    legend.setFormSize(10f);
    legend.setTextSize(12f);
    legend.setTextColor(Color.rgb(52, 73, 94));
    legend.setXEntrySpace(5f);
    legend.setYEntrySpace(5f);
    XAxis xAxis = barChart.getXAxis();
    xAxis.setDrawGridLines(false);
    xAxis.setDrawAxisLine(true);
    xAxis.setTextColor(R.color.colorAccent);
    YAxis left = barChart.getAxisLeft();
    left.setDrawAxisLine(true);
    left.setDrawGridLines(true);
    //CustomMarkerView cv=new CustomMarkerView(this,R.layout.mycustommarkerview);
    //barChart.setMarkerView(cv);
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
}catch(Exception e){e.printStackTrace();}
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}

