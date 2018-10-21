package gt.hack.photogallery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CloudClassifier {

    detector = FirebaseVision.getInstance().getVisionCloudLabelDetector();
    FirebaseVisionImage image;
    public void execute(Bitmap bitmap, OnSuccessListener successListener, OnFailureListener failureListener){
        image = FirebaseVisionImage.fromBitmap(bitmap);
        .addOnSuccessListener(successListener);
        .addOnFailureListener(failureListener);
    }
}
