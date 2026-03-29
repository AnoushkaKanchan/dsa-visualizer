package com.anoushka.dsavisualizer.algorithms;
import com.anoushka.dsavisualizer.models.AlgorithmRequest;
import com.anoushka.dsavisualizer.models.AlgorithmResult;

    public interface Algorithm {
        AlgorithmResult execute(AlgorithmRequest request);
    }

