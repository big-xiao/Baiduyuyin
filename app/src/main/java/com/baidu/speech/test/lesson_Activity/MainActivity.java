package com.baidu.speech.test.lesson_Activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.baidu.speech.test.Data.Course_Info;
import com.baidu.speech.test.R;
import com.baidu.speech.test.Retrofit.GetCourse;
import com.google.gson.Gson;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private ListView lv_main;
    private LessonAdapter adapter;
	Course_Info info=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
	    initPermission();
         initData();
        lv_main =findViewById(R.id.lv_main);




        lv_main.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

	             Intent intent = new Intent(MainActivity.this,DetailActivity.class);
                intent.putExtra("Position",position);
                startActivity(intent);
            }
        });


    }
	private void initData() {
		SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(this);
		final SharedPreferences.Editor editor=sh.edit();
		Retrofit retrofit=new Retrofit.Builder()
				.baseUrl(getString(R.string.Baseuri))
				.addConverterFactory(GsonConverterFactory.create())
				.build();
		GetCourse Course= retrofit.create(GetCourse.class);
		Call<ResponseBody> newcall=Course.getCourse_Info("");
		newcall.enqueue(new Callback<ResponseBody>() {
			@Override
			public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


				try {
					String tmp=response.body().string();
					editor.putString("responsebody",tmp);
					editor.commit();
					praseJson(tmp);
					adapter = new LessonAdapter(MainActivity.this,info);
					lv_main.setAdapter(adapter);

				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}

			@Override
			public void onFailure(Call<ResponseBody> call, Throwable t) {
				t.printStackTrace();
			}
		});

	}
	private void praseJson(String tmp) throws JSONException {

		Gson gson=new Gson();
		info=gson.fromJson(tmp,Course_Info.class);




		Log.i(getPackageName(),info.getCourses().get(1).getChapters().get(0).getChapters().get(0).getChapters().get(0).getName());
	}
	private void initPermission() {
		String permissions[] = {Manifest.permission.RECORD_AUDIO,
				Manifest.permission.ACCESS_NETWORK_STATE,
				Manifest.permission.INTERNET,
				Manifest.permission.READ_PHONE_STATE,
				Manifest.permission.WRITE_EXTERNAL_STORAGE
		};

		ArrayList<String> toApplyList = new ArrayList<String>();

		for (String perm : permissions) {
			if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
				toApplyList.add(perm);
				// 进入到这里代表没有权限.

			}
		}
		String tmpList[] = new String[toApplyList.size()];
		if (!toApplyList.isEmpty()) {
			ActivityCompat.requestPermissions(this, toApplyList.toArray(tmpList), 123);
		}

	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		// 此处为android 6.0以上动态授权的回调，用户自行实现。
	}
}
