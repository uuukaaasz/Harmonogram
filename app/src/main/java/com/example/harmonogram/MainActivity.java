package com.example.harmonogram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnAdd, btnShow, btnDelete, btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        btnShow = findViewById(R.id.btnShow);
        btnDelete = findViewById(R.id.btnDelete);
        btnExit = findViewById(R.id.btnExit);

        btnAdd.setOnClickListener(this);
        btnShow.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdd:
                moveTo(AddTask.class);
                break;
            case R.id.btnShow:
                moveTo(ShowTask.class);
                break;
            case R.id.btnDelete:
                moveTo(RemoveTask.class);
                break;
            case R.id.btnExit:
                finish();
                System.exit(0);
        }
    }

    public void moveTo(Class c) {
        Intent intent = new Intent(getApplicationContext(),c);
        startActivity(intent);
    }
}
