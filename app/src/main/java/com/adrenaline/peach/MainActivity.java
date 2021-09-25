package com.adrenaline.peach;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.adrenaline.peach.R;

public class MainActivity extends AppCompatActivity {

    private View decorView;
    private CardView btn_creator,btn_brand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if(visibility == 0)
                    decorView.setSystemUiVisibility(hideSystemBars());
            }
        });

        btn_creator =findViewById(R.id.create_btn);
        btn_creator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreatorLogin();
            }
        });

        btn_brand =findViewById(R.id.brand_btn);
        btn_brand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBrandLogin();
            }
        });

    }

    public void openCreatorLogin(){
        Intent intent = new Intent(this,creatorLogin.class);
        startActivity(intent);

    }

    public void openBrandLogin(){
        Intent intent = new Intent(this,brand_login.class);
        startActivity(intent);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){
            decorView.setSystemUiVisibility(hideSystemBars());
        }
    }

    private int hideSystemBars(){
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    };
}