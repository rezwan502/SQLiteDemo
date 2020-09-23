package com.example.sqlitedatabasedemo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText nameEditText, ageEditText, genderEditText,idEditTextId;
    private Button addButton, showAllDataId, updateDataId, deleteDataId;

    MyDataBaseHelper myDataBaseHelper;

    public void data_send(){
        String Name = nameEditText.getText().toString();
        String Age = ageEditText.getText().toString();
        String Gender = genderEditText.getText().toString();

        long val = myDataBaseHelper.InsertData(Name,Age,Gender);

        Toast.makeText(getApplicationContext(), ""+val, Toast.LENGTH_LONG).show();
    }

    public void retrieve_data(){

        Cursor cursor = myDataBaseHelper.displayAllData();

        if(cursor.getCount() == 0){
            show_data("Error", "Empty");
            return;
        }

        StringBuffer stringBuffer = new StringBuffer();
        while(cursor.moveToNext()){
            stringBuffer.append("ID : " + cursor.getString(0) + "\n");
            stringBuffer.append("Name : " + cursor.getString(1) + "\n");
            stringBuffer.append("Age : " + cursor.getString(2) + "\n");
            stringBuffer.append("Gender : " + cursor.getString(3) + "\n\n\n");
        }
        show_data("DataSet ", stringBuffer.toString());

    }

    public void update_data(){

        String Name = nameEditText.getText().toString();
        String Age = ageEditText.getText().toString();
        String Gender = genderEditText.getText().toString();
        String Id = idEditTextId.getText().toString();

        myDataBaseHelper.updateData(Id,Name,Age,Gender);

    }

    public void delete_data(){
        String Id = idEditTextId.getText().toString();
        myDataBaseHelper.deleteData(Id);
    }

    public void show_data(String title, String data){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(data);
        builder.setCancelable(true);
        builder.show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDataBaseHelper = new MyDataBaseHelper(this);

        SQLiteDatabase sqLiteDatabase = myDataBaseHelper.getWritableDatabase();

        nameEditText = findViewById(R.id.nameEditTextId);
        ageEditText = findViewById(R.id.ageEditTextId);
        genderEditText = findViewById(R.id.genderEditTextId);
        addButton = findViewById(R.id.addButtonId);
        showAllDataId = findViewById(R.id.showAllDataId);
        idEditTextId = findViewById(R.id.idEditTextId);
        updateDataId = findViewById(R.id.updateDataId);
        deleteDataId = findViewById(R.id.deleteDataId);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data_send();
            }
        });

        showAllDataId.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                retrieve_data();
            }
        });

        updateDataId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_data();
            }
        });

        deleteDataId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete_data();
            }
        });
    }
}