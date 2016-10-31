package com.fd.goraebang.main.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.fd.goraebang.R;
import com.fd.goraebang.common.ActivityWebview_;
import com.fd.goraebang.custom.CustomFragmentWithRecyclerView;
import com.fd.goraebang.main.ActivityMain;
import com.fd.goraebang.main.mypage.ActivityMyProfile_;
import com.fd.goraebang.util.CustomAlertDialog;
import com.fd.goraebang.util.Utils;
import com.fd.goraebang.util.adapter.RecyclerAdapterText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

import java.util.ArrayList;
import java.util.List;

@EFragment(R.layout.fragment_tab_settings)
public class FragmentSettings extends CustomFragmentWithRecyclerView {
    private List<String> items = new ArrayList<>();
    private CustomAlertDialog mCustomAlertDialog;
    public static FragmentSettings newInstance() {
        FragmentSettings f = new FragmentSettings_();
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        isInfiniteScroll = false;

        super.onCreate(savedInstanceState);

        if(adapter == null) {
            adapter = new RecyclerAdapterText(getActivity(), items);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @AfterViews
    void afterViews() {
        if(adapter == null)
            return;

        super.setupRecyclerView();
        super.setupSwipeRefreshLayout();

        if(items.size() == 0) {
            loadData(0);
        }
    }

    @Override
    protected void loadData(int page) {
        items.add("프로필 편집");
        items.add("버전정보");
        items.add("FAQ");
        items.add("문의하기");
        items.add("로그아웃");

        updateView();
    }

    void updateView(){
        swipeRefreshLayout.setRefreshing(false);
        swipeRefreshLayout.setEnabled(false);
        setMessage("");
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onItemClick(View view, int position) {
        if(items.size() < position)
            return;

        Intent intent = null;

        switch (position){
            case 0:
                intent = new Intent(getActivity(), ActivityMyProfile_.class);
                break;
            case 1:
                intent = new Intent(getActivity(), ActivityVersion_.class);
                break;
            case 2:
                intent = new Intent(getActivity(), ActivityWebview_.class);
                intent.putExtra("url", "http://www.google.com");
                intent.putExtra("title", "FAQ");
                break;
            case 3:
                Utils.sendEmail(getActivity(), "[고래방] 이메일 문의");
                break;
            case 4:
                logoutDialog();
                break;
            default:
                break;
        }

        if(intent != null){
            getActivity().startActivity(intent);
        }
    }

    private void logoutDialog() {
        mCustomAlertDialog = new CustomAlertDialog(getActivity(),
                "로그아웃 하시겠습니까?", "아니오", "예",
                new View.OnClickListener() {
                    public void onClick(View v) {
                        mCustomAlertDialog.dismiss();
                    }
                },
                new View.OnClickListener() {
                    public void onClick(View v) {
                        mCustomAlertDialog.dismiss();
                        logout();
                    }
                });
        mCustomAlertDialog.show();
    }

    private void logout(){
        ((ActivityMain)getActivity()).logout();
    }

    @Override
    protected void onRefresh() {
        updateView();
    }
}
