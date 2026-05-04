# 🧩 DSA Visualizer

[![Live Demo](https://img.shields.io/badge/Live%20Demo-Vercel-black?style=flat-square&logo=vercel)](https://dsa-visualizer-omega-five.vercel.app/)
[![Backend](https://img.shields.io/badge/Backend-Render-46E3B7?style=flat-square&logo=render)](https://render.com/)
[![Frontend](https://img.shields.io/badge/Frontend-React-61DAFB?style=flat-square&logo=react)](https://react.dev/)
[![Backend](https://img.shields.io/badge/Backend-Spring%20Boot-6DB33F?style=flat-square&logo=springboot)](https://spring.io/projects/spring-boot)

> A full-stack algorithm visualization platform — watch sorting and search algorithms execute step-by-step with real-time animation, playback controls, and live performance metrics.

---

## ✨ Features

- **Step-by-step execution** — animated visualization of each algorithm tick (compare, swap, found)
- **Playback controls** — run, pause, resume, step-forward, and adjustable speed
- **Performance metrics** — comparison count and swap count tracked per algorithm run
- **6 algorithms** — Bubble Sort, Merge Sort, Quick Sort, Selection Sort, Binary Search, Linear Search
- **Input validation** — handled on both client and server with meaningful error feedback
- **Fully deployed** — frontend on Vercel, backend on Railway

---

## 🏗️ Architecture

```
┌─────────────────────┐        POST /api/run         ┌──────────────────────────┐
│   React Frontend    │ ──────────────────────────→  │  Spring Boot Backend     │
│                     │                               │                          │
│  • Animation engine │ ←──────────────────────────  │  • Algorithm dispatcher  │
│  • Playback controls│     execution steps + metrics │  • execute() interface   │
│  • Speed slider     │                               │  • Performance tracking  │
└─────────────────────┘                               └──────────────────────────┘
       Vercel                                                  Railway
```

### Backend Design

Each algorithm implements a shared `execute()` contract via an interface — enabling plug-and-play extensibility. A dynamic dispatcher at `POST /api/run` routes requests to the correct implementation at runtime.

```java
    public interface Algorithm {
        AlgorithmResult execute(AlgorithmRequest request);
    }
```

Per-tick execution state includes:
- **Array snapshot** — the full array at each step
- **Index pointers** — which elements are being operated on
- **Action type** — `COMPARE`, `SWAP`, `FOUND`, `OVERWRITE`, `CHECK`, `NOT_FOUND`, `PIVOT`, `RIGHT`, or `LEFT`;

### API Contract

**Request**
```json
POST /api/run
{
  "type": "sorting",
  "algorithm": "quick",
  "array": [5,3,8,1]
}
```

**Response**
```json
{
  "steps": [
    { "array": [5, 3, 8, 1], "index1": 3, "index2": 3, "actionType": "PIVOT" },
    { "array": [5, 3, 8, 1], "index1": 0, "index2": 3, "actionType": "COMPARE" },
    { "array": [1, 3, 8, 5], "index1": 0, "index2": 3, "actionType": "SWAP" }
  ],
  "comparisonCount": 5,
  "swapCount": 3,
  "finalArray": [1, 3, 5, 8],
  "foundIndex": null
}
```

---

## 🧮 Algorithms

| Algorithm | Type | Time Complexity | Space Complexity |
|-----------|------|----------------|-----------------|
| Bubble Sort | Sorting | O(n²) | O(1) |
| Selection Sort | Sorting | O(n²) | O(1) |
| Merge Sort | Sorting | O(n log n) | O(n) |
| Quick Sort | Sorting | O(n log n) avg | O(log n) |
| Linear Search | Searching | O(n) | O(1) |
| Binary Search | Searching | O(log n) | O(1) |

---

## 🚀 Getting Started

### Prerequisites

- Java 17+
- Maven

### Frontend (React)

```bash
# Clone the repo
git clone https://github.com/AnoushkaKanchan/dsa-visualizer.git
cd dsa-visualizer/frontend

# Install dependencies
npm install

# Run the app
npm start

👉 The frontend runs on: http://localhost:3000
```

### Backend (Spring Boot)

```bash
cd dsa-visualizer/backend

# Run backend
mvn spring-boot:run

👉 Backend runs on: http://localhost:8080
```

##  Environment Configuration

Create a `.env` file inside the `frontend/` directory:

```env
REACT_APP_API_URL=http://localhost:8080
```

👉 This is used for **local development**

For production (already configured in deployment):

```env
REACT_APP_API_URL=https://dsa-visualizer-78t7.onrender.com
```

---

##  Docker (Optional)

To run the backend using Docker:

```bash
cd backend
docker build -t dsa-visualizer-backend .
docker run -p 8080:8080 dsa-visualizer-backend
```

---

## 🌐 Deployment

| Layer    | Platform | Notes                            |
| -------- | -------- | -------------------------------- |
| Frontend | Vercel   | Auto-deployed from `main` branch |
| Backend  | Render   | Deployed using Docker            |

⚠️ Backend may take 30–60 seconds to respond initially due to free tier cold starts.
---

## 🔗 Environment Variables

### Frontend (Vercel)

```env id="t1m7hl"
REACT_APP_API_URL=https://dsa-visualizer-78t7.onrender.com
```

---

## 🔐 CORS Configuration

The backend allows requests from the deployed frontend domain.

If you change the frontend URL, update the CORS configuration in:

```text id="m4l3qv"
CorsConfig.java
```

## 🧱 Tech Stack

**Frontend**
- React — UI and animation engine
- CSS animations — step transition effects
- Environment-based API URL config

**Backend**
- Java 17 + Spring Boot
- Interface-based algorithm architecture
- REST API with structured execution state
- Input validation with error responses

**Infrastructure**
- Vercel — frontend hosting + CI/CD
- Render — backend hosting via Docker
- GitHub — source control

---

## 🐛 Known Challenges & Solutions

### ▾ CORS errors in production

Cross-origin requests were blocked when the frontend (Vercel) attempted to call the backend API.
**Solution:** Configured CORS in Spring Boot using `@CrossOrigin` to allow requests from the frontend domain.

---

### ▾ Deployment issues on Render

The backend failed to deploy due to incorrect Docker configuration and root directory settings.
**Solution:** Fixed the Docker setup and set the correct root directory (`backend`) in Render.

---

### ▾ Maven wrapper issues on Windows

The Maven wrapper (`mvnw`) caused permission errors during local and Docker builds.
**Solution:** Switched to system-installed Maven (`mvn`) for consistent builds.

---

### ▾ Frontend–backend integration issues

The frontend was not reflecting backend changes due to incorrect API endpoints.
**Solution:** Verified backend APIs using Postman and updated frontend API URLs accordingly.

---

## 👩‍💻 Author

**Anoushka Kanchan** — [GitHub](https://github.com/AnoushkaKanchan)

---

*Built to make algorithms click — not just in theory, but visually.*
