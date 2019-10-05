import java.util.Objects;

public class Deposit{
    /**
     * 用于存放题目的类，用二叉树的形式
     * 这样声明变量的目的是不管是在类中还是在类外，使变量能够引用这个类的对象。
     */
    private Deposit left;
    private Deposit right;
    private fraction value; //用于二叉树结点的是符号与运算结果数值的之间的变化
    public Deposit(fraction value,Deposit left,Deposit right){
        this.value = value;
        this.left = left;
        this.right = right;
    }

    /**
     * 把每一个节点要存的东西抽出来
     */
    public Deposit(fraction value){
        this.value = value;
    }

    //拿到结点数据
    public Deposit getRight(){
        return right;
    }
    public Deposit getLeft(){
        return left;
    }

    //设置结点数据
    public void setLeft(Deposit left){
        this.left = left;
    }
    public void setRight(Deposit right){
        this.right = right ;
    }

    public fraction getValue() {
        return value;
    }

    public void setValue(fraction value) {
        this.value = value;
    }

    //重写一些方法
    @Override
    public String toString() {
        return value.toString();
    }

    @Override
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
