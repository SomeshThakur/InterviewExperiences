package com.someshthakur.interviewexperiences;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ScrollingActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView title, info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        imageView = (ImageView) findViewById(R.id.imageView);
        // Glide.with(this).load(getIntent().getExtras().getInt("image")).into(imageView);
        title = (TextView) findViewById(R.id.exp_title);
        title.setText(getIntent().getExtras().getString("title").toString());
        info = (TextView) findViewById(R.id.exp_info);
        info.setText(getIntent().getExtras().getString("info").toString());
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fav);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Action will be added here", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
