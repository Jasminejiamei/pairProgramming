//import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 *把所有随机生成的数都当成是分数处理
 * 定义分数的四则运算类
 * 生成随机的分子分母
 */
public class fraction{
    int maxNum = 100; //生成题目的最大值
    int denArea = 20; //分母的范围
    private int mol; //分子
    private int den;  //分母
    int maxCount = 10;//生成题目的数值最大值
    String exeFileName;  // 题目文件名
    String ansFileName;  // 答案文件名
    public Deposit content;
    static final String[] SYMBOLS = new String[]{
            "+", "-", "x", "\u00F7"
    };

    /**
     * 处理随机生成的数值,这个用于分解分数对象
     */
    public fraction(String str) {
        int a = str.indexOf("'");
        int b = str.indexOf("/");
        if (a != -1) {
            //取出数组，转换类型
            int c = Integer.valueOf(str.substring(0, a));
            den = Integer.valueOf(str.substring(b + 1));
            mol = c * den + Integer.valueOf(str.substring(a + 1), b);
        } else if (b != -1) {
            String[] strs = str.split("/");
            mol = Integer.valueOf(strs[0]);
            den = Integer.valueOf(strs[1]);
        } else {
            mol = Integer.valueOf(str);
            den = 1;
        }
    }

    /**
     * 处理随机生成的数值,组合分数对象，也是重载这个fraction方法，后面调用
     */
    public fraction(int mol, int den) {
        this.mol = mol;
        this.den = den;
        if (den <= 0) {
            throw new RuntimeException("分数分母不能为0");
        }
        //否则就进行约分
        int mod = 1;
        int max = den > mol ? den : mol;
        for (int i = 1; i <= max; i++) {
            if (mol % i == 0 && den % i == 0) {
                mod = i;
            }
        }
        this.mol = mol / mod;
        this.den = den / mod;
    }

    public String toString() {
        if (den == 1) {
            return String.valueOf(mol);
        } else {
            int z = 0;
            if (den != 0 && mol > den) {
                z = mol / den;
            }
            if (z == 0) {
                return String.valueOf(mol) + "/" + String.valueOf(den);
            } else {
                return String.valueOf(z) + "'" + String.valueOf(mol % den) + "/" + String.valueOf(den);
            }
        }
    }

    /**
     * 判空
     */
    public boolean Zero() {
        return den == 0;
    }

    /**
     * 判负
     */
    public boolean Negative() {
        return mol < 0;
    }

    /**
     * 定义加减乘除类,返回值类型(全都当成分数处理)
     */
    public fraction add(fraction fraction) {
        return new fraction(this.mol * fraction.den + this.den * fraction.mol, this.den * fraction.den);
    }

    public fraction subtract(fraction fraction) {
        return new fraction(this.mol * fraction.den - this.den * fraction.mol, this.den * fraction.den);
    }

    public fraction multiply(fraction fraction) {
        return new fraction(this.mol * fraction.mol, this.den * fraction.den);
    }

    public fraction divide(fraction fraction) {
        return new fraction(this.mol * fraction.den, this.den * fraction.mol);
    }

    /**
     * 生成随机数
     */
    private int random(int area) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int x = random.nextInt(area);
        if (x == 0) x = 1;
        return x;
    }

    //ThreadLocalRandom类在多线程环境中生成随机数。
    //从随机数生成器的序列返回下一个伪随机的，均匀分布的布尔值
    private boolean randomBoolean() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return random.nextBoolean();
    }

    private fraction creatfra() {
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
     * 把生成的每个数进行处理得出分子分母
     */
    public fraction calculate(String symbol, fraction left, fraction right) {
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
     * 初始化
     * 生成随机题目
     */
    public fraction(Map<String, String> params) {
        for (String str : params.keySet()) {
            if (str.equals("-n")) {
                maxCount = Integer.valueOf(params.get(str));
            } else if (str.equals("-r")) {
                maxNum = Integer.valueOf(params.get(str));
            } else if (str.equals("-e")) {
                exeFileName = (params.get(str));
            } else if (str.equals("-a")) {
                ansFileName = params.get(str);
            } else if (str.equals("-d")) {
                denArea = Integer.valueOf(params.get(str));
            }
        }
    }

    public fraction getResult() {
        return content.getValue();
    }

    /**
     * 随机生成一道四则运算题目
     * @param fractionNum 运算符个数
     * @return 二叉树
     */
    public Deposit build(int fractionNum){
        if(fractionNum == 0){
            return new Deposit(creatfra(),null,null);
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

    public String print(){
        return print(content) +  " = " + content.getValue();
    }
    public String print(Deposit node){
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

    private ExecutorService executor = Executors.newCachedThreadPool();

    public void createAth() {
        StringBuilder exercises = new StringBuilder();
        StringBuilder answers = new StringBuilder();
        List<fraction> list = new ArrayList<>();
        for (int i = 1; i <= maxCount;) {
            fraction generate = new fraction(true);
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
            System.out.println("生成的" + maxCount + "道题和答案存放在当前目录下的Exercises.txt和Answers.txt，耗时为");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public fraction(boolean isBuild){
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
}

