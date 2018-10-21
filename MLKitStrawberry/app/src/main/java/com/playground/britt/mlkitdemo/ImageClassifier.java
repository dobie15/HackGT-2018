package com.playground.britt.mlkitdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.cloud.label.FirebaseVisionCloudLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionLabel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.NonNull;

class ImageClassifier {

    public static final int RESULTS_TO_SHOW = 3;
    //yields why less images show up
    public static final float CONFIDENCE_THRESHOLD = 0.80f;

    //classifiercallback intereface, used below
    interface ClassifierCallback
    {
        void onClassified(String modelTitle, List<String> topLabels, long executionTime);
    }

    private static ImageClassifier instance;
    private final Context appContext;
    //private LocalClassifier localClassifier;
    private CloudClassifier cloudClassifier;

    private List<String> resultLabels = new ArrayList<>();

    private OnFailureListener failureListener = new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            handleError(e);
        }
    };

    //used to sort in order of confidence
    private Comparator<FirebaseVisionCloudLabel> cloudLabelComparator = new Comparator<FirebaseVisionCloudLabel>() {
        @Override
        public int compare(FirebaseVisionCloudLabel label1, FirebaseVisionCloudLabel label2) {
            return (int) (label1.getConfidence() - label2.getConfidence());
        }
    };

    static synchronized public ImageClassifier getInstance(Context context) {
        if (instance == null) {
            instance = new ImageClassifier(context.getApplicationContext());
        }
        return instance;
    }

    private ImageClassifier(Context appContext) {
        this.appContext = appContext;
        this.cloudClassifier = new CloudClassifier();
    }
    //execute cloud called in MAIN ==> uses callback
    //callback contains labels (+confidence score?)
    public void executeCloud(Bitmap bitmap, final ClassifierCallback callback)
    {
        final long start = System.currentTimeMillis();

        OnSuccessListener<List<FirebaseVisionCloudLabel>> successListener = new OnSuccessListener<List<FirebaseVisionCloudLabel>>() {
            @Override
            public void onSuccess(List<FirebaseVisionCloudLabel> labels) {
                processCloudResult(labels, callback, start);
            }
        };
        cloudClassifier.execute(bitmap, successListener, failureListener);
    }

    //printing to screen
    private synchronized void processCloudResult(List<FirebaseVisionCloudLabel> labels,
                                                 ClassifierCallback callback,
                                                 long start)
    {
        //labels == all labels found,
        //then sorts all labels by confidence
        labels.sort(cloudLabelComparator);
        labels.size();
        resultLabels.clear();

        //getting 3 labels, adding them to label "cloud"
        FirebaseVisionCloudLabel label;
        int count = 0;

        //using math.min because .size() could yield to be < RESULTS_SHOW
        for(int i=0; count < Math.min(RESULTS_TO_SHOW, labels.size())
                    && i < labels.size(); i++)
        {
            if(!labels.get(i).getLabel().equals("male") &&
               !labels.get(i).getLabel().equals("guy") &&
               !labels.get(i).getLabel().equals("men") &&
               !labels.get(i).getLabel().equals("man") &&
               !labels.get(i).getLabel().equals("female") &&
               !labels.get(i).getLabel().equals("gal") &&
               !labels.get(i).getLabel().equals("woman") &&
               !labels.get(i).getLabel().equals("women") &&
               !labels.get(i).getLabel().equals("boy") &&
               !labels.get(i).getLabel().equals("girl") &&
               !labels.get(i).getLabel().equals("person") &&
               !labels.get(i).getLabel().equals("people"))
            {
                label = labels.get(i);
                //arraylist: adds label to list (to get number: .getConfidence)
                resultLabels.add(label.getLabel());
                count++;
            }
        }
//***GET LABELS HERE****\\
        //passes labels to MainActivity
        callback.onClassified("Cloud Labels", resultLabels, System.currentTimeMillis() - start);

        //below code was original: gets top 3 labels
//        for (int i = 0; i < Math.min(RESULTS_TO_SHOW, labels.size()); ++i) {
//            label = labels.get(i);
//            resultLabels.add(label.getLabel());
//            //resultLabels.add(label.getLabel() + ":" + label.getConfidence());
//        }

    }

    private void handleError(Exception e) {
        Toast.makeText(appContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        e.printStackTrace();
    }
}