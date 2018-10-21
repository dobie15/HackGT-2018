package gt.hack.photogallery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class onImageClassifier {
    void processLocalResult(List labels, ClassifierCallback callback) {
        labels.sort(localLabelComparator);
        resultLabels.clear();
        FirebaseVisionLabel label;
        for (int i = 0; i < Math.min(2, labels.size()); ++i) {
            label = labels.get(i);
            resultLabels.add(label.getLabel() + “:” + label.getConfidence());
        }
        callback.onClassified(“Local Model”, resultLabels);
    }
}
