package com.jdy.hotel.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.jdy.hotel.R;
import com.jdy.hotel.ui.MainActivity;
import com.jdy.hotel.utils.L;
import com.jdy.hotel.utils.SharedPreferencesUtil;
import com.jdy.hotel.utils.StatusBarUtil;

public class LoginActivity extends AppCompatActivity{

    public static final String APP = "Supa";
    public final static String KEEP_PASS = APP + "-" + "keep-pass";
    public final static String ACCOUNT = APP + "-" + "account";
    public final static String PASS = APP + "-" + "password";
    private LoginViewModel loginViewModel;
    private ProgressBar loadingProgressBar;
    private EditText usernameEditText, passwordEditText;
    private CheckBox remember;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StatusBarUtil.setCommonUI(this, true);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory()).get(LoginViewModel.class);
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        remember = findViewById(R.id.user_remember);
        final Button loginButton = findViewById(R.id.login);
        loadingProgressBar = findViewById(R.id.login_load_pb);

        //本地界面数据校验结果
        loginViewModel.getLoginFormState().observe(this, result -> {
            if (result == null) {
                return;
            }
            boolean status = result.isSuccess();
            loginButton.setEnabled(status);

            if (!status) {
                int code = result.getCode();
                if (code == R.string.invalid_username) {
                    usernameEditText.setError(getString(R.string.invalid_username));
                }

                if (code == R.string.invalid_password) {
                    passwordEditText.setError(getString(R.string.invalid_password));
                }
            }
        });

        //网络数据请求结果
        loginViewModel.getLoginResult().observe(this, result -> {
            if (result == null) {
                return;
            }
            loadingProgressBar.setVisibility(View.GONE);
            if (result.isSuccess()) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            } else if (result.getData() == null) { //出现异常，证明是系统问题
                showLoginFailed(result.getMessage());
            } else {//可能需要注册账号，或者修改密码
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
            setResult(Activity.RESULT_OK);
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };

        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);

        passwordEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
            return false;
        });

        L.i("-------------------------onCreate-----------------------------------");
        loginButton.setOnClickListener(v -> {
            loadingProgressBar.setVisibility(View.VISIBLE);
            loginViewModel.login(usernameEditText.getText().toString(),
                    passwordEditText.getText().toString());
        });
    }

    private void showLoginFailed(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //保存状态
        SharedPreferencesUtil.putBoolean(KEEP_PASS, remember.isChecked());
        //是否记住密码
        if (remember.isChecked()) {
            //记住用户名和密码
            SharedPreferencesUtil.putString(ACCOUNT, usernameEditText.getText().toString().trim());
            SharedPreferencesUtil.putString(PASS, passwordEditText.getText().toString().trim());
        } else {
            SharedPreferencesUtil.deleShare(ACCOUNT);
            SharedPreferencesUtil.deleShare(PASS);
        }
    }
}
