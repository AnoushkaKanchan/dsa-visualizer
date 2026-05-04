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
public interface SortingAlgorithm {
    ExecutionResult execute(int[] input);
}
```

Per-tick execution state includes:
- **Array snapshot** — the full array at each step
- **Index pointers** — which elements are being operated on
- **Action type** — `COMPARE`, `SWAP`, or `FOUND`

### API Contract

**Request**
```json
POST /api/run
{
  "algorithm": "bubbleSort",
  "input": [5, 2, 8, 1, 9]
}
```

**Response**
```json
{
  "steps": [
    { "array": [5, 2, 8, 1, 9], "action": "COMPARE", "indices": [0, 1] },
    { "array": [2, 5, 8, 1, 9], "action": "SWAP",    "indices": [0, 1] }
  ],
  "comparisons": 10,
  "swaps": 4
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

- Node.js 18+
- Java 17+
- Maven

### Frontend (React)

```bash
# Clone the repo
git clone https://github.com/AnoushkaKanchan/dsa-visualizer.git
cd dsa-visualizer/frontend

# Install dependencies
npm install

# Set environment variable
echo "REACT_APP_API_URL=http://localhost:8080" > .env

# Start dev server
npm start
```

### Backend (Spring Boot)

```bash
cd dsa-visualizer/backend

# Run with Maven
./mvnw spring-boot:run
```

The backend will start at `http://localhost:8080`.

### Docker (optional)

```bash
cd backend
docker build -t dsa-visualizer-backend .
docker run -p 8080:8080 dsa-visualizer-backend
```

---

## 🌐 Deployment

| Layer | Platform | Notes |
|-------|----------|-------|
| Frontend | [Vercel](https://vercel.com/) | Auto-deploys from `main` branch |
| Backend | [Render](https://render.com/) | Deployed via Docker |

### Environment Variables

**Frontend (Vercel)**
```
REACT_APP_API_URL=https://your-render-backend-url.onrender.com
```

**Backend (Railway)**
```
PORT=8080
ALLOWED_ORIGINS=https://dsa-visualizer-omega-five.vercel.app
```

### CORS Configuration

The Spring Boot backend is configured to allow requests from the Vercel frontend domain. Update `CorsConfig.java` if your frontend URL changes.

---

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

<details>
<summary><strong>CORS errors in production</strong></summary>

Cross-origin requests were blocked when the React app (Vercel) tried to call the Spring Boot API (Railway). Fixed by explicitly whitelisting the Vercel origin in `@CrossOrigin` and the global CORS config in Spring Boot.

</details>

<details>
<summary><strong>Environment variable injection on Render</strong></summary>

Environment variables set locally in `.env` weren't being picked up in the Render Docker build context. Resolved by injecting them directly via Render's dashboard environment settings instead of relying on the Dockerfile `ENV` instruction.

</details>

<details>
<summary><strong>Docker build failures</strong></summary>

Build failures during Railway deployment were caused by the Maven wrapper (`mvnw`) not having execute permissions in the Docker image. Fixed with `RUN chmod +x mvnw` in the Dockerfile.

</details>

<details>
<summary><strong>Frontend not reflecting backend changes</strong></summary>

API integration issues were debugged by testing backend endpoints independently with Postman/curl before connecting to the frontend — isolating whether bugs were in the API response shape or the frontend parsing logic.

</details>

---

## 👩‍💻 Author

**Anoushka Kanchan** — [GitHub](https://github.com/AnoushkaKanchan)

---

*Built to make algorithms click — not just in theory, but visually.*
