package com.commit451.fitternumberpicker.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewConfiguration;
import android.widget.NumberPicker;

import com.commit451.fitternumberpicker.FitterNumberPicker;


public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);

        NumberPicker stockNumberPicker = (NumberPicker) findViewById(R.id.stock);
        stockNumberPicker.setMinValue(0);
        stockNumberPicker.setMaxValue(10);

        FitterNumberPicker defaultNumberPicker = (FitterNumberPicker) findViewById(R.id.default_number_picker);
        FitterNumberPicker customNumberPicker = (FitterNumberPicker) findViewById(R.id.custom_number_picker);

        ViewConfiguration configuration = ViewConfiguration.get(this);
        //This would be a good value to start with, then / or * to adjust
        //By default, the velocity is divided by 8, so do what you want to adjust
        int maxFlingVelocity = configuration.getScaledMaximumFlingVelocity() / 6;
        customNumberPicker.setMaximumFlingVelocity(maxFlingVelocity);
    }
}
