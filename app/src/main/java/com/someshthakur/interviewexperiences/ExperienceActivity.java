package com.someshthakur.interviewexperiences;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ExperienceActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ExperienceAdapter experienceAdapter;
    private List<Experience> experiencesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experience_list);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_list);

        experiencesList = new ArrayList<>();
        experienceAdapter = new ExperienceAdapter(this, experiencesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        prepareExperincesList();
        recyclerView.setAdapter(experienceAdapter);
    }

    private void prepareExperincesList() {
        Experience e;
        for (int i = 1; i < 11; i++) {
            e = new Experience("This is Experience " + i, i, getIntent().getIntExtra("image", 0));
            experiencesList.add(e);
        }
        experienceAdapter.notifyDataSetChanged();
    }
}
