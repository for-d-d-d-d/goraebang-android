package com.fd.goraebang.main.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;

import com.fd.goraebang.R;
import com.fd.goraebang.custom.CustomFragment;
import com.fd.goraebang.model.Banner;
import com.fd.goraebang.model.Song;
import com.fd.goraebang.search.ActivitySearch_;
import com.fd.goraebang.util.AppController;
import com.fd.goraebang.util.CallUtils;
import com.fd.goraebang.util.CustomProgressDialog;
import com.fd.goraebang.util.Utils;
import com.fd.goraebang.util.adapter.FragmentTabPagerAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

@EFragment(R.layout.fragment_tab_home)
public class FragmentHome extends CustomFragment {
    @ViewById
    ViewPager viewPagerBanner, viewPagerTopChart, viewPagerNewChart;

    private List<Banner> itemsBanner = null;
    private ArrayList<Song> itemsTopChart = null;
    private ArrayList<Song> itemsNewChart = null;

    private CustomProgressDialog dialog;
    private FragmentTabPagerAdapter adapterBanner;
    private FragmentTabPagerAdapter adapterTopChart;
    private FragmentTabPagerAdapter adapterNewChart;

    public static FragmentHome newInstance() {
        FragmentHome f = new FragmentHome_();
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (itemsBanner == null) {
            itemsBanner = new ArrayList<>();
        }
        if (itemsTopChart == null) {
            itemsTopChart = new ArrayList<>();
        }
        if (itemsNewChart == null) {
            itemsNewChart = new ArrayList<>();
        }
    }

    @AfterViews
    void afterViews() {
        if (itemsBanner.size() == 0 || itemsTopChart.size() == 0 || itemsNewChart.size() == 0) {
            loadData();
        }

        updateViewBanner();
        updateViewTopChart();
        updateViewNewChart();
    }

    private void loadData() {
        dialog = Utils.createDialog(getActivity(), dialog);
        {
            // Banner
            Call<List<Banner>> call = AppController.getSongService().getMainBanner();
            call.enqueue(new CallUtils<List<Banner>>(call, getActivity(), getResources().getString(R.string.msgErrorCommon)) {
                @Override
                public void onResponse(Call<List<Banner>> call, Response<List<Banner>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        itemsBanner.addAll(response.body());
                    }
                    onComplete();
                }

                @Override
                public void onFailure(Call<List<Banner>> call, Throwable t) {
                    onComplete();
                }

                @Override
                public void onComplete() {
                    updateViewBanner();
                    dialog = Utils.hideDialog(dialog);
                }
            });
        }

        {
            // Top Chart
            Call<List<Song>> call = AppController.getSongService().getTopChart(0);
            call.enqueue(new CallUtils<List<Song>>(call, getActivity(), getResources().getString(R.string.msgErrorCommon)) {
                @Override
                public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        itemsTopChart.addAll(response.body());
                    }
                    onComplete();
                }

                @Override
                public void onFailure(Call<List<Song>> call, Throwable t) {
                    onComplete();
                }

                @Override
                public void onComplete() {
                    updateViewTopChart();
                    dialog = Utils.hideDialog(dialog);
                }
            });
        }

        {
            // New Chart
            Call<List<Song>> call = AppController.getSongService().getMainNewChart();
            call.enqueue(new CallUtils<List<Song>>(call, getActivity(), getResources().getString(R.string.msgErrorCommon)) {
                @Override
                public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        itemsNewChart.addAll(response.body());
                    }
                    onComplete();
                }

                @Override
                public void onFailure(Call<List<Song>> call, Throwable t) {
                    onComplete();
                }

                @Override
                public void onComplete() {
                    updateViewNewChart();
                    dialog = Utils.hideDialog(dialog);
                }
            });
        }
    }

    private void updateViewBanner() {
        adapterBanner = new FragmentTabPagerAdapter(getChildFragmentManager());

        for (Banner obj : itemsBanner) {
            adapterBanner.addFragment(FragmentHomeBanner.newInstance(obj), obj.getTitle());
        }

        viewPagerBanner.setAdapter(adapterBanner);
    }

    private void updateViewTopChart() {
        adapterTopChart = new FragmentTabPagerAdapter(getChildFragmentManager());

        if(itemsTopChart.size() >= 6){
            adapterTopChart.addFragment(FragmentHomeTopChart.newInstance(new ArrayList<Song>(itemsTopChart.subList(0, 6))), "A");
        }
        if(itemsTopChart.size() >= 12){
            adapterTopChart.addFragment(FragmentHomeTopChart.newInstance(new ArrayList<Song>(itemsTopChart.subList(6, 12))), "B");
        }
        if(itemsTopChart.size() >= 18){
            adapterTopChart.addFragment(FragmentHomeTopChart.newInstance(new ArrayList<Song>(itemsTopChart.subList(12, 18))), "C");
        }

        viewPagerTopChart.setAdapter(adapterTopChart);

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)viewPagerTopChart.getLayoutParams();
        if(AppController.SCREEN_WIDTH > 0){
            layoutParams.height = ((int)(AppController.SCREEN_WIDTH / 3) * 2) + 50;
        }else{
            layoutParams.height = 250;
        }
    }

    private void updateViewNewChart() {
        adapterNewChart = new FragmentTabPagerAdapter(getChildFragmentManager());

        if(itemsNewChart.size() >= 6){
            adapterNewChart.addFragment(FragmentHomeNewChart.newInstance(new ArrayList<Song>(itemsNewChart.subList(0, 6))), "A");
        }
        if(itemsNewChart.size() >= 12){
            adapterNewChart.addFragment(FragmentHomeNewChart.newInstance(new ArrayList<Song>(itemsNewChart.subList(6, 12))), "B");
        }
        if(itemsNewChart.size() >= 18){
            adapterNewChart.addFragment(FragmentHomeNewChart.newInstance(new ArrayList<Song>(itemsNewChart.subList(12, 18))), "C");
        }

        viewPagerNewChart.setAdapter(adapterNewChart);

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)viewPagerNewChart.getLayoutParams();
        if(AppController.SCREEN_WIDTH > 0){
            layoutParams.height = ((int)(AppController.SCREEN_WIDTH / 3) * 2) + 50;
        }else{
            layoutParams.height = 250;
        }
    }

    @Click(R.id.llTopChart)
    void onClickTopChart(){
        startActivity(new Intent(getActivity(), ActivitySearch_.class));
    }

    @Click(R.id.llNewChart)
    void onClickNewChart(){
        startActivity(new Intent(getActivity(), ActivitySearch_.class));
    }
}