package com.someshthakur.interviewexperiences;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExperienceActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ExperienceAdapter experienceAdapter;
    private List<Experience> experiencesList;
    private String companyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experience_list);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        experiencesList = new ArrayList<>();
        experienceAdapter = new ExperienceAdapter(this, experiencesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        companyName = getIntent().getStringExtra("company");
        final DatabaseReference myRef = database.getReference("companies").child(companyName);
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Loading Data from server");
        progress.setMessage("Please Wait...");
        progress.setCancelable(false);
        progress.show();

        myRef.child("Experinces").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                prepareExperincesList((Map<String, String>) dataSnapshot.getValue());
                progress.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        recyclerView.setAdapter(experienceAdapter);
    }

    private void prepareExperincesList(Map<String, String> exps) {
        Experience e;
        for (Map.Entry<String, String> exp : exps.entrySet()) {
            e = new Experience(companyName, exp.getKey(), exp.getValue());
            experiencesList.add(e);
        }
        experienceAdapter.notifyDataSetChanged();
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
