package com.someshthakur.interviewexperiences;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Somesh Thakur on 24-08-2017.
 */

public class ExperienceAdapter extends RecyclerView.Adapter<ExperienceAdapter.MyViewHolder> {
    private List<Experience> experiencesList;
    private Context mContext;
    //  private View view;
    private int lastPosition = -1;

    public ExperienceAdapter(Context mContext, List<Experience> experiencesList) {
        this.mContext = mContext;
        this.experiencesList = experiencesList;
    }

    public void showExperience(View view, Experience exp, MyViewHolder holder) {
        Intent i = new Intent(mContext, ScrollingActivity.class);
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext, holder.imageView, "sharedTitle");
        i.putExtra("title", exp.getExpText());
        i.putExtra("image", exp.getImage());
        mContext.startActivity(i, optionsCompat.toBundle());
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
        holder.textView.setText(experience.getExpText().toString());
        Glide.with(mContext).load(experience.getImage()).into(holder.imageView);
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

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.textView);
            imageView = (ImageView) view.findViewById(R.id.expImage);
        }
    }
}
