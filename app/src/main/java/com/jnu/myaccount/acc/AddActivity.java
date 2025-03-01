package com.jnu.myaccount.acc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jnu.myaccount.R;
import com.jnu.myaccount.ui.item.AddExpendFragment;
import com.jnu.myaccount.ui.item.AddIncomeFragment;
import com.jnu.myaccount.utils.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity {
    public final static int foods = 1;
    public final static int debt = foods+1;
    public final static int supplies = foods+2;
    public final static int shopping = foods+3;
    public final static int sports = foods+4;
    public final static int call_credit = foods+5;
    public final static int traffic = foods+6;
    public final static int party = foods+7;
    public final static int housing = foods+8;
    public final static int medical = foods+9;
    public final static int gift = foods+10;
    public final static int salary = foods+11;
    public final static int red_packet = foods+12;
    public final static int fund = foods+13;
    public final static int others = foods+14;

    public final static int OPERATION_EDIT = 500;
    public final static int OPERATION_ADD = 600;

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private String[] tabTitle;
    private List<Fragment> fragments = new ArrayList<>();

    public static int operationTAG = OPERATION_ADD;
    public static String previousSelectTime;
    public static double previousNum;
    public static int previousSelectItem;
    public static String createTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setStatusBarColor(getResources().getColor(R.color.background));

        StatusBarUtils.setBarDarkMode(this, true);

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

        initTabLayout();

        Intent intent = getIntent();
        operationTAG = intent.getIntExtra("operation",OPERATION_ADD);
        previousNum = intent.getDoubleExtra("previousNum",0);
        previousSelectTime = intent.getStringExtra("previousSelectTime");
        previousSelectItem = intent.getIntExtra("previousSelectItem",14);
        createTime = intent.getStringExtra("createTime");
    }

    private void initTabLayout() {
        tabLayout = findViewById(R.id.tl_tab);
        viewPager2 = findViewById(R.id.vp_content);
        tabLayout.setBackgroundColor(getResources().getColor(R.color.purple2));
        tabTitle = new String[]{"收入", "支出"};
        AddIncomeFragment incomeFragment = new AddIncomeFragment();
        AddExpendFragment expendFragment = new AddExpendFragment();
        fragments.add(incomeFragment);
        fragments.add(expendFragment);

        viewPager2.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return fragments.get(position);
            }

            @Override
            public int getItemCount() {
                return 2;
            }
        });

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabTitle[position]);
            }
        });
        tabLayoutMediator.attach();
    }
}
