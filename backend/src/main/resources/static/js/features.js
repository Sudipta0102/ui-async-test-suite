
// This file owns everything about the feature flag table.
// If something is async, flaky, delayed, or annoying — it lives here.
// No fetch calls directly in this file; api.js handles HTTP.

import { fetchFeatures, toggleFeature } from './api.js';

// How often we ask the backend “are we there yet?”
const POLLING_INTERVAL_MS = 2000;

// Where we store the setInterval handle so we don’t start polling twice
let pollingHandle = null;

// PUBLIC API (Called from app.js)

// 1.
// Starts the polling loop that refreshes feature state from the backend.
// Called once after successful login.
export function startPolling(){
    // if polling is running, don't start once again
    if(pollingHandle !== null){
        return;
    }

    // one poll immediately so UI doesn't come up empty
    pollOnce();

    // then keep polling
    // setInterval(function, pollingDelayInMilliseconds);
    pollingHandle = setInterval(() => {
        pollOnce();
    }, POLLING_INTERVAL_MS);
}

// 2.
// Stop polling (useful for logout or fatal errors)
export function stopPolling(){
    // if polling already stopped
    if(pollingHandle === null){
        return;
    }

    clearInterval(pollingHandle);

    // reset
    pollingHandle = null;
}

// 3.
// Called when someone clicks “Toggle” on a feature
export function handleToggle(featureName) {

    // default is disabled
    disableFeatureAction(featureName);

    // asking backend to toggle
    requestFeatureToggle(featureName);
}

// 4.
// Renders the complete list of feature flags.
// Called on every successful polling cycle.
export function renderFeatures(features){

    console.log('renderFeatures called with:', features);
     // re-rendering from scratch every time

     //
     const tableBody = document.getElementById('features-table-body');

     // empty it
     tableBody.innerHTML = '';

     features.forEach(feature => {
        // creating new row
        const row = document.createElement('tr');
        
        // a. feature name going in the first column
        row.appendChild(renderNameCell(feature));
        
        // b. ON OFF value
        row.appendChild(renderStateCell(feature));

        // c. async values (ACTIVE/PENDING/FAILED)
        row.appendChild(renderStatusCell(feature));

        // d. toggle button
        row.appendChild(renderActionCell(feature));

        // add the row to the table
        tableBody.appendChild(row);
        
     });

     // update time stamp to make polling apparent in this web page
     updateLastRefreshTimestamp();

}


// POLLING LOGIC

// 5.
// Executes a single polling cycle.
async function pollOnce(){
    // putting async in try catch, equivalent of InterruptedException 
    // must be applied here.
    try{
    const featureMap = await fetchFeaturesFromBackend();

    // Backend returns an object (map), UI needs a list
    // const featureList = Object.values(featureMap);
    const featureList = Object.values(featureMap).map(f => ({
        name: f.flag,
        state: f.enabled ? 'ON' : 'OFF',
        status: 'ACTIVE'
    }));    

    renderFeatures(featureList);
    }catch(error){
        showPollingError(error);
    }    
}

// DOM RENDERING HELPERS

// 6.
// Renders the feature name cell.
function renderNameCell(feature){

    const cell = document.createElement('td');
    cell.textContent = feature.name;
    return cell;

}

// 7.
// Renders the feature state cell (ON / OFF).
function renderStateCell(feature){

    const cell = document.createElement('td');
    cell.textContent = feature.state;
    return cell;
}

// 8.
// Renders the async status cell (ACTIVE / PENDING / FAILED).
function renderStatusCell(feature){

    const cell = document.createElement('td');
    cell.textContent = feature.status;
    return cell;

}

// 9.
// Renders the action cell containing the toggle button.
function renderActionCell(feature){

    const cell = document.createElement('td');

    // create the toggle button
    const button = document.createElement('button');
    button.textContent = 'Toggle';

    // if the feature is still propagating, disable the button
    if(feature.status === 'PENDING'){
        button.disabled = true;
    }

    // when clicked, asking to toggle the feature
    button.addEventListener('click', ()=>{
        handleToggle(feature.name);
    });

    cell.appendChild(button);

    return cell;
}


// ASYNC VISIBILITY HELPERS


// 10.
// Disables the toggle button for a specific feature.
// Used immediately after a toggle request.
function disableFeatureAction(featureName){

    // taking all the rows in the table
    const rows = document.querySelectorAll('#features-table-body tr');

    rows.forEach(row=>{
        // matching the feature name in first col
        if(row.firstChild.textContent === featureName){

            const button = row.lastChild.querySelector('button');

            // then disable it
            if(button){
                button.disabled = true;
            }

        }

    });
}

// 11.
// Updates the visible timestamp after each successful poll.
function updateLastRefreshTimestamp(){

    const timestampElement = document.getElementById('last-refresh-timestamp');

    timestampElement.textContent = new Date().toLocaleTimeString();

}

// 12.
// Displays a visible error when polling fails.
function showPollingError(error){

    // not showing in UI as of now
    console.log('Polling failed:', error);

}

// These will be wired to api.js
// 13.
async function fetchFeaturesFromBackend(){

    return await fetchFeatures();

}

// 14.
function requestFeatureToggle(featureName){

    toggleFeature(featureName);
}