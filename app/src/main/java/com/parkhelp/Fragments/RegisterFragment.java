package com.parkhelp.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.parkhelp.R;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterFragment extends Fragment {
    Unbinder unbinder;
    ViewPager pager;
    @BindView(R.id.resimYukle)
    CircleImageView resimYukle;
    @BindView(R.id.imgperson)
    ImageView imgperson;
    @BindView(R.id.edtname)
    AppCompatEditText edtname;
    @BindView(R.id.imgmail)
    ImageView imgmail;
    @BindView(R.id.edtmail)
    AppCompatEditText edtmail;
    @BindView(R.id.imgphone)
    ImageView imgphone;
    @BindView(R.id.edtphone)
    AppCompatEditText edtphone;
    @BindView(R.id.imgpasw)
    ImageView imgpasw;
    @BindView(R.id.edtpasw)
    AppCompatEditText edtpasw;
    @BindView(R.id.btnRegister)
    Button btnRegister;
    @BindView(R.id.registerProgresBar)
    ProgressBar registerProgresBar;
    @BindView(R.id.passwordlayout)
    LinearLayout passwordlayout;
    @BindView(R.id.password2layout)
    LinearLayout password2layout;

    private Uri imageUri;

    private FirebaseAuth mAuth;

    private static final int PICK_IMAGE = 1;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, null);
        unbinder = ButterKnife.bind(this, view);
        mAuth = FirebaseAuth.getInstance();
        imageUri = null;
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.resimYukle, R.id.btnRegister})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.resimYukle:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Resim Seçin"), PICK_IMAGE);
                break;
            case R.id.btnRegister:

                String email = edtmail.getText().toString().trim();
                String password = edtpasw.getText().toString().trim();

                if (imageUri == null){
                    Toast.makeText(getContext(), "Resim ekleyin!", Toast.LENGTH_SHORT).show();
                }

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getContext(), "E-mail adresini boş bırakmayın!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getContext(), "Parola girin!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getContext(), "Şifre çok kısa, en az 6 karakter girin!", Toast.LENGTH_SHORT).show();
                    return;
                }
                registerProgresBar.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(getContext(), "Profil oluşturuldu!", Toast.LENGTH_SHORT).show();
                                registerProgresBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    Toast.makeText(getContext(), "Hata oluştu.Tekrar deneyin!",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    pager = (ViewPager) getActivity().findViewById(R.id.pager);
                                    pager.setCurrentItem(0);
                                }
                            }
                        });

                break;
        }
    }

    private void sendToMain() {
        pager = (ViewPager) getActivity().findViewById(R.id.pager);
        pager.setCurrentItem(0);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            resimYukle.setImageURI(imageUri);
        }
    }
}
