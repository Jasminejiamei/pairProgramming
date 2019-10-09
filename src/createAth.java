import com.sun.org.apache.xalan.internal.xsltc.compiler.util.StringStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class createAth{
    private int maxNum = 100; //生成题目的最大值
    private int denArea = 20; //分母的范围
    private int maxCount = 10;//生成题目的数值最大值
    private Deposit content;
    private static final String[] SYMBOLS = new String[]{
            "+", "-", "x", "\u00F7"
    };

    /**
     * 生成组成题目随机数
     */
    private int random(int area) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int x = random.nextInt(area);
        if (x == 0) x = 1;
        return x;
    }

    /**
     * ThreadLocalRandom类在多线程环境中生成随机数。
     * 从随机数生成器的序列返回下一个伪随机的，均匀分布的布尔值
     */
    private boolean randomBoolean() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return random.nextBoolean();
    }
    /**
     * 把生成的每个数进行处理得出分子分母
     */
    private fraction creator() {
        if (randomBoolean()) {
            return new fraction((random(maxNum)), 1);
        } else {
            if (randomBoolean()) {
                int den = random(denArea);
                int mol = random(den * maxNum);
                return new fraction(den, mol);
            } else {
                int den = random(denArea);
                return new fraction(random(den), den);
            }
        }
    }

    /**
     * 单步计算
     */
    private fraction calculate(String symbol, fraction left, fraction right) {
        switch (symbol) {
            case "+":
                return left.add(right);
            case "-":
                return left.subtract(right);
            case "x":
                return left.multiply(right);
            default:
                return left.divide(right);
        }
    }

    /**
     * 生成随机题目，初始化，把主类中输入的参数内容调进来
     */
    createAth(Map<String, String> params) {
        for (String str : params.keySet()) {
            if (str.equals("-n")) {
                maxCount = Integer.valueOf(params.get(str));
            } else if (str.equals("-r")) {
                maxNum = Integer.valueOf(params.get(str));
            } else if (str.equals("-e")) {
                // 题目文件名
                String exeFileName = (params.get(str));
            } else if (str.equals("-a")) {
                // 答案文件名
                String ansFileName = params.get(str);
            } else if (str.equals("-d")) {
                denArea = Integer.valueOf(params.get(str));
            }
        }
    }

    /**
     * 随机生成一道四则运算题目
     * @param fractionNum 运算符个数
     * @return 二叉树
     */
    private Deposit build(int fractionNum){
        if(fractionNum == 0){
            return new Deposit(creator(),null,null);
        }
        ThreadLocalRandom random = ThreadLocalRandom.current();
        recSymbols node = new recSymbols(SYMBOLS [random.nextInt(4)],null, null);
//        System.out.println(node);
        //左子树运算符数量
        int left = random.nextInt(fractionNum);
        //右子树运算符数量
        int right = fractionNum - left - 1;
        node.setLeft(build(left));
        node.setRight(build(right));
        fraction value = calculate(node.getSymbol(),node.getLeft().getValue(),node.getRight().getValue());
        //负数处理
        if(value.Negative()){
            //交换左右子树，就是交换两个减数的顺序
            if (node != null) {
                Deposit swap = node.getLeft();
                node.setLeft(node.getRight());
                node.setRight(swap);
            }
            value = calculate(node.getSymbol(),node.getLeft().getValue(),node.getRight().getValue());
        }
        node.setValue(value);
        return node;
    }

    /**
     * 获取表达式，
     * 打印题目与答案
     */
    private String print(){
        return print(content) +  " = " + content.getValue();
    }

    private String print(Deposit node){
        if (node == null){
            return "";
        }
        String frac = node.toString();
        String left = print(node.getLeft());
        if (node.getLeft() instanceof recSymbols && node instanceof recSymbols) {
            if (bracketsLeft(((recSymbols) node.getLeft()).getSymbol(), ((recSymbols) node).getSymbol())) {
                left = "(" + " " + left + " " + ")";
            }
        }
        String right = print(node.getRight());
        if (node.getRight() instanceof recSymbols && node instanceof recSymbols) {
            if (bracketsRight(((recSymbols) node.getRight()).getSymbol(), ((recSymbols) node).getSymbol())) {
                right = "(" + " " + right + " " + ")";
            }
        }
        return left + frac + right;
    }
    /**
     * 比较两个符号谁优先级更高，子树的箱号优先级低要加括号
     */
    //要左括号
    private boolean bracketsLeft(String left,String mid){
        return (left.equals("+")|| left.equals("-")) && (mid.equals("x")||mid.equals("\u00F7"));
    }
    private boolean bracketsRight(String right, String mid){
        return (right.equals("+")|| right.equals("-")) && (mid.equals("x")||mid.equals("\u00F7"))||(mid.equals("\u00F7"))||(mid.equals("-")&&(mid.equals("+")|| mid.equals("-")));
    }

    /**
     *生成一个题目，先调用下面的createAth方法来判断有没有生成一个用build方法生成的树
     */
    createAth(boolean isBuild){
        if(isBuild){
            ThreadLocalRandom random = ThreadLocalRandom.current();
            int kind = random.nextInt(4);
            if (kind == 0) kind = 1;
            content = build(kind);
            while (content.getValue().Zero()){
                content =build(kind);
            }
        }
    }
    private ExecutorService executor = Executors.newCachedThreadPool();

    void createAth() {
        StringBuilder exercises = new StringBuilder();
        StringBuilder answers = new StringBuilder();
        List<createAth> list = new ArrayList<>();
        for (int i = 1; i <= maxCount;) {
            createAth generate = new createAth(true);
            if (!list.contains(generate)){
                String[] strs = generate.print().split("=");
                exercises.append(i).append(". ").append(strs[0]).append("\n");
                answers.append(i).append(".").append(strs[1]).append("\n");
                list.add(generate);
                i++;
            }
        }
        executor.execute(() -> aboutFile.writeFile(exercises.toString(), "Exercises.txt"));
        executor.execute(() -> aboutFile.writeFile(answers.toString(), "Answers.txt"));
        executor.shutdown();
        try {
            boolean loop = true;
            while (loop) {
                loop = !executor.awaitTermination(30, TimeUnit.SECONDS);  //超时等待阻塞，直到线程池里所有任务结束
            } //等待所有任务完成
            System.out.println("生成的" + maxCount + "道题和答案存放在当前目录下的Exercises.txt和Answers.txt");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    fraction getResult() {
        return content.getValue();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof createAth)) return false;
        createAth exercise = (createAth) o;
        return content.equals(exercise.content);
    }

    @Override
    public int hashCode() {
        return content.hashCode();
    }


    /**
     * 中缀表达式生成树
     * 在差错中调用
     * @param exercise 中缀表达式
     * @return 二叉树
     */
    Deposit build(String exercise) {
        String[] strs = exercise.trim().split(" "); //拿走标号
        Stack<Deposit> depositStack = new Stack<>();  //结点栈
        StringStack symbolStack = new StringStack();  //符号栈
        //中缀表达式转换成前缀表达式，然后再用前序遍历生成数
        for (int i = strs.length - 1; i >= 0; i--) {
            String str = strs[i];
            if (!str.matches("[()+\\u00F7\\-x]")) {
                depositStack.push(new Deposit(new fraction(str)));
            } else {
                //符号结点
                while (!symbolStack.empty() && ((symbolStack.peekString().equals("x") ||
                        symbolStack.peekString().equals("\u00F7"))
                        && (str.equals("+") || str.equals("-"))
                        || str.equals("("))) {
                    String symbol = symbolStack.popString();
                    if (symbol.equals(")")) {
                        break;
                    }
                    push(symbol, depositStack);
                }
                if (str.equals("(")) {
                    continue;
                }
                symbolStack.pushString(str);
            }
        }
        while (!symbolStack.empty()) {
            push(symbolStack.popString(), depositStack);
        }
        this.content = depositStack.pop();
        return content;
    }

    /**
     * 将符号压入节点栈且计算结果
     */
    private void push(String symbol, Stack<Deposit> nodeStack) {
        Deposit left = nodeStack.pop();
        Deposit right = nodeStack.pop();
        recSymbols node = new recSymbols(symbol, left, right);
        node.setValue(calculate(symbol, left.getValue(), right.getValue()));
        nodeStack.push(node);
    }
}
