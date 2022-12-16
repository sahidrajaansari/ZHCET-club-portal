package com.example.zcm.fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zcm.Main_Club;
import com.example.zcm.Main_Student;
import com.example.zcm.R;
import com.example.zcm.Signup_Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class loginstudent_tabfragment extends Fragment {
    Button button;
    TextView btn;
    TextInputLayout Password,Email;
    TextInputEditText epassword,eemail;
    CircularProgressIndicator circularProgressIndicator;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup Container, Bundle savedInstanceState) {
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.loginstudent_tab_fragment,Container,false);
        button=root.findViewById(R.id.button);
        btn=root.findViewById(R.id.SignUp);
        Email=root.findViewById(R.id.Email);
        Password=root.findViewById(R.id.Password);
        circularProgressIndicator=root.findViewById(R.id.circularProgressIndicator);
        eemail=root.findViewById(R.id.eemail);
        epassword=root.findViewById(R.id.epassword);
        mAuth=FirebaseAuth.getInstance();
        epassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    Password.setHelperText("Enter Minimum 8 char");
                    Password.setError("");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        eemail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(eemail.length()==0){
                    Email.setHelperText("Enter Email Id(.@myamu.ac.in)");
                    Email.setError("");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(loginstudent_tabfragment.this.getActivity(), Signup_Student.class);
                startActivity(intent);
                loginstudent_tabfragment.this.getActivity().finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkcredentials();
            }
        });
        return root;
    }

    private void checkcredentials() {
        String mail=eemail.getText().toString();
        String pass=epassword.getText().toString();
        Boolean bmail=checkemail(mail);
        Boolean bpass=checkpassword(pass);
        FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=firebaseDatabase.getReference("userdata");
        if(bpass==true && bmail==true){
            final String mail_data=eemail.getText().toString();
            final String pass_data=epassword.getText().toString();
            circularProgressIndicator.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(mail_data,pass_data).addOnCompleteListener(loginstudent_tabfragment.this.getActivity(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        FirebaseUser user=mAuth.getCurrentUser();
                        if(!user.isEmailVerified()){
                            circularProgressIndicator.setVisibility(View.INVISIBLE);
                            Toast.makeText(getContext(), "Please verify your email", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            circularProgressIndicator.setVisibility(View.INVISIBLE);
                            Intent intent=new Intent(loginstudent_tabfragment.this.getActivity(),Main_Student.class);
                            startActivity(intent);
                            loginstudent_tabfragment.this.getActivity().finish();
                        }

                    }
                    else {
                        String exception="Error: "+task.getException().getMessage();
                        circularProgressIndicator.setVisibility(View.INVISIBLE);
                        Email.setError("User Not Registered or Wrong Password");
                        Password.setError("User Not Registered or Wrong Password");
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
        else if(pass.length()<8){
            Password.setHelperText("");
            Password.setError("Enter Minimum 8 Characters ");
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
}
