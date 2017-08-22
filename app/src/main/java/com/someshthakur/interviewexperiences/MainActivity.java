package com.someshthakur.interviewexperiences;
//Test
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CompanyAdapter companyAdapter;
    private List<Company> companyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        companyList = new ArrayList<>();
        companyAdapter = new CompanyAdapter(this, companyList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(companyAdapter);

        prepareComapaniesList();
    }

    private void prepareComapaniesList() {
        int banner[] = {
                R.drawable.amazon_banner,
                R.drawable.google_banner,
                R.drawable.microsoft_banner,
                R.drawable.oracle_banner,
                R.drawable.qualcomm_banner
        };
        Company c = new Company("Amazon", banner[0]);
        companyList.add(c);
        c = new Company("Google", banner[1]);
        companyList.add(c);
        c = new Company("Microsoft", banner[2]);
        companyList.add(c);
        c = new Company("Oracle", banner[3]);
        companyList.add(c);
        c = new Company("Qualcomm", banner[4]);
        companyList.add(c);
        c = new Company("Google", banner[1]);
        companyList.add(c);
        c = new Company("Google", banner[1]);
        companyList.add(c);
        companyAdapter.notifyDataSetChanged();
    }
}
