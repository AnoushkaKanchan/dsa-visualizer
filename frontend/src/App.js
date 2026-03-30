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

const algoLabels = {
  bubble: "Bubble Sort",
  selection: "Selection Sort",
  quick: "Quick Sort",
  merge: "Merge Sort",
  linear: "Linear Search",
  binary: "Binary Search",
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

  // ── STATUS ──
  const statusLabel = isRunning
    ? isPaused ? "paused" : "running"
    : currentStepIndex === steps.length - 1 && steps.length > 0
    ? "done"
    : "idle";

  // ── RUN ALGORITHM ──
  const start = async () => {
    setIsRunning(true);
    pauseRef.current = false;

    try {
      const response = await fetch("https://dsa-visualizer-production.up.railway.app/api/run", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
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
          await sleep(100);
        }
        setCurrentStepIndex(i);
        await sleep(speed);
      }
    } catch (err) {
      console.error("Error:", err);
    }

    setIsRunning(false);
  };

  const pause = () => { pauseRef.current = true; setIsPaused(true); };
  const resume = () => { pauseRef.current = false; setIsPaused(false); };
  const stepForward = () => {
    if (currentStepIndex < steps.length - 1) {
      setCurrentStepIndex((prev) => prev + 1);
    }
  };
  const reset = () => {
    setSteps([]); setCurrentStepIndex(-1);
    setIsRunning(false); setIsPaused(false);
    pauseRef.current = false;
  };

  // ── COLOR LOGIC ──
  const getColor = (index) => {
    if (!currentStep) return "var(--bar-default)";
    if (index === currentStep.index1 || index === currentStep.index2) {
      switch (currentStep.actionType) {
        case "COMPARE":  return "var(--yellow)";
        case "SWAP":     return "var(--red)";
        case "OVERWRITE":return "var(--blue)";
        case "FOUND":    return "var(--green)";
        case "CHECK":    return "var(--orange)";
        default:         return "purple";
      }
    }
    return "var(--bar-default)";
  };

  const getBarClass = (index) => {
    if (!currentStep) return "bar";
    if (index === currentStep.index1 || index === currentStep.index2) {
      return `bar ${currentStep.actionType}`;
    }
    return "bar";
  };

  // ── HUMAN READABLE EXPLANATION ──
  const getStepExplanation = () => {
    if (!currentStep) return null;
    const a = currentStep.array;
    const i1 = currentStep.index1;
    const i2 = currentStep.index2;
    const v1 = a?.[i1];
    const v2 = a?.[i2];

    switch (currentStep.actionType) {
      case "COMPARE":
        return {
          emoji: "👀",
          title: "Comparing",
          detail: v2 !== undefined && i1 !== i2
            ? `Comparing ${v1} (pos ${i1}) and ${v2} (pos ${i2}) — ${v1 > v2 ? `${v1} > ${v2}, a swap is needed` : v1 < v2 ? `${v1} < ${v2}, already in order` : `they are equal, no swap needed`}`
            : `Checking element ${v1} at position ${i1}`,
        };
      case "SWAP":
        return {
          emoji: "🔄",
          title: "Swapping",
          detail: `Swapping ${v1} and ${v2} — placing the larger value to the right`,
        };
      case "OVERWRITE":
        return {
          emoji: "✏️",
          title: "Placing",
          detail: `Writing ${v1} into position ${i1} — merging sorted halves`,
        };
      case "FOUND":
        return {
          emoji: "✅",
          title: "Found!",
          detail: `Target found at position ${i1} — the value ${v1} matches what we were looking for`,
        };
      case "CHECK":
        return {
          emoji: "🔍",
          title: "Checking",
          detail: i2 !== undefined && i1 !== i2
            ? `Checking middle element ${v1} at position ${i1} against target — ${v1 > Number(target) ? "target is smaller, search left half" : v1 < Number(target) ? "target is larger, search right half" : "match!"}`
            : `Checking element ${v1} at position ${i1}`,
        };
      default:
        return { emoji: "⚙️", title: currentStep.actionType, detail: "" };
    }
  };

  const explanation = getStepExplanation();

  return (
    <div className="App">
      {/* HEADER */}
      <header className="app-header">
        <h1>DSA <span>Visualizer</span></h1>
        <div className="status-badge">
          <span className={`status-dot ${statusLabel === "running" ? "running" : statusLabel === "paused" ? "paused" : ""}`} />
          {statusLabel}
        </div>
      </header>

      {/* HOW TO USE */}
      <div className="how-to-card">
        <div className="how-to-title">✦ How to use</div>
        <div className="how-to-steps">
          <div className="how-step">
            <div className="how-step-num">1</div>
            <div className="how-step-text">
              <strong>Pick an algorithm</strong>
              <span>Choose a sorting or searching algorithm from the dropdown</span>
            </div>
          </div>
          <div className="how-step-divider" />
          <div className="how-step">
            <div className="how-step-num">2</div>
            <div className="how-step-text">
              <strong>Enter your array</strong>
              <span>Type comma-separated numbers e.g. <code>5, 3, 8, 1, 9</code></span>
            </div>
          </div>
          <div className="how-step-divider" />
          <div className="how-step">
            <div className="how-step-num">3</div>
            <div className="how-step-text">
              <strong>Set target</strong>
              <span>For Linear / Binary Search only — enter the number to find</span>
            </div>
          </div>
          <div className="how-step-divider" />
          <div className="how-step">
            <div className="how-step-num">4</div>
            <div className="how-step-text">
              <strong>Press Run ▶</strong>
              <span>Watch the bars animate — pause, step through, or reset anytime</span>
            </div>
          </div>
        </div>
      </div>

      {/* MAIN CARD */}
      <div className="main-card">

        {/* TOP BAR */}
        <div className="card-topbar">
          <div className="topbar-dots">
            <span /><span /><span />
          </div>
          <span className="topbar-label">visualizer.exe</span>
          <span className="topbar-algo-tag">{algoLabels[algorithm]}</span>
        </div>

        {/* VISUALIZER */}
        <div className="visualizer-wrapper">

          {/* ── BAR CHART with pointers ── */}
          <div className="bars-section">
            {/* pointer row — arrows above active bars */}
            <div className="pointer-row">
              {displayArray.map((_, index) => {
                const isI1 = currentStep && index === currentStep.index1;
                const isI2 = currentStep && index === currentStep.index2 && currentStep.index2 !== currentStep.index1;
                return (
                  <div key={index} className="pointer-cell" style={{ width: "30px" }}>
                    {isI1 && (
                      <div className={`pointer pointer-i1 ${currentStep.actionType}`}>
                        <span className="pointer-label">i</span>
                        <span className="pointer-arrow">▼</span>
                      </div>
                    )}
                    {isI2 && (
                      <div className={`pointer pointer-i2 ${currentStep.actionType}`}>
                        <span className="pointer-label">j</span>
                        <span className="pointer-arrow">▼</span>
                      </div>
                    )}
                  </div>
                );
              })}
            </div>

            {/* bars */}
            <div className="visualizer-container">
              {displayArray.map((value, index) => (
                <div
                  key={index}
                  className={getBarClass(index)}
                  style={{
                    height: `${value * 3}px`,
                    width: "30px",
                    backgroundColor: getColor(index),
                  }}
                />
              ))}
            </div>
            <div className="axis-line" />
          </div>

          {/* ── ARRAY BOXES ── */}
          <div className="array-boxes-section">
            <div className="array-boxes-label">array</div>
            <div className="array-boxes">
              {displayArray.map((value, index) => {
                const isI1 = currentStep && index === currentStep.index1;
                const isI2 = currentStep && index === currentStep.index2 && currentStep.index2 !== currentStep.index1;
                const isActive = isI1 || isI2;
                return (
                  <div key={index} className="array-box-wrap">
                    <div
                      className={`array-box ${isActive ? "active " + currentStep.actionType : ""}`}
                      style={{ borderColor: isActive ? getColor(index) : undefined, color: isActive ? getColor(index) : undefined }}
                    >
                      {value}
                    </div>
                    <div className="array-box-index">{index}</div>
                    {isI1 && <div className="box-pointer box-pointer-i" style={{ color: getColor(index) }}>i</div>}
                    {isI2 && <div className="box-pointer box-pointer-j" style={{ color: getColor(index) }}>j</div>}
                  </div>
                );
              })}
            </div>
          </div>

        </div>

        {/* EXPLANATION PANEL */}
        <div className="explanation-panel">
          {explanation ? (
            <div className="explanation-active">
              <div className="explanation-left">
                <span className="explanation-emoji">{explanation.emoji}</span>
                <div>
                  <div className="explanation-title">
                    <span className={`step-action ${currentStep.actionType}`}>
                      {explanation.title}
                    </span>
                    <span className="step-counter">
                      step {currentStepIndex + 1} / {steps.length}
                    </span>
                  </div>
                  <div className="explanation-detail">{explanation.detail}</div>
                </div>
              </div>
              {/* mini progress bar */}
              <div className="explanation-progress">
                <div
                  className="explanation-progress-fill"
                  style={{ width: `${((currentStepIndex + 1) / steps.length) * 100}%` }}
                />
              </div>
            </div>
          ) : (
            <div className="explanation-idle">
              <span>⬆️</span>
              <span>Pick an algorithm, enter your array, then hit <strong>Run ▶</strong> — each step will be explained here in plain English</span>
            </div>
          )}
        </div>

        {/* CONTROLS */}
        <div className="controls">

          {/* Row 1: Algorithm + Array + Target */}
          <div className="controls-row">
            <span className="controls-label">Algo</span>
            <select onChange={(e) => setAlgorithm(e.target.value)} value={algorithm}>
              <option value="bubble">Bubble Sort</option>
              <option value="selection">Selection Sort</option>
              <option value="quick">Quick Sort</option>
              <option value="merge">Merge Sort</option>
              <option value="linear">Linear Search</option>
              <option value="binary">Binary Search</option>
            </select>

            <span className="controls-label">Array</span>
            <input
              type="text"
              placeholder="e.g. 5, 3, 1, 8"
              onChange={(e) => {
                const parts = e.target.value.split(",");
                const parsed = parts
                  .map((v) => Number(v.trim()))
                  .filter((n) => !isNaN(n));
                if (parsed.length > 0) setArray(parsed);
              }}
            />

            {algorithmConfig[algorithm]?.requiresTarget && (
              <>
                <span className="controls-label">Target</span>
                <input
                  type="number"
                  placeholder="Search target"
                  value={target}
                  onChange={(e) => setTarget(e.target.value)}
                  style={{ minWidth: "110px" }}
                />
              </>
            )}
          </div>

          {/* Row 2: Speed */}
          <div className="controls-row">
            <span className="controls-label">Speed</span>
            <div className="speed-row">
              <input
                type="range"
                min="100"
                max="1000"
                value={speed}
                onChange={(e) => setSpeed(Number(e.target.value))}
              />
              <span className="speed-value">{speed}ms</span>
            </div>
          </div>

          {/* Row 3: Action Buttons */}
          <div className="controls-row">
            <span className="controls-label">Actions</span>
            <div className="btn-group">
              <button
                className="btn-run"
                onClick={start}
                disabled={isRunning || (algorithmConfig[algorithm]?.requiresTarget && target === "")}
              >
                ▶ Run
              </button>
              <button className="btn-pause" onClick={pause} disabled={!isRunning || isPaused}>
                ⏸ Pause
              </button>
              <button className="btn-resume" onClick={resume} disabled={!isPaused}>
                ▶ Resume
              </button>
              <button className="btn-step" onClick={stepForward} disabled={isRunning}>
                → Step
              </button>
              <button className="btn-reset" onClick={reset}>
                ↺ Reset
              </button>
            </div>
          </div>
        </div>

        {/* LEGEND */}
        <div className="legend">
          {[
            { color: "var(--yellow)", label: "Compare" },
            { color: "var(--red)",    label: "Swap" },
            { color: "var(--blue)",   label: "Overwrite" },
            { color: "var(--green)",  label: "Found" },
            { color: "var(--orange)", label: "Check" },
            { color: "var(--bar-default)", label: "Default" },
          ].map(({ color, label }) => (
            <div key={label} className="legend-item">
              <div className="legend-dot" style={{ background: color }} />
              {label}
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}

export default App;