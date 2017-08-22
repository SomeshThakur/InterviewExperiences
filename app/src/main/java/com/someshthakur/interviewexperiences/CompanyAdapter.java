package com.someshthakur.interviewexperiences;


import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Somesh Thakur on 21-08-2017.
 */

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.MyViewHolder> {
    private List<Company> companiesList;
    private Context mContext;
    private View view;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView banner;

        public MyViewHolder(View view) {
            super(view);
            banner = (ImageView) view.findViewById(R.id.banner);
        }
    }


    public CompanyAdapter(Context mContext, List<Company> comapaniesList) {
        this.mContext = mContext;
        this.companiesList = comapaniesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.company_banner_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Company company = companiesList.get(position);
        Glide.with(mContext).load(company.getImage()).into(holder.banner);
        holder.banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInterviewsList(view, company);
            }
        });

    }

    public void showInterviewsList(View view, Company company) {
        Snackbar.make(view, "You clicked on " + company.getTitle(), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public int getItemCount() {
        return companiesList.size();
    }
}