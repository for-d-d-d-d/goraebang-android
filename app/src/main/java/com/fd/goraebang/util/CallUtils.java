package com.fd.goraebang.util;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;

import retrofit2.Call;
import retrofit2.Callback;

public abstract class CallUtils<T> implements Callback<T> {
    private CustomAlertDialog mCustomAlertDialog = null;
    private final String TAG = CallUtils.class.getSimpleName();
    private final Call<T> call;
    private final Context context;
    private final boolean isFinishAfterFailureDialog;
    protected final String msg;
    protected static final int TOTAL_RETRIES = 3;
    protected int retryCount = 0;

    public CallUtils(Call<T> call, Context context, String msg, boolean isFinishAfterFailureDialog) {
        this.context = context;
        this.call = call;
        this.msg = msg;
        this.isFinishAfterFailureDialog = isFinishAfterFailureDialog;
    }

    public CallUtils(Call<T> call, Context context, String msg) {
        this(call, context, msg, false);
    }

    public CallUtils(Call<T> call, Context context) {
        this(call, context, null, false);
    }

    public void onFailure(Throwable t) {
        if (retryCount++ < TOTAL_RETRIES) {
            Log.v(TAG, "Retrying... (" + retryCount + " out of " + TOTAL_RETRIES + ")");
            retry();
        } else {
            onComplete();
            if (msg != null) {
                Log.d("aaaaaaa"," msg not null !!! ");
                showSingleDialog(msg, isFinishAfterFailureDialog);
            }
        }
        Log.d("log" , "retrofit log : " + t.getMessage());
    }

    private void retry() {
        call.clone().enqueue(this);
    }

    //not abstract! because onComplete is not required!!
    public void onComplete(){}

    protected void showToast(String msg) {
        Utils.showGlobalToast(context, msg);
    }

    protected void showSingleDialog(String msg) {
        showSingleDialog(msg, false);
    }

    protected void showSingleDialog(String msg, final boolean isFinish) {
        if(mCustomAlertDialog == null && context instanceof Activity && !((Activity) context).isFinishing()) {
            mCustomAlertDialog = new CustomAlertDialog(context,
                    msg,
                    "확인",
                    new View.OnClickListener() {
                        public void onClick(View v) {
                            mCustomAlertDialog.dismiss();
                            try {
                                if (isFinish) {
                                    ((Activity) context).finish();
                                }
                            } catch (Exception e) {
                            }
                        }
                    });
            mCustomAlertDialog.show();
        }else{
            showToast(msg);
        }
    }
}