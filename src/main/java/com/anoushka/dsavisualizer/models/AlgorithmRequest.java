package com.anoushka.dsavisualizer.models;

public  class AlgorithmRequest{
    private int[] array;
    private Integer target;
    private String algorithm;
    
    public String getAlgorithm() {
        return algorithm;
    }
    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }
    
    public int[] getArray() {
        return array;
    }
    public void setArray(int[] array) {
        this.array = array;
    }
    
    public Integer getTarget() {
        return target;
    }
    public void setTarget(Integer target) {
        this.target = target;
    }
       
}

