package com.anoushka.dsavisualizer.algorithms.searching;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.anoushka.dsavisualizer.algorithms.Algorithm;
import com.anoushka.dsavisualizer.models.ActionType;
import com.anoushka.dsavisualizer.models.AlgorithmRequest;
import com.anoushka.dsavisualizer.models.AlgorithmResult;
import com.anoushka.dsavisualizer.models.Step;

@Component("linear")
public class LinearSearch implements Algorithm {

    @Override
    public AlgorithmResult execute(AlgorithmRequest request) {

        int comparisonCount=0;
        List<Step> steps=new ArrayList<>();
        int [] arr=request.getArray().clone();
        int target=request.getTarget();

        for(int i=0;i<arr.length;i++){

            comparisonCount++;
            steps.add(new Step(arr,i,-1,ActionType.CHECK));
            if(arr[i]==target){
                steps.add(new Step(arr, i, -1, ActionType.FOUND));
                return new AlgorithmResult(steps, comparisonCount, 0, arr, i);
            }
        }
        steps.add(new Step(arr, -1, -1, ActionType.NOT_FOUND));
        return new AlgorithmResult(steps, comparisonCount, 0, arr, null);
        
    }
    
}
