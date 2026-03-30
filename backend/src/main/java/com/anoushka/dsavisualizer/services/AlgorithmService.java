package com.anoushka.dsavisualizer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.anoushka.dsavisualizer.algorithms.Algorithm;
import com.anoushka.dsavisualizer.models.AlgorithmRequest;
import com.anoushka.dsavisualizer.models.AlgorithmResult;

@Service
public class AlgorithmService {
    
    @Autowired
    private ApplicationContext context;
        
    public AlgorithmResult run(AlgorithmRequest request){

        Algorithm algorithm=(Algorithm) context.getBean(request.getAlgorithm());
        System.out.println("Algorithm selected: " + algorithm.getClass().getSimpleName());

        AlgorithmResult result=algorithm.execute(request);

        return result;
    }
}
