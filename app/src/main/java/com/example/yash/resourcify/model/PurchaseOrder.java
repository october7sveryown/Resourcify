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
public class PurchaseOrder extends RealmObject{
    @PrimaryKey
    private int id;
    private float Purchase_order;
    private String date;
    private Date mydate;
    public void setPurchase_order(float Purchase_order){
        this.Purchase_order=Purchase_order;
    }
    public void setDate(String date){
        this.date=date;
    }
    public float getPurchase_order(){
        return Purchase_order;
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
    public PurchaseOrder() {
    }
    public Date getMydate(){
        return mydate;
    }
    public void setMydate(Date mydate){
        this.mydate=mydate;
    }
    public PurchaseOrder(int id,float Purchase_order,Date mydate,String date) {
       // try {
         //   DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            this.id = id;
            this.Purchase_order = Purchase_order;
            this.mydate = mydate;
            this.date = date;
       // }catch(ParseException e){e.printStackTrace();}
    }
}
