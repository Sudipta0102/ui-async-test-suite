// This is the entry point for the UI.

// Import auth (login flow)
import { initAuth } from './auth.js';

// import polling mechanisms of the dashboard
import { startPolling, stopPolling } from './features.js';

//app startup
function startApp(){
    initAuth(() =>{
        showDashboard();

        startPolling();
    });
}


// ui state transitons
function showDashboard(){

    const loginSection = document.getElementById('login-section');
    const dashboardSection = document.getElementById('dashboard-section');

    // hide login
    loginSection.hidden = true;

    // show dashboard 
    dashboardSection.hidden = false;

}


// this is for future extension 
function shutdownApp(){

    stopPolling();

    console.error('App shutdown');
}

//starting app when script loading completes
startApp();