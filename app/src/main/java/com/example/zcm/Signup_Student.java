package com.example.zcm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zcm.dataholder.StudentData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup_Student extends AppCompatActivity {
    TextView btn;
    Button button;
    TextInputLayout Fullname,Facultynumber,Email,Password;
    TextInputEditText efullname,efacultynumber,eemail,epassword;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    ProgressBar bar;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_student);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        TextView btn=findViewById(R.id.Signin);
        Fullname=findViewById(R.id.Fullname);
        Facultynumber=findViewById(R.id.Facultynumber);
        Email=findViewById(R.id.Email);
        button=findViewById(R.id.button);
        bar=findViewById(R.id.progressbar);
        Password=findViewById(R.id.Password);
        efullname=findViewById(R.id.efullname);
        efacultynumber=findViewById(R.id.efacultynumber);
        eemail=findViewById(R.id.eemail);
        epassword=findViewById(R.id.epassword);
        button=findViewById(R.id.button);
        firebaseDatabase=FirebaseDatabase.getInstance();
        reference=firebaseDatabase.getReference("userdata");
        bar.setVisibility(View.INVISIBLE);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Signup_Student.this, Main_login.class));
                finish();
            }
        });
        efullname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String expression = "^[a-zA-Z\\s]+";
                String fullname=charSequence.toString();
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
        efacultynumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String facnumber=charSequence.toString();
                String expression = "^[0-9a-zA-Z\\s]+";
                Facultynumber.setError("");
                if(facnumber.length()>=5){
                    if (!facnumber.matches(expression)) {
                        Facultynumber.setHelperText("");
                        Facultynumber.setError("Only Alphanumeric Character's are allowed");
                    }
                    else if ((!facnumber.contains("COB")) && (!facnumber.contains("MEB")) && (!facnumber.contains("EEB")) && (!facnumber.contains("EEB")) && (!facnumber.contains("ELB")) && (!facnumber.contains("CEB")) && (!facnumber.contains("CHB"))) {
                        Facultynumber.setHelperText("");
                        Facultynumber.setError("Invalid Faculty Number");
                    }
                }
                else {
                    if (facnumber.length()>=1&&!facnumber.matches(expression)) {
                        Facultynumber.setHelperText("");
                        Facultynumber.setError("Only Alphanumeric Character's are allowed");
                    }
                    else{
                        Facultynumber.setHelperText("Enter valid Faculty Number");
                        Facultynumber.setError("");
                    }
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
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkcredentials();
            }
        });

    }

    private void checkcredentials() {
        String name=efullname.getText().toString();
        String number=efacultynumber.getText().toString();
        String mail=eemail.getText().toString();
        String pass=epassword.getText().toString();
        Boolean bname=checkfullname(name);
        Boolean bnumber=checkfacultynumber(number);
        Boolean bmail=checkemail(mail);
        Boolean bpass=checkpassword(pass);
        if( bname==true && bnumber==true && bmail==true && bpass==true){
            bar.setVisibility(View.VISIBLE);
            String fullname_s=efullname.getText().toString();
            String facultynumber_s=efacultynumber.getText().toString();
            mAuth = FirebaseAuth.getInstance();
            String email_s=eemail.getText().toString();
            String pass_s=epassword.getText().toString();
            mAuth.createUserWithEmailAndPassword(email_s,pass_s).addOnCompleteListener(Signup_Student.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        FirebaseUser user=mAuth.getCurrentUser();
                        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                bar.setVisibility(View.INVISIBLE);
                                eemail.setText(" ");
                                epassword .setText(" ");
                                String Uid=mAuth.getUid();
                                StudentData storingData =new StudentData(fullname_s,facultynumber_s,Uid);
                                reference.child(facultynumber_s).setValue(storingData);
                                Toast.makeText(Signup_Student.this, "Verification Mail Sent", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Signup_Student.this, Main_login.class));
                                finish();
                            }
                        });

                    }
                    else {
                        bar.setVisibility(View.INVISIBLE);
                        eemail.setText(" ");
                        epassword .setText(" ");
                        Toast.makeText(Signup_Student.this, "Process Error ", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

    private Boolean checkpassword(String pass) {
        if(pass.isEmpty()){
            Password.setHelperText("");
            Password.setError("Can't Be Left Empty");
            return false;
        }
        else if(pass.length()<7){
            Password.setHelperText("");
            Password.setError("Enter Minimum 7 Characters ");
            return false;
        }
        return true;
    }

    private Boolean checkemail(String mail) {
        if(mail.isEmpty()){
            Email.setHelperText("");
            Email.setError("Can't Be Left Empty");
            return false;
        }
        else if(!mail.contains("@myamu.ac.in")){
            Email.setHelperText("");
            Email.setError("Enter Valid College Id");
            return false;
        }
        return true;
    }

    private Boolean checkfacultynumber(String number) {
        String expression = "^[0-9A-Z\\s]+";
        if(number.isEmpty()){
            Facultynumber.setHelperText("");
            Facultynumber.setError("Can't Be Left Empty");
            return false;
        }
        else if(!number.matches(expression)){
            return false;
        }
        else if((!number.contains("COB")) && (!number.contains("MEB")) && (!number.contains("EEB"))&& (!number.contains("EEB"))&& (!number.contains("ELB"))&& (!number.contains("CEB"))&& (!number.contains("CHB"))){

            return false;
        }
        return true;
    }

    private Boolean checkfullname(String fullname) {
        String expression = "^[a-zA-Z\\s]+";
        if(fullname.isEmpty()){
            Fullname.setHelperText("");
            Fullname.setError("Can't Be Left Empty");
            return false;
        }
        else if(!fullname.matches(expression)){
            return false;
        }
        return true;
    }
}