package com.baidu.speech.test.lesson_Activity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.speech.test.Data.Course_Info;
import com.baidu.speech.test.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class LessonAdapter extends BaseAdapter {
    private String imageuri; ;
    private List<Lesson> data = new ArrayList<Lesson>();
    private Context context;
	private Course_Info courses;
	RequestOptions options_pre ;
    RequestOptions options_error;
    public LessonAdapter(Context context, Course_Info courses) {
        data.add(new Lesson("创造性思维与创新方法",R.drawable.o,12,null));
        data.add(new Lesson("生命伦理学",R.drawable.tw,24,null));
        data.add(new Lesson("中国历史地理",R.drawable.t,8,null));
        this.context = context;
        this.courses=courses;
        imageuri=context.getString(R.string.Baseuri)+"thumbnails/";
        options_pre = new RequestOptions()
                .placeholder(R.drawable.o);
     options_error= new RequestOptions()
                .error(R.drawable.tw);
    }


    public int getCount() {
        return courses.getCourses().size();
    }

    @Override
    public Object getItem(int position) {
        return courses.getCourses().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            convertView = View.inflate(context, R.layout.item_main, null);
           holder=new ViewHolder();
            holder.iconImageView=convertView.findViewById(R.id.iv_item_icon);
            holder.nameTextView=convertView.findViewById(R.id.tv_item_name);
            holder.totalTextViw =convertView.findViewById(R.id.tv_item_total);
            convertView.setTag(holder);
        }else
        {
            holder= (ViewHolder) convertView.getTag();

        }


        Glide.with(convertView)
                .load(imageuri+courses.getCourses().get(position).getThumbnail_id())
                .apply(options_pre)
                 .apply(options_error).into(holder.iconImageView);

//        Drawable.createFromResourceStream(null,null,null,null);
//        iconImageView.setImageDrawable(lesson.getIcon());

        holder.nameTextView.setText(courses.getCourses().get(position).getName());

        String totalStr = courses.getCourses().get(position).getChapters().size()+ "章节";
        holder.totalTextViw.setText(totalStr);

        return convertView;
    }
    class ViewHolder
    {
       public ImageView iconImageView ;
        public   TextView nameTextView ;
        public  TextView totalTextViw ;
    }
}
