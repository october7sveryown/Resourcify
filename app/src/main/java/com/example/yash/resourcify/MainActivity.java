package com.example.yash.resourcify;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView=null;
    Toolbar toolbar=null;
    SessionManagement sessionManagement;
    TextView myUsername;
    String myLogin;
    EditText userInput;
    CoordinatorLayout coordinatorLayout;
    ProgressDialog pDialog;
    String URL;
    EditText oldPassword;
AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coordinatorLayout=(CoordinatorLayout)findViewById(R.id.mycoordinatorLayout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
         HomeFragment homeFragment=new HomeFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, homeFragment);
        fragmentTransaction.commit();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.title_fragment_home);
        sessionManagement=new SessionManagement(getApplicationContext());
        sessionManagement.checkLogin();
       // TextView navtitle=(TextView)headerView.findViewById(R.id.navTitle);
        //Typeface navface = Typeface.createFromAsset(getAssets(), "fonts/GrandHotel-Regular.ttf");
        //navtitle.setTypeface(navface);
        //HashMap<String,String> myLogin=new HashMap<String,String>();
        //Bundle extras=getIntent().getExtras();
        myUsername=(TextView)headerView.findViewById(R.id.mynavusername);
        HashMap<String,String> user=new HashMap<>();
        String usernameNav=getIntent().getStringExtra("uname");
        myUsername.setText(usernameNav);
        Typeface tf=Typeface.createFromAsset(getAssets(), "fonts/RobotoCondensed-Regular.ttf");
        myUsername.setTypeface(tf);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle (this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
       // navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

   /* @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            HomeFragment homeFragment = new HomeFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, homeFragment);
            fragmentTransaction.commit();

            getSupportActionBar().setTitle(R.string.title_fragment_home);
            // Handle the camera action
        } else if (id == R.id.nav_about) {
            AboutFragment aboutFragment = new AboutFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, aboutFragment);
            fragmentTransaction.commit();
            getSupportActionBar().setTitle(R.string.title_fragment_about);

        } else if (id == R.id.nav_change) {
            LayoutInflater li = LayoutInflater.from(this);
            View promptsView = li.inflate(R.layout.mypasswordchange, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);

            // set prompts.xml to alertdialog builder
            alertDialogBuilder.setView(promptsView);

            userInput = (EditText) promptsView
                    .findViewById(R.id.editTextDialogUserInput);
            oldPassword=(EditText)promptsView.findViewById(R.id.oldPass);

            // set dialog message
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    checkParam();

                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                       alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        else if (id == R.id.nav_logout) {
            /*MyAlertDialog myAlertDialog=new MyAlertDialog();
            String msg="Are you sure you want to Logout?";
            myAlertDialog.showAlertDialog(getApplicationContext(), "Logout", msg,true);*/
            sessionManagement.logoutUser();
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to Logout?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            MainActivity.super.onBackPressed();
                            sessionManagement.logoutUser();
                            Intent i = new Intent(MainActivity.this, LogActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            // i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                            Toast.makeText(getApplicationContext(),"You have successfully logged out",Toast.LENGTH_LONG).show();
                        }
                    }).create().show();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
        //    super.onBackPressed();

            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to Logout?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            MainActivity.super.onBackPressed();
                            sessionManagement.logoutUser();

                            Intent i = new Intent(MainActivity.this, LogActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                           // i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            Toast.makeText(getApplicationContext(),"You have successfully logged out",Toast.LENGTH_LONG).show();
                            startActivity(i);
                        }
                    }).create().show();
        }
    }
    void MyCall(String url){
        StringRequest postRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        getResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        alertDialog.dismiss();
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
                params.put("newpassword", userInput.getText().toString());
                params.put("password",oldPassword.getText().toString() );
                return params;
            }
        };
        postRequest.setShouldCache(false);
        ApplicationController.getInstance().getRequestQueue().getCache().remove(url);
        ApplicationController.getInstance().getRequestQueue().getCache().invalidate(url,true);
        ApplicationController.getInstance().getRequestQueue().add(postRequest);
    }
    void getResponse(String response) {
        String response1=response.substring(9,13);
        pDialog.hide();
        switch(response1) {
            case "true":
                alertDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Old password does not match.Try again",Toast.LENGTH_SHORT).show();
                break;
            case "fals":
                alertDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Password Changed.Login Again",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this, LogActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
              //  i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                break;
            default:
                alertDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Something went wrong.Try again",Toast.LENGTH_SHORT).show();
                break;
        }
    }
    void checkParam(){
        if(userInput.getText().toString().trim().length()> 0){
            URL="http://192.168.1.4:34/MyTaskManager/v1/change";
            MyCall(URL);
            pDialog = new ProgressDialog(MainActivity.this,R.style.MyDialog);
            pDialog.setMessage("Authenticating..");
            pDialog.show();
        }
        else{
            alertDialog.dismiss();
            //pDialog.hide();
            Toast.makeText(getApplicationContext(),"Please fill required details",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}
