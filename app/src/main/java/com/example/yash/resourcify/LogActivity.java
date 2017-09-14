package com.example.yash.resourcify;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.appindexing.Action;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LogActivity extends Activity {
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public ProgressDialog pDialog,pDialog1;
    public EditText txtPassword;
    public EditText txtUserName;
    public String uName,pWord;
    public TextView t1,forgotpass;
    public String url,url1,url2;
    public JSONObject jsonObject;
    public CoordinatorLayout coordinatorLayout;
    SessionManagement sessionManagement;
    MyAlertDialog myAlertDialog;
    Animation animationFadein;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        sessionManagement = new SessionManagement(getApplicationContext());
        View decorView = getWindow().getDecorView();
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.mycoordinatorLayout);
        forgotpass = (TextView) findViewById(R.id.forgot);
        t1 = (TextView) findViewById(R.id.textView4);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/GrandHotel-Regular.ttf");
        t1.setTypeface(typeface);
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/RobotoCondensed-Italic.ttf");
        forgotpass.setTypeface(type1);
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        txtUserName = (EditText) findViewById(R.id.editText1);
        Typeface typeface1 = Typeface.createFromAsset(getAssets(), "fonts/RobotoCondensed-Regular.ttf");
        txtUserName.setTypeface(typeface1);
            forgotpass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (txtUserName.getText().toString().trim().length() > 0) {

                        new AlertDialog.Builder(LogActivity.this)
                                .setMessage("Email with temporary password has been sent to your email address registered with this " + txtUserName.getText().toString())
                                .setPositiveButton("Close", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface arg0, int arg1) {
                                        arg0.dismiss();
                                        url1 = "http://192.168.12.52:34/MyTaskManager/v1/getEmail";
                                        getUsername(url1);
                                        //Volley request for email sending

                                    }
                                }).create().show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Enter username", Toast.LENGTH_SHORT).show();
                    }

                }
                });

        txtPassword = (EditText) findViewById(R.id.editText2);
        txtPassword.setTypeface(typeface1);
        uName = txtUserName.getText().toString();
        pWord = txtPassword.getText().toString();
        Button btnLogin = (Button) findViewById(R.id.buttonLog);
        btnLogin.setTypeface(typeface1);
        txtUserName.requestFocus();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = "http://192.168.12.52:34/MyTaskManager/v1/login";
                checkParams();
                //MyResponse(url);
            }
        });
    }
    void checkParams(){
        if(txtUserName.getText().toString().trim().length()> 0 && txtPassword.getText().toString().trim().length()>0){
            MyResponse(url);
            if(Build.VERSION.SDK_INT > 20) {
                pDialog = new ProgressDialog(LogActivity.this, R.style.MyDialog);
                pDialog.setMessage("Authenticating..");
                pDialog.show();
            }
            else{
                pDialog = new ProgressDialog(LogActivity.this, R.style.MyDialog1);
                pDialog.setMessage("Authenticating..");
                pDialog.show();
            }
        }
        else{
            //Toast.makeText(getApplicationContext(),"Please fill required details",Toast.LENGTH_LONG).show();
            Snackbar snackbar=Snackbar.make(coordinatorLayout,"Please fill required details",Snackbar.LENGTH_LONG);
            snackbar.show();
            //txtUserName.requestFocus();
        }
    }
    void getUsername(String url1){
         StringRequest postRequest1 = new StringRequest(Request.Method.POST, url1,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        switch(response.substring(9,13)) {
                            case "true":
                                Toast.makeText(getApplicationContext(), "Enter correct username", Toast.LENGTH_SHORT).show();
                                break;
                            case "fals":
                               // pDialog1=new ProgressDialog(LogActivity.this,R.style.MyDialog);
                              //  pDialog1.setMessage("Working on it...");
                             //   pDialog1.show();
                                try {

                                    jsonObject = new JSONObject(response);
                                    String email = jsonObject.getString("email");
                                    Toast.makeText(getApplicationContext(), "Sending Mail to "+email, Toast.LENGTH_SHORT).show();
                                    String url2="http://192.168.12.52:34/MyTaskManager/v1/forgot";
                                  forgotPassword(url2, email);

                                }catch(Exception e){e.printStackTrace();}
                                break;
                            default:
                                if(Build.VERSION.SDK_INT >20) {
                                    Snackbar snackbar2 = Snackbar.make(coordinatorLayout, "Something went wrong", Snackbar.LENGTH_LONG);
                                    snackbar2.show();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_LONG).show();
                                }
                                break;
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Snackbar snackbar=Snackbar.make(coordinatorLayout,error.toString(),Snackbar.LENGTH_LONG);
                        snackbar.show();
                        //Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("username", txtUserName.getText().toString());
                //params.put("password", txtPassword.getText().toString());
                return params;
            }
        };
        postRequest1.setShouldCache(false);
        ApplicationController.getInstance().getRequestQueue().getCache().remove(url1);
        ApplicationController.getInstance().getRequestQueue().getCache().invalidate(url1,true);
        ApplicationController.getInstance().getRequestQueue().add(postRequest1);
    }
    void forgotPassword(String url2,final String myemail){
        StringRequest postRequest2 = new StringRequest(Request.Method.POST, url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        switch(response.substring(9,13)) {
                            case "true":
                                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                                break;
                            case "fals":
                                Toast.makeText(getApplicationContext(), "Check your email and login with temporary password", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                if (Build.VERSION.SDK_INT > 20) {
                                    Snackbar snackbar2 = Snackbar.make(coordinatorLayout, "Check your email and login with temporary password", Snackbar.LENGTH_LONG);
                                    snackbar2.show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Check your email and login with temporary password", Toast.LENGTH_LONG).show();
                                }
                                break;
                        }
                        }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Snackbar snackbar=Snackbar.make(coordinatorLayout,error.toString(),Snackbar.LENGTH_LONG);
                        snackbar.show();
                        //Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("email", myemail);
                //params.put("password", txtPassword.getText().toString());
                return params;
            }
        };
        postRequest2.setShouldCache(false);
        postRequest2.setRetryPolicy(new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ApplicationController.getInstance().getRequestQueue().getCache().remove(url2);
        ApplicationController.getInstance().getRequestQueue().getCache().invalidate(url2,true);
        ApplicationController.getInstance().getRequestQueue().add(postRequest2);
    }

    void MyResponse(String url){
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        getAct(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Snackbar snackbar=Snackbar.make(coordinatorLayout,"Network fail.Internet not working",Snackbar.LENGTH_LONG);
                        snackbar.show();
                        //Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("username", txtUserName.getText().toString());
                params.put("password", txtPassword.getText().toString());
                return params;
            }
        };
        postRequest.setShouldCache(false);
        ApplicationController.getInstance().getRequestQueue().getCache().remove(url);
        ApplicationController.getInstance().getRequestQueue().getCache().invalidate(url,true);
        ApplicationController.getInstance().getRequestQueue().add(postRequest);

    }
    void getAct(String response) {
        String response1=response.substring(9,13);
        pDialog.hide();
        switch(response1) {
            case "true":
                //Toast.makeText(getApplicationContext(), "Incorrect Credentials", Toast.LENGTH_SHORT).show();
                if(Build.VERSION.SDK_INT > 20) {
                    Snackbar snackbar = Snackbar.make(coordinatorLayout, "Incorrect Credentials.Try again", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Incorrect Credentials.Try again",Toast.LENGTH_LONG).show();
                }
                break;
            case "fals":
                sessionManagement.createLoginSession(txtUserName.getText().toString(),txtPassword.getText().toString());
                Toast.makeText(getApplicationContext(),"Welcome "+txtUserName.getText().toString(), Toast.LENGTH_SHORT).show();
                //Snackbar snackbar1=Snackbar.make(coordinatorLayout,"Welcome "+txtUserName.getText().toString(),Snackbar.LENGTH_LONG);
                //snackbar1.show();
                Intent i = new Intent(LogActivity.this, MainActivity.class);
                i.putExtra("uname",txtUserName.getText().toString());
                startActivity(i);
                break;
            default:
                if(Build.VERSION.SDK_INT >20) {
                    Snackbar snackbar2 = Snackbar.make(coordinatorLayout, "Incorrect Credentials.Try again", Snackbar.LENGTH_LONG);
                    snackbar2.show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Incorrect Credentials.Try again",Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
    }

    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
// Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        txtUserName.getText().clear();
        txtPassword.getText().clear();
        txtUserName.requestFocus();
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
}

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        View decorView = getWindow().getDecorView();
        int opt = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(opt);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Log Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.yash.resourcify/http/host/path")
        );
    }
    /*@Override
    public void onBackPressed() {
       /* new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                }).create().show();*/

        /*Toast.makeText(getApplicationContext(),"Press again to exit",Toast.LENGTH_LONG).show();
        LogActivity.super.onBackPressed();*/
        boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        //Toast.makeText(this, "Please click back again to exit", Toast.LENGTH_SHORT).show();
      if(Build.VERSION.SDK_INT>20) {
          Snackbar snackbar = Snackbar.make(coordinatorLayout, "Please press back again to exit", Snackbar.LENGTH_LONG);
          snackbar.show();
      }
        else{
          Toast.makeText(getApplicationContext(),"Please press back again to exit",Toast.LENGTH_LONG).show();
          
      }
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

