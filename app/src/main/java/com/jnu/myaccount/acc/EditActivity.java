package com.jnu.myaccount.acc;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.jnu.myaccount.R;
import com.jnu.myaccount.utils.StatusBarUtils;

public class EditActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        StatusBarUtils.setBarDarkMode(this, true);
    }
}