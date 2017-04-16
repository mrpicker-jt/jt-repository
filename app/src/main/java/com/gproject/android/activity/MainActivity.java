package com.gproject.android.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gproject.R;
import com.gproject.android.network.BaseException;
import com.gproject.android.network.RequestListener;
import com.gproject.android.response.RegisterResponse;
import com.gproject.android.response.TestResponse;
import com.gproject.utils.TextUtils;

public class MainActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView textView = (TextView) findViewById(R.id.main_hello);
        final TextView textView1= (TextView) findViewById(R.id.main_state);
        Button button = (Button) findViewById(R.id.main_connect);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                networkManager.testConnection(new RequestListener<TestResponse>() {
                    @Override
                    public void onSuccess(TestResponse response) {
                        textView.setText(response.response);
                        textView1.setText("success!");
                        TextUtils.setTextColor(textView1,R.color.green_a);
                    }

                    @Override
                    public void onFailure(BaseException exception, Throwable throwable) {
                        textView.setText(exception.message + "_" + exception.code);
                        textView1.setText("fail!");
                        TextUtils.setTextColor(textView1,R.color.red_a);
                    }
                });
            }
        });

        Button loginButton= (Button) findViewById(R.id.main_login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username="jt";
                String password="123";
                int authority=1;
                networkManager.register(username, password, authority, new RequestListener<RegisterResponse>() {
                    @Override
                    public void onSuccess(RegisterResponse response) {
                        showText(response.username);
                        textView.setText(response.username);
                    }

                    @Override
                    public void onFailure(BaseException exception, Throwable throwable) {
                        networkManager.handleNetworkFailure(exception,throwable);
                    }
                });
            }
        });

    }

}
