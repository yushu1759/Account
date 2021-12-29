package com.jnu.myaccount.ui.item;

import static com.jnu.myaccount.acc.AddActivity.*;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
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

public class AddExpendFragment extends Fragment implements View.OnClickListener{
    public int selectItem=0;
    private double num;
    public String selectDate;
    private LinearLayout linearLayout;
    private ImageButton button_foods,button_drinks,button_supplies,button_shopping,button_sports,
            button_call_credit,button_traffic,button_party, button_housing,button_medical,button_gift,button_others;
    private Button button_time;
    private EditText edittextRemark;
    private TextView addRecord;

    private EditText numEdit;
    private KeyboardView keyboardView;

    int[] nowDate = new CalendarUtils().getNowDate();
    int[] IntSelectDate = new int[5];

    private void initSelectDate(){
        selectDate = new CalendarUtils().IntToTimeString(nowDate[0],nowDate[1],nowDate[2]);
        IntSelectDate = new CalendarUtils().TimeStringToInt(selectDate,IntSelectDate);
        button_time.setText(IntSelectDate[1]+"月"+IntSelectDate[2]+"日");
    }

    public AddExpendFragment() {

    }

    public static AddExpendFragment newInstance() {
        AddExpendFragment fragment = new AddExpendFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_expend,container,false);

        initView(rootView);
        KeyBoardUtils keyBoardUtils = new KeyBoardUtils(keyboardView, numEdit);
        keyBoardUtils.showKeyboard();
        initSelectDate();

        linearLayout.setBackgroundColor(getResources().getColor(R.color.purple1));
        addRecord.setText(getResources().getString(R.string.foods));
        selectItem = foods;

        if(operationTAG == OPERATION_EDIT){
            selectDate = previousSelectTime;
            selectItem = previousSelectItem;
            numEdit.setText(previousNum+"");
        }

        keyBoardUtils.setOnEnsureListener(new KeyBoardUtils.OnEnsureListener(){
            @Override
            public void onEnsure() {
                if(numEdit.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "请输入金额！", Toast.LENGTH_SHORT).show();
                }
                else if(selectItem == 0){
                    Toast.makeText(getContext(),"请选择类型！",Toast.LENGTH_SHORT).show();
                }
                else {
                    DataUtils dataUtils = new DataUtils(getActivity());
                    if(operationTAG == OPERATION_ADD) {
                        dataUtils.InsertData(selectItem, Double.parseDouble(numEdit.getText().toString()), selectDate,edittextRemark.getText().toString());
                    }
                    else if(operationTAG == OPERATION_EDIT){
                        dataUtils.EditData(selectItem,Double.parseDouble(numEdit.getText().toString()),selectDate,createTime,edittextRemark.getText().toString());
                    }
                    getActivity().finish();
                }
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

        edittextRemark.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent acc) {
                if (acc.getAction() == MotionEvent.ACTION_DOWN){
                    keyBoardUtils.hideKeyboard();
                    return false;
                }
                return false;
            }
        });

        edittextRemark.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView view, int actionId, KeyEvent event){
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    edittextRemark.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getActivity()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

        numEdit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    InputMethodManager imm = (InputMethodManager) getActivity()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
                    keyBoardUtils.showKeyboard();
                    return false;
                }
                return false;
            }
        });

        return rootView;
    }

    private void initView(View rootView){
        linearLayout = rootView.findViewById(R.id.linear_layout_add_expend);
        addRecord = rootView.findViewById(R.id.text_view_add_expend_record);
        numEdit = rootView.findViewById(R.id.edit_text_add_expend_num);
        button_time = rootView.findViewById(R.id.button_time);
        keyboardView =rootView.findViewById(R.id.keyBoard);
        edittextRemark = rootView.findViewById(R.id.edit_text_remark);

        button_foods = rootView.findViewById(R.id.button_add_expend_foods);
        button_drinks = rootView.findViewById(R.id.button_add_expend_debt);
        button_supplies = rootView.findViewById(R.id.button_add_expend_supplies);
        button_shopping = rootView.findViewById(R.id.button_add_expend_shopping);
        button_sports = rootView.findViewById(R.id.button_add_expend_sports);
        button_call_credit = rootView.findViewById(R.id.button_add_expend_call_credit);
        button_traffic = rootView.findViewById(R.id.button_add_expend_traffic);
        button_party = rootView.findViewById(R.id.button_add_expend_party);
        button_housing = rootView.findViewById(R.id.button_add_expend_housing);
        button_medical = rootView.findViewById(R.id.button_add_expend_medical);
        button_gift = rootView.findViewById(R.id.button_add_expend_medical);
        button_others = rootView.findViewById(R.id.button_add_expend_medical);
        button_foods.setOnClickListener(this);
        button_drinks.setOnClickListener(this);
        button_supplies.setOnClickListener(this);
        button_shopping.setOnClickListener(this);
        button_sports.setOnClickListener(this);
        button_call_credit.setOnClickListener(this);
        button_traffic.setOnClickListener(this);
        button_party.setOnClickListener(this);
        button_housing.setOnClickListener(this);
        button_medical.setOnClickListener(this);
        button_gift.setOnClickListener(this);
        button_others.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_add_expend_foods:
                addRecord.setText(getResources().getString(R.string.foods));
                selectItem = foods;
                break;
            case R.id.button_add_expend_debt:
                addRecord.setText(getResources().getString(R.string.debt));
                selectItem = debt;
                break;
            case R.id.button_add_expend_supplies:
                addRecord.setText(getResources().getString(R.string.supplies));
                selectItem = supplies;
                break;
            case R.id.button_add_expend_shopping:
                addRecord.setText(getResources().getString(R.string.shopping));
                selectItem = shopping;
                break;
            case R.id.button_add_expend_sports:
                addRecord.setText(getResources().getString(R.string.sports));
                selectItem = sports;
                break;
            case R.id.button_add_expend_call_credit:
                addRecord.setText(getResources().getString(R.string.call_credit));
                selectItem = call_credit;
                break;
            case R.id.button_add_expend_traffic:
                addRecord.setText(getResources().getString(R.string.traffic));
                selectItem = traffic;
                break;
            case R.id.button_add_expend_party:
                addRecord.setText(getResources().getString(R.string.party));
                selectItem = party;
                break;
            case R.id.button_add_expend_housing:
                addRecord.setText(getResources().getString(R.string.housing));
                selectItem = housing;
                break;
            case R.id.button_add_expend_medical:
                addRecord.setText(getResources().getString(R.string.medical));
                selectItem = medical;
                break;
            case R.id.button_add_expend_gift:
                addRecord.setText(getResources().getString(R.string.gift));
                selectItem = gift;
                break;
            case R.id.button_add_expend_others:
                addRecord.setText(getResources().getString(R.string.others));
                selectItem = others;
                break;
        }
    }

}
