package com.example.harmonogram;

public class TaskDone {
    public Integer id;
    public String subject;
    public String content;
    public String owner;
    public String startdatetime;
    public String enddatetime;

    public TaskDone() {
    }

    public TaskDone(String Task_subject, String Task_content, String Task_owner, String Task_startdatetime, String Task_enddatetime) {
        this.subject = Task_subject;
        this.content = Task_content;
        this.owner = Task_owner;
        this.startdatetime = Task_startdatetime;
        this.enddatetime = Task_enddatetime;
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

    public String getStartDatetime() {
        return startdatetime;
    }

    public void setDatetime(String StartDatetime) {
        this.startdatetime = StartDatetime;
    }

    public String getEndDatetime() {
        return enddatetime;
    }

    public void setEndDatetime(String EndDatetime) {
        this.enddatetime = EndDatetime;
    }
}
