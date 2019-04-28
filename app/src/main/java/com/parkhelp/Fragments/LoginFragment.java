package com.parkhelp.Fragments;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.parkhelp.Activitys.GirisSayfasiActivity;
import com.parkhelp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LoginFragment extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.imgpersonmail)
    ImageView imgpersonmail;
    @BindView(R.id.edtmailadresi)
    AppCompatEditText edtmailadresi;
    @BindView(R.id.imgpasw)
    ImageView imgpasw;
    @BindView(R.id.edtpasw)
    AppCompatEditText edtpasw;
    @BindView(R.id.btnLogin)
    Button btnLogin;

    private FirebaseAuth auth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, null);
        unbinder = ButterKnife.bind(this, view);
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(getActivity(), GirisSayfasiActivity.class));
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = edtmailadresi.getText().toString().trim();
                final String password = edtpasw.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getContext(), "Mail adresini girin!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getContext(), "Şifre girin!", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (!task.isSuccessful()) {

                                    if (password.length() < 6) {
                                        Toast.makeText(getContext(), "Şifre çok kısa, en az 6 karakter girin!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getContext(), "Kimlik doğrulama başarısız oldu.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Intent intent = new Intent(getContext(), GirisSayfasiActivity.class);
                                    startActivity(intent);

                                }
                            }
                        });
            }
        });



        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
