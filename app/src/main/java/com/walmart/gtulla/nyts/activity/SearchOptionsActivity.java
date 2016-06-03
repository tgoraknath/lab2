package com.walmart.gtulla.nyts.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.walmart.gtulla.nyts.R;
import com.walmart.gtulla.nyts.model.SearchFilterOptions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SearchOptionsActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText fromDateEtxt;
    private EditText toDateEtxt;

    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;

    private SimpleDateFormat dateFormatter;
    private SearchFilterOptions filterOptions = new SearchFilterOptions();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_options);
        dateFormatter = new SimpleDateFormat("yyyyMMdd", Locale.US);
        findViewsById();
        setDateTimeField();
    }

    private void findViewsById() {
        fromDateEtxt = (EditText) findViewById(R.id.dpBeginDate);
        fromDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.requestFocus();
        toDateEtxt = (EditText) findViewById(R.id.dpEndDate);
        toDateEtxt.setInputType(InputType.TYPE_NULL);
    }


    private void setDateTimeField() {
        fromDateEtxt.setOnClickListener(this);
        toDateEtxt.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                filterOptions.setCreatedDate(dateFormatter.format(newDate.getTime()));
                fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                toDateEtxt.setText(dateFormatter.format(newDate.getTime()));
                filterOptions.setEndDate(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    protected void onFilter(View view){
        Intent data = new Intent();
        data.putExtra("fromDate", filterOptions.getCreatedDate());
        data.putExtra("toDate", filterOptions.getEndDate());
        data.putExtra("code", 50);
        setResult(RESULT_OK, data);
        finish();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        if(view == fromDateEtxt) {
            fromDatePickerDialog.show();
        } else if(view == toDateEtxt) {
            toDatePickerDialog.show();
        }
    }
}
