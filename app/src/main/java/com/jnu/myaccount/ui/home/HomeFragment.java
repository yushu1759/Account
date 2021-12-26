package com.jnu.myaccount.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jnu.myaccount.R;
import com.jnu.myaccount.data.AccountItem;
import com.jnu.myaccount.data.HomeItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    HomeItemAdapter adapter;

    TreeMap<String,List<HomeItem>> listTreeMap;
    List<HomeItem> homeItemList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        initData();
        View rootView = inflater.inflate(R.layout.fragment_home,container,false);

        recyclerView = rootView.findViewById(R.id.recycler_view_item);
        LinearLayoutManager layoutManager = new LinearLayoutManager(HomeFragment.this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new HomeItemAdapter(homeItemList);
        recyclerView.setAdapter(adapter);

        return rootView;
    }
    private void initData() {
        listTreeMap = new TreeMap<>();
        List<HomeItem> accountItemList1 = new ArrayList<>();
        List<HomeItem> accountItemList2 = new ArrayList<>();

        accountItemList1.add(new AccountItem(R.drawable.foods,1,50,true,2021,11,27));
        accountItemList1.add(new AccountItem(R.drawable.medical,2,60,true,2021,11,27));
        accountItemList2.add(new AccountItem(R.drawable.party,3,70,true,2020,1,1));
        accountItemList2.add((new AccountItem(R.drawable.gift,4,80,true,2020,1,1)));

        String date1 = ((AccountItem)accountItemList1.get(0)).getTagDate();
        String date2 = ((AccountItem)accountItemList2.get(0)).getTagDate();
        listTreeMap.put(date2,accountItemList1);
        listTreeMap.put(date1,accountItemList2);

        arrangeData(listTreeMap);

    }

    private void arrangeData(TreeMap<String, List<HomeItem>> treeMap){
        homeItemList = new ArrayList<>();
        Iterator<Map.Entry<String,List<HomeItem>>> it = treeMap.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String,List<HomeItem>> entry = it.next();
            List<HomeItem> homeItems = entry.getValue();

            Calendar calendar = ((AccountItem)homeItems.get(0)).getDate();

            for(int i = 0 ;i < homeItems.size();i++){
                homeItemList.add(homeItems.get(i));
            }
        }

        for(int i = 0;i < homeItemList.size();i++){
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    public class HomeItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private static final int VIEW_TYPE_ACCOUNT_ITEM = 1;

        private final List<HomeItem> adpList;

        public HomeItemAdapter(List<HomeItem> adpList) {
            this.adpList = adpList;

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
                viewHolder.icon.setBackgroundResource(accountItem.getIcon());
                viewHolder.record.setText(accountItem.getRecord() + "");
                viewHolder.num.setText(accountItem.getNum() + "");
            }
        }

        @Override
        public int getItemCount() {
            return homeItemList.size();
        }

        class AccountItemHolder extends RecyclerView.ViewHolder {
            ImageView icon;
            TextView record;
            TextView num;

            public AccountItemHolder(@NonNull View itemView) {
                super(itemView);
                icon = itemView.findViewById(R.id.image_view_account_icon);
                record = itemView.findViewById(R.id.text_view_account_record);
                num = itemView.findViewById(R.id.text_view_account_num);
            }


        }
    }
}