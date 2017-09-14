package com.example.yash.resourcify.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
/**
 * Created by Yash on 4/7/2016.
 */
public class SalesOrder extends RealmObject{
    @PrimaryKey
    private int id;
    private float Sales_order;
    private String date;
    private Date Currdate;
    public void setSales_order(float Sales_order){
        this.Sales_order=Sales_order;
    }
    public void setDate(String date){
        this.date=date;
    }
    public float getSales_order(){
        return Sales_order;
    }
     public void setid(int id){
        this.id=id;
    }
    public int getid(){
        return id;
    }
    public String getDate(){
        return date;
    }
    public SalesOrder() {
    }
    public Date getCurrdate(){
        return Currdate;
    }
    public void setCurrdate(Date mydate){
        this.Currdate=mydate;

    }
    public SalesOrder(int id,float Sales_order,Date Currdate,String date) {
        //try {
            this.id = id;
            this.Sales_order = Sales_order;
            this.Currdate = Currdate;
            this.date = date;
        //}catch(ParseException e){e.printStackTrace();}
    }
}
