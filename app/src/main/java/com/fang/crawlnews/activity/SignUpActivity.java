package com.fang.crawlnews.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fang.crawlnews.R;
import com.fang.crawlnews.base.BaseActivity;
import com.fang.crawlnews.util.KeyBoardUtil;
import com.fang.crawlnews.widget.HeaderLayout;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2016/8/6.
 */
public class SignUpActivity extends BaseActivity {
    private HeaderLayout headerLayout;
    private EditText etAccount;
    private EditText etPassword;
    private Button btnSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initView();
    }

    private void initView() {
        headerLayout = (HeaderLayout) findViewById(R.id.sign_up_headerlayout);
        etAccount = (EditText) findViewById(R.id.sign_up_et_account);
        etPassword = (EditText) findViewById(R.id.sign_up_et_password);
        btnSignUp = (Button) findViewById(R.id.sign_up_btn_sign);

        headerLayout.setTitle("注册");
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyBoardUtil.closeKeyboard(etAccount, SignUpActivity.this);

                if (TextUtils.isEmpty(etAccount.getText().toString()) ||
                        TextUtils.isEmpty(etPassword.getText().toString())) {
                    return;
                } else {
                    userSignUp();
                }
            }
        });
    }


    /**
     * 用户注册
     **/
    private void userSignUp() {
        BmobUser bu = new BmobUser();
        bu.setUsername(etAccount.getText().toString());
        bu.setPassword(etPassword.getText().toString());

        bu.signUp(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser s, BmobException e) {
                if (e == null) {
                    Toast.makeText(SignUpActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    if (e.getErrorCode() == 202) {
                        Toast.makeText(SignUpActivity.this, "该用户名已被注册", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
