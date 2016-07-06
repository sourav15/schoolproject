package com.sourav.schooldetails.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sourav.schooldetails.R;
import com.sourav.schooldetails.helpers.StudentBean;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static String TAG = "MainActivity";

    private ListView studentListView;
    ArrayList<StudentBean> students;
    ArrayList<StudentBean> newStudents;
    StudentBean studentBean;

    MyCustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Students");
        }
        studentListView = (ListView) findViewById(R.id.studentsListView);
        newStudents = new ArrayList<>();
        studentBean = new StudentBean();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        newStudents.add(0,studentBean);
        for (int i = 0; i < students.size(); i++) {
            newStudents.add(i+1,students.get(i));
        }
        adapter = new MyCustomAdapter(newStudents);
        studentListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private class MyCustomAdapter extends ArrayAdapter<StudentBean> {

        ArrayList<StudentBean> mData;

        public MyCustomAdapter(ArrayList<StudentBean> student) {
            super(getApplicationContext(), 0, student);
            this.mData = student;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return this.mData.size();
        }

        @Override
        public StudentBean getItem(int position) {
            // TODO Auto-generated method stub
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                if (position == 0) {
                    convertView = getLayoutInflater().inflate(R.layout.item_add, null);
                    convertView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                            startActivity(intent);
                        }
                    });
                } else {
                   convertView = getLayoutInflater().inflate(R.layout.item_student, null);
                    ImageView Logo = (ImageView) convertView
                            .findViewById(R.id.logo);

                    TextView name = (TextView) convertView
                            .findViewById(R.id.name);

                    TextView email = (TextView) convertView
                            .findViewById(R.id.email);

                    TextView mobile = (TextView) convertView
                            .findViewById(R.id.mobile);

                    TextView address = (TextView) convertView
                            .findViewById(R.id.address);

                    StudentBean student = mData.get(position);
                    name.setText(student.getName());
                    email.setText(student.getEmail());
                    mobile.setText(student.getMobile());
                    address.setText(student.getAddress());
                }
            }
            return convertView;
        }

    }
}

