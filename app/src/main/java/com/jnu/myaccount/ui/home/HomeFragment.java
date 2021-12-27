package com.jnu.myaccount.ui.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
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
    HomeItemAdapter adapter;

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
        adapter = new HomeItemAdapter(homeItemList);
        recyclerView.setAdapter(adapter);
    }

    private void initFloatingActionButton(View view) {
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddActivity.class);
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

    private void arrangeData(TreeMap<String, List<HomeItem>> treeMap){
        homeItemList.clear();
        DataUtils dataUtils = new DataUtils(this.getActivity());
        dataUtils.loadData(listTreeMap);
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
                viewHolder.icon.setBackgroundResource(accountItem.getIcon(accountItem.getRecord()));
                viewHolder.record.setText(accountItem.getTitle(HomeFragment.this.getContext(),accountItem.getRecord()));
                viewHolder.num.setText(accountItem.getNum() + "");
            }
        }

        @Override
        public int getItemCount() {
            return adpList.size();
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