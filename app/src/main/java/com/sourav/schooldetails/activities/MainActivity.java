package com.sourav.schooldetails.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
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
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static String TAG = "MainActivity";

    private ListView studentListView;
    TextView addstudent;
    ArrayList<StudentBean> students;

    MyCustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Students");
        }
        addstudent = (TextView) findViewById(R.id.addStudent);
        studentListView = (ListView) findViewById(R.id.studentsListView);

        addstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
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
        adapter = new MyCustomAdapter(students);
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

                   convertView = getLayoutInflater().inflate(R.layout.item_student, null);
                    ImageView Logo = (ImageView) convertView
                            .findViewById(R.id.Logo);

                    TextView name = (TextView) convertView
                            .findViewById(R.id.name);

                    TextView email = (TextView) convertView
                            .findViewById(R.id.email);

                    TextView mobile = (TextView) convertView
                            .findViewById(R.id.mobile);

                    TextView address = (TextView) convertView
                            .findViewById(R.id.address);

                    final StudentBean student = mData.get(position);
                    Logo.setVisibility(View.GONE);
                    name.setText(student.getName());
                    email.setText(student.getEmail());
                    mobile.setText(student.getMobile());
                    address.setText(student.getAddress());

                mobile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + student.getMobile()));
                        startActivity(callIntent);
                    }
                });

                name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                        intent.putExtra("student",student);
                        intent.putExtra("edit", true);
                        startActivity(intent);
                    }
                });

                email.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{student.getEmail()});
                       // intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                       // intent.putExtra(Intent.EXTRA_TEXT, content);
                        startActivity(intent);
                    }
                });

                address.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String uri = String.format(Locale.ENGLISH, "geo:0,0?q="+student.getAddress());
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        startActivity(intent);
                    }
                });
            }
            return convertView;
        }

    }
}

