package com.example.alanzhu39.mycontactsapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDB;
    EditText editName;
    EditText editPhone;
    EditText editAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MyContactApp","MainActivity: setting up layout");
        setContentView(R.layout.activity_main);

        editName = findViewById(R.id.editText_name);
        editPhone = findViewById(R.id.editText_phone);
        editAddress = findViewById(R.id.editText_address);

        myDB = new DatabaseHelper(this);
        Log.d("MyContactApp","MainActivity: instantiated DatabaseHelper");
    }

    public void addData(View view) {
        Log.d("MyContactApp","MainActivity: add contact button pressed");
        boolean isInserted = myDB.insertData(editName.getText().toString(),editPhone.getText().toString(),editAddress.getText().toString());
        if(isInserted) {
            Toast.makeText(MainActivity.this,"Success - contact inserted", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(MainActivity.this,"Failed - contact not inserted", Toast.LENGTH_LONG).show();
        }
    }

    public static final String EXTRA_MESSAGE = "com.example.alanzhu39.mycontactsapp.MESSAGE";
    public void SearchRecord(View view) {
        Log.d("MyContactsApp","MainActivity: launching SearchRecord");
        Intent intent = new Intent(this,SearchActivity.class);
        intent.putExtra(EXTRA_MESSAGE,editName.getText().toString());
        intent.putExtra(EXTRA_MESSAGE,editPhone.getText().toString());
        intent.putExtra(EXTRA_MESSAGE,editAddress.getText().toString());
        startActivity(intent);
    }

    public void viewData(View view) {
        Log.d("MyContactApp","MainActivity: View contact button pressed");
        Cursor res = myDB.getAllData();

        if(res.getCount() == 0) {
            showMessage("Error","no data cound in database");
        }

        StringBuffer buffer = new StringBuffer();
        buffer.append("ID: " + res.getString(0) + "\n");
        buffer.append("Name: " + res.getString(1) + "\n");
        buffer.append("Phone: " + res.getString(2) + "\n");
        buffer.append("Address: " + res.getString(3) + "\n");
        Log.d("MyContactApp","MainActivity: in viewData - buffer assembled");
        showMessage("Data",buffer.toString());

    }

    public void showMessage(String title, String message) {
        Log.d("MyContactApp","MainActivity: showMessage - building alert dialog");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
