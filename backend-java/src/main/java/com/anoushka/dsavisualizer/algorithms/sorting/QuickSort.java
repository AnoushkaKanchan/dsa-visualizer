package com.anoushka.dsavisualizer.algorithms.sorting;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.anoushka.dsavisualizer.algorithms.Algorithm;
import com.anoushka.dsavisualizer.models.ActionType;
import com.anoushka.dsavisualizer.models.AlgorithmResult;
import com.anoushka.dsavisualizer.models.Step;

public class QuickSort implements Algorithm {
    private int comparisonCount;
    private int swapCount;
    private List<Step> steps;
    private final Random rand = new Random();

    public AlgorithmResult execute (int[] input){

        int[] arr=input.clone();
        comparisonCount=0;
        swapCount=0;
        steps = new ArrayList<>();

        quickSort(arr, 0, arr.length - 1);

        return new AlgorithmResult(steps,comparisonCount,swapCount,arr,null); 
    }
    
    private void quickSort(int[] arr, int low, int high) {

        if (low < high) {
            int pivotIndex = partition(arr, low, high);

            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
    }

    private int partition(int[] arr, int low, int high) {

        int pivotIndex = low+rand.nextInt(high-low+1);  
        steps.add(new Step(arr, pivotIndex, pivotIndex, ActionType.PIVOT));

        if (pivotIndex != high) {
            swap(arr, pivotIndex, high);
            swapCount++;
            steps.add(new Step(arr, pivotIndex, high, ActionType.SWAP));
        }
        int pivot = arr[high];

        int i = low - 1;

        for (int j = low; j < high; j++) {
            comparisonCount++;
            steps.add(new Step(arr, j, high, ActionType.COMPARE));

            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);
                swapCount++;
                steps.add(new Step(arr, i, j, ActionType.SWAP));
            }
        }

        swap(arr, i + 1, high);
        swapCount++;
        steps.add(new Step(arr, i+1, high, ActionType.SWAP));
        return i + 1;
    }

    private void swap(int[] arr, int a, int b) {

        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }
}
