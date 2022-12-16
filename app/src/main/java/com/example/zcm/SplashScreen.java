package com.example.zcm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    ImageView icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        FirebaseAuth auth=FirebaseAuth.getInstance();
        FirebaseUser user=auth.getCurrentUser();
        icon=findViewById(R.id.icon);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                if(user==null){
                    startActivity(new Intent(SplashScreen.this,Main_login.class));
//                }else{
//                    startActivity(new Intent(SplashScreen.this,Main_Student.class));
//                }
                finish();
            }
        },2500);
    }
}