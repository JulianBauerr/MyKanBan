# MyKanban

Kanban board app – Spring Boot backend + React (Vite) frontend.

```
backend/    Spring Boot 4, Java 21, PostgreSQL
frontend/   React + Vite
```

## Backend

1. Copy `backend/src/main/resources/application-local.properties.example`
   to `application-local.properties` and set your DB password
   (or set the `DB_PASSWORD` environment variable).
2. Run:
   ```bash
   cd backend
   ./gradlew bootRun
   ```
   API runs on http://localhost:8080 (see `backend/api_Overview.md`).

## Frontend

1. Copy `frontend/.env.example` to `frontend/.env.local` (adjust the API URL if needed).
2. Run:
   ```bash
   cd frontend
   npm install
   npm run dev
   ```
