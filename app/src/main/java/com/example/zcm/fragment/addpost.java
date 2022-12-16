package com.example.zcm.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zcm.R;
import com.example.zcm.dataholder.Clubdata;
import com.example.zcm.dataholder.Postdata;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class addpost extends Fragment {


    public addpost() {
        // Required empty public constructor
    }

    Uri uri;
    FirebaseDatabase database;
    FirebaseStorage storage;
    ProgressDialog dialog;
    String Path;

    ImageView profileImage, addImage, imageView;
    TextView Clubname;
    EditText postDescription;
    AppCompatButton postBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        dialog = new ProgressDialog(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_addpost, container, false);
        Path = getActivity().getIntent().getExtras().getString("number");
        profileImage = root.findViewById(R.id.profileImage);
        Clubname = root.findViewById(R.id.Clubname);
        postDescription = root.findViewById(R.id.postDescription);
        postBtn = root.findViewById(R.id.postBtn);
        addImage = root.findViewById(R.id.addImage);
        imageView = root.findViewById(R.id.imageView);

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Post Uploading");
        dialog.setMessage("Please Wait...");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("clubdata").child(Path);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Clubdata clubdata = snapshot.getValue(Clubdata.class);
                Clubname.setText(clubdata.getClubname());
                Glide.with(profileImage.getContext()).load(clubdata.getProfileimage()).into(profileImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        postDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                String description = postDescription.getText().toString();
//                if (!description.isEmpty()) {
//                    postBtn.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.follow_btn_bg));
//                    postBtn.setTextColor(getContext().getResources().getColor(R.color.white));
//                    postBtn.setEnabled(true);
//                } else {
//                    postBtn.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.follow_active_btn));
//                    postBtn.setTextColor(getContext().getResources().getColor(R.color.silver));
//                    postBtn.setEnabled(false);
//                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 10);
            }
        });


        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                final StorageReference storageReference = storage.getReference().child("Post").child(Path).child(new Date().getTime() + "");
                storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String pattern = "dd/MM/yyyy";
                                DateFormat df = new SimpleDateFormat(pattern);
                                Date today = Calendar.getInstance().getTime();
                                String Date = df.format(today);
                                Long postedAt=(new Date().getTime()-Integer.MAX_VALUE)*-1;
                                Postdata post = new Postdata(database.getReference().child("Post").push().getKey(), uri.toString(), Path, postDescription.getText().toString(), postedAt,Date);
                                database.getReference().child("Post").child(post.getPostId()).setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        dialog.dismiss();
                                        Toast.makeText(getContext(), "Posted Successfully", Toast.LENGTH_SHORT).show();
                                        postDescription.setText("");
                                        imageView.setVisibility(View.GONE);
                                    }
                                });
                            }
                        });
                    }
                });

            }
        });
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data.getData() != null) {
            uri = data.getData();
            imageView.setImageURI(uri);
            imageView.setVisibility(View.VISIBLE);

            postBtn.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.follow_btn_bg));
            postBtn.setTextColor(getContext().getResources().getColor(R.color.color2));
            postBtn.setEnabled(true);
        }
    }
}