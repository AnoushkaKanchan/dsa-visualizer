package com.anoushka.dsavisualizer.algorithms.searching;

import org.springframework.stereotype.Component;

import com.anoushka.dsavisualizer.algorithms.Algorithm;
import com.anoushka.dsavisualizer.models.AlgorithmRequest;
import com.anoushka.dsavisualizer.models.AlgorithmResult;

@Component("binary")
public class BinarySearch implements Algorithm {
    private final int target;

    public BinarySearch(int target) {
        this.target=target;
    }
    @Override
    public AlgorithmResult execute(AlgorithmRequest request) {
        return null;
    }
    
}
