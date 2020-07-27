package com.example.harmonogram;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.List;

public class RemoveTask extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public boolean RBcheck;
    Spinner spinner;
    Button btn_del;
    CheckBox cb_database, cb_file;
    DBController db;
    RadioButton rb_todo, rb_done;

    String local_path = Environment.getExternalStorageDirectory().getAbsolutePath();
    File path = new File(local_path);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        spinner = (Spinner) findViewById(R.id.spinner_delete);
        cb_database = (CheckBox) findViewById(R.id.check_del_database);
        cb_file = (CheckBox) findViewById(R.id.check_del_file);
        btn_del = (Button) findViewById(R.id.btn_del);

        RBcheck = true;

        db = new DBController(getApplicationContext());

        AddSpinnerToDo();

        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    int Id = ((SpinnerObject) spinner.getSelectedItem()).getId();
                    String title = "";
                    String owner = "";

                    if (RBcheck == true) {
                        List<SpinnerObject> ListOwner = db.getTaskOwnerById(Id);
                        owner = GetOwner(ListOwner);
                        List<SpinnerObject> ListSubject = db.getTaskSubjectById(Id);
                        title = GetSubject(ListSubject);
                    }
                    if (RBcheck == false) {
                        List<SpinnerObject> ListOwner = db.getTaskDoneOwnerById(Id);
                        owner = GetOwner(ListOwner);
                        List<SpinnerObject> ListSubject = db.getTaskDoneSubjectById(Id);
                        title = GetSubject(ListSubject);
                    }
                    if (cb_database.isChecked()) {
                        deleteFromDatabase();
                    }
                    if (cb_file.isChecked()) {
                        deleteFile(title, owner);
                    }
            }
        });
    }

    public String GetOwner (List<SpinnerObject> results)
    {
        String MyOwner = "";
        for(SpinnerObject o: results){
            MyOwner = o.getValue();
        }
        return MyOwner;
    }

    public String GetSubject (List<SpinnerObject> results)
    {
        String MySubject = "";
        for(SpinnerObject o: results){
            MySubject = o.getValue();
        }
        return MySubject;
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.rb_todo:
                if (checked) {
                    AddSpinnerToDo();
                    RBcheck = true;
                } break;
            case R.id.rb_done:
                if (checked) {
                    AddSpinnerDone();
                    RBcheck = false;
                } break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void deleteFromDatabase () {
        int Id = ((SpinnerObject)spinner.getSelectedItem()).getId();
        if(RBcheck == true) {
            db.DeleteTask(Id);
            AddSpinnerToDo();
        }

        if(RBcheck == false) {
            db.DeleteTaskDone(Id);
            AddSpinnerDone();
        }
        Toast.makeText(this,"Usunięto z bazy.",Toast.LENGTH_LONG).show();
    }

    public void deleteFile(String title, String owner) {
        String fileName = owner + " > " + title + ".txt";
        if (fileName.matches(".txt")) {
            Toast.makeText(this, "Wprowadź nazwę pliku", Toast.LENGTH_SHORT).show();
            return;
        }

        File file_to_delete = new File(path, fileName);
        if(!file_to_delete.exists()){
            Toast.makeText(this,"Plik nie istnieje.",Toast.LENGTH_LONG).show();
        }
        else {
            file_to_delete.delete();
            Toast.makeText(this, "Usunięto " + fileName, Toast.LENGTH_SHORT).show();
        }
    }

    public void AddSpinnerToDo () {
        db = new DBController(getApplicationContext());
        List<SpinnerObject> tasksSpinner = db.getTaskSubject();
        ArrayAdapter<SpinnerObject> ArrayAdapter = new ArrayAdapter<SpinnerObject>(this, android.R.layout.simple_spinner_item, tasksSpinner);
        ArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(ArrayAdapter);
        spinner.setOnItemSelectedListener(this);
    }

    public void AddSpinnerDone () {
        db = new DBController(getApplicationContext());
        List<SpinnerObject> tasksSpinner2 = db.getTaskDoneSubject();
        ArrayAdapter<SpinnerObject> ArrayAdapter2 = new ArrayAdapter<SpinnerObject>(this, android.R.layout.simple_spinner_item, tasksSpinner2);
        ArrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(ArrayAdapter2);
        spinner.setOnItemSelectedListener(this);
    }

    public void ShowToast() {
        Toast.makeText(this,"Nie zaznaczono checkBoxa!",Toast.LENGTH_LONG).show();
    }
}