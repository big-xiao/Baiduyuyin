package com.baidu.speech.test.Retrofit;

import com.baidu.speech.test.Data.Course_Info;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;

import retrofit2.http.Header;

public interface GetCourse {
	@GET("courses")
	Call<ResponseBody> getCourse_Info(@Header("Auth") String header );

}
