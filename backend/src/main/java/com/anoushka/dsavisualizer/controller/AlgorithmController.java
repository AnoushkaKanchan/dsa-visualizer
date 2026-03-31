package com.anoushka.dsavisualizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anoushka.dsavisualizer.models.AlgorithmRequest;
import com.anoushka.dsavisualizer.services.AlgorithmService;

@CrossOrigin(origins = "https://dsa-visualizer-omega-five.vercel.app/")
@RestController
@RequestMapping("/api")
public class AlgorithmController {

    @Autowired
    private AlgorithmService algorithmService;

    @PostMapping("/run")
public Object run(@RequestBody AlgorithmRequest request) {

    try {
        System.out.println("🔥 Algorithm: " + request.getAlgorithm());
        System.out.println("🔥 Array: " + java.util.Arrays.toString(request.getArray()));

        return algorithmService.run(request);

    } catch (Exception e) {
        e.printStackTrace();

        // 🔥 RETURN ERROR TO FRONTEND
        return "ERROR: " + e.toString();
    }
}
    }
    

