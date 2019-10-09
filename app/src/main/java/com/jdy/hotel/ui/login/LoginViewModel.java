package com.jdy.hotel.ui.login;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jdy.hotel.R;
import com.jdy.hotel.data.LoginDataSource;
import com.jdy.hotel.data.LoginRepository;
import com.jdy.hotel.net.ResponseData;
import com.jdy.hotel.net.ResponseDataListener;


public class LoginViewModel extends ViewModel implements ResponseDataListener<ResponseData> {

    private MutableLiveData<ResponseData> loginFormState = new MutableLiveData<>();
    private MutableLiveData<ResponseData> loginResult = new MutableLiveData<>();

    private LoginRepository loginRepository;

    LoginViewModel() {
        this.loginRepository = LoginRepository.getInstance(new LoginDataSource(this));
    }

    LiveData<ResponseData> getLoginFormState() {
        return loginFormState;
    }

    LiveData<ResponseData> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        loginRepository.login(username, password);
    }

    public void loginDataChanged(String username, String password) {
        ResponseData result;
        if (!isUserNameValid(username)) {
            result = ResponseData.error(R.string.invalid_username);
        } else if (!isPasswordValid(password)) {
            result = ResponseData.error(R.string.invalid_password);
        } else {
            result = ResponseData.success();
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
    public void onRespond(ResponseData data) {
        loginResult.setValue(data);
    }
}
