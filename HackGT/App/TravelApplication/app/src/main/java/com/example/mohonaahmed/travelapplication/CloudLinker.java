package com.example.mohonaahmed.travelapplication;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.mohonaahmed.travelapplication.UploadImage.getFilePath;

public class CloudLinker extends AppCompatActivity implements AdapterView.OnItemSelectedListener,
        ImageClassifier.ClassifierCallback {

//    private final String[] filePaths = new String[]{
//            "personmainlyicecream.jpg","strawberry.jpg", "personguitar.jpg",  "banana.jpg",
//            "pineapple.jpg", "raspberry.jpg", "peach.jpg", "apple.jpg"};
    private String filePath_ = getFilePath();

    private ImageView imageView;
    private Bitmap selectedImage;
    private TextView labelsOverlay;
    //private Button btn_cloud;
    private List <String> labelsPass;

    ImageClassifier classifier;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUI();

        classifier = ImageClassifier.getInstance(this);

//        findViewById(R.id.btn_cloud).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                labelsOverlay.setText("");
//                classifier.executeCloud(selectedImage, CloudLinker.this);
//                passLabels();
//            }
//        });
    }
    private void setUI() {
        labelsOverlay = findViewById(R.id.graphicOverlay);
        imageView = findViewById(R.id.imageView);

        setSpinner();
    }
    public void passLabels(){
        Intent i = new Intent(this, CloudLinker.class);
        TextView _graph = findViewById(R.id.graphicOverlay);
        i.putExtra("labels", _graph.getText().toString());
        startActivity(i);
    }

    private void setSpinner() {
        Spinner spinner = findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout
                .simple_spinner_dropdown_item, getImageNames());
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }
    private List<String> getImageNames() {
        List<String> items = new ArrayList<>();
        //getting images .jpg list
        String name = "";
        for (int i = 0; i < filePath_.length(); i++)
        {
            name = filePath_.substring(0,filePath_.length()-4);
        }
        items.add(name);
        return items;
    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        labelsOverlay.setText("");
        selectedImage = getBitmapFromAsset(this, filePath_);
        if (selectedImage != null) {
            imageView.setImageBitmap(selectedImage);
        }
        //...

    }

    /*public void printLabels(){
        Intent i = new Intent(this, UploadImage.class);
        EditText _textView4 = (EditText) findViewById(R.id.textView4);
        i.putExtra("name", textView4.getText().toString());
    }*/

    public List<String> getLabels(List<String> labels)
    {
        labelsPass = labels;
        return labels;
    }



    //where labels, onclassified info gets sent to
    @Override
    public void onClassified(String modelTitle,
                             List<String> topLabels, long executionTime)
    {
        //***List<String> topLabels
        SpannableStringBuilder builder = new SpannableStringBuilder();
        StyleSpan boldSpan = new StyleSpan(android.graphics.Typeface.BOLD);
        builder.append(modelTitle + "\n\n", boldSpan, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        if (topLabels == Collections.EMPTY_LIST || topLabels.size() == 0)
            builder.append("No results..");

        else {
            for (String s : topLabels)
            {
                builder.append(s);
                builder.append("\n");
            }
            //PRINTS TO SCREEN HERE!!!!!!! using labelsOverlay
            labelsOverlay.setText(builder);
        }
        getLabels(topLabels);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    public static Bitmap getBitmapFromAsset(Context context, String filePath)
    {
        AssetManager assetManager = context.getAssets();
        InputStream is;
        Bitmap bitmap = null;
        try {
            is = assetManager.open(filePath);
            bitmap = BitmapFactory.decodeStream(is);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
