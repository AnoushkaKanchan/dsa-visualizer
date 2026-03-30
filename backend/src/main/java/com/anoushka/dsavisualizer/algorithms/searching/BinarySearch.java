package com.anoushka.dsavisualizer.algorithms.searching;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.anoushka.dsavisualizer.algorithms.Algorithm;
import com.anoushka.dsavisualizer.models.ActionType;
import com.anoushka.dsavisualizer.models.AlgorithmRequest;
import com.anoushka.dsavisualizer.models.AlgorithmResult;
import com.anoushka.dsavisualizer.models.Step;

@Component("binary")
public class BinarySearch implements Algorithm {

    @Override
    public AlgorithmResult execute(AlgorithmRequest request) {
        if (request.getTarget() == null) {
            throw new RuntimeException("Target is required for binary search");
        }

        int comparisonCount = 0;
        List<Step> steps = new ArrayList<>();
        
      
        int[] arr = request.getArray().clone();
        Arrays.sort(arr);
        steps.add(new Step(arr.clone(), -1, -1, ActionType.OVERWRITE));
        
        int target = request.getTarget();
        int low = 0;
        int high = arr.length - 1;

        while (low <= high) {
            
            int mid = low + (high - low) / 2;
            comparisonCount++;

            
            steps.add(new Step(arr.clone(), mid, -1, ActionType.CHECK));

            if (arr[mid] == target) {
                
                steps.add(new Step(arr.clone(), mid, -1, ActionType.FOUND));
                return new AlgorithmResult(steps, comparisonCount, 0, arr, mid);
            }

            if (arr[mid] < target) {
                steps.add(new Step(arr.clone(), mid, -1, ActionType.RIGHT));
                low = mid + 1;
            } else {
                high = mid - 1;
                steps.add(new Step(arr.clone(), mid, -1, ActionType.LEFT));
            }
        }

        steps.add(new Step(arr.clone(), -1, -1, ActionType.NOT_FOUND));
        return new AlgorithmResult(steps, comparisonCount, 0, arr, null);
    }
}