package com.example.yash.resourcify;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class HomeFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private EditText ed2;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;

    private SimpleDateFormat dateFormatter;
    private RadioGroup rg1;
    private RadioButton rb;
    private Button b1;
    EditText ed1;
    private Context context;
    NavigationView navigationView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context=getActivity();
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        //navigationView = (NavigationView) rootView.findViewById(R.id.nav_view);
        //View headerView = LayoutInflater.from(context).inflate(R.layout.nav_header_main, navigationView, false);
        //TextView textView=(TextView)headerView.findViewById(R.id.mynavusername);
        //textView.setText("Yahs");
        Typeface choose = Typeface.createFromAsset(getActivity().getAssets(), "fonts/RobotoCondensed-Bold.ttf");
        TextView Tv1=(TextView)rootView.findViewById(R.id.textchoose);
        Tv1.setTypeface(choose);
        RadioButton rb1=(RadioButton)rootView.findViewById(R.id.radioButton1);
        RadioButton rb2=(RadioButton)rootView.findViewById(R.id.radioButton2);
        RadioButton rb3=(RadioButton)rootView.findViewById(R.id.radioButton3);
        rg1=(RadioGroup)rootView.findViewById(R.id.group1);
        b1=(Button)rootView.findViewById(R.id.button1);
        TextView description=(TextView)rootView.findViewById(R.id.textDescription);
        Typeface mydescription= Typeface.createFromAsset(getActivity().getAssets(),"fonts/RobotoCondensed-Italic.ttf");
        description.setTypeface(mydescription);

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/RobotoCondensed-Regular.ttf");
        rb1.setTypeface(typeface);
        rb2.setTypeface(typeface);
        rb3.setTypeface(typeface);
        b1.setTypeface(choose);
        TextView Tv2=(TextView)rootView.findViewById(R.id.textdate);
        Tv2.setTypeface(choose);
        ed1= (EditText) rootView.findViewById(R.id.editText3);
        ed1.setInputType(InputType.TYPE_NULL);
        ed1.requestFocus();
        ed2 = (EditText) rootView.findViewById(R.id.editText4);
        ed2.setInputType(InputType.TYPE_NULL);

        setDateTimeField();
        onClickListenerButton();
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        // Inflate the layout for this fragment
        return rootView;
    }
    private void setDateTimeField() {
        ed1.setOnClickListener(HomeFragment.this);
        ed2.setOnClickListener(HomeFragment.this);

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                ed1.setText(dateFormatter.format(newDate.getTime()));
                String s1 = ed1.getText().toString();


            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                ed2.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }


    @Override
    public void onClick(View view) {
        if(view == ed1) {
            fromDatePickerDialog.show();
        } else if(view == ed2) {
            toDatePickerDialog.show();
        }
    }
    public void onClickListenerButton(){

         b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                int radioButtonID = rg1.getCheckedRadioButtonId();
                View radioButton = rg1.findViewById(radioButtonID);
                int pos=rg1.indexOfChild(radioButton);
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

             try {
                   Date date1=dateFormat.parse(ed1.getText().toString());
                   Date date2=dateFormat.parse(ed2.getText().toString());

                 int count=date1.compareTo(date2);

                   if(pos==2){
                            if(count<0){
                                Toast.makeText(getActivity(), "Showing:Sales and Purchase Order", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), BarChartActivity.class);
                                intent.putExtra("Start Date", ed1.getText().toString());
                                intent.putExtra("End Date", ed2.getText().toString());
                                startActivity(intent);

                            }
                           else {


                                Toast.makeText(getActivity(), "Please select proper duration", Toast.LENGTH_LONG).show();
                                ed1.requestFocus();
                            }
                   }
                   else if(pos==1) {
                       if(count<0) {


                           Toast.makeText(getActivity(), "Showing:Purchase Order", Toast.LENGTH_SHORT).show();
                           Intent intent = new Intent(getActivity(), BarChartPurchase.class);
                           intent.putExtra("Start Date", ed1.getText().toString());
                           intent.putExtra("End Date", ed2.getText().toString());
                           startActivity(intent);
                       }else {
                           Toast.makeText(getActivity(), "Please select proper duration", Toast.LENGTH_LONG).show();

                           ed1.requestFocus();
                       }
                   }
                   else if(pos==0){
                             if(count<0) {


                              //   Toast.makeText(getActivity(), "Showing:Sales Order", Toast.LENGTH_SHORT).show();
                                 Intent intent = new Intent(getActivity(), BarChartSales.class);
                                 intent.putExtra("Start Date", ed1.getText().toString());
                                 intent.putExtra("End Date", ed2.getText().toString());
                                 startActivity(intent);
                             }
                             else {


                                 Toast.makeText(getActivity(), "Please select proper duration", Toast.LENGTH_LONG).show();
                                 ed1.requestFocus();
                             }
                   }
               }catch (NullPointerException e){e.printStackTrace();}catch(ParseException e){e.printStackTrace();}



            }
        });
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