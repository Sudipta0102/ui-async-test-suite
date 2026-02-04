// This file talks to the backend.
// Fetch, Timeout, Errorhandling

// Base Url, Empty string means same host + port
const BASE_URL = '';

// Async can be slow, that's why 5 secs(not sure)
const REQUEST_TIMEOUT_MS = 7000;

// FETCH HELPER

// Wrapper around fetch that adds:
// timeout, JSON parsing, error hanlding
async function fetchWithTimeout(url, options={}) {
    // aborting a request if it takes too long
    const controller = new AbortController();
    const timeoutId = setTimeout(() => controller.abort(), REQUEST_TIMEOUT_MS);


    try{
        // request with abort
        const response = await fetch(url, {
            ...options,
            signal: controller.signal
        });
    

        // If a non-2xx response, that’s an error for the UI
        if(!response.ok){
            throw new Error('HTTP ${response.status}');
        }

        // Only attempt JSON parsing if backend actually sent JSON
        const contentType = response.headers.get('Content-Type');
        if (contentType && contentType.includes('application/json')) {
           return await response.json();
        }

        // For endpoints like /api/auth/login (200 with no body)
        return;
    } catch(error){
        if (error.name === 'AbortError') {
            throw new Error('Request timed out');
        }

        // Re-throw everything else so UI can decide what to do
        throw error;
    } finally {
        clearTimeout(timeoutId);
    }   

}


// AUTH

// login call here. ALso Backend is simple, no token storage, no nothing.
export async function login(username, password) {

    return fetchWithTimeout(`${BASE_URL}/api/auth/login`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      username,
      password
    })
  });
    
} 


// FEATURE API
export async function fetchFeatures() {
  return fetchWithTimeout(`${BASE_URL}/api/features`, {
    method: 'GET'
  });
}

export async function toggleFeature(featureName) {
  return fetchWithTimeout(`${BASE_URL}/api/features/${featureName}`, {
    method: 'POST'
  });
}

// TEST ONLY
export async function resetSystem() {
  return fetchWithTimeout(`${BASE_URL}/api/test/reset`, {
    method: 'POST'
  });
}

// HEALTH VISIBILITY(CHAOS)
export async function fetchHealth() {
  return fetchWithTimeout(`${BASE_URL}/actuator/health`, {
    method: 'GET'
  });
}



