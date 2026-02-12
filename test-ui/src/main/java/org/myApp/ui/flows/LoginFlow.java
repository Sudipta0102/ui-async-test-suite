package org.myApp.ui.flows;

import org.myApp.ui.pages.DashboardPage;
import org.myApp.ui.pages.LoginPage;

public class LoginFlow {

    private final LoginPage loginPage;

    public LoginFlow(LoginPage loginPage) {
        this.loginPage = loginPage;
    }

    public DashboardPage loginAs(String username, String password) {

        return loginPage.loginAs(username, password);
    }
}
