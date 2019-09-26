package com.jdy.hotel.ui.login;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jdy.hotel.R;
import com.jdy.hotel.data.LoginDataSource;
import com.jdy.hotel.data.LoginRepository;
import com.jdy.hotel.data.RequestOverListener;
import com.jdy.hotel.data.response.CorrectData;
import com.jdy.hotel.data.response.ErrorData;
import com.jdy.hotel.data.response.ResponseResult;


public class LoginViewModel extends ViewModel implements RequestOverListener<ResponseResult> {

    private MutableLiveData<ResponseResult> loginFormState = new MutableLiveData<>();
    private MutableLiveData<ResponseResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

//    private ModelCallback callback;

    LoginViewModel() {
        this.loginRepository = LoginRepository.getInstance(new LoginDataSource(this));
    }

    LiveData<ResponseResult> getLoginFormState() {
        return loginFormState;
    }

    LiveData<ResponseResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        loginRepository.login(username, password);
    }

    public void loginDataChanged(String username, String password) {
        ResponseResult result;
        if (!isUserNameValid(username)) {
            result = new ErrorData(R.string.invalid_username);
        } else if (!isPasswordValid(password)) {
            result = new ErrorData(R.string.invalid_password);
        } else {
            result = new CorrectData(true);
        }
        loginFormState.setValue(result);
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }

        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    @Override
    public void over(ResponseResult result) {
        loginResult.setValue(result);
    }

//
//    public void setCallback(ModelCallback callback) {
//        this.callback = callback;
//    }
}
