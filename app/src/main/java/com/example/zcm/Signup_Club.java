package com.example.zcm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zcm.dataholder.Clubdata;
import com.example.zcm.mailapi.JavaMailAPI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;

//import javax.mail.Message;

public class Signup_Club extends AppCompatActivity {
    ProgressBar progressBar;
    TextView btn, filepath;
    Uri clubprovepath;
    Button button, Clubid;
    TextInputLayout Clubname, Email, Password, Fullname;
    TextInputEditText eclubname, eemail, epassword, efullname;
    private final int CHOOSE_PDF_FROM_DEVICE = 1001;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_club);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        btn = findViewById(R.id.Signin);
        filepath = findViewById(R.id.filepath);
        Clubname = findViewById(R.id.Clubname);
//        Clubid=findViewById(R.id.Clubid);
        Email = findViewById(R.id.Email);
        Fullname = findViewById(R.id.Fullname);
        button = findViewById(R.id.button);
        Clubid = findViewById(R.id.Clubid);
        Password = findViewById(R.id.Password);
        eclubname = findViewById(R.id.eclubname);
//        eclubnumber=findViewById(R.id.eclubnumber);
        eemail = findViewById(R.id.eemail);
        epassword = findViewById(R.id.epassword);
        efullname = findViewById(R.id.efullname);
        button = findViewById(R.id.button);
        progressBar = findViewById(R.id.progressbar);
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("clubdata");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Signup_Club.this, Main_login.class));
                finish();
            }
        });

        Clubid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callchooseFileFromDevice();
            }
        });

        eclubname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String expression = "^[a-zA-Z\\s]+";
                String fullname = charSequence.toString();
                if (fullname.matches(expression)) {
                    Clubname.setHelperText("Enter Club Name");
                    Clubname.setError("");
                } else if(!fullname.matches(expression)){
                    Clubname.setHelperText("");
                    Clubname.setError("Only Alphabetic Character's Are Allowed");
                }
                if(efullname.length()==0){
                    Clubname.setHelperText("Enter Club Name");
                    Clubname.setError("");
                }
            }


            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        epassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String password = charSequence.toString();
                if (password.length() >= 6) {
                    Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
                    Matcher matcher = pattern.matcher(password);
                    boolean isPwdContainsSpeChar = matcher.find();
                    if (isPwdContainsSpeChar) {
                        Password.setHelperText("Strong Password");
                        Password.setError("");
                    } else {
                        Password.setHelperText("");
                        Password.setError("Weak Password, Include Minimum 1 special Character");
                    }

                } else {
                    Password.setHelperText("Enter Minimum 8 char");
                    Password.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        efullname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String expression = "^[a-zA-Z\\s]+";
                String fullname = charSequence.toString();
                if(fullname.matches(expression)){
                    Fullname.setHelperText("Enter Full Name");
                    Fullname.setError("");
                }
                else if(!fullname.matches(expression)){
                    Fullname.setHelperText("");
                    Fullname.setError("Only Alphabetic Character's Are Allowed");
                }
                if(efullname.length()==0){
                    Fullname.setHelperText("Enter Full Name");
                    Fullname.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
//                    Toast.makeText(clubsignup.this, "Hello", Toast.LENGTH_SHORT).show();
                    checkcredentials();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void checkcredentials() throws MessagingException {
        ProgressDialog progressDialog = new ProgressDialog(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        progressDialog.setTitle("File uploader ");
        progressDialog.show();
        progressBar.setVisibility(View.VISIBLE);
        String fullname = efullname.getText().toString();
        String clubname = eclubname.getText().toString();
        String mail = eemail.getText().toString();
        String pass = epassword.getText().toString();
        Boolean bclubname = checkclubname(clubname);
        Boolean bfullname = checkfullname(fullname);
        Boolean bmail = checkemail(mail);
        Boolean bpass = checkpassword(pass);
        if (bclubname && bfullname && bmail && bpass) {
            Email.setError(" ");
            Email.setHelperText("Enter valid Email");
            final String stringReciversEmail_admin = "gl9796@myamu.ac.in";
            final String stringReciversEmail_user = eemail.getText().toString();
            final String clubname_s = eclubname.getText().toString();
            final String fullname_s = efullname.getText().toString();
            final String pass_s = epassword.getText().toString();
            final String email_s = eemail.getText().toString();
            final String status_s = "0";
            final String profilepicture_s = "";
            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
            final StorageReference uploader = firebaseStorage.getReference("Club Prove " + clubname_s + (int) (System.currentTimeMillis()));
            uploader.putFile(clubprovepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            int clubid_s = new Random().nextInt(1000000) + 100000;
                            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                            DatabaseReference root = firebaseDatabase.getReference("clubdata");
                            Clubdata clubdata = new Clubdata(fullname_s, clubname_s, pass_s, uri.toString(), email_s, status_s, profilepicture_s, String.valueOf(clubid_s),"");
//                            int clubid_s = ThreadLocalRandom.current().nextInt(100000, 999999);
                            root.child(String.valueOf(clubid_s)).setValue(clubdata);
                            final String Subject = "ZCM Club Registration";
                            final String body_admin = "My Name is " + fullname_s + "\nClub Name :-" + clubname_s + "\nPassword :-" + pass_s + "\nYour Email Id :-" + email_s + "\n\nClub Prove :" + uri.toString()+"\nRepo Link : https://console.firebase.google.com/u/0/project/zcm-media/database/zcm-media-default-rtdb/data/~2F";
                            JavaMailAPI javaMailAPI = new JavaMailAPI(Signup_Club.this, stringReciversEmail_admin, Subject, body_admin);
                            javaMailAPI.execute();
//                            final String body_user="Your Club Registartion Is Sent For Validation Your \nClub Id:"+clubdata.getClubid()+" \nYou Would Be Notified for Validation.";
//                            JavaMailAPI javaMailAPI2 = new JavaMailAPI(Signup_Club.this, stringReciversEmail_user, Subject, body_user);
//                            javaMailAPI.execute();
                            progressBar.setVisibility(View.INVISIBLE);
                            progressDialog.dismiss();
                            startActivity(new Intent(Signup_Club.this, Main_login.class));
                            finish();
                            Toast.makeText(Signup_Club.this, "Mail Sent For Verification", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    float percent = (100 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                    progressDialog.setMessage("Uploaded :" + (int) percent + " %");
                }
            });
        }
    }

    private Boolean checkemail(String mail) {
        if (mail.isEmpty()) {
            Email.setHelperText("");
            Email.setError("Enter Valid E-mail");
            return false;
        } else if (!mail.contains("@myamu.ac.in")) {
            Email.setHelperText("");
            Email.setError("Enter Valid College Id");
            return false;
        }
        return true;
    }

    private Boolean checkpassword(String pass) {
        if (pass.isEmpty()) {
            Password.setHelperText("");
            Password.setError("Enter Valid E-mail");
            return false;
        } else if (pass.length() < 7) {
            Password.setHelperText("");
            Password.setError("Enter Minimum 7 Characters ");
            return false;
        }
        return true;
    }


    private Boolean checkclubname(String clubname) {
        String expression = "^[a-zA-Z\\s]+";
        if (clubname.isEmpty()) {
            Clubname.setHelperText("");
            Clubname.setError("Enter Valid E-mail");
            return false;
        } else if (!clubname.matches(expression)) {
            return false;
        }
        return true;
    }

    private Boolean checkfullname(String fullname) {
        String expression = "^[a-zA-Z\\s]+";
        if (fullname.isEmpty()) {
            Fullname.setHelperText("");
            Fullname.setError("Enter Valid E-mail");
            return false;
        } else if (!fullname.matches(expression)) {
            return false;
        }
        return true;
    }

    private void callchooseFileFromDevice() {
        Dexter.withActivity(Signup_Club.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("application/pdf");
                startActivityForResult(intent, CHOOSE_PDF_FROM_DEVICE);
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).check();

    }

    @Override
    public void onActivityResult(int requestCode, int resultcode, Intent resultdata) {
        super.onActivityResult(requestCode, resultcode, resultdata);
        if (requestCode == CHOOSE_PDF_FROM_DEVICE && resultcode == Activity.RESULT_OK) {
            clubprovepath = resultdata.getData();
            if (resultdata != null) {
                filepath.setText("Prove Uploaded");
            }
        }
    }
}