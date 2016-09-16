package com.fd.goraebang.account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fd.goraebang.R;
import com.fd.goraebang.consts.CONST;
import com.fd.goraebang.custom.CustomActivity;
import com.fd.goraebang.util.CustomProgressDialog;
import com.fd.goraebang.util.Utils;
import com.fd.goraebang.util.listener.EditTextChanged;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_account_register)
public class ActivityAccountRegister extends CustomActivity {
    @ViewById
    EditText etEmail, etPassword, etPasswordConfirm;
    @ViewById
    Button btnSignUp;

    private CustomProgressDialog dialog = null;
    private List<String> emailItems = new ArrayList<String>();
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private int prevLength = 1;
    private boolean checkEmail, checkPassword, checkPasswordConfirm, checkNickname;

    @AfterViews
    void init(){
        pref = getSharedPreferences(CONST.PREF_NAME, MODE_PRIVATE);
        editor = pref.edit();

        btnSignUp.setClickable(false);
        btnSignUp.setTextColor(getResources().getColor(R.color.gray));

        ListenerEditTextChanged listener = new ListenerEditTextChanged();

        etEmail.addTextChangedListener(listener);
        etPassword.addTextChangedListener(listener);
        etPasswordConfirm.addTextChangedListener(listener);
    }

    private class ListenerEditTextChanged extends EditTextChanged {
        @Override
        public void afterTextChanged(Editable s) {
            checkEmail = false;
            checkPassword = false;
            checkPasswordConfirm = false;

            if(etPassword.getText().toString().length() > 8 && etPassword.getText().toString().contains("@")){
                checkEmail = true;
            }

            if(etPassword.getText().toString().length() > 3){
                checkPassword = true;
            }

            if(etPasswordConfirm.getText().toString().length() > 0 && etPassword.getText().toString().equals(etPasswordConfirm.getText().toString())){
                checkPasswordConfirm = true;
            }

            checkForm();
        }
    }

    private void checkForm(){
        //모두 통과이면 다음 버튼 활성화.
        if(checkEmail && checkPassword && checkPasswordConfirm) {
            btnSignUp.setTextColor(getResources().getColor(R.color.white));
            btnSignUp.setClickable(true);
        }else{
            btnSignUp.setTextColor(getResources().getColor(R.color.gray));
            btnSignUp.setClickable(false);
        }
    }

    private void register(){
        String registrationId = pref.getString(CONST.PROPERTY_REG_ID, "");
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        dialog = Utils.createDialog(this, dialog);
        /*
        Call<User> call = AppController.getAccountService().register(email, nickanme, password, "AN", null, null, null, false);
        call.enqueue(new CallUtils<User>(call, this) {
            @Override
            public void onResponse(Response<User> response) {
                dialog = Utils.hideDialog(dialog);
                if (response.isSuccess()) {
                    AppController.AUTHORIZATION = "JWT " + response.body().getToken();

                    editor.putString("authorization", response.body().getToken());
                    editor.putString("email", response.body().getEmail());
                    editor.putString("password", response.body().getEmail() + "_p");
                    editor.putBoolean(CONST.PREF_AGREE + response.body().getEmail(), true);
                    editor.commit();

                    getUser();
                } else {
                    tvMsgEmail.setTextColor(getResources().getColor(R.color.orange));
                    tvMsgEmail.setText(getResources().getString(R.string.msgErrorEmail));
                }
            }

            @Override
            public void onFailure(Throwable t) {
                dialog = Utils.hideDialog(dialog);
                super.onFailure(t);
            }
        });
        */
    }

    public void onClick(View v) {
        Intent intent = null;

        switch (v.getId()) {
            case R.id.btnSignUp:
                register();
                break;
            case R.id.btnLogin:
                intent = new Intent(ActivityAccountRegister.this, ActivityAccountLogin_.class);
                break;
            default:
                break;
        }

        if(intent != null)
            startActivity(intent);
    }
}
