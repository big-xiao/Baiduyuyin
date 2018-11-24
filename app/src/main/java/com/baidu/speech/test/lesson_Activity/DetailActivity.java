package com.baidu.speech.test.lesson_Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.speech.test.Data.Course_Info;
import com.baidu.speech.test.R;
import com.baidu.speech.test.Retrofit.GetCourse;
import com.baidu.speech.test.VideoActivity;
import com.google.gson.Gson;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DetailActivity extends AppCompatActivity {

    private ListView lv_detail_first;
    Course_Info info=null;
    private List<Integer> date = new ArrayList<>();
    private List<String> names = new ArrayList<>();
	int superid =0;

	public void addDate() {
        date.add(1);
        date.add(2);
    }

    public void addNames() {
        names.add("1-1 课程简介");
        names.add("1-2 困惑与思考");
        names.add("1-3 什么是创造");
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        addDate();
        addNames();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
	    superid=intent.getIntExtra("Position",0);
        initListView();



    }



    private void initListView() {
        SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(this);
        String tmp=sh.getString("responsebody",null);
        if (tmp!=null)
        {
            Gson gson=new Gson();
           info=  gson.fromJson(tmp,Course_Info.class);
        }
	    TextView course_name=findViewById(R.id.course_name);
        TextView course_description=findViewById(R.id.course_desciption);

        course_name.setText(info.getCourses().get(superid).getName());
	    course_description.setText(info.getCourses().get(superid).getIntroduction());
        lv_detail_first = findViewById(R.id.lv_detail_first);

        ListAdapterFirst listAdapterFirst= new ListAdapterFirst();

        lv_detail_first.setAdapter( listAdapterFirst);
    }


    class ListAdapterThird extends BaseAdapter {
        int parentPosition=0;
        int graparentPositon=0;
        public ListAdapterThird(int graparentPositon ,int parentPosition) {

            this.parentPosition=parentPosition;
            this.graparentPositon=graparentPositon;
        }

        @Override
        public int getCount() {
            return info.getCourses().get(superid).getChapters().get(graparentPositon).getChapters().get(parentPosition).getChapters().size();
        }

        @Override
        public Object getItem(int position) {
            return info.getCourses().get(superid).getChapters().get(graparentPositon).getChapters().get(parentPosition).getChapters().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(DetailActivity.this, R.layout.item_detail_second, null);
            }
           TextView tv_detail_item_name = convertView.findViewById(R.id.tv_detail_item_name);

            tv_detail_item_name.setText( info.getCourses().get(superid).getChapters().get(graparentPositon).getChapters().get(parentPosition).getChapters().get(position).getName());
            return convertView;
        }
    }

    class ListAdapterSecond extends BaseAdapter {
        int parentPosition=0;
        public ListAdapterSecond(int parentPosition) {

            this.parentPosition=parentPosition;
        }

        @Override
        public int getCount() {
            return info.getCourses().get(parentPosition).getChapters().size();
        }

        @Override
        public Object getItem(int position) {
            return info.getCourses().get(superid).getChapters().get(parentPosition).getChapters().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(DetailActivity.this, R.layout.item_detail_second, null);
            }
            TextView tv_detail_item_name = convertView.findViewById(R.id.tv_detail_item_name);
            tv_detail_item_name.setText(info.getCourses().get(superid).getChapters().get(parentPosition).getChapters().get(position).getName());
            ListView lv_detail_third = convertView.findViewById(R.id.lv_detail_third);
            if (info.getCourses().get(superid).getChapters().get(parentPosition).getChapters().get(position).getChapters()!=null)
            {
            ListAdapterThird adapterThird = new ListAdapterThird(parentPosition,position);
           lv_detail_third.setAdapter(adapterThird);
           lv_detail_third.setOnItemClickListener(new AdapterView.OnItemClickListener() {
               @Override
               public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   Intent intent = new Intent(DetailActivity.this, VideoActivity.class);

                   startActivity(intent);

               }
           });}
           else
            {
             lv_detail_third.setVisibility(View.GONE);
            }
            return convertView;
        }
    }

    class ListAdapterFirst extends BaseAdapter{



            @Override
            public int getCount() {
                return info.getCourses().size();
            }

            @Override
            public Object getItem(int position) {
                return
                        info.getCourses().get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = View.inflate(DetailActivity.this, R.layout.item_detail_first, null);
                }
                TextView tv_detail_chapter = convertView.findViewById(R.id.tv_detail_chapter);
                String text = info.getCourses().get(superid).getChapters().get(position).getName();
                tv_detail_chapter.setText(text);
                ListView lv_detail_second = convertView.findViewById(R.id.lv_detail_second);
                //lv_detail_second .setVerticalScrollBarEnabled(false);
                ListAdapterSecond adapterSecond = new ListAdapterSecond(position);
                lv_detail_second.setAdapter(adapterSecond);

                return convertView;
            }
        }
    }






