package com.sourav.schooldetails.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sourav.schooldetails.R;
import com.sourav.schooldetails.helpers.StudentBean;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by sourav on 6/7/16.
 */
public class DetailsActivity extends AppCompatActivity {

    public static String TAG = "DetailsActivity";

    private EditText nameEditText;
    private EditText addressEditText;
    private EditText emailEditText;
    private EditText mobileEditText;

    ArrayList<StudentBean> students;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Add Student");
        }

        nameEditText = (EditText) findViewById(R.id.NameEditTxt);
        addressEditText = (EditText) findViewById(R.id.addressEditTxt);
        emailEditText = (EditText) findViewById(R.id.EmailEditTxt);
        mobileEditText = (EditText) findViewById(R.id.mobileEditTxt);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.action_save).setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.homeAsUp:
                return true;
            case android.R.id.home:
                // called when the up affordance/carat in actionbar is pressed
                onBackPressed();
                return true;
            case R.id.action_save:
                saveData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void saveData() {
        StudentBean studentBean = new StudentBean();
        studentBean.setAddress(addressEditText.getText().toString());
        studentBean.setEmail(emailEditText.getText().toString());
        studentBean.setMobile(mobileEditText.getText().toString());
        studentBean.setName(nameEditText.getText().toString());

        SharedPreferences pref = getPreferences(MODE_PRIVATE);
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<StudentBean>>() {
        }.getType();

        /**
         * Get data from sharedpreferences
         */
        String oldData = pref.getString("students","");
        if (oldData.equals("")) {
            students = new ArrayList<StudentBean>();
        } else {
            students = gson.fromJson(oldData,listType);
        }

        /**
         * Set data to sharedpreferences
         */
        students.add(studentBean);
        SharedPreferences.Editor prefsEditor = pref.edit();
        String newData = gson.toJson(students,listType);
        prefsEditor.putString("students", newData);
        prefsEditor.commit();
    }
}
