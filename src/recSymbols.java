public class recSymbols extends Deposit {
    private String symbol;

    public recSymbols(String symbol,Deposit left,Deposit right){
        super(null, left, right);
        this.symbol = symbol;
    }
//    public recSymbols(String symbol){
//        super(null);
//        this.symbol = symbol;
//    }
    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return " " + symbol + " ";
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof recSymbols)) return false;
        recSymbols that = (recSymbols) o;

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
