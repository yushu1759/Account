package com.jnu.myaccount.ui.home;

import static com.jnu.myaccount.acc.AddActivity.OPERATION_ADD;
import static com.jnu.myaccount.acc.AddActivity.OPERATION_EDIT;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jnu.myaccount.R;
import com.jnu.myaccount.acc.AddActivity;
import com.jnu.myaccount.acc.ShowActivity;
import com.jnu.myaccount.data.AccountItem;
import com.jnu.myaccount.data.HomeItem;
import com.jnu.myaccount.utils.DataUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private static final int RESULT_CODE_ADD_OK = 200;
    HomeAdapter adapter;

    TreeMap<String,List<HomeItem>> listTreeMap;
    List<HomeItem> homeItemList;

    @Override
    public void onResume(){
        super.onResume();
        arrangeData(listTreeMap);
        adapter.notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        initData();
        View rootView = inflater.inflate(R.layout.fragment_home,container,false);

        initFloatingActionButton(rootView);
        initRecyclerView(rootView);

        return rootView;
    }

    private void initRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view_item);
        LinearLayoutManager layoutManager = new LinearLayoutManager(HomeFragment.this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new HomeAdapter(homeItemList);
        recyclerView.setAdapter(adapter);
    }

    private void initFloatingActionButton(View view) {
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddActivity.class);
                intent.putExtra("operation", OPERATION_ADD);
                startActivity(intent);
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initData() {
        homeItemList = new ArrayList<>();
        listTreeMap = new TreeMap<String, List<HomeItem>>(Comparator.reverseOrder());
        DataUtils dataUtils = new DataUtils(this.getActivity());

        dataUtils.loadData(listTreeMap);
        arrangeData(listTreeMap);

    }

    private void arrangeData(TreeMap<String, List<HomeItem>> treeMap) {
        DataUtils dataUtils = new DataUtils(this.getActivity());
        dataUtils.loadData(listTreeMap);
        homeItemList.clear();
        Iterator<Map.Entry<String, List<HomeItem>>> it = treeMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, List<HomeItem>> entry = it.next();
            List<HomeItem> homeItems = entry.getValue();

            Calendar calendar = ((AccountItem) homeItems.get(0)).getDate();

            for (int i = 0; i < homeItems.size(); i++) {
                homeItemList.add(homeItems.get(i));
            }
        }

        for (int i = 0; i < homeItemList.size(); i++) {
            if (homeItemList.get(i) instanceof AccountItem) {
                AccountItem accountItem = (AccountItem) homeItemList.get(i);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private static final int VIEW_TYPE_ACCOUNT_ITEM = 100;
        private static final int MENU_DELETE = 1;
        private static final int MENU_DETAIL = 2;
        private static final int MENU_EDIT = 3;
        private final List<HomeItem> adpList;
        private Context context;


        public HomeAdapter(List<HomeItem> adpList) {
            this.adpList = adpList;
            this.context = context;
        }

        public void updateDataWhenDeleteItem() {

        }

        @Override
        public int getItemViewType(int position) {
            if (adpList.get(position) instanceof AccountItem) {
                return VIEW_TYPE_ACCOUNT_ITEM;
            } else return VIEW_TYPE_ACCOUNT_ITEM;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView;
            if (viewType==VIEW_TYPE_ACCOUNT_ITEM) {
                    itemView = LayoutInflater
                            .from(parent.getContext())
                            .inflate(R.layout.layout_account, parent, false);
                    return new AccountItemHolder(itemView);
            }
            else
                return null;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof AccountItemHolder) {
                AccountItemHolder viewHolder = (AccountItemHolder) holder;
                AccountItem accountItem = (AccountItem) adpList.get(position);
                viewHolder.icon.setBackgroundResource(accountItem.getIcon(accountItem.getRecord()));
                viewHolder.record.setText(accountItem.getTitle(HomeFragment.this.getContext(),accountItem.getRecord()));
                if(accountItem.getType()==0) {
                    viewHolder.num.setText(String.format("%.2f",accountItem.getNum()));
                    viewHolder.num.setTextColor(getResources().getColor(R.color.expend));
                }
                else{
                    viewHolder.num.setText(String.format("%.2f",accountItem.getNum()));
                    viewHolder.num.setTextColor(getResources().getColor(R.color.income));
                }
            }
        }

        @Override
        public int getItemCount() {
            return adpList.size();
        }

        class AccountItemHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
            ImageView icon;
            TextView record;
            TextView num;

            public AccountItemHolder(@NonNull View itemView) {
                super(itemView);
                icon = itemView.findViewById(R.id.image_view_account_icon);
                record = itemView.findViewById(R.id.text_view_account_record);
                num = itemView.findViewById(R.id.text_view_account_num);

                itemView.setOnCreateContextMenuListener(this);
            }

            @Override
            public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
                MenuItem menuDelete = menu.add(Menu.NONE, MENU_DELETE, 1, "删除");
                MenuItem menuDetail = menu.add(Menu.NONE, MENU_DETAIL, 2, "详情");
                MenuItem menuEdit = menu.add(Menu.NONE, MENU_EDIT, 3, "修改");

                menuDelete.setOnMenuItemClickListener(this::onMenuItemClick);
                menuDetail.setOnMenuItemClickListener(this::onMenuItemClick);
                menuEdit.setOnMenuItemClickListener(this::onMenuItemClick);
            }

            public boolean onMenuItemClick(MenuItem menuItem){
                int position = getAdapterPosition();
                Intent intent;
                AccountItem accountItem = (AccountItem) adpList.get(position);
                switch (menuItem.getItemId()) {
                    case MENU_DELETE:
                        DataUtils dataUtils = new DataUtils(getActivity());
                        dataUtils.DeleteItem(accountItem.getCreateTime());
                        onResume();
                        break;
                    case MENU_DETAIL:
                        intent = new Intent(getActivity(), ShowActivity.class);
                        intent.putExtra("num",accountItem.getNum());
                        intent.putExtra("record",accountItem.getTitle(getContext()));
                        intent.putExtra("type",accountItem.getPrintType());
                        intent.putExtra("date",accountItem.getTagDate());
                        intent.putExtra("createTime", accountItem.getCreateTime());
                        startActivity(intent);
                        break;
                    case MENU_EDIT:
                        intent = new Intent(getActivity(),AddActivity.class);
                        intent.putExtra("operation", OPERATION_EDIT);
                        intent.putExtra("previousNum", accountItem.getNum());
                        intent.putExtra("previousSelectTime", accountItem.getTagDate());
                        intent.putExtra("previousSelectItem", accountItem.getRecord());
                        intent.putExtra("createTime",accountItem.getCreateTime());
                        intent.putExtra("remark",accountItem.getRemark());
                        startActivity(intent);
                        break;
                }
                return false;
            }

        }
    }
}