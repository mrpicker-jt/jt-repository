package com.gproject.android.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gproject.R;
import com.gproject.android.network.BaseException;
import com.gproject.android.network.RequestListener;
import com.gproject.android.response.LoginResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 姜腾 on 2017/4/15.
 */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.login_studentid)
    public EditText idEdit;
    @BindView(R.id.login_password)
    public EditText passwordEdit;
    @BindView(R.id.login_loginbtn)
    public Button loginBtn;

    //login
    private boolean isIdEdit = false;
    private boolean isPasswordEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        idEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    isIdEdit = true;
                } else {
                    isIdEdit = false;
                }
                changeLoginBtn();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        passwordEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    isPasswordEdit = true;
                } else {
                    isPasswordEdit = false;
                }
                changeLoginBtn();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void changeLoginBtn() {
        if (isIdEdit && isPasswordEdit) {
            loginBtn.setSelected(true);
            loginBtn.setClickable(true);
        } else {
            loginBtn.setSelected(false);
            loginBtn.setClickable(false);
        }
    }

    private void login() {
        String id = idEdit.getText().toString();
        String password = passwordEdit.getText().toString();
        if (id != null && password != null) {
            networkManager.login(id, password, new RequestListener<LoginResponse>() {
                @Override
                public void onSuccess(LoginResponse response) {
                    showText(response.doc.name);
                    TabActivity.Start(LoginActivity.this);
                    finish();
                }

                @Override
                public void onFailure(BaseException exception, Throwable throwable) {
                    showText("fail");
                    networkManager.handleNetworkFailure(exception, throwable);
                }
            });
        } else {
            showText("请输入id和密码");
            return;
        }
    }
}
