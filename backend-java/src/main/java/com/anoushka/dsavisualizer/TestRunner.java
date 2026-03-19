package com.anoushka.dsavisualizer;

import java.lang.reflect.Array;
import java.util.Arrays;

import com.anoushka.dsavisualizer.algorithms.sorting.BubbleSort;
import com.anoushka.dsavisualizer.algorithms.sorting.SelectionSort;
import com.anoushka.dsavisualizer.models.AlgorithmResult;

public class TestRunner {
    public static void main(String[] args) {

    int[] input = {2, 3, 1};

    BubbleSort bubbleSort = new BubbleSort();
    AlgorithmResult result = bubbleSort.execute(input);

    System.out.println("Comparisons: " + result.getComparisonCount());
    System.out.println("Swaps: " + result.getSwapCount());
    System.out.println("Steps: " + result.getSteps().size());
    System.out.println("Arrayfinal " + Arrays.toString(result.getFinalArray()));

    
    SelectionSort selectionsort = new SelectionSort();
    AlgorithmResult result1 = selectionsort.execute(input);

    System.out.println("Comparisons: " + result1.getComparisonCount());
    System.out.println("Swaps: " + result1.getSwapCount());
    System.out.println("Steps: " + result1.getSteps().size());
    System.out.println("Arrayfinal " + Arrays.toString(result1.getFinalArray()));
}
}
