package com.someshthakur.interviewexperiences;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileWriter;

public class ShareExperienceActivity extends AppCompatActivity {
    Spinner spinner;
    private EditText expInfo, expTitle;
    private FirebaseAuth mAuth;
    private TextView username, userenmail;
    private ImageView userpic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_experience);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        expInfo = findViewById(R.id.expText);
        expTitle = findViewById(R.id.expTitle);
        mAuth = FirebaseAuth.getInstance();

        username = findViewById(R.id.username);
        userenmail = findViewById(R.id.useremail);
        userpic = findViewById(R.id.userPic);

        userenmail.setText(mAuth.getCurrentUser().getEmail().toString());
        username.setText(mAuth.getCurrentUser().getDisplayName());
        Glide.with(this).load(mAuth.getCurrentUser().getPhotoUrl()).into(userpic);

        spinner = findViewById(R.id.companiesDropdown);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(1);

        FloatingActionButton fab = findViewById(R.id.submit);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (expInfo.getText().toString().trim().equals("")) {
                    expInfo.setError("Empty?");
                } else {
                    submitResponse(expInfo.getText().toString());
                    Toast.makeText(getApplicationContext(), "Submitted to server", Toast.LENGTH_LONG).show();
                    onBackPressed();
                }
            }
        });

    }

    private void submitResponse(final String info) {
        final int[] count = new int[1];
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Query lastQuery = databaseReference.child("companies").child(spinner.getSelectedItem().toString()).child("Experinces").orderByValue().limitToLast(1);
        lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue().toString();
                count[0] = Integer.parseInt(value.substring(value.indexOf("=") + 1, value.indexOf("}")));
                count[0] += 1;
            }

            @Override

            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final DatabaseReference databaseReference2 = database.getReference("Responses")
                .child("company")
                .child(spinner.getSelectedItem().toString())
                .child(expTitle.getText().toString());
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        File localFile = null;
        try {
            Thread.sleep(100);
            localFile = new File(Environment.getExternalStorageDirectory() + "/" + spinner.getSelectedItem().toString().toLowerCase() + "-" + count[0] + ".txt");
            FileWriter fr = new FileWriter(localFile);
            fr.write(info);

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Cant write " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        Uri file = Uri.fromFile(new File(localFile.toURI()));
        StorageReference riversRef = storageReference.child(file.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(file);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getApplicationContext(), "Failed to upload file", Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                databaseReference2.setValue(count[0]);
                Toast.makeText(getApplicationContext(), "Successfully uploaded file", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
