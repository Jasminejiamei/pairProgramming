package po;

import java.util.Objects;
/**
 * 用于存放题目的类，用二叉树的形式
 */
public class Deposit{

    private Deposit left;
    private Deposit right;
    private Fraction value; //用于二叉树结点的是符号与运算结果数值的之间的变化

    public Deposit(Fraction value, Deposit left, Deposit right){
        this.value = value;
        this.left = left;
        this.right = right;
    }

    /**
     * 取结点数据
     */
    public Fraction getValue() {
        return value;
    }
    public Deposit getRight(){
        return right;
    }
    public Deposit getLeft(){
        return left;
    }

    /**
     * 设置结点数据
     */
    public Deposit(Fraction value){
        this.value = value;
    }
    public void setLeft(Deposit left){
        this.left = left;
    }
    public void setRight(Deposit right){
        this.right = right ;
    }
    public void setValue(Fraction value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    /**
     * 用于查重,被recSymbols和createAth重写的方法equals调用
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Deposit)) return false;
        Deposit node = (Deposit) o;
        return Objects.equals(value, node.value) &&
                Objects.equals(left, node.left) &&
                Objects.equals(right, node.right);
    }
}
