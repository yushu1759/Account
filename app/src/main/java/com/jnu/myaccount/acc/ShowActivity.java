package com.jnu.myaccount.acc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.jnu.myaccount.R;
import com.jnu.myaccount.utils.StatusBarUtils;

public class ShowActivity extends AppCompatActivity {
    TextView textviewNum,textviewRecord,textviewType,textviewDate,textviewCreateTime,textviewRemark;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setStatusBarColor(getResources().getColor(R.color.background));

        StatusBarUtils.setBarDarkMode(this,true);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        textviewNum=findViewById(R.id.num);
        textviewRecord=findViewById(R.id.record);
        textviewType=findViewById(R.id.type);
        textviewDate=findViewById(R.id.date);
        textviewCreateTime=findViewById(R.id.createTime);
        textviewRemark = findViewById(R.id.text_view_remark);

        Intent intent=getIntent();
        textviewNum.setText(intent.getDoubleExtra("num",0.00)+"");
        textviewRecord.setText(intent.getStringExtra("record"));
        textviewType.setText(intent.getStringExtra("type"));
        textviewDate.setText(intent.getStringExtra("date"));
        textviewCreateTime.setText(intent.getStringExtra("createTime").substring(0,19));
        if(intent.getStringExtra("remark")==null){
            textviewRemark.setText("暂无备注");
        }
        else textviewRemark.setText("备注\n\n\t\t\t\t"+ intent.getStringExtra("remark"));
    }
}
