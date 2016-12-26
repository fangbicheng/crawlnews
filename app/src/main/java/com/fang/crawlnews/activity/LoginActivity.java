package com.fang.crawlnews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fang.crawlnews.R;
import com.fang.crawlnews.base.BaseActivity;
import com.fang.crawlnews.fragment.MeFragment;
import com.fang.crawlnews.util.KeyBoardUtil;
import com.fang.crawlnews.widget.HeaderLayout;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2016/8/6.
 */
public class LoginActivity extends BaseActivity {
    private HeaderLayout headerLayout;
    private EditText etAccount;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvSignUp;

    private static final int RESULT_OK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        headerLayout = (HeaderLayout) findViewById(R.id.login_headerlayout);
        etAccount = (EditText) findViewById(R.id.login_et_account);
        etPassword = (EditText) findViewById(R.id.login_et_password);
        btnLogin = (Button) findViewById(R.id.login_btn_login);
        tvSignUp = (TextView) findViewById(R.id.login_tv_sign_up);

        headerLayout.setTitle("登录");
        btnLogin.setOnClickListener(new mOnClickListener());
        tvSignUp.setOnClickListener(new mOnClickListener());
    }

    private class mOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.login_btn_login:
                    KeyBoardUtil.closeKeyboard(etAccount, LoginActivity.this);
                    userLogin();
                    break;
                case R.id.login_tv_sign_up:
                    Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }

    private void userLogin() {
        BmobUser bu = new BmobUser();
        bu.setUsername(etAccount.getText().toString());
        bu.setPassword(etPassword.getText().toString());
        bu.login(new SaveListener<BmobUser>() {

            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if(e==null){
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MeFragment.class);
                    intent.putExtra("username", etAccount.getText().toString());
                    setResult(RESULT_OK, intent);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "用户名或密码错误，请重新输入", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
