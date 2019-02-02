package com.example.bellosil21.pokertexasholdem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView coinStackImage = (ImageView) findViewById(R.id.chipStack);
        coinStackImage.setImageResource(R.drawable.chip_stack);
    }
}
