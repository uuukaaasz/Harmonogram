package com.example.harmonogram;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DBController extends SQLiteOpenHelper {
    public DBController(Context context){
        super(context, "harmonogram.db" ,null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.init(db);
    }

    private void init(SQLiteDatabase db) {
        db.execSQL(
                "create table if not exists tasks(" +
                        "id integer primary key autoincrement," +
                        "subject text," +
                        "content text," +
                        "owner text," +
                        "datetime text);" + "");
        db.execSQL(
                "create table if not exists tasks_checked(" +
                        "id integer primary key autoincrement," +
                        "subject text," +
                        "content text," +
                        "owner text," +
                        "start_datetime text," +
                        "end_datetime text);" + "");
    }

    public void RemoveAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS tasks_checked");
        init(db);
    }

    public void InsertTask(Task task) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("subject", task.getSubject());
        values.put("content", task.getContent());
        values.put("owner", task.getOwner());
        values.put("datetime", task.getDatetime());
        db.insertOrThrow("tasks",null, values);
    }

    public void InsertTaskDone(TaskDone taskDone) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("subject", taskDone.getSubject());
        values.put("content", taskDone.getContent());
        values.put("owner", taskDone.getOwner());
        values.put("start_datetime", taskDone.getStartDatetime());
        values.put("end_datetime", taskDone.getEndDatetime());
        db.insertOrThrow("tasks_checked",null, values);
    }

    public void DeleteTask(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String[] args={""+id};
        db.delete("tasks", "id=?", args);
    }

    public void DeleteTaskDone(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String[] args={""+id};
        db.delete("tasks_checked", "id=?", args);
    }

    public List<SpinnerObject> getTaskSubject() {
        List<SpinnerObject> read = new ArrayList<SpinnerObject>();
        String query =  "SELECT tasks.id, tasks.subject" +
                " FROM" + " tasks";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            read.add(new SpinnerObject(cursor.getInt(0), cursor.getString(1)));
        }
        return read;
    }

    public List<SpinnerObject> getTaskDoneSubject() {
        List<SpinnerObject> read = new ArrayList<SpinnerObject>();
        String query =  "SELECT tasks_checked.id, tasks_checked.subject" +
                " FROM" + " tasks_checked";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            read.add(new SpinnerObject(cursor.getInt(0), cursor.getString(1)));
        }
        return read;
    }

    public List<SpinnerObject> getTaskSubjectById(int Id) {
        List<SpinnerObject> read = new ArrayList<SpinnerObject>();
        String query =  "SELECT tasks.id, tasks.subject" +
                " FROM" + " tasks" +
                " WHERE tasks.id = " + Id;
        SQLiteDatabase db = getReadableDatabase();
        Cursor kursor = db.rawQuery(query, null);
        while (kursor.moveToNext()) {
            read.add(new SpinnerObject(kursor.getInt(0), kursor.getString(1)));
        }
        return read;
    }

    public List<SpinnerObject> getTaskDoneSubjectById(int Id) {
        List<SpinnerObject> read = new ArrayList<SpinnerObject>();
        String query =  "SELECT tasks_checked.id, tasks_checked.subject" +
                " FROM" + " tasks_checked" +
                " WHERE tasks_checked.id = " + Id;
        SQLiteDatabase db = getReadableDatabase();
        Cursor kursor = db.rawQuery(query, null);
        while (kursor.moveToNext()) {
            read.add(new SpinnerObject(kursor.getInt(0), kursor.getString(1)));
        }
        return read;
    }

    public List<SpinnerObject> getTaskContentById(int Id) {
        List<SpinnerObject> read = new ArrayList<SpinnerObject>();
        String query =  "SELECT tasks.id, tasks.content" +
                " FROM" + " tasks" +
                " WHERE tasks.id = " + Id;
        SQLiteDatabase db = getReadableDatabase();
        Cursor kursor = db.rawQuery(query, null);
        while (kursor.moveToNext()) {
            read.add(new SpinnerObject(kursor.getInt(0), kursor.getString(1)));
        }
        return read;
    }

    public List<SpinnerObject> getTaskDoneContentById(int Id) {
        List<SpinnerObject> read = new ArrayList<SpinnerObject>();
        String query =  "SELECT tasks_checked.id, tasks_checked.content" +
                " FROM" + " tasks_checked" +
                " WHERE tasks_checked.id = " + Id;
        SQLiteDatabase db = getReadableDatabase();
        Cursor kursor = db.rawQuery(query, null);
        while (kursor.moveToNext()) {
            read.add(new SpinnerObject(kursor.getInt(0), kursor.getString(1)));
        }
        return read;
    }

    public List<SpinnerObject> getTaskOwnerById(int Id) {
        List<SpinnerObject> read = new ArrayList<SpinnerObject>();
        String query =  "SELECT tasks.id, tasks.owner" +
                " FROM" + " tasks" +
                " WHERE tasks.id = " + Id;
        SQLiteDatabase db = getReadableDatabase();
        Cursor kursor = db.rawQuery(query, null);
        while (kursor.moveToNext()) {
            read.add(new SpinnerObject(kursor.getInt(0), kursor.getString(1)));
        }
        return read;
    }

    public List<SpinnerObject> getTaskDoneOwnerById(int Id) {
        List<SpinnerObject> read = new ArrayList<SpinnerObject>();
        String query =  "SELECT tasks_checked.id, tasks_checked.owner" +
                " FROM" + " tasks_checked" +
                " WHERE tasks_checked.id = " + Id;
        SQLiteDatabase db = getReadableDatabase();
        Cursor kursor = db.rawQuery(query, null);
        while (kursor.moveToNext()) {
            read.add(new SpinnerObject(kursor.getInt(0), kursor.getString(1)));
        }
        return read;
    }

    public List<SpinnerObject> getTaskDateTimeById(int Id) {
        List<SpinnerObject> read = new ArrayList<SpinnerObject>();
        String query =  "SELECT tasks.id, tasks.datetime" +
                " FROM" + " tasks" +
                " WHERE tasks.id = " + Id;
        SQLiteDatabase db = getReadableDatabase();
        Cursor kursor = db.rawQuery(query, null);
        while (kursor.moveToNext()) {
            read.add(new SpinnerObject(kursor.getInt(0), kursor.getString(1)));
        }
        return read;
    }

    public List<SpinnerObject> getTaskDoneDateTimeById(int Id) {
        List<SpinnerObject> read = new ArrayList<SpinnerObject>();
        String query =  "SELECT tasks_checked.id, tasks_checked.start_datetime" +
                " FROM" + " tasks_checked" +
                " WHERE tasks_checked.id = " + Id;
        SQLiteDatabase db = getReadableDatabase();
        Cursor kursor = db.rawQuery(query, null);
        while (kursor.moveToNext()) {
            read.add(new SpinnerObject(kursor.getInt(0), kursor.getString(1)));
        }
        return read;
    }

    public List<SpinnerObject> getTaskDoneEndDateTimeById(int Id) {
        List<SpinnerObject> read = new ArrayList<SpinnerObject>();
        String query =  "SELECT tasks_checked.id, tasks_checked.end_datetime" +
                " FROM" + " tasks_checked" +
                " WHERE tasks_checked.id = " + Id;
        SQLiteDatabase db = getReadableDatabase();
        Cursor kursor = db.rawQuery(query, null);
        while (kursor.moveToNext()) {
            read.add(new SpinnerObject(kursor.getInt(0), kursor.getString(1)));
        }
        return read;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
