package com.example.harmonogram;

public class Task {
    public Integer id;
    public String subject;
    public String content;
    public String owner;
    public String datetime;

    public Task() {
    }

    public Task(String Task_subject, String Task_content, String Task_owner, String Task_datatime) {
        this.subject = Task_subject;
        this.content = Task_content;
        this.owner = Task_owner;
        this.datetime = Task_datatime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer Id) {
        id = Id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String Subject) {
        this.subject = Subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String Content) {
        this.content = Content;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String Owner) {
        this.owner = Owner;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String Datetime) {
        this.datetime = Datetime;
    }
}
