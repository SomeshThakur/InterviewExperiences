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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.up_from_bottom
                        : R.anim.down_from_top);
        holder.itemView.startAnimation(animation);
        lastPosition = position;

        final Company company = companiesList.get(position);
        final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(company.getTitle().toLowerCase() + ".png");

        Glide.with(mContext).using(new FirebaseImageLoader()).load(storageReference).into(holder.banner);
        holder.banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInterviewsList(company.getTitle());
            }
        });
    }

    public void showInterviewsList(String companyName) {
        Intent i = new Intent(mContext, ExperienceActivity.class);
        i.putExtra("company", companyName);
        mContext.startActivity(i);
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