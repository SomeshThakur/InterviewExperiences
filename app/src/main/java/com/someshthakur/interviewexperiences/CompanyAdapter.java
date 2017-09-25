package com.someshthakur.interviewexperiences;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
    private int lastPosition = -1;

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
        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.up_from_bottom
                        : R.anim.down_from_top);
        holder.itemView.startAnimation(animation);
        lastPosition = position;


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
        Intent i = new Intent(mContext, ExperienceActivity.class);
        i.putExtra("image", company.getImage());
        mContext.startActivity(i);

        //  ((Activity)mContext).overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

    }

    @Override
    public int getItemCount() {
        return companiesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView banner;

        public MyViewHolder(View view) {
            super(view);
            banner = (ImageView) view.findViewById(R.id.banner);
        }
    }


}