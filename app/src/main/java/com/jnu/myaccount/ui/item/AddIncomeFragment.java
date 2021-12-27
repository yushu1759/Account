package com.jnu.myaccount.ui.item;

import static com.jnu.myaccount.acc.AddActivity.*;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.jnu.myaccount.R;
import com.jnu.myaccount.utils.CalendarUtils;
import com.jnu.myaccount.utils.DataUtils;
import com.jnu.myaccount.utils.KeyBoardUtils;

public class AddIncomeFragment extends Fragment implements View.OnClickListener{
    public int selectItem;
    public double num;
    public String selectDate;
    private LinearLayout linearLayout;
    private ImageButton button_salary, button_red_packet, button_fund, button_others;
    private TextView addRecord;
    private Button button_time, button_remark;
    private EditText numEdit;
    private KeyboardView keyboardView;

    int[] nowDate = new CalendarUtils().getNowDate();
    int[] IntSelectDate = new int[5];

    private void initSelectDate(){
        selectDate = new CalendarUtils().IntToTimeString(nowDate[0],nowDate[1]+1,nowDate[2]);
        IntSelectDate = new CalendarUtils().TimeStringToInt(selectDate,IntSelectDate);
        button_time.setText(IntSelectDate[1]+"月"+IntSelectDate[2]+"日");
    }

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

        initView(rootView);
        KeyBoardUtils keyBoardUtils = new KeyBoardUtils(keyboardView,numEdit);
        keyBoardUtils.showKeyboard();
        initSelectDate();

        keyBoardUtils.setOnEnsureListener(new KeyBoardUtils.OnEnsureListener(){
            @Override
            public void onEnsure() {
                if(numEdit.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "请输入金额！", Toast.LENGTH_SHORT).show();
                }
                else {
                    DataUtils dataUtils = new DataUtils(getActivity());
                    dataUtils.InsertData(selectItem, Double.parseDouble(numEdit.getText().toString()), selectDate);
                    getActivity().finish();
                }
            }
        });

        button_remark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        button_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(), AlertDialog.THEME_HOLO_LIGHT,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                selectDate = new CalendarUtils().IntToTimeString(year,month+1,dayOfMonth);
                                IntSelectDate = new CalendarUtils().TimeStringToInt(selectDate,IntSelectDate);
                                button_time.setText(month+1+"月"+dayOfMonth+"日");
                            }
                        },IntSelectDate[0],IntSelectDate[1]-1,IntSelectDate[2]).show();
            }
        });
        return rootView;
    }

    private void initView(View rootView){
        linearLayout = rootView.findViewById(R.id.linear_layout_add_income);
        addRecord = rootView.findViewById(R.id.text_view_add_income_record);
        button_time = rootView.findViewById(R.id.button_time);
        button_remark = rootView.findViewById(R.id.button_remark);
        numEdit = rootView.findViewById(R.id.edit_text_add_income_num);
        keyboardView = rootView.findViewById(R.id.keyBoard);

        button_salary = rootView.findViewById(R.id.button_add_income_salary);
        button_red_packet = rootView.findViewById(R.id.button_add_income_red_packet);
        button_fund = rootView.findViewById(R.id.button_add_income_fund);
        button_others = rootView.findViewById(R.id.button_add_income_others);
        button_salary.setOnClickListener(this);
        button_red_packet.setOnClickListener(this);
        button_fund.setOnClickListener(this);
        button_others.setOnClickListener(this);
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
