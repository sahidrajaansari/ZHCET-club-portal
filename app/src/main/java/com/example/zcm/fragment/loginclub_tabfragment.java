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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.zcm.Main_Club;
import com.example.zcm.Main_Student;
import com.example.zcm.R;
import com.example.zcm.Signup_Club;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class loginclub_tabfragment extends Fragment {
    Button button;
    TextView btn;
    TextInputLayout Clubid;
    TextInputLayout Password;
    TextInputEditText eclubid;
    TextInputEditText epassword;
    CircularProgressIndicator circularProgressIndicator;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup Container, Bundle savedInstanceState) {
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.loginclub_tab_fragment,Container,false);
        button=root.findViewById(R.id.button);
        btn=root.findViewById(R.id.SignUp);
        Clubid=root.findViewById(R.id.Clubid);
        Password=root.findViewById(R.id.Password);
        circularProgressIndicator=root.findViewById(R.id.circularProgressIndicator);
        eclubid=root.findViewById(R.id.eclubid);
        epassword=root.findViewById(R.id.epassword);
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
        eclubid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    String clubid=charSequence.toString();
                    if(clubid.length()==0){
                        Clubid.setHelperText("Enter Valid Club Id");
                        Clubid.setError("");
                    }
                    else if(clubid.length()>6){
                        Clubid.setHelperText(" ");
                        Clubid.setError("Club id Should be of 6 digit");
                    }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(loginclub_tabfragment.this.getActivity(), Signup_Club.class));
                loginclub_tabfragment.this.getActivity().finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkcredentils();
            }
        });
        return root;
    }

    private void checkcredentils() {
        String number=eclubid.getText().toString();
        String pass=epassword.getText().toString();
        Boolean bpass=checkpassword(pass);
        Boolean bnumber=checkclubnumber(number);
        FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=firebaseDatabase.getReference("clubdata");
        if(bnumber==true && bpass==true){
            circularProgressIndicator.setVisibility(View.VISIBLE);
            final String clubid_data=eclubid.getText().toString();
            final String pass_data=epassword.getText().toString();
            final String status_data="1";
            Query check_clubnumber=databaseReference.orderByChild("clubid").equalTo(clubid_data);
            check_clubnumber.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        String password_check=snapshot.child(clubid_data).child("pass").getValue(String.class);
                        String status_check=snapshot.child(clubid_data).child("status").getValue(String.class);
                        if(password_check.equals(pass_data) && status_check.equals(status_data) ){
                            Password.setError(null);
                            Toast.makeText(loginclub_tabfragment.this.getActivity(), "Login", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(loginclub_tabfragment.this.getActivity(), Main_Club.class));
                            Intent intent=new Intent(loginclub_tabfragment.this.getActivity(), Main_Club.class);
                            intent.putExtra("number",clubid_data);
                            startActivity(intent);
                            loginclub_tabfragment.this.getActivity().finish();
                        }
                        else if(status_check.equals(status_data)==false){
                            circularProgressIndicator.setVisibility(View.INVISIBLE);
                            Password.setError("Status Pending");
                            Clubid.setError("Status Pending");
//                            Toast.makeText(loginclub_tabfragment.this.getActivity(), "Status Pending", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            circularProgressIndicator.setVisibility(View.INVISIBLE);
                            Password.setError("Club Doesn't exist or password is incorrect");
                            Clubid.setError("Club Doesn't exist or password is incorrect");
                        }
                    }
                    else{
                        circularProgressIndicator.setVisibility(View.INVISIBLE);
                        Password.setError("Club Doesn't exist or password is incorrect");
                        Clubid.setError("Club Doesn't exist or password is incorrect");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private Boolean checkclubnumber(String number) {
        if(number.isEmpty()){
            return false;
        }
        else if(number.length()!=6){
            Clubid.setHelperText("");
            Clubid.setError("Invalid College Id");
            return false;
        }
        return true;
    }

    private Boolean checkpassword(String pass) {
        if(pass.isEmpty()){
            return false;
        }
        else if(pass.length()<7){
            Password.setHelperText("");
            Password.setError("Enter Minimum 8 Characters ");
            return false;
        }
        return true;
    }
}
