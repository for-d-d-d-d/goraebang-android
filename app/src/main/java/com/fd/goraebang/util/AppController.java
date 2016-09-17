package com.fd.goraebang.util;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;

import com.crashlytics.android.Crashlytics;
import com.fd.goraebang.consts.CONST;
import com.fd.goraebang.consts.URL;
import com.fd.goraebang.interfaces.AccountService;
import com.fd.goraebang.interfaces.SongService;
import com.fd.goraebang.model.User;
import com.fd.goraebang.util.helper.PrimitiveConverterFactory;
import com.kakao.auth.ApprovalType;
import com.kakao.auth.AuthType;
import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.ISessionConfig;
import com.kakao.auth.KakaoAdapter;

import io.fabric.sdk.android.Fabric;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class AppController extends Application {
    private static volatile AppController instance = null;
    private static volatile Activity currentActivity = null;

    //private Tracker mTracker;

    public static User user;
    public static int SCREEN_WIDTH = 0;
    public static int SCREEN_HEIGHT = 0;
    public static String AUTHORIZATION = null;
    public static String MB_VERSION_NAME = "";

    // retrofit and interface
    private static Retrofit retrofit;
    private static AccountService accountService;
    private static SongService songService;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        // 재실행 시, 일단 shared preferences 에서 값을 가져와서 저장해둠.
        if (AUTHORIZATION == null) {
            AUTHORIZATION = "JWT " + getSharedPreferences(CONST.PREF_NAME, MODE_PRIVATE).getString("authorization", null);
        }

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        SCREEN_WIDTH = displayMetrics.widthPixels;
        SCREEN_HEIGHT = displayMetrics.heightPixels;
        Fabric.with(this, new Crashlytics());
    }

    /*
        KakaoSDK.init(new KakaoSDKAdapter());
    }

    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            mTracker = analytics.newTracker(getResources().getString(R.string.ga_trackingId));
        }
        return mTracker;
    }*/

    static{
        user = new User();

        retrofit = new Retrofit.Builder()
                .baseUrl(URL.GET_API_URL())
                .addConverterFactory(PrimitiveConverterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        accountService = retrofit.create(AccountService.class);
        songService = retrofit.create(SongService.class);
    }

    public static AccountService getAccountService(){ return accountService; }
    public static SongService getSongService(){ return songService; }

    private static class KakaoSDKAdapter extends KakaoAdapter {
        @Override
        public ISessionConfig getSessionConfig() {
            return new ISessionConfig() {
                @Override
                public AuthType[] getAuthTypes() {
                    return new AuthType[] {AuthType.KAKAO_LOGIN_ALL};
                }

                @Override
                public boolean isUsingWebviewTimer() {
                    return false;
                }

                @Override
                public ApprovalType getApprovalType() {
                    return ApprovalType.INDIVIDUAL;
                }

                @Override
                public boolean isSaveFormData() {
                    return true;
                }
            };
        }

        @Override
        public IApplicationConfig getApplicationConfig() {
            return new IApplicationConfig() {
                @Override
                public Activity getTopActivity() {
                    return AppController.getCurrentActivity();
                }

                @Override
                public Context getApplicationContext() {
                    return AppController.getGlobalApplicationContext();
                }
            };
        }
    }

    public static AppController getGlobalApplicationContext() {
        if(instance == null)
            throw new IllegalStateException("this application does not inherit com.kakao.GlobalApplication");
        return instance;
    }

    public static Activity getCurrentActivity() {
        return currentActivity;
    }
    public static void setCurrentActivity(Activity currentActivity) {
        AppController.currentActivity = currentActivity;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        instance = null;
    }
}
