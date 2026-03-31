package com.anoushka.dsavisualizer.algorithms.sorting;
import java.util.ArrayList;
import java.util.List;

import com.anoushka.dsavisualizer.algorithms.Algorithm;
import com.anoushka.dsavisualizer.models.ActionType;
import com.anoushka.dsavisualizer.models.AlgorithmRequest;
import com.anoushka.dsavisualizer.models.AlgorithmResult;
import com.anoushka.dsavisualizer.models.Step;

public class MergeSort implements Algorithm {

    private int comparisonCount;     
    private int swapCount;           
    private List<Step> steps; 
    
    @Override
    public AlgorithmResult execute(AlgorithmRequest request) {
        
        comparisonCount = 0;     
        swapCount = 0;           
        steps = new ArrayList<>(); 
        int[] arr = request.getArray().clone();

        int start = 0;
        int end = arr.length - 1;
        
        mergeSort(arr, start, end);
    
        return new AlgorithmResult(steps,comparisonCount,swapCount,arr,null);  
    }

    // HELPER 1: Get middle index
    private int getMiddleIndex(int start, int end) {
        return start+(end-start)/2;        // Why? Avoids integer overflow

    }

    // MERGE FUNCTION - THE CORE LOGIC
    private void merge(int[] arr, int start, int middle, int end) {

        int[] temp=new int[(end - start + 1)];
        int leftPointer = start;            //(points to left subarray)
        int rightPointer = middle + 1;      // (points to right subarray)
        int tempIndex = 0;                // (for temp array)

        while (leftPointer <= middle && rightPointer <= end){

            comparisonCount++;
            steps.add(new Step(arr.clone(), leftPointer, rightPointer, ActionType.COMPARE));

            if (arr[leftPointer] <= arr[rightPointer]) {
                temp[tempIndex] = arr[leftPointer];
                leftPointer++;
                tempIndex++;

            }
             else{
                temp[tempIndex] = arr[rightPointer];
                rightPointer++;
                tempIndex++;

            }
        }
         // Step 2: Copy remaining LEFT
        while (leftPointer <= middle){
            temp[tempIndex] = arr[leftPointer];
            leftPointer++;
            tempIndex++;
        }  
        // Step 3: Copy remaining RIGHT
        while (rightPointer <= end){
            temp[tempIndex] = arr[rightPointer];
            rightPointer++;
            tempIndex++;
        }
        // Step 4: Copy sorted result back
        for (int i = 0; i < temp.length; i++){
            arr[start + i] = temp[i];
            steps.add(new Step(arr.clone(), start + i, -1, ActionType.OVERWRITE));
        }

    }

   
    private void mergeSort(int[] arr, int start, int end) {

    // BASE CASE
    if (start >= end){
        return;
    } 
    
    int middle = getMiddleIndex(start, end);
    
    //Recursively sort LEFT half
    mergeSort(arr, start, middle);
    
    // Recursively sort RIGHT half
    mergeSort(arr, middle + 1, end);
    
    //Merge the sorted halves
     merge(arr, start, middle, end);
    }
}