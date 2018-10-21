package com.example.mohonaahmed.travelapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.net.Uri;
import java.io.File;
import android.database.Cursor;
import android.provider.MediaStore;
import android.content.Context;
import java.io.InputStream;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.*;




public class UploadImage extends AppCompatActivity {

    private Button _button2;
    private ImageView _imageView;
    private static String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
        _button2 = findViewById(R.id.button2);


        TextView userNameDisplay = (TextView) findViewById(R.id.textView2);

        Intent i = getIntent();

        Bundle bd = i.getExtras();

        if (bd != null){
            String userNameText = (String) bd.get("name");
            userNameDisplay.setText("creating " + userNameText + "'s travel board");
        }
    }
    public void openActivity3(){
        Intent i = new Intent(this, CloudLinker.class);
        startActivity(i);
    }

    public void openBoard(){
        Intent j = new Intent(this, pictureBoard.class);
        startActivity(j);
    }

    public void onClick(View v) {

        Intent intent = new Intent();

        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1002);
    }
    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data)
    {
        super.onActivityResult(reqCode, resultCode, data);
        try {
            final Uri imageUri = data.getData();
            final InputStream imageStream = getContentResolver().openInputStream(imageUri);
            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            _imageView = findViewById(R.id.imageView5);
            _imageView.setImageBitmap(selectedImage);
            fileName = imageUri.toString();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String getFilePath()
    {
        return fileName;
    }

}
