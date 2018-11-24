package com.baidu.speech.test;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;




import android.Manifest;
import android.content.pm.PackageManager;

import com.baidu.speech.test.Data.*;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.baidu.speech.EventListener;
import com.baidu.speech.EventManager;
import com.baidu.speech.EventManagerFactory;
import com.baidu.speech.asr.SpeechConstant;
import com.baidu.speech.test.vIEW.myPopupWindow;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;
import com.google.gson.Gson;

import org.json.JSONObject;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class VideoActivity extends AppCompatActivity   implements EventListener{


	protected boolean enableOffline = false;

	private boolean mBackPressed;
	private EventManager asr;
  private myPopupWindow popupWindow=null;
	private boolean logTime = true;
	private PlayerView playerView;
	SimpleExoPlayer player;
	DefaultBandwidthMeter bandwidthMeter;
	DataSource.Factory dataSourceFactory;
	ExtractorsFactory extractorsFactory;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video_player);
		initPermission();
		asr = EventManagerFactory.create(this, "asr");
		asr.registerListener(this);
		initPlayer();
		playVideo();


       //todo:设置监视器
player.setPlayWhenReady(true);





	}
	private void initPlayer() {
		//1. 创建一个默认的 TrackSelector
		BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
		TrackSelection.Factory videoTackSelectionFactory =
				new AdaptiveTrackSelection.Factory(bandwidthMeter);
		TrackSelector trackSelector =
				new DefaultTrackSelector(videoTackSelectionFactory);

		//2.创建ExoPlayer
		player = ExoPlayerFactory.newSimpleInstance(this,trackSelector);
		//3.创建SimpleExoPlayerView
		playerView = findViewById(R.id.video_view);
		//4.为SimpleExoPlayer设置播放器
		playerView.setPlayer(player);
	}

	private void playVideo() {
		//测量播放过程中的带宽。 如果不需要，可以为null。
		 bandwidthMeter = new DefaultBandwidthMeter();
		// 生成加载媒体数据的DataSource实例。
	  dataSourceFactory
				= new DefaultDataSourceFactory(this,
				Util.getUserAgent(this,"useExoplayer"),bandwidthMeter);
		// 生成用于解析媒体数据的Extractor实例。
		 extractorsFactory = new DefaultExtractorsFactory();


		// MediaSource代表要播放的媒体。
		MediaSource videoSource = new ExtractorMediaSource(Uri.parse("http://media.w3.org/2010/05/sintel/trailer.mp4")
				,dataSourceFactory,extractorsFactory,
				null,null);
		//Prepare the player with the source.

		player.prepare(videoSource);
		//添加监听的listener
//        mSimpleExoPlayer.setVideoListener(mVideoListener);
		player.addListener(getEventListener());
//        mSimpleExoPlayer.setTextOutput(mOutput);
		player.setPlayWhenReady(true);

	}
private  Player.EventListener getEventListener()
{
	return  new Player.EventListener() {
		@Override
		public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

		}

		@Override
		public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

		}

		@Override
		public void onLoadingChanged(boolean isLoading) {

		}

		@Override
		public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
			switch (playbackState) {
				case ExoPlayer.STATE_ENDED:

					popupWindow=new myPopupWindow(VideoActivity.this);

					popupWindow.setContent();
					start();
					break;

			}
		}

		@Override
		public void onRepeatModeChanged(int repeatMode) {

		}

		@Override
		public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

		}

		@Override
		public void onPlayerError(ExoPlaybackException error) {

		}

		@Override
		public void onPositionDiscontinuity(int reason) {

		}

		@Override
		public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

		}

		@Override
		public void onSeekProcessed() {

		}
	};
}

	private void start() {

		Map<String, Object> params = new LinkedHashMap<String, Object>();
		String event = null;
		event = SpeechConstant.ASR_START; // 替换成测试的event

		if (enableOffline) {
			params.put(SpeechConstant.DECODER, 2);
		}
		params.put(SpeechConstant.ACCEPT_AUDIO_VOLUME, false);
		// params.put(SpeechConstant.NLU, "enable");
		// params.put(SpeechConstant.VAD_ENDPOINT_TIMEOUT, 0); // 长语音
		// params.put(SpeechConstant.IN_FILE, "res:///com/baidu/android/voicedemo/16k_test.pcm");
		// params.put(SpeechConstant.VAD, SpeechConstant.VAD_DNN);
		// params.put(SpeechConstant.PROP ,20000);
		// params.put(SpeechConstant.PID, 1537); // 中文输入法模型，有逗号
		// 请先使用如‘在线识别’界面测试和生成识别参数。 params同ActivityRecog类中myRecognizer.start(params);
		// 复制此段可以自动检测错误
	/*	(new AutoCheck(getApplicationContext(), new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what == 100) {
					AutoCheck autoCheck = (AutoCheck) msg.obj;
					synchronized (autoCheck) {
						String message = autoCheck.obtainErrorMessage(); // autoCheck.obtainAllMessage();
						txtLog.append(message + "\n");
						; // 可以用下面一行替代，在logcat中查看代码
						// Log.w("AutoCheckMessage", message);
					}
				}
			}
		},enableOffline)).checkAsr(params);*/
		String json = null; // 可以替换成自己的json
		json = new JSONObject(params).toString(); // 这里可以替换成你需要测试的json
		asr.send(event, json, null, 0, 0);//
		printLog("输入参数：" + json);
	}
	private void stop() {
		printLog("停止识别：ASR_STOP");
		asr.send(SpeechConstant.ASR_STOP, null, null, 0, 0); //
	}
	private void loadOfflineEngine() {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put(SpeechConstant.DECODER, 2);
		params.put(SpeechConstant.ASR_OFFLINE_ENGINE_GRAMMER_FILE_PATH, "assets://baidu_speech_grammar.bsg");
		asr.send(SpeechConstant.ASR_KWS_LOAD_ENGINE, new JSONObject(params).toString(), null, 0, 0);
	}
	private void unloadOfflineEngine() {
		asr.send(SpeechConstant.ASR_KWS_UNLOAD_ENGINE, null, null, 0, 0); //
	}
	@Override
	protected void onPause(){
		super.onPause();
		asr.send(SpeechConstant.ASR_CANCEL, "{}", null, 0, 0);
		Log.i("ActivityMiniRecog","On pause");
	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		asr.send(SpeechConstant.ASR_CANCEL, "{}", null, 0, 0);
		if (enableOffline) {
			unloadOfflineEngine(); // 测试离线命令词请开启, 测试 ASR_OFFLINE_ENGINE_GRAMMER_FILE_PATH 参数时开启
		}

		// 必须与registerListener成对出现，否则可能造成内存泄露
		asr.unregisterListener(this);
	}
	@Override
	public void onBackPressed() {
		mBackPressed = true;
		super.onBackPressed();
	}
	@Override
	protected void onStop() {
		super.onStop();
		//点击返回或不允许后台播放时 释放资源

		if (enableOffline) {
			unloadOfflineEngine(); // 测试离线命令词请开启, 测试 ASR_OFFLINE_ENGINE_GRAMMER_FILE_PATH 参数时开启
		}

		// 必须与registerListener成对出现，否则可能造成内存泄露
		player.release();;
		asr.unregisterListener(this);
	}
	@Override
	public void onEvent(String name, String params, byte[] data, int offset, int length) {
		String logTxt = "name: " + name;
		String tmp = null;


		if (params != null && !params.isEmpty()) {
			logTxt += " ;params :" + params;
		}
		if (length > 0 && data.length > 0) {//可以在这里做操作，如果是离线的话，上面有个参数要关掉
			tmp += new String(data, offset, length);


		}
		if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_PARTIAL)) {
			Gson gson=new Gson();
			Params params1=gson.fromJson(params, Params.class);
			if (params1.getResults_recognition().get(0).equals("我听懂了"))
			{
				popupWindow.miss();
				MediaSource videoSource = new ExtractorMediaSource(Uri.parse("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4")
						,dataSourceFactory,extractorsFactory,
						null,null);
				player.prepare(videoSource);
				player.setPlayWhenReady(true);
				stop();
			}
			else
			{
				popupWindow.miss();
				player.seekTo(0);
				player.setPlayWhenReady(true);
				stop();
			}
			if (params.contains("\"nlu_result\"")) {
				if (length > 0 && data.length > 0) {
					logTxt += ", 语义解析结果：" + new String(data, offset, length);
				}
			}

		} else if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_FINISH)) {
			// 识别结束， 最终识别结果或可能的错误
			logTxt += "识别结束";

			asr.send(SpeechConstant.ASR_STOP, null, null, 0, 0);
			if (params != null && !params.isEmpty()) {
				logTxt += "params :" + params;
			}
		}
		else if (data != null) {


			logTxt += " ;data length=" + data.length;
		}

		printLog(logTxt);

	}
	private void printLog(String text) {
		if (logTime) {
			text += "  ;time=" + System.currentTimeMillis();
		}
		text += "\n";
		Log.i(getClass().getName(), text);

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
