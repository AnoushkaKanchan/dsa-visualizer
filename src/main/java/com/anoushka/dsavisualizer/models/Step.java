package com.anoushka.dsavisualizer.models;
public class Step{

    private final int[] array;
    private final int index1;
    private final int index2;
    private final ActionType actionType;

    public Step(int[] array, int index1 ,int index2, ActionType actionType){
        this.array=array.clone();
        this.index1=index1;
        this.index2=index2;
        this.actionType=actionType;
    }
    public ActionType getActionType() {
        return actionType;
    }
    public int[] getArray() {
        return array;
    }
    public int getIndex1() {
        return index1;
    }
    public int getIndex2() {
        return index2;
    }
}