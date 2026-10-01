# Geodata Hub

A public site for hosting cleaned, geo-matched datasets and interactive maps — not a portfolio, a usable data resource.

## Structure

- `backend/` — Spring Boot REST API (Java, Spring Data JPA). Serves dataset metadata, map file paths, and download links. Runs on an in-memory H2 database by default (zero setup) — switch to PostgreSQL in `application.properties` when ready.
- `frontend/` — React (Vite). An index page listing datasets and a detail page per dataset with an embedded map (your Folium/GeoPandas export) and a download button.

## Running locally

**Backend:**
```
cd backend
./mvnw spring-boot:run
```
API runs at `http://localhost:8080/api/datasets`. H2 console at `http://localhost:8080/h2-console`.

**Frontend:**
```
cd frontend
npm install
npm run dev
```
Runs at `http://localhost:5173`.

## Adding a dataset

Right now, add datasets via a POST request (Postman, curl) to `http://localhost:8080/api/datasets`:

```json
{
  "slug": "hyderabad-water-stress",
  "title": "Hyderabad Water Stress Index",
  "description": "Composite water-stress index across 186 Hyderabad sections.",
  "category": "Urban Infrastructure",
  "mapUrl": "/maps/water-stress.html",
  "downloadUrl": "/data/water-stress.geojson",
  "sourceNote": "Civic tanker, billing, and groundwater datasets",
  "license": "CC-BY-4.0",
  "dateAdded": "2026-09-27"
}
```

For map files: export your Folium map as standalone HTML (`m.save("water-stress.html")`) and serve it as a static file (place in `frontend/public/maps/` for now, or a CDN/object storage later).

## Next steps

- Add your two existing datasets (water stress, empowerment archetypes) via the API
- Serve the Folium HTML exports as static files
- Deploy backend (Render/Railway free tier) + frontend (Vercel/GitHub Pages)
- Optional: a simple admin form instead of raw POST requests, once you have more than a couple datasets
