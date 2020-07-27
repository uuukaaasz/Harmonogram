package com.example.harmonogram;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddTask extends AppCompatActivity {
    ImageButton btn_save;
    EditText txt_owner;
    EditText txt_title;
    EditText txt_content;

    String local_path = Environment.getExternalStorageDirectory().getAbsolutePath();
    File path = new File(local_path);

    DBController db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        btn_save = (ImageButton) findViewById(R.id.btn_save);
        txt_owner = (EditText) findViewById(R.id.txt_owner);
        txt_title = (EditText) findViewById(R.id.txt_title);
        txt_content = (EditText) findViewById(R.id.txt_content);
    }

    public void saveText(View view) {
        String owner = txt_owner.getText().toString();
        String title = txt_title.getText().toString();
        String content = txt_content.getText().toString();

        if (owner.matches("")) {
            Toast.makeText(this, "Wprowadź wykonawcę!", Toast.LENGTH_SHORT).show();
            return;
        } else if (title.matches("")) {
            Toast.makeText(this, "Wprowadź tytuł zadania!", Toast.LENGTH_SHORT).show();
            return;
        } else if (content.matches("")) {
            Toast.makeText(this, "Wprowadź treść zadania!", Toast.LENGTH_SHORT).show();
            return;
        }

        String data_to_file = title + "\n" + content;
        String fileName = owner + " > " + title + ".txt";

        String file_result = saveToFile(data_to_file, fileName);
        String database_result = saveToDatabase(owner, title, content);

        Toast.makeText(this, file_result + "\n" + database_result, Toast.LENGTH_SHORT).show();
    }

    public String saveToFile(String data_to_file, String fileName) {
        String result = "Wykonano backup zadania do pliku: "+ fileName;
        try {
            File save_to_file = new File(path, fileName);
            FileWriter writer = new FileWriter(save_to_file);
            writer.append(data_to_file);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Błąd: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    public String saveToDatabase(String owner, String title, String content) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        String currentDateTime = sdf.format(new Date());

        db = new DBController(getApplicationContext());
        db.InsertTask(new Task(title, content, owner, currentDateTime));

        txt_owner.setText("");
        txt_title.setText("");
        txt_content.setText("");

        String result = "\nZapisano w bazie danych!";
        return result;
    }
}
