# Treat Studio Cafe

A small cafe website with a Spring Boot backend and a static frontend. Menu data is stored in a file-based H2 database and can be filtered by category.

## Prerequisites

- **Java 17+**
- **Maven 3.6+**

## Run the backend

From the project root:

```bash
cd backend
mvn spring-boot:run
```

The API runs at **http://localhost:8080**.

- **Menu API:** `GET /api/menu` (optional: `?category=Coffee`)
- **Add item:** `POST /api/menu` with JSON body: `{ "name": "...", "description": "...", "price": 99.00, "category": "Coffee" }`
- **H2 console:** http://localhost:8080/h2-console (JDBC URL: `jdbc:h2:file:./data/treatstudio`, user: `SA`, password blank)

Data is stored in `backend/data/` and persists across restarts.

## Add sample menu items (PowerShell)

With the backend running, you can add sample items:

```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/menu" -Method Post -ContentType "application/json" -Body '{"name":"Cappuccino","description":"Double shot, steamed milk","price":120,"category":"Coffee"}'
Invoke-RestMethod -Uri "http://localhost:8080/api/menu" -Method Post -ContentType "application/json" -Body '{"name":"Croissant","description":"Buttery, fresh","price":80,"category":"Snacks"}'
```

## Open the frontend

**Option A — Double-click:** Open `frontend/index.html` in your browser.

**Option B — Local live server (recommended):** Run one of these from the project folder so the site is served at a URL (e.g. http://localhost:3000).

**PowerShell (from project root):**

```powershell
# Using Python (if installed)
cd frontend; python -m http.server 3000

# Or using Node (if installed)
cd frontend; npx -y serve -p 3000
```

**Command Prompt (cmd):**

```cmd
cd frontend
python -m http.server 3000
```

```cmd
cd frontend
npx -y serve -p 3000
```

Then open **http://localhost:3000** in your browser. Keep the backend running on port 8080 so the menu loads.

**API URL:** The frontend uses `http://localhost:8080` by default (set via `data-api-base` on the `<html>` tag if you need to change it).

The frontend shows the menu, supports category filter and retry if the API is unavailable.

**Gallery (Treat Studio photos):** To show real cafe photos in the "Ambience & moments" section, add images to `frontend/images/` (and `docs/images/` for the live site) as `gallery1.jpg`, `gallery2.jpg`, `gallery3.jpg`, `gallery4.jpg`. You can save photos from the **Treat Studio Cafe Pune** listing on Google Maps (Photos tab) or use your own. See `frontend/images/README.txt` for steps.

## Live site (GitHub Pages)

The frontend can be hosted **live** on GitHub so anyone can open it in a browser.

1. Push the repo to GitHub (it already has a `docs/` folder with the website).
2. On GitHub, open your repo **TreatStudioCafe_Project**.
3. Go to **Settings** → **Pages** (left sidebar).
4. Under **Build and deployment**, set **Source** to **Deploy from a branch**.
5. Set **Branch** to `master` (or `main`) and **Folder** to **/docs**.
6. Click **Save**. After a minute or two, GitHub will show the live URL.

**Live URL:** `https://<your-username>.github.io/TreatStudioCafe_Project/`  
Example: `https://mahendramahanor.github.io/TreatStudioCafe_Project/`

The live site shows the full cafe page (hero, about, contact, location). The **menu** section will load items only when the backend is running or deployed elsewhere; otherwise it shows a friendly message and a Retry button.

## Project structure

```
TreatStudioCafe_Project/
├── backend/          # Spring Boot API (Java 17, Maven)
│   ├── src/main/java/com/treatstudio/
│   │   ├── controller/   # MenuController, error handling
│   │   ├── model/        # MenuItem (JPA entity)
│   │   └── repository/   # MenuRepository
│   └── src/main/resources/
│       └── application.properties
├── docs/             # Same as frontend — used for GitHub Pages live site
├── frontend/         # Static site (HTML, CSS, JS)
│   ├── index.html
│   ├── style.css
│   └── script.js
└── README.md
```

## API summary

| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/menu` | List all menu items. Optional: `?category=Name` |
| GET | `/api/menu/{id}` | Get one item by id |
| POST | `/api/menu` | Create item (body: name, description, price, category) |
| PUT | `/api/menu/{id}` | Update item |
| DELETE | `/api/menu/{id}` | Delete item |

Validation: `name` required, `price` ≥ 0. Invalid requests return 400 with an `error` (and optional `details`) JSON body. 
