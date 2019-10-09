package com.jdy.hotel.data;


import com.jdy.hotel.net.ResponseData;


public class LoginRepository {

    private static volatile LoginRepository instance;

    private LoginDataSource dataSource;

    private ResponseData data = null;

    private LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static LoginRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return data != null;
    }

    public void logout() {
        data = null;
        dataSource.logout();
    }

    private void setLoggedInUser(ResponseData user) {
        this.data = user;
    }

    public void login(String username, String password) {
        dataSource.login(username, password);
    }
}
