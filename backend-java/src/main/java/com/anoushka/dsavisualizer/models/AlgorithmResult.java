package com.anoushka.dsavisualizer.models;
import java.util.List;

public class AlgorithmResult{
    private List<Step> steps;
    private int comparisonCount;
    private int swapCount;
    private int[] finalArray;
    private Integer foundIndex;

    public AlgorithmResult(List<Step> steps2,int comparisonCount,int swapCount,int[] finalArray,Integer foundIndex){
       this.steps=steps2;
       this.comparisonCount=comparisonCount;
       this.swapCount=swapCount;
       this.finalArray=finalArray.clone();
       this.foundIndex=foundIndex;
    }
    public int getComparisonCount() {
        return comparisonCount;
    }
    public int[] getFinalArray() {
        return finalArray;
    }
    public Integer getFoundIndex() {
        return foundIndex;
    }
    public List<Step> getSteps() {
        return steps;
    }
    public int getSwapCount() {
        return swapCount;
    }
}