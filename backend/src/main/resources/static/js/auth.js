// This file handles the login flow.

// importing API login call
import { login } from './api.js';

// entry points

// wiring login ui and notify app when login succeeds
export function initAuth(onLoginSuccess){

    // all the web elements 
    const usernameInput = document.getElementById('username-input');
    const passwordInput = document.getElementById('password-input');
    const loginButton = document.getElementById('login-button');
    const loginError = document.getElementById('login-error');
    const loginLoading = document.getElementById('login-loading');

    // helpers
    // showing a visiblw error message
    function showError(message){
        loginError.textContent = message;
        loginError.hidden = false; 
    }

    // clearing my existing error
    function clearError(){
        loginError.textContent = '';
        loginError.hidden = true;

    }

    // a loading indicator
    function showLoading(){
        loginLoading.hidden = false;
        loginLoading.disabled = true;
    }

    // hide loading indicator
    function hideLoading(){
        loginLoading.hidden = true;
        loginButton.disabled = false;
    }

    // after clicking login button
    loginButton.addEventListener('click', async() => {

        // starting by clearing old errors
        clearError();

        // Read current input values
        const username = usernameInput.value;
        const password = passwordInput.value;

        // checking no request is going empty
        if (!username || !password) {
            showError('Username and password are required');
            return;
        }

        // show loading to show async behavior
        showLoading();

        try{
            // Call the backend login endpoint
            // If this succeeds, we assume auth is good
            await login(username, password);

            // hide loading 
            hideLoading();

            // notifying app.js
            onLoginSuccess();
        }catch(error){
            // if login fails, this comes up
            // I don't want silent auth failures
            hideLoading();
            showError('Login Failed');

            console.log('Login error:', error);
        }

    });
}