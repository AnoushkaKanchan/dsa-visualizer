public class Step{

    private int[] array;
    private int index1;
    private int index2;
    private ActionType actionType;

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