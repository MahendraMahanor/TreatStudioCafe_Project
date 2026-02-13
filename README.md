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

1. Open `frontend/index.html` in a browser (double-click or use a local server).
2. To use a different API URL, set the `data-api-base` attribute on the `<html>` tag, e.g.  
   `<html data-api-base="http://localhost:8080">`  
   Default is `http://localhost:8080`.

The frontend shows the menu, supports category filter and retry if the API is unavailable.

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
"# TreatStudioCafe_Project" 
