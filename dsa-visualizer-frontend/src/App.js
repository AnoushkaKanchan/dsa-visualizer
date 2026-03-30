import React, { useState, useRef } from "react";
import "./App.css";

const algorithmConfig = {
  bubble: { requiresTarget: false },
  selection: { requiresTarget: false },
  quick: { requiresTarget: false },
  merge: { requiresTarget: false },
  linear: { requiresTarget: true },
  binary: { requiresTarget: true } 
};

const sleep = (ms) => new Promise((resolve) => setTimeout(resolve, ms));

function App() {
  const [array, setArray] = useState([10, 40, 20, 50, 30, 90, 70]);
  const [steps, setSteps] = useState([]);
  const [currentStepIndex, setCurrentStepIndex] = useState(-1);
  const [isRunning, setIsRunning] = useState(false);
  const [isPaused, setIsPaused] = useState(false);

  const [algorithm, setAlgorithm] = useState("bubble");
  const [target, setTarget] = useState("");
  const [speed, setSpeed] = useState(100);

  const pauseRef = useRef(false);

  const currentStep = steps[currentStepIndex] || null;
  const displayArray = currentStep ? currentStep.array : array;

  //RUN ALGORITHM
  const start = async () => {
    setIsRunning(true);
    pauseRef.current = false;

    try {
      const response = await fetch("http://localhost:8080/api/run", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          algorithm,
          array,
          target: target === "" ? null : Number(target),
        }),
      });

      const data = await response.json();
      setSteps(data.steps);

      for (let i = 0; i < data.steps.length; i++) {
        while (pauseRef.current) {
          await sleep(100); // wait while paused
        }

        setCurrentStepIndex(i);
        await sleep(speed);
      }
    } catch (err) {
      console.error("Error:", err);
    }

    setIsRunning(false);
  };

  // ⏸ Pause
  const pause = () => {
    pauseRef.current = true;
    setIsPaused(true);
  };

  // ▶ Resume
  const resume = () => {
    pauseRef.current = false;
    setIsPaused(false);
  };

  // ⏭ Step Forward
  const stepForward = () => {
    if (currentStepIndex < steps.length - 1) {
      setCurrentStepIndex((prev) => prev + 1);
    }
  };

  // 🔄 Reset
  const reset = () => {
    setSteps([]);
    setCurrentStepIndex(-1);
    setIsRunning(false);
    setIsPaused(false);
    pauseRef.current = false;
  };

  // 🎨 COLOR LOGIC
  const getColor = (index) => {
    if (!currentStep) return "skyblue";

    if (index === currentStep.index1 || index === currentStep.index2) {
      switch (currentStep.actionType) {
        case "COMPARE":
          return "yellow";
        case "SWAP":
          return "red";
        case "OVERWRITE":
          return "blue";
        case "FOUND":
          return "green";
        case "CHECK":
          return "orange";
        default:
          return "purple";
      }
    }

    return "skyblue";
  };

  return (
    <div className="App">
      <h1>DSA Visualizer</h1>

      {/* CONTROLS */}
      <div className="controls">
        <select onChange={(e) => setAlgorithm(e.target.value)}>
          <option value="bubble">Bubble Sort</option>
          <option value="selection">Selection Sort</option>
          <option value="quick">Quick Sort</option>
          <option value="merge">Merge Sort</option>
          <option value="linear">Linear Search</option>
        </select>

        <input
          type="text"
          placeholder="Enter array (e.g. 5,3,1)"
          onChange={(e) =>
            setArray(e.target.value.split(",").map(Number))
          }
        />

        <input
          type="number"
          placeholder="Target (for search)"
          value={target}
          onChange={(e) => {
            const raw = e.target.value.split(",");
            const trimmed = raw.map((val) => val.trim()).filter((val) => val !== "");
            const parsed = trimmed.map(Number);
            const hasInvalid = parsed.some((n) => isNaN(n));
            if (!hasInvalid && parsed.length > 0) {
              setArray(parsed);
            }
          }}
        />

        <label>Speed:</label>
        <input
          type="range"
          min="10"
          max="500"
          value={speed}
          onChange={(e) => setSpeed(Number(e.target.value))}
        />

        <button onClick={start} disabled={isRunning||(algorithmConfig[algorithm]?.requiresTarget && target === "")}>
          Run
        </button>

        <button onClick={pause} disabled={!isRunning || isPaused}>
          Pause
        </button>

        <button onClick={resume} disabled={!isPaused}>
          Resume
        </button>

        <button onClick={stepForward} disabled={isRunning}>
          Step →
        </button>

        <button onClick={reset}>Reset</button>
      </div>

      {/* VISUALIZER */}
      <div className="visualizer-container">
        {displayArray.map((value, index) => (
          <div
            key={index}
            className="bar"
            style={{
              height: `${value * 3}px`,
              width: "30px",
              margin: "0 2px",
              backgroundColor: getColor(index),
            }}
          />
        ))}
      </div>
    </div>
  );
}

export default App;