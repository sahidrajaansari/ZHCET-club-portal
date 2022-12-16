package com.example.zcm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zcm.dataholder.Clubdata;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditProfile extends AppCompatActivity {
    Uri uri;
    FirebaseDatabase database;
    ProgressDialog dialog;
    String Path;
    EditText clubDescription;
    AppCompatButton editBtn;
    TextView text2,About;
    TextInputLayout Password,clubAdmin;;
    TextInputEditText epassword,eclubAdmin;
    CircularProgressIndicator circularProgressIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        database = FirebaseDatabase.getInstance();
        Path = getIntent().getExtras().getString("number");
        clubDescription = findViewById(R.id.clubDescription);
        clubAdmin = findViewById(R.id.clubAdmin);
        eclubAdmin = findViewById(R.id.eclubAdmin);
        editBtn = findViewById(R.id.editBtn);
        text2=findViewById(R.id.text2);
        About=findViewById(R.id.About);
        Password=findViewById(R.id.Password);
        epassword=findViewById(R.id.epassword);
        circularProgressIndicator=findViewById(R.id.circularProgressIndicator);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("clubdata").child(Path);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Clubdata clubdata = snapshot.getValue(Clubdata.class);
                eclubAdmin.setText(clubdata.getUser());
                if (clubdata.getClubdescription().length() != 0) {

                    clubDescription.setText(clubdata.getClubdescription());
                }
                text2.setText("Club ID :"+clubdata.getClubid());
                epassword.setText(clubdata.getPass());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        clubDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String description = clubDescription.getText().toString();
                if (!description.isEmpty()) {
                    editBtn.setBackgroundDrawable(ContextCompat.getDrawable(EditProfile.this, R.drawable.follow_btn_bg));
                    editBtn.setTextColor(EditProfile.this.getResources().getColor(R.color.color2));
                    editBtn.setEnabled(true);
                } else {
                    editBtn.setBackgroundDrawable(ContextCompat.getDrawable(EditProfile.this, R.drawable.follow_active_btn));
                    editBtn.setTextColor(EditProfile.this.getResources().getColor(R.color.color6));
                    editBtn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        eclubAdmin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String description = eclubAdmin.getText().toString();
                String expression = "^[a-zA-Z\\s]+";
                String fullname=charSequence.toString();
                if(fullname.matches(expression)){
                    clubAdmin.setHelperText("Name*");
                    clubAdmin.setError("");
                }
                else{
                    clubAdmin.setHelperText("");
                    clubAdmin.setError("Only Alphabetic Character's Are Allowed");
                }
                if (!description.isEmpty()) {
                    editBtn.setBackgroundDrawable(ContextCompat.getDrawable(EditProfile.this, R.drawable.follow_btn_bg));
                    editBtn.setTextColor(EditProfile.this.getResources().getColor(R.color.color2));
                    editBtn.setEnabled(true);
                } else {
                    editBtn.setBackgroundDrawable(ContextCompat.getDrawable(EditProfile.this, R.drawable.follow_active_btn));
                    editBtn.setTextColor(EditProfile.this.getResources().getColor(R.color.color6));
                    editBtn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

            epassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editBtn.setBackgroundDrawable(ContextCompat.getDrawable(EditProfile.this, R.drawable.follow_active_btn));
                editBtn.setTextColor(EditProfile.this.getResources().getColor(R.color.color6));
                editBtn.setEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String password=charSequence.toString();
                if(password.length()>=6) {
                    Pattern pattern= Pattern.compile("[^a-zA-Z0-9]");
                    Matcher matcher=pattern.matcher(password);
                    boolean isPwdContainsSpeChar=matcher.find();
                    if(isPwdContainsSpeChar){
                        Password.setHelperText("Strong Password");
                        Password.setError("");
                    }
                    else{
                        Password.setHelperText("");
                        Password.setError("Weak Password, Include Minimum 1 special Character");
                    }

                }else{
                    Password.setHelperText("Enter Minimum 8 char");
                    Password.setError("");
                }
                String description = epassword.getText().toString();
                if (!description.isEmpty()) {
                    editBtn.setBackgroundDrawable(ContextCompat.getDrawable(EditProfile.this, R.drawable.follow_btn_bg));
                    editBtn.setTextColor(EditProfile.this.getResources().getColor(R.color.color2));
                    editBtn.setEnabled(true);
                } else {
                    editBtn.setBackgroundDrawable(ContextCompat.getDrawable(EditProfile.this, R.drawable.follow_active_btn));
                    editBtn.setTextColor(EditProfile.this.getResources().getColor(R.color.color6));
                    editBtn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                circularProgressIndicator.setVisibility(View.VISIBLE);
                database.getReference().child("clubdata").child(Path).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        database.getReference().child("clubdata").child(Path).child("user").setValue(eclubAdmin.getText().toString());
                        database.getReference().child("clubdata").child(Path).child("clubdescription").setValue(clubDescription.getText().toString());
                        database.getReference().child("clubdata").child(Path).child("pass").setValue(epassword.getText().toString());
                        circularProgressIndicator.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}