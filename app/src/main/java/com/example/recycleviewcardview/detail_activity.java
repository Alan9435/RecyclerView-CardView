package com.example.recycleviewcardview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class detail_activity extends AppCompatActivity {
    TextView textId,textLikes;
    ImageView mimg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_activity);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        Intent intent = getIntent();

        mimg = findViewById(R.id.img);
        textId = findViewById(R.id.textId);
        textLikes = findViewById(R.id.textLike);

        String img = intent.getStringExtra("img");
        String id = "ID: " + intent.getStringExtra("id");
        String likes = "Likes: " + intent.getStringExtra("likes");

        Picasso.with(this).load(img).fit().centerInside().into(mimg);
        textId.setText(id);
        textLikes.setText(likes);
    }
}