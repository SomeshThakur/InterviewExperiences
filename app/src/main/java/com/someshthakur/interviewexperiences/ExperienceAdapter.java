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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

/**
 * Created by Somesh Thakur on 24-08-2017.
 */

public class ExperienceAdapter extends RecyclerView.Adapter<ExperienceAdapter.MyViewHolder> {
    private List<Experience> experiencesList;
    private Context mContext;
    private int lastPosition = -1;
    private String experinceCount;

    public ExperienceAdapter(Context mContext, List<Experience> experiencesList) {
        this.mContext = mContext;
        this.experiencesList = experiencesList;
    }

    public void showExperience(View view, Experience exp, MyViewHolder holder) {
        Intent i = new Intent(mContext, ScrollingActivity.class);
        i.putExtra("title", exp.getTitle());
        i.putExtra("info", exp.getInfo());
        i.putExtra("company", exp.getCompany());
        mContext.startActivity(i);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.experience_item_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onViewDetachedFromWindow(MyViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.up_from_bottom
                        : R.anim.down_from_top);
        holder.itemView.startAnimation(animation);
        lastPosition = position;

        final Experience experience = experiencesList.get(position);
        holder.textView.setText(experience.getTitle().toString());
        final String company = experience.getCompany().toLowerCase();
        experinceCount = experience.getInfo();
        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://interview-experiences.appspot.com/images/" + company + ".png");
        Glide.with(mContext).using(new FirebaseImageLoader()).load(storageReference).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showExperience(view, experience, holder);
            }
        });
    }

    @Override
    public int getItemCount() {
        return experiencesList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        MyViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.textView);
            imageView = view.findViewById(R.id.expImage);
        }
    }
}
