package com.restapi.lab1_ph46328.lab1;

import java.util.HashMap;

public class ToDo {
    private String id,title,content;

    public ToDo(String id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
    //viet phuong thuc xu ly du lieu voi firebase
    public HashMap<String, Object> convertHashMap(){
        HashMap<String,Object> work=new HashMap<>();
        work.put("id",id);
        work.put("title",title);
        work.put("content",content);
        return work;
    }

    public ToDo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}