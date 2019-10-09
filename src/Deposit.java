import java.util.Objects;
/**
 * 用于存放题目的类，用二叉树的形式
 */
public class Deposit{

    private Deposit left;
    private Deposit right;
    private fraction value; //用于二叉树结点的是符号与运算结果数值的之间的变化

    Deposit(fraction value, Deposit left, Deposit right){
        this.value = value;
        this.left = left;
        this.right = right;
    }

    /**
     * 把每一个节点存的东西抽出来
     */
    Deposit(fraction value){
        this.value = value;
    }

    //拿到结点数据
    Deposit getRight(){
        return right;
    }
    Deposit getLeft(){
        return left;
    }

    //设置结点数据
    void setLeft(Deposit left){
        this.left = left;
    }
    void setRight(Deposit right){
        this.right = right ;
    }

    fraction getValue() {
        return value;
    }

    void setValue(fraction value) {
        this.value = value;
    }

    @Override
    //连接结点
    public String toString() {
        return value.toString();
    }

    @Override
    /**
     * 用于查重
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Deposit)) return false;
        Deposit node = (Deposit) o;
        return Objects.equals(value, node.value) &&
                Objects.equals(left, node.left) &&
                Objects.equals(right, node.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, left, right);
    }
}
