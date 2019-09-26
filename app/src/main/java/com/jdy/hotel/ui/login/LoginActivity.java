package com.jdy.hotel.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.jdy.hotel.R;
import com.jdy.hotel.ui.MainActivity;
import com.jdy.hotel.utils.L;
import com.jdy.hotel.utils.StatusBarUtil;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        StatusBarUtil.setCommonUI(this,true);

        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory()).get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.login_load_pb);

        loginViewModel.getLoginFormState().observe(this, result -> {
            if (result == null) {
                return;
            }
            boolean status = result.getStatus();
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

        loginViewModel.getLoginResult().observe(this, result -> {
            if (result == null) {
                return;
            }
            loadingProgressBar.setVisibility(View.GONE);
            if (result.getStatus()) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            } else if (result.getException() == null){ //出现异常，证明是系统问题
                showLoginFailed(result.getMessage());
            } else {//可能需要注册账号，或者修改密码
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
            setResult(Activity.RESULT_OK);
//            finish();
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

}
