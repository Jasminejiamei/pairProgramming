package po;

/**
 * 用于记录符号结点，与Deposit类是一样的道理
 */
public class RecSymbols extends Deposit{

    private String symbol;

    public RecSymbols(String symbol, Deposit left, Deposit right){
        super(null, left, right);
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return " " + symbol + " ";
    }

    /**
     * 用于查重
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RecSymbols)) return false;
        RecSymbols that = (RecSymbols) o;

        boolean flag = this.symbol != null && symbol.equals(that.symbol);
        if(!flag) return false;

        boolean left = this.getLeft() != null && getLeft().equals(that.getLeft());
        boolean right = this.getRight() != null && getRight().equals(that.getRight());
        //左右子树相同
        if(left && right) {
            return true;
        }
        if(left ^ right) {
            return false;
        }
        //如果是加法或乘法由于满足交换律所以要判断
        if(this.symbol.equals("+") || this.symbol.equals("x")) {
            left = this.getLeft() != null && getLeft().equals(that.getRight());
            right = this.getRight() != null && getRight().equals(that.getLeft());
        }
        return left && right;
    }
}
