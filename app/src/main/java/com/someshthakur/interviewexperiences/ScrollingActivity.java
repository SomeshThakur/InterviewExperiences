package com.someshthakur.interviewexperiences;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ScrollingActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView title, info;
    private String ExpCompany, ExpCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        imageView = findViewById(R.id.imageView);
        StorageReference comImg = FirebaseStorage.getInstance().getReference(getIntent().getStringExtra("company").toLowerCase() + ".png");
        Glide.with(this).using(new FirebaseImageLoader()).load(comImg).into(imageView);
        title = findViewById(R.id.exp_title);
        title.setText(getIntent().getStringExtra("title"));
        info = findViewById(R.id.exp_info);
        ExpCompany = getIntent().getStringExtra("company");
        ExpCount = getIntent().getStringExtra("info");

        final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(getIntent().getStringExtra("company").toLowerCase()
                + "-" + getIntent().getExtras().getString("info").toString() + ".txt");
        File localFile = null;
        try {
            localFile = File.createTempFile("content", "txt");
        } catch (IOException e) {
        }
        final File finalLocalFile = localFile;
        storageReference.getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        try {
                            FileReader fr = new FileReader(finalLocalFile);
                            BufferedReader br = new BufferedReader(fr);
                            String line;
                            while ((line = br.readLine()) != null)
                                info.append(line + "\n");
                        } catch (FileNotFoundException e) {
                        } catch (IOException e) {

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getApplicationContext(), "Cannot download file" + getIntent().getExtras().getString("company").toString().toLowerCase() + "-" + getIntent().getExtras().getString("info").toString() + ".txt", Toast.LENGTH_LONG).show();
            }
        });
        FloatingActionButton fab = findViewById(R.id.fav);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addtoFavList();
                Snackbar.make(view, "Added to favorite list", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void addtoFavList() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Responses").child("favList");
        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().
                getUid()).child(ExpCompany)
                .child(title.getText().toString()).setValue(ExpCount);

    }
}
