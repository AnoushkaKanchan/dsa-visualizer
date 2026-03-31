package com.anoushka.dsavisualizer.algorithms.sorting;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.anoushka.dsavisualizer.algorithms.Algorithm;
import com.anoushka.dsavisualizer.models.ActionType;
import com.anoushka.dsavisualizer.models.AlgorithmRequest;
import com.anoushka.dsavisualizer.models.AlgorithmResult;
import com.anoushka.dsavisualizer.models.Step;

@Component("merge")
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
        mergeSort(arr, 0, arr.length - 1);

        return new AlgorithmResult(steps, comparisonCount, swapCount, arr, null);
    }

    
    private int getMiddleIndex(int start, int end) {
        return start + (end - start) / 2;
    }

    // MERGE FUNCTION
    private void merge(int[] arr, int start, int middle, int end) {

        // Add a COMPARE step to highlight the two subarrays being merged
        steps.add(new Step(arr.clone(), start, end, ActionType.COMPARE));

        int[] temp = new int[end - start + 1];
        int leftPointer = start;
        int rightPointer = middle + 1;
        int tempIndex = 0;

        //  Merge the two halves into temp
        while (leftPointer <= middle && rightPointer <= end) {
            comparisonCount++;

            //  Corrected guard — leftPointer <= middle (not <= end)
            steps.add(new Step(arr.clone(), leftPointer, rightPointer, ActionType.COMPARE));

            if (arr[leftPointer] <= arr[rightPointer]) {
                temp[tempIndex++] = arr[leftPointer++];
            } else {
                temp[tempIndex++] = arr[rightPointer++];
            }
        }

        // Copy remaining LEFT elements
        while (leftPointer <= middle) {
            temp[tempIndex++] = arr[leftPointer++];
        }

        // Copy remaining RIGHT elements
        while (rightPointer <= end) {
            temp[tempIndex++] = arr[rightPointer++];
        }

        // Copy sorted temp back into arr
        for (int i = 0; i < temp.length; i++) {
            arr[start + i] = temp[i];
        }

        // ONE single OVERWRITE step for the whole merged segment (not per element)
        steps.add(new Step(arr.clone(), start, end, ActionType.OVERWRITE));
    }

    // MERGE SORT (recursive)
    private void mergeSort(int[] arr, int start, int end) {

        // BASE CASE
        if (start >= end) {
            return;
        }

        int middle = getMiddleIndex(start, end);

        // Recursively sort LEFT half
        mergeSort(arr, start, middle);

        // Recursively sort RIGHT half
        mergeSort(arr, middle + 1, end);

        // Merge the sorted halves
        merge(arr, start, middle, end);
    }
}