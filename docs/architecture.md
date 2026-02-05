# Architecture — UI Async Test Platform

## Purpose

This repository is a mature showcase focused on **testing asynchronous, eventually consistent UI systems**.

It is intentionally **not**:
- a generic Selenium framework
- a production application
- a frontend showcase

The architecture exists to **surface async pain**, not hide it.

> **This system is designed to make bad UI tests fail.**

---

## High-Level Overview

```
Browser (Static UI)
        |
Spring Boot Backend (8084)
        |
        |-- Auth Gate
        |-- Feature Flag API
        |-- Async Propagation Engine
        |-- Chaos Injection
        |-- In-Memory State Store
        |-- Health / Observability
```

Key property:
- **Single-origin** (UI served by backend)
- No CORS
- No proxies
- No mocks in UI tests

Selenium interacts only via **HTTP**, treating the system as a black box.

---

## Why Single-Origin Matters

The UI is served from:

```
backend/src/main/resources/static/
```

This ensures:
- identical behavior in local, CI, and Docker
- no preflight requests
- no CORS configuration
- no environment-specific hacks

**If a test passes locally, it passes in CI.**

## Backend Architecture

### Backend Responsibilities

#### API Surface (Minimal)
| Endpoint	        | Purpose                 |
|------------------|-------------------------|
| `/api/auth/login`	 | Auth gate (status-only) |
| `/api/features`	| Polling snapshot        |        
| `/api/features/{flag}`	| Async toggle request    |
| `/api/test/reset`	| Deterministic baseline |  
| `/actuator/health`	| Readiness + chaos visibility |

The UI never bypasses HTTP.
Selenium never reaches into services.

### Entry Point
- `UiBackendApplication`
- Spring Boot application root

### API Layer (`api/*`)

#### Auth
- `/api/auth/login`
- Status-only auth (200 / 401)
- No sessions
- No tokens
- Secrets injected via **environment variables** only

Purpose:
> Gate Access for Selenium without building real auth infra 


#### Feature API
- `/api/features` (GET)
- `/api/features/{flag}` (POST)
- `/api/test/reset` (POST, test-only)

Purpose:
> Provide a realistic async configuration surface.

### Domain Layer (`domain/*`)

Core types:
- `FeatureFlag` (enum)
- `FeatureState` (record)
- `Environment`

Purpose:
> Represent real-world config domains without business bloat.

### State Store (`state/*`)

- `FeatureStore`
- `InMemoryFeatureStore`

Properties:
- deterministic
- resettable
- no external dependencies

Purpose:
> Simulate distributed state without DB/Kafka complexity.

### Async Engine (`async/*`)

Components:
- `ExecutorRegistry`
- `PropagationTask`
- `PropagationProcessor`

Behavior:
- state changes happen off-thread
- UI must poll
- no immediate consistency

This forces tests to:
- wait for convergence
- avoid sleeps
- reason about eventual state

Purpose:
> Force Selenium tests to reason about time and convergence.

### Chaos Injection (`chaos/*`)

Configurable via env vars:
```
FAILURE_RATE   (0.0 – 1.0)
MAX_DELAY_MS   (milliseconds)
```

Capabilities:
- random propagation delay
- random propagation failure

Purpose:
> Flakiness is intentional and observable — never accidental.

### Health & Observability (`health/*`)

Endpoints:
- `/actuator/health`

Exposes:
- async executor health
- feature store readiness
- chaos configuration state

Purpose:
> Tests must know *what kind of system they are testing*.

### Logging (`logging/*`)

- request logging filter
- secret masking utilities

Purpose:
> CI-safe logs without credential leakage.

## UI Architecture

### Philosophy

The UI is **intentionally boring**.

It exists to:
- expose async delay
- expose polling behavior
- surface eventual consistency
- make incorrect waits obvious

It does **not**:
- optimistic updates
- retries
- animations
- hide latency

---

### Structure

```
static/
|-- index.html
|-- css/
|   |-- styles.css
|
|--js/
    |-- app.js
    |-- auth.js
    |-- features.js
    |-- api.js
```

---

### JavaScript Responsibilities

#### `api.js`
- All HTTP calls
- Timeouts
- Error propagation
- No DOM access

#### `auth.js`
- Login flow
- Visible loading state
- Explicit failure handling
- No persistence

#### `features.js`
- Polling loop
- Full re-render on each poll
- Async visibility
- No backend assumptions

#### `app.js`
- Application lifecycle orchestration
- Login → Dashboard transition
- Starts and stops polling

## Feature Flags: Where They Come From

Feature flags are defined **only** in the backend:

```java
public enum FeatureFlag {
    SLOW_COOKED_MUTTON,
    LATE_DELIVERY,
    MEET_AT_CROSSROADS
}
```

On startup and reset:
- the in-memory store seeds all enum values
- UI discovers flags dynamically via polling

The UI never hardcodes feature names.

---

## Secrets & Environment Variables

Required:
```
SECURITY_USERNAME
SECURITY_PASSWORD
FAILURE_RATE
MAX_DELAY_MS
```

Rules:
- secrets never committed
- secrets never logged
- secrets injected at runtime
- backend fails fast if missing

Purpose:
> CI failures should be loud and deterministic.

---

## What This System Intentionally Does NOT Include

- No database
- No message broker
- No OAuth / JWT
- No session management
- No frontend framework
- No BDD / Cucumber
- No visual testing
- No cloud deployment

## Testing Philosophy (Key)

This system is designed so that:

- naive `Thread.sleep()` tests fail
- correct polling + waits succeed
- async behavior is observable
- flakiness is controlled, not accidental

> **If a Selenium test passes here, it respects time.**

## Final Thought

> **This architecture exists to test delay, not clicks.**

Everything else is scaffolding.
