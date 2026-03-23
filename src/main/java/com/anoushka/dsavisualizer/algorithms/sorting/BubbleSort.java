package com.anoushka.dsavisualizer.algorithms.sorting;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.anoushka.dsavisualizer.algorithms.Algorithm;
import com.anoushka.dsavisualizer.models.ActionType;
import com.anoushka.dsavisualizer.models.AlgorithmResult;
import com.anoushka.dsavisualizer.models.Step;

@Component("bubble")
public class BubbleSort implements Algorithm {
    public AlgorithmResult execute(int[] input, Integer target){
        int comparisonCount=0;
        int swapCount=0;
        int []arr=input.clone();
        List<Step> steps = new ArrayList<>();

        for (int i = 0; i < arr.length - 1; i++) {
          for (int j = 0; j < arr.length - i - 1; j++) {
               comparisonCount++;
               steps.add(new Step(arr,j,j+1,ActionType.COMPARE));
               if (arr[j] > arr[j + 1]) {
                 swapCount++;
                 int temp = arr[j];
                 arr[j] = arr[j + 1];
                 arr[j + 1] = temp;
                 steps.add(new Step(arr,j,j+1,ActionType.SWAP));
                }
            }
        }

       return new AlgorithmResult(steps, comparisonCount, swapCount, arr, null);
    }
}
