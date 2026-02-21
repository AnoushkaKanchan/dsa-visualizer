import java.util.List;

class AlgorithmResult{
    private List<Step> steps;
    private int comparisonCount;
    private int swapCount;
    private int[] finalArray;
    private Integer foundIndex;

    public AlgorithmResult(List<Step> steps,int comparisonCount,int swapCount,int[] finalArray,Integer foundIndex){
       this.steps=steps;
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