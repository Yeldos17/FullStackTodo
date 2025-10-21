
# Lab 2 - Frontend (React) — COMPLETE

This delivery upgrades the Lab 2 frontend with:
- Detailed README + component diagram and grader checklist
- Theming (light/dark) with Context and toggle control
- Apple-like clean UI (rounded cards, airy spacing, subtle shadows)
- Unit tests (Jest + React Testing Library) for `UserLogin` and `TaskList`
- Improved API integration notes and test instructions

## Project structure (important files)
- `src/api.js` — Axios instance with JWT header injection
- `src/hooks/useAuth.js` — authentication context (login/logout, token storage)
- `src/hooks/useTheme.js` — theme context (light/dark)
- `src/components/UserLogin.js` — login form (unit tested)
- `src/components/Dashboard.js` — task listing, creation form
- `src/components/TaskList.js` — drag-and-drop list (unit tested)
- `src/components/TaskCard.js` — stylized task card using theme variables
- `src/components/ThemeToggle.js` — small toggle control placed in Profile
- `src/components/Profile.js` — shows theme toggle and logout
- `README.md` — this file (expanded)

## Component diagram (ASCII)
```
[App] 
 ├─ [AuthProvider] 
 │   └─ [Routes] -> /login => [UserLogin]
 │                 -> /dashboard => [Dashboard] -> [TaskList] -> [TaskCard]
 │                 -> /profile => [Profile] -> [ThemeToggle]
 └─ [ThemeProvider]
```

## Grader checklist (follow for 20+ points)
- [ ] Repo created public
- [ ] Branch `lab-2-frontend` contains 5+ commits
- [ ] README updated with instructions & diagram
- [ ] Demo video recorded (5-10 min) showing login, create task, drag-and-drop, theme toggle
- [ ] Pull Request with description created & merged to `main`

## Theming & Design notes
- Theme is implemented via `src/hooks/useTheme.js` and `ThemeProvider` wrapping App
- UI uses `styled-components` with theme-aware styles (colors, background, card shadows)
- Priority colors: High — strong red, Medium — amber, Low — green
- Layout focuses on airy spacing, rounded corners (12-16px), and subtle shadows for depth

## Tests
Run `npm install` then:
```
npm test
```
Two example tests are included:
- `src/__tests__/UserLogin.test.js`
- `src/__tests__/TaskList.test.js`

Adjust API endpoints in `src/api.js` if backend runs elsewhere.

