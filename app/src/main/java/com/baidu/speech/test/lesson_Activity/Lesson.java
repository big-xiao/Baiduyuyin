package com.baidu.speech.test.lesson_Activity;

public class Lesson {
    private String lessonName;

    private int lessonTotal;
    private String path;
    private int icon;
    public Lesson(String lessonName, int icon, int lessonTotal,String path) {
        this.lessonName = lessonName;
        this.icon = icon;

        this.lessonTotal = lessonTotal;
        this.path = path;
    }
    public Lesson() {
    }

    public String getLessonName() {
        return lessonName;
    }



    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }
    public  void setIcon(){
        this.icon=icon;
    }
    public int getIcon() {

        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getLessonTotal() {
        return lessonTotal;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setLessonTotal(int lessonTotal) {
        this.lessonTotal = lessonTotal;
    }
}
