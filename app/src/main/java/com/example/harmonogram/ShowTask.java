package com.example.harmonogram;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ShowTask extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView txt_owner;
    TextView txt_title;
    TextView txt_date;
    TextView txt_content;
    CheckBox check_ifdone;
    RadioButton rb_todo, rb_done;
    Button btn_confirm;
    Spinner spinner;
    DBController db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        txt_owner = (TextView) findViewById(R.id.txt_owner);
        txt_title = (TextView) findViewById(R.id.txt_title);
        txt_date = (TextView) findViewById(R.id.txt_date);
        txt_content = (TextView) findViewById(R.id.txt_content);
        check_ifdone = (CheckBox) findViewById(R.id.check_ifdone);
        btn_confirm = (Button) findViewById(R.id.btn_confirm);
        spinner = (Spinner) findViewById(R.id.spinner_tasks);
        rb_todo = (RadioButton) findViewById(R.id.rb_todo);
        rb_done = (RadioButton) findViewById(R.id.rb_done);

        txt_owner.setBackgroundColor(Color.YELLOW);
        txt_date.setBackgroundColor(Color.YELLOW);
        txt_title.setBackgroundColor(Color.YELLOW);
        txt_content.setBackgroundColor(Color.rgb(255,252,187));

        db = new DBController(getApplicationContext());

        List<SpinnerObject> tasksSpinner = db.getTaskSubject();
        ArrayAdapter<SpinnerObject> ArrayAdapter = new ArrayAdapter<SpinnerObject>(this, android.R.layout.simple_spinner_item, tasksSpinner);
        ArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(ArrayAdapter);
        spinner.setOnItemSelectedListener(this);

        btn_confirm.setEnabled(false);

        check_ifdone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(!check_ifdone.isChecked()) btn_confirm.setEnabled(false);
                if(check_ifdone.isChecked() && rb_todo.isChecked()) btn_confirm.setEnabled(true);
            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int Id = ((SpinnerObject)spinner.getSelectedItem()).getId();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());

                String title = txt_title.getText().toString();
                String content = txt_content.getText().toString();
                String owner = txt_owner.getText().toString();
                String date = txt_date.getText().toString();
                String now = sdf.format(new Date());

                content = content + "\nZakończono: "+ now;

                db.InsertTaskDone(new TaskDone(title, content, owner, date, now));
                db.DeleteTask(Id);
                AddSpinnerToDo();

                txt_owner.setText("");
                txt_title.setText("");
                txt_content.setText("");
                txt_date.setText("");

                Show_toast();
            }
        });
    }

    public void Show_toast() {
        Toast.makeText(this, "Pomyślnie wykonano zadanie!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int Id = ((SpinnerObject)spinner.getSelectedItem()).getId();
        if(rb_todo.isChecked()) {
            List<SpinnerObject> ListSubject = db.getTaskSubjectById(Id);
            ShowSubject(ListSubject);
            List<SpinnerObject> ListContent = db.getTaskContentById(Id);
            ShowContent(ListContent);
            List<SpinnerObject> ListOwner = db.getTaskOwnerById(Id);
            ShowOwner(ListOwner);
            List<SpinnerObject> ListDateTime = db.getTaskDateTimeById(Id);
            ShowDateTime(ListDateTime);
        }
        if(rb_done.isChecked()) {
            List<SpinnerObject> ListSubject = db.getTaskDoneSubjectById(Id);
            ShowSubject(ListSubject);
            List<SpinnerObject> ListContent = db.getTaskDoneContentById(Id);
            ShowContent(ListContent);
            List<SpinnerObject> ListOwner = db.getTaskDoneOwnerById(Id);
            ShowOwner(ListOwner);
            List<SpinnerObject> ListDateTime = db.getTaskDoneDateTimeById(Id);
            ShowDateTime(ListDateTime);
        }
    }

    private void ShowSubject(List<SpinnerObject> results)
    {
        for(SpinnerObject o: results){
            txt_title.setText(o.getValue());
        }
    }

    private void ShowContent(List<SpinnerObject> results)
    {
        for(SpinnerObject o: results){
            txt_content.setText(o.getValue());
        }
    }

    private void ShowOwner(List<SpinnerObject> results)
    {
        for(SpinnerObject o: results){
            txt_owner.setText(o.getValue());
        }
    }

    private void ShowDateTime(List<SpinnerObject> results)
    {
        for(SpinnerObject o: results){
            txt_date.setText(o.getValue());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.rb_todo:
                if (checked) {
                    check_ifdone.setEnabled(true);
                    btn_confirm.setEnabled(true);
                    check_ifdone.setChecked(false);
                    txt_owner.setBackgroundColor(Color.YELLOW);
                    txt_date.setBackgroundColor(Color.YELLOW);
                    txt_title.setBackgroundColor(Color.YELLOW);
                    txt_content.setBackgroundColor(Color.rgb(255,252,187));

                    txt_owner.setText("");
                    txt_title.setText("");
                    txt_content.setText("");
                    txt_date.setText("");

                    AddSpinnerToDo();
                } break;
            case R.id.rb_done:
                if (checked) {
                    check_ifdone.setEnabled(false);
                    btn_confirm.setEnabled(false);
                    check_ifdone.setChecked(true);
                    txt_owner.setBackgroundColor(Color.GREEN);
                    txt_date.setBackgroundColor(Color.GREEN);
                    txt_title.setBackgroundColor(Color.GREEN);
                    txt_content.setBackgroundColor(Color.rgb(152,251,152));

                    txt_owner.setText("");
                    txt_title.setText("");
                    txt_content.setText("");
                    txt_date.setText("");

                    AddSpinnerDone();
                } break;
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
}
