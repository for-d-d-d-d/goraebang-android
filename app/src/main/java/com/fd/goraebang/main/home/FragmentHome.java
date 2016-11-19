package com.fd.goraebang.main.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;

import com.fd.goraebang.R;
import com.fd.goraebang.custom.CustomFragment;
import com.fd.goraebang.model.Banner;
import com.fd.goraebang.model.Song;
import com.fd.goraebang.song.ActivityNewChart_;
import com.fd.goraebang.song.ActivityTopChart_;
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

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Response;


@EFragment(R.layout.fragment_tab_home)
public class FragmentHome extends CustomFragment {
    @ViewById
    ViewPager viewPagerBanner, viewPagerTopChart, viewPagerNewChart;
    @ViewById
    CircleIndicator indicatorBanner, indicatorTopChart, indicatorNewChart;

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
        loadData();

        dialog = Utils.createDialog(getActivity(), dialog);
        updateViewBanner();
        updateViewTopChart();
        updateViewNewChart();
    }

    private void loadData() {
        dialog = Utils.createDialog(getActivity(), dialog);
        if(itemsBanner.size() == 0) {
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
                }
            });
        }

        if(itemsTopChart.size() == 0) {
            // Top Chart
            Call<List<Song>> call = AppController.getSongService().getTopChart(AppController.USER_TOKEN, 0);
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
                }
            });
        }

        if(itemsNewChart.size() == 0) {
            // New Chart
            Call<List<Song>> call = AppController.getSongService().getMainNewChart(AppController.USER_TOKEN, 0);
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
        indicatorBanner.setViewPager(viewPagerBanner);
    }

    private void updateViewTopChart() {
        adapterTopChart = new FragmentTabPagerAdapter(getChildFragmentManager());

        if(itemsTopChart.size() >= 6){
            adapterTopChart.addFragment(FragmentHomeChart.newInstance(new ArrayList<Song>(itemsTopChart.subList(0, 6))), "A");
        }
        if(itemsTopChart.size() >= 12){
            adapterTopChart.addFragment(FragmentHomeChart.newInstance(new ArrayList<Song>(itemsTopChart.subList(6, 12))), "B");
        }
        if(itemsTopChart.size() >= 18){
            adapterTopChart.addFragment(FragmentHomeChart.newInstance(new ArrayList<Song>(itemsTopChart.subList(12, 18))), "C");
        }

        viewPagerTopChart.setAdapter(adapterTopChart);
        indicatorTopChart.setViewPager(viewPagerTopChart);

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)viewPagerTopChart.getLayoutParams();
        layoutParams.height = (int)((AppController.GRID_COLUMN_HEIGHT + 10 * AppController.SCREEN_DENSITY) * 2);

        hideDialog();
    }

    private void updateViewNewChart() {
        adapterNewChart = new FragmentTabPagerAdapter(getChildFragmentManager());

        if(itemsNewChart.size() >= 6){
            adapterNewChart.addFragment(FragmentHomeChart.newInstance(new ArrayList<Song>(itemsNewChart.subList(0, 6))), "A");
        }
        if(itemsNewChart.size() >= 12){
            adapterNewChart.addFragment(FragmentHomeChart.newInstance(new ArrayList<Song>(itemsNewChart.subList(6, 12))), "B");
        }
        if(itemsNewChart.size() >= 18){
            adapterNewChart.addFragment(FragmentHomeChart.newInstance(new ArrayList<Song>(itemsNewChart.subList(12, 18))), "C");
        }

        viewPagerNewChart.setAdapter(adapterNewChart);
        indicatorNewChart.setViewPager(viewPagerNewChart);

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)viewPagerNewChart.getLayoutParams();
        layoutParams.height = (int)((AppController.GRID_COLUMN_HEIGHT + 10 * AppController.SCREEN_DENSITY) * 2);

        hideDialog();
    }

    private void hideDialog(){
        dialog = Utils.hideDialog(dialog);
    }

    @Click(R.id.llTopChart)
    void onClickTopChart(){
        startActivity(new Intent(getActivity(), ActivityTopChart_.class));
    }

    @Click(R.id.llNewChart)
    void onClickNewChart(){
        startActivity(new Intent(getActivity(), ActivityNewChart_.class));
    }
}