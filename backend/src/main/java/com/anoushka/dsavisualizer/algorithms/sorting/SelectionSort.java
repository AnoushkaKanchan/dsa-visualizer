package com.anoushka.dsavisualizer.algorithms.sorting;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.anoushka.dsavisualizer.algorithms.Algorithm;
import com.anoushka.dsavisualizer.models.ActionType;
import com.anoushka.dsavisualizer.models.AlgorithmRequest;
import com.anoushka.dsavisualizer.models.AlgorithmResult;
import com.anoushka.dsavisualizer.models.Step;

@Component("selection")
public class SelectionSort implements Algorithm{
    
    @Override
    public AlgorithmResult execute (AlgorithmRequest request){

        int comparisonCount=0;
        int swapCount=0;
        List<Step> steps=new ArrayList<>();
        int[] arr=request.getArray().clone();


        for (int i = 0; i < arr.length - 1; i++){
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++){
                comparisonCount++;
                steps.add(new Step(arr,j,minIndex,ActionType.COMPARE));
                if (arr[j] < arr[minIndex]){
                 minIndex = j;
                }
            }
            if (minIndex != i) {
                int temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
                swapCount++;
                steps.add(new Step(arr,i,minIndex,ActionType.SWAP));
            }
        }
        return new AlgorithmResult(steps,comparisonCount,swapCount,arr,null);
    }
    
}
