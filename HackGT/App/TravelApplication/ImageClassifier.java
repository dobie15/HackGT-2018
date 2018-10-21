package gt.hack.photogallery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ImageClassifier {
    localClassifier = new LocalClassifier();

    public void executeLocal(Bitmap bitmap, ClassifierCallback callback) {
        successListener = new OnSuccessListener<List>() {

            public void onSuccess(List labels) {
                processLocalResult(labels, callback, start);
            }
        };

        localClassifier.execute(bitmap, successListener, failureListener);
}
