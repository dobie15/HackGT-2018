package com.playground.britt.mlkitdemo;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener,
                   ImageClassifier.ClassifierCallback
{
    private final String[] filePaths =
            new String[]{"personmainlyicecream.jpg","strawberry.jpg", "personguitar.jpg",  "banana.jpg",
                    "pineapple.jpg", "raspberry.jpg", "peach.jpg", "apple.jpg"};

    private ImageView imageView;
    private Bitmap selectedImage;
    private TextView labelsOverlay;

    ImageClassifier classifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUI();

        classifier = ImageClassifier.getInstance(this);

        findViewById(R.id.btn_cloud).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                labelsOverlay.setText("");
                classifier.executeCloud(selectedImage, MainActivity.this);
            }
        });
    }

    private void setUI() {
        labelsOverlay = findViewById(R.id.graphicOverlay);
        imageView = findViewById(R.id.imageView);

        setSpinner();
    }

    private void setSpinner() {
        Spinner spinner = findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout
                .simple_spinner_dropdown_item, getImageNames());
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }
    //getting hardcoded image files .jpeg,
    // naming the sections on app by file name
    //**can remove later
    private List<String> getImageNames() {
        List<String> items = new ArrayList<>();
        //getting images .jpg list
        for (int i = 0; i < filePaths.length; i++)
        {
            String name = filePaths[i].substring(0,filePaths[i].length()-4);
            items.add(name);
            //items.add("Image " + (i + 1));
        }
        return items;
    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        labelsOverlay.setText("");
        selectedImage = getBitmapFromAsset(this, filePaths[position]);
        if (selectedImage != null) {
            imageView.setImageBitmap(selectedImage);
        }

    }
    //where labels, onclassified info gets sent to
    @Override
    public void onClassified(String modelTitle,
                             List<String> topLabels, long executionTime)
    {
     //***List<String> topLabels contains builder: some sort of infinite space holding strings
        SpannableStringBuilder builder = new SpannableStringBuilder();
        StyleSpan boldSpan = new StyleSpan(android.graphics.Typeface.BOLD);
        //adjust center visuals
        builder.append(modelTitle + "\n\n", boldSpan, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        //below, is for visuals,
        // builder ^ contains the data for output display
        if (topLabels == Collections.EMPTY_LIST || topLabels.size() == 0) {
            builder.append("No results..");
        }
        else {
            for (String s : topLabels) {
                builder.append(s);
                //adds new line between labels, adds to builder span
                builder.append("\n");
            }
            //PRINTS TO SCREEN HERE!!!!!!! using labelsOverlay
            labelsOverlay.setText(builder);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    public static Bitmap getBitmapFromAsset(Context context, String filePath) {
        AssetManager assetManager = context.getAssets();

        InputStream is;
        Bitmap bitmap = null;
        try {
            is = assetManager.open(filePath);
            bitmap = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

}
