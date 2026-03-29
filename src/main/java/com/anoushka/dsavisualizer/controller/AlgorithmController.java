package com.anoushka.dsavisualizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anoushka.dsavisualizer.models.AlgorithmRequest;
import com.anoushka.dsavisualizer.models.AlgorithmResult;
import com.anoushka.dsavisualizer.services.AlgorithmService;

@RestController
@RequestMapping("/api")
public class AlgorithmController {

    @Autowired
    private AlgorithmService algorithmService;

    @PostMapping("/run")
    public AlgorithmResult run(@RequestBody AlgorithmRequest request){
        
        if (request.getAlgorithm() == null || request.getArray() == null) {
            throw new RuntimeException("Invalid request");
        }
        
        return algorithmService.run(request);
    }
    
}
