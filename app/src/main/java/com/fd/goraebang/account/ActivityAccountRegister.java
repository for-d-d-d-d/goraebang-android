package com.fd.goraebang.account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fd.goraebang.main.ActivityMain_;
import com.fd.goraebang.R;
import com.fd.goraebang.consts.CONST;
import com.fd.goraebang.custom.CustomActivity;
import com.fd.goraebang.model.User;
import com.fd.goraebang.util.AppController;
import com.fd.goraebang.util.CallUtils;
import com.fd.goraebang.util.CustomProgressDialog;
import com.fd.goraebang.util.Utils;
import com.fd.goraebang.util.listener.EditTextChanged;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

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
    private boolean checkEmail, checkPassword;

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

            if(etEmail.getText().toString().length() > 8 && etEmail.getText().toString().contains("@")){
                checkEmail = true;
            }

            if(etPassword.getText().toString().length() > 3 && etPassword.getText().toString().equals(etPasswordConfirm.getText().toString())){
                checkPassword = true;
            }

            checkForm();
        }
    }

    private void checkForm(){
        //모두 통과이면 다음 버튼 활성화.
        if(checkEmail && checkPassword) {
            btnSignUp.setTextColor(getResources().getColor(R.color.white));
            btnSignUp.setClickable(true);
        }else{
            btnSignUp.setTextColor(getResources().getColor(R.color.gray));
            btnSignUp.setClickable(false);
        }
    }

    private void register(){
        final String email = etEmail.getText().toString();
        final String password = etPassword.getText().toString();
        final String passwordConfirm = etPasswordConfirm.getText().toString();

        dialog = Utils.createDialog(this, dialog);
        Call<User> call = AppController.getAccountService().register(email, null, null, password, passwordConfirm);
        call.enqueue(new CallUtils<User>(call, this, getResources().getString(R.string.msgErrorCommon)) {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body().getResult().equals("SUCCESS")) {
                    AppController.USER = response.body();
                    AppController.USER_ID = response.body().getId();
                    AppController.USER_MY_LIST_ID = response.body().getMyListId();
                    AppController.USER_TOKEN = response.body().getMyToken();

                    saveUserDataAndGoNext(email, password);
                    showToast("회원가입에 성공했습니다.");
                } else {
                    showToast(response.body().getMessage());
                }
                onComplete();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t){
                showToast(msg);
                onComplete();
            }

            @Override
            public void onComplete() {
                dialog = Utils.hideDialog(dialog);
            }
        });
    }

    private void saveUserDataAndGoNext(String email, String password){
        editor.putString("user_id", AppController.USER_ID);
        editor.putString("user_my_list_id", AppController.USER_MY_LIST_ID);
        editor.putString("user_token", AppController.USER_TOKEN);
        editor.putString("email", email);
        editor.putString("password", password);
        editor.commit();

        finish();
    }

    @Override
    public void finish() {
        Intent intent = new Intent(getApplicationContext(), ActivityMain_.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        super.finish();
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
