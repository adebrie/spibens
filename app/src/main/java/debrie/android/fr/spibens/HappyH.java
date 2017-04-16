package debrie.android.fr.spibens;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class HappyH extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_happyh);

        StorageReference mStorageRef;

        mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://spibens-331c8.appspot.com/").child("flyer.png");

        File localFile = null;
        try {
            localFile = File.createTempFile("flyer", "png");
        } catch (IOException e) {
            e.printStackTrace();
        }

        mStorageRef.getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        // Successfully downloaded data to local file
                        // ...
                        System.out.println("flyer downloaded");

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle failed download
                System.out.println("Download Failed");
                // ...
            }
        });
    }
}
