package com.jnu.myaccount.ui.item;

import static com.jnu.myaccount.acc.AddActivity.*;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.jnu.myaccount.R;

public class AddIncomeFragment extends Fragment implements View.OnClickListener{
    public int selectItem;
    private LinearLayout linearLayout;
    private ImageButton button_salary, button_red_packet, button_fund, button_others;
    private TextView addRecord;

    public AddIncomeFragment(){

    }

    public static AddIncomeFragment newInstance() {
        AddIncomeFragment fragment = new AddIncomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_income,container,false);
        linearLayout = rootView.findViewById(R.id.linear_layout_add_income);
        addRecord = rootView.findViewById(R.id.text_view_add_income_record);

        button_salary = rootView.findViewById(R.id.button_add_income_salary);
        button_red_packet = rootView.findViewById(R.id.button_add_income_red_packet);
        button_fund = rootView.findViewById(R.id.button_add_income_fund);
        button_others = rootView.findViewById(R.id.button_add_income_others);
        button_salary.setOnClickListener(this);
        button_red_packet.setOnClickListener(this);
        button_fund.setOnClickListener(this);
        button_others.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_add_income_salary:
                linearLayout.setBackgroundColor(getResources().getColor(R.color.salary));
                addRecord.setText(getResources().getString(R.string.salary));
                selectItem = salary;
                break;
            case R.id.button_add_income_red_packet:
                linearLayout.setBackgroundColor(getResources().getColor(R.color.red_packet));
                addRecord.setText(getResources().getString(R.string.red_packet));
                selectItem = red_packet;
                break;
            case R.id.button_add_income_fund:
                linearLayout.setBackgroundColor(getResources().getColor(R.color.fund));
                addRecord.setText(getResources().getString(R.string.fund));
                selectItem = fund;
                break;
            case R.id.button_add_income_others:
                linearLayout.setBackgroundColor(getResources().getColor(R.color.others));
                addRecord.setText(getResources().getString(R.string.others));
                selectItem = others;
                break;
        }

    }
}
