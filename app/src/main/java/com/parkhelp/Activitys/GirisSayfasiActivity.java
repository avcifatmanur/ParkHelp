package com.parkhelp.Activitys;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.parkhelp.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GirisSayfasiActivity extends AppCompatActivity {
    private static final int ZXING_CAMERA_PERMISSION = 1;

    private Class<?> mClss;
    @BindView(R.id.bolum1)
    LinearLayout bolum1;
    @BindView(R.id.bolum2)
    LinearLayout bolum2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bolum1, R.id.bolum2})
    public void onViewClicked(View view) {
        FragmentManager fmanager = getSupportFragmentManager();
        FragmentTransaction ft = fmanager.beginTransaction();
        switch (view.getId()) {
            case R.id.bolum1:
                launchActivity(KodActivity.class);
                break;
            case R.id.bolum2:
                launchActivity(SorunActivity.class);
                break;

        }
    }
    public void launchActivity(Class<?> clss) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            mClss = clss;
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, ZXING_CAMERA_PERMISSION);
        } else {
            Intent intent = new Intent(this, clss);
            startActivity(intent);
        }
    }

}