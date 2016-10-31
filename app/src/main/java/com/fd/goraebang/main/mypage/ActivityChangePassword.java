package com.fd.goraebang.main.mypage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.fd.goraebang.R;
import com.fd.goraebang.custom.CustomActivityWithToolbar;
import com.fd.goraebang.util.CustomProgressDialog;
import com.fd.goraebang.util.Utils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_change_password)
public class ActivityChangePassword extends CustomActivityWithToolbar{
    @ViewById
    EditText etCurrentPassword, etNewPassword, etNewPasswordConfirm;

    private CustomProgressDialog dialog = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @AfterViews
    void afterViews(){
        setToolbar("비밀번호 변경", 0, R.drawable.ic_arrow_back_white_24dp, 0, 0);
    }

    private void updateUserPassword(){
        dialog = Utils.createDialog(this, dialog);

        final String currentPassword = etCurrentPassword.getText().toString();
        final String newPassword = etNewPassword.getText().toString();
        final String newPasswordConfirm = etNewPasswordConfirm.getText().toString();

        if(newPassword != newPasswordConfirm){
            Utils.showGlobalToast(this, "새로운 비밀번호를 다시 확인해주세요.");
            return;
        }

        Utils.showGlobalToast(this, "저장되었습니다.");
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
