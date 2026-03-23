package com.anoushka.dsavisualizer.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anoushka.dsavisualizer.algorithms.Algorithm;
import com.anoushka.dsavisualizer.models.AlgorithmResult;
import com.anoushka.dsavisualizer.models.DSARequest;

@RestController
@RequestMapping("/api/dsa")
public class DSAController {

    private final Map<String, Algorithm> algorithmMap;

    @Autowired
    public DSAController(Map<String, Algorithm> algorithmMap) {
        this.algorithmMap = algorithmMap;
    }

    @PostMapping
    public AlgorithmResult process(@RequestBody DSARequest request){

        String algoKey = request.getAlgorithm().toLowerCase();

        Algorithm algorithm = algorithmMap.get(algoKey);

        if (algorithm == null) {
            throw new RuntimeException("Invalid algorithm");
        }

        return algorithm.execute(request.getArray(),null);
    }
}