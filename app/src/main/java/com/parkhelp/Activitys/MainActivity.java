package com.parkhelp.Activitys;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.parkhelp.Adapter.TabAdapter;
import com.parkhelp.NetworkChangeReceiver;
import com.parkhelp.R;

import butterknife.BindView;
import butterknife.ButterKnife;



public class MainActivity extends AppCompatActivity {
    @BindView(R.id.ustKisim)
    RelativeLayout ustKisim;
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.change_fragment)
    FrameLayout changeFragment;
    @BindView(R.id.pager)
    ViewPager pager;
    private static final String LOG_TAG = "Otomatik internet Kontrol¸";
    private NetworkChangeReceiver receiver;
    public static FirebaseAuth mAuth;
    private TextView email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
        email = (TextView) findViewById(R.id.edtmailadresi);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkChangeReceiver();
        registerReceiver(receiver, filter);

        //şuanki kullanıcıyı getirme
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        setDataToView(user);

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // kullanıcı kimlik doğrulama durumu değiştirildi
                    // Giriş sayfasını başlat
                    startActivity(new Intent(MainActivity.this, GirisSayfasiActivity.class));
                    finish();
                }
            }
        };


        DisplayMetrics metrics = new DisplayMetrics();
        MainActivity.this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int deviceWidth = metrics.widthPixels;

        FragmentManager fmanager = getSupportFragmentManager();
        final TabAdapter adapter = new TabAdapter(fmanager, tab.getTabCount());
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
        tab.post(new Runnable()
        {
            @Override
            public void run()
            {
                int tabLayoutWidth = tab.getWidth();

                DisplayMetrics metrics = new DisplayMetrics();
                MainActivity.this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
                int deviceWidth = metrics.widthPixels;

                if (tabLayoutWidth < deviceWidth)
                {
                    tab.setTabMode(TabLayout.MODE_FIXED);
                    tab.setTabGravity(TabLayout.GRAVITY_FILL);
                } else
                {
                    tab.setTabMode(TabLayout.MODE_SCROLLABLE);
                }
            }
        });
        tab.setSelectedTabIndicatorColor(Color.parseColor("#FFFFFF"));
        tab.setTabTextColors(Color.WHITE, Color.WHITE);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {


            }
        });
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });

    }


    @SuppressLint("SetTextI18n")
    private void setDataToView(FirebaseUser user) {

    }
    FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user == null) {

                startActivity(new Intent(MainActivity.this, GirisSayfasiActivity.class));
                finish();
            } else {
                setDataToView(user);

            }
        }


    };

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authListener);
    }


    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            mAuth.removeAuthStateListener(authListener);
        }
    }
    @Override
    protected void onDestroy() { //Activity kapatıldığı zaman receiver durduralacak.
                                // Uygulama arka plana alındığı zamanda receiver çalışmaya devam eder
        super.onDestroy();
        unregisterReceiver(receiver);//receiver durduruluyor

    }
}

