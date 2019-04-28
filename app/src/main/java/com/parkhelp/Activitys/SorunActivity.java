package com.parkhelp.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parkhelp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SorunActivity extends AppCompatActivity {
    Unbinder unbinder;
    public String[] oncelik = {"Çok Acil", "Acil", "Orta", "Az"};
    public ArrayAdapter<String> dataAdapterForOncelik;
    @BindView(R.id.edtBilgi)
    EditText edtBilgi;
    @BindView(R.id.edtTel)
    EditText edtTel;
    @BindView(R.id.edtBaslik)
    EditText edtBaslik;
    @BindView(R.id.oncelikSpinner)
    TextView oncelikSpinner;
    @BindView(R.id.oncelikEntry)
    Spinner oncelikEntry;
    @BindView(R.id.idSpinner)
    LinearLayout idSpinner;
    @BindView(R.id.multiLine)
    EditText multiLine;
    @BindView(R.id.btnGonder)
    Button btnGonder;
    private static final String TAG="Anasayfa";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorun);
        unbinder = ButterKnife.bind(this);
        dataAdapterForOncelik = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, oncelik);
        dataAdapterForOncelik.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        oncelikEntry.setAdapter(dataAdapterForOncelik);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnGonder)
    public void onViewClicked() {
        Toast.makeText(this, "Geri bildiriminiz için teşekkürler.", Toast.LENGTH_SHORT).show();

      Intent intent = new Intent(this, GirisSayfasiActivity.class);
        startActivity(intent);
    }
}
