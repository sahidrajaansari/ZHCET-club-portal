package com.example.zcm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class SinglePost extends AppCompatActivity {
    ImageView postImg;
    TextView postDescription;
    String postDEscription, postImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_post);
        postImg = findViewById(R.id.postImg);
        postDescription = findViewById(R.id.postDescription);
        postDEscription = getIntent().getExtras().getString("postDescription");
        postImage = getIntent().getExtras().getString("postImg");
        postDescription.setText(postDEscription);

        Glide.with(postImg.getContext()).load(postImage).into(postImg);
    }
}