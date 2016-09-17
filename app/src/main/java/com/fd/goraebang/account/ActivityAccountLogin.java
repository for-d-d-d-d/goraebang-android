package com.fd.goraebang.account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fd.goraebang.ActivityMain_;
import com.fd.goraebang.R;
import com.fd.goraebang.consts.CONST;
import com.fd.goraebang.custom.CustomActivity;
import com.fd.goraebang.util.CustomProgressDialog;
import com.fd.goraebang.util.listener.EditTextChanged;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_account_login)
public class ActivityAccountLogin extends CustomActivity {
    @ViewById
    Button btnLogin;
    @ViewById
    EditText etEmail, etPassword;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private CustomProgressDialog dialog = null;
    private String email = null, mbFacebookId = null, mbName;

    @AfterViews
    void init(){
        pref = getSharedPreferences(CONST.PREF_NAME, MODE_PRIVATE);
        editor = pref.edit();

        // btnLogin.setClickable(false);
        btnLogin.setTextColor(getResources().getColor(R.color.gray));

        ListenerEditTextChanged listener = new ListenerEditTextChanged();
        etEmail.addTextChangedListener(listener);
        etPassword.addTextChangedListener(listener);
    }

    private class ListenerEditTextChanged extends EditTextChanged {
        @Override
        public void afterTextChanged(Editable s) {
            if(etPassword.getText().toString().length() > 3 && etEmail.getText().toString().length() > 4) {
                btnLogin.setTextColor(getResources().getColor(R.color.white));
                btnLogin.setClickable(true);
            }else{
                btnLogin.setTextColor(getResources().getColor(R.color.gray));
                btnLogin.setClickable(false);
            }
        }
    }

    private void login(){
        final String email = etEmail.getText().toString();
        final String password = etPassword.getText().toString();

        startActivity(new Intent(ActivityAccountLogin.this, ActivityMain_.class));
        finish();
        /*
        dialog = Utils.createDialog(this, dialog);
        Call<User> call = AppController.getAccountService().login(email, password);
        call.enqueue(new CallUtils<User>(call, this, getResources().getString(R.string.msgErrorCommon)) {
            @Override
            public void onResponse(Response<User> response) {
                onComplete();

                if (response.isSuccess() && response.body().getToken() != null && response.body().getToken().length() > 0) {
                    AppController.AUTHORIZATION = "JWT " + response.body().getToken();

                    editor.putString("authorization", response.body().getToken());
                    editor.putString("email", email);
                    editor.putString("password", password);
                    editor.commit();
                } else {
                    tvMsgPassword.setText("아이디 혹은 비밀번호가 정확하지 않습니다.");
                }
            }

            @Override
            public void onComplete() {
                dialog = Utils.hideDialog(dialog);
            }
        });
        */
    }

    public void onClick(View v) {
        Intent intent = null;

        switch(v.getId()){
            case R.id.btnLogin:
                login();
                break;
            case R.id.btnRegister:
                intent = new Intent(ActivityAccountLogin.this, ActivityAccountRegister_.class);
                break;
            default :
                break;
        }

        if(intent != null) {
            startActivity(intent);
        }
    }
}
