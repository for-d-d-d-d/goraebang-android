package com.fd.goraebang.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.fd.goraebang.consts.CONST;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
	public static int TYPE_WIFI = 1;
	public static int TYPE_MOBILE = 2;
	public static int TYPE_NOT_CONNECTED = 0;
    private static CustomProgressDialog dialog;
	private static Toast mToast = null;

	public static CustomProgressDialog createDialog(Context context, CustomProgressDialog dialog) {
		if(dialog == null && context instanceof Activity && !((Activity) context).isFinishing()) {
            try {
                dialog = new CustomProgressDialog(context);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.show();
            }catch (Exception e){
                e.printStackTrace();
            }
		}
		return dialog;
	}

	public static CustomProgressDialog hideDialog(final CustomProgressDialog dialog) {
		try{
			dialog.dismiss();
		}catch (Exception e){
		}
		return null;
	}

    public static CustomProgressDialog getDialog(){
        return dialog;
    }

    public static void showSoftKeyboard(Activity activity){
        try{
            if(!activity.isFinishing()) {
                InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
        }catch(NullPointerException e){ }
    }

    public static void hideSoftKeyboard(Activity activity) {
        try {
            if(!activity.isFinishing()) {
                InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            }
        }catch (NullPointerException e){ }
    }

	public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        try {
            if (null != activeNetwork) {
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                    return TYPE_WIFI;

                if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                    return TYPE_MOBILE;
            }
        }catch (Exception e){
            return TYPE_NOT_CONNECTED;
        }
        return TYPE_NOT_CONNECTED;
    }

	public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

	public final static void showGlobalToast(Context context, String str) {
        if(context instanceof Activity && !((Activity) context).isFinishing()) {
            if (mToast == null) {
                mToast = Toast.makeText(context, str, Toast.LENGTH_LONG);
            } else {
                mToast.setText(str);
            }
            mToast.show();
        }
    }

    public static String getHumanizeTime(String datetime){
        SimpleDateFormat formatter = null;

        if(datetime.contains("T")){
            formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        }else {
            formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }

        String result = datetime.replace("-", ".");
        try {
            String today = formatter.format(new Date());
            Date todayDate = formatter.parse(today);
            Date objectDate = formatter.parse(datetime);
            long diff = (todayDate.getTime() - objectDate.getTime()) / 1000;

            if(diff < 60){
                result = "방금";
            }else if(diff < (60 * 10)){
                result = "몇 분 전";
            }else if(diff < (60 * 60)){
                result = (diff/60) + "분 전";
            }else if(diff < (60 * 60 * 24)){
                diff = diff / (60 * 60);
                result = diff + "시간 전";
            }else if(diff < (60 * 60 * 24 * 2)) {
                result = "어제";
            }else if(diff < (60 * 60 * 24 * 31)) {
                result = ((int)(diff / (60 * 60 * 24))) + "일 전";
            }else{
                result = result.substring(0, 10);
            }
        } catch (ParseException e) {
            result = datetime.replace("-", ".");
        }

        return result;
    }


    public static void sendEmail(Context context, String subject){
        String content = "내용을 입력하세요.";

        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", CONST.EMAIL, null));
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        try {
            context.startActivity(Intent.createChooser(intent, "[" + subject + "] 을(를) 전송할 이메일 앱을 선택해주세요."));
        }catch (Exception e){ }
    }
}