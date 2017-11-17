package com.someshthakur.interviewexperiences;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FavoriteActivity extends AppCompatActivity {
    private FirebaseUser mAuth;
    private FirebaseDatabase database;
    private DatabaseReference storageReference;
    private TextView noFav;
    private List<Company> companyList;
    private RecyclerView recyclerView;
    private CompanyAdapter companyAdapter;
    private String companyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mAuth = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        storageReference = database.getReference();
        noFav = findViewById(R.id.noFav);
        companyList = new ArrayList<>();
        companyAdapter = new CompanyAdapter(this, companyList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.fav_recycler_view_list);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        // companyName = "Google";//getIntent().getStringExtra("company");
        final DatabaseReference myRef = database.getReference("Responses").child("favList").child(mAuth.getUid());

        SharedPreferences getPrefs = PreferenceManager
                .getDefaultSharedPreferences(getBaseContext());
        SharedPreferences.Editor e = getPrefs.edit();
        e.putString("path", "Responses/favList/" + mAuth.getUid() + "/");
        e.apply();
        //Toast.makeText(this, getPrefs.getString("path","NOt updated"), Toast.LENGTH_LONG).show();
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Loading Data from server");
        progress.setMessage("Please Wait...");
        progress.setCancelable(false);
        progress.show();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                noFav.setVisibility(View.INVISIBLE);
                prepareExperincesList((Map<String, String>) dataSnapshot.getValue());
                progress.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        recyclerView.setAdapter(companyAdapter);
    }

    private void prepareExperincesList(Map<String, String> comp) {
        for (Map.Entry<String, String> com : comp.entrySet()) {
            Company c = new Company(com.getKey());
            companyList.add(c);
        }
        companyAdapter.notifyDataSetChanged();
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
