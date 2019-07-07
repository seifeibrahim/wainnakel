package com.aqwas.wainnakel.Activites;

import android.os.Bundle;
import com.aqwas.wainnakel.R;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.btn_suggest)
    public void onViewClicked() {
        MainActivity.start(this);
    }
}
