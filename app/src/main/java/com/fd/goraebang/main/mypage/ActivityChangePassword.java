package com.fd.goraebang.main.mypage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.fd.goraebang.R;
import com.fd.goraebang.consts.CONST;
import com.fd.goraebang.custom.CustomActivityWithToolbar;
import com.fd.goraebang.model.User;
import com.fd.goraebang.util.AppController;
import com.fd.goraebang.util.CallUtils;
import com.fd.goraebang.util.CustomProgressDialog;
import com.fd.goraebang.util.Utils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import retrofit2.Call;
import retrofit2.Response;

@EActivity(R.layout.activity_change_password)
public class ActivityChangePassword extends CustomActivityWithToolbar{
    @ViewById
    EditText etCurrentPassword, etNewPassword, etNewPasswordConfirm;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private CustomProgressDialog dialog = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pref = getSharedPreferences(CONST.PREF_NAME, MODE_PRIVATE);
        editor = pref.edit();
    }
    
    @AfterViews
    void afterViews(){
        setToolbar("비밀번호 변경", 0, R.drawable.ic_arrow_back_white_24dp, 0, 0);
    }

    private void updateUserPassword(){
        final String currentPassword = etCurrentPassword.getText().toString();
        final String newPassword = etNewPassword.getText().toString();
        final String newPasswordConfirm = etNewPasswordConfirm.getText().toString();

        if(!newPassword.equals(newPasswordConfirm) || newPassword.length() < 6){
            Utils.showGlobalToast(this, "새로운 비밀번호를 다시 확인해주세요. (6자리 이상)");
            return;
        }

        dialog = Utils.createDialog(this, dialog);
        Call<User> call = AppController.getAccountService().modify(
                AppController.USER_TOKEN,
                AppController.USER.getName(),
                AppController.USER.getGender(),
                currentPassword,
                newPassword,
                newPasswordConfirm,
                null
        );

        call.enqueue(new CallUtils<User>(call, this, getResources().getString(R.string.msgErrorCommon)) {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body().getResult().equals("SUCCESS")) {
                    editor.putString("password", newPassword);
                    editor.commit();
                    showToast("저장되었습니다.");
                } else {
                    showToast(response.body().getMessage());
                }
                onComplete();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                showToast(msg);
                onComplete();
            }

            @Override
            public void onComplete(){
                dialog = Utils.hideDialog(dialog);
            }
        });
    }

    public void onClick(View v) {
        Intent intent = null;

        switch(v.getId()){
            case R.id.btnBack:
                finish();
                break;
            case R.id.btnComplete:
                updateUserPassword();
                break;
            default :
                break;
        }

        if(intent != null) {
            startActivity(intent);
        }
    }
}
