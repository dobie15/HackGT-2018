package com.example.mohonaahmed.travelapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private Button _button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _button1 = findViewById(R.id.button1);
        }

    public void openActivity2(){
        Intent i = new Intent(this, UploadImage.class);
        EditText editTextUsername = (EditText) findViewById(R.id.usernameEditText);
        i.putExtra("name", editTextUsername.getText().toString());
        startActivity(i);
    }

    public void onClick(View v){
        openActivity2();
    }


}
