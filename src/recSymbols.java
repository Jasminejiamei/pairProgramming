public class recSymbols extends Deposit {
    private String symbol;
    public recSymbols(String symbol,Deposit left,Deposit right){
        super(null, left, right);
        this.symbol = symbol;
    }
    public recSymbols(String symbol){
        super(null);

        this.symbol = symbol;
    }
    public String getSymbol() {
        return symbol;
    }
}
