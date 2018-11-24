package com.baidu.speech.test.vIEW;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.baidu.speech.test.R;

import java.lang.ref.WeakReference;
import java.util.zip.Inflater;

public class myPopupWindow {
	PopupWindow pop=null;
	View content=null;
	WeakReference<Activity> weakReference=null;
	public myPopupWindow() {     pop=new PopupWindow();

	}
	public void setContent()
	{
		pop.showAtLocation(content,Gravity.CENTER,0,0);
		ImageView imageView=content.findViewById(R.id.imageView);
		imageView.setImageResource(R.drawable.voice);
		TextView textView=content.findViewById(R.id.textView);
		textView.setText("语音识别中");
	}
	public myPopupWindow(Activity activity) {     pop=new PopupWindow();
        weakReference=new WeakReference<Activity>(activity);
       content=LayoutInflater.from(weakReference.get()).inflate(R.layout.pop_tip,null);

        pop=new PopupWindow(content,WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT, true);

	} public void backgroundAlpha(float bgalpha) {
		WindowManager.LayoutParams lp = weakReference.get().getWindow().getAttributes();
		lp.alpha = bgalpha;
		weakReference.get().getWindow().setAttributes(lp);
	}

	public  void miss()
	{
		pop.dismiss();
	}
}
