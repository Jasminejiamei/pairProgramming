import com.sun.org.apache.xalan.internal.xsltc.compiler.util.StringStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Judge{
    int trueNum;  //正确数目
    int wrongNum;    //错误数目
    String exerciseFileName;  // 题目文件名
    String answerFileName;  // 答案文件名

    /**
     * 判断错误 ，并把错误写入文件
     */
    public void Judge() {
        long start = System.currentTimeMillis();
        List<String> correctNums = new ArrayList<>();
        List<String> wrongNums = new ArrayList<>();
        aboutFile.readFile((exercise, answer) -> {
            String[] strs1 = exercise.split("\\."); //匹配每一行
            String[] strs2 = answer.split("\\.");
            if (strs1[0].equals(strs2[0])) {
                fraction exes = new fraction(false);
                build(strs1[1].trim());  //去掉两端的空格
                if (exes.getResult().equals(new fraction(strs2[1].trim()))) {
                    correctNums.add(strs1[0]);
                    trueNum++;
                } else {
                    wrongNums.add(strs1[0]);
                    wrongNum++;
                }
            }
        }, exerciseFileName, answerFileName);
        aboutFile.writeFile(printResult(correctNums, wrongNums), "Correction.txt");
        long end = System.currentTimeMillis();
        System.out.println("题目答案对错统计存在当前目录下的Grade.txt文件下，耗时为：" + (end - start) + "ms");
    }

    private String printResult(List<String> correctNums, List<String> wrongNums) {
        StringBuilder builder = new StringBuilder();
        builder.append("Correct: ").append(trueNum).append(" (");
        for (int i = 0; i < correctNums.size(); i++) {
            if (i == correctNums.size() - 1) {
                builder.append(correctNums.get(i));
                break;
            }
            builder.append(correctNums.get(i)).append(", ");
        }
        builder.append(")").append("\n");
        builder.append("Wrong: ").append(wrongNum).append(" (");
        for (int i = 0; i < wrongNums.size(); i++) {
            if (i == wrongNums.size() - 1) {
                builder.append(wrongNums.get(i));
                break;
            }
            builder.append(wrongNums.get(i)).append(", ");
        }
        builder.append(")").append("\n");
        return builder.toString();
    }

    /**
     * 中缀表达式生成树
     * 在差错中调用
     * @param exercise 中缀表达式
     * @return 二叉树
     */
    public Deposit build(String exercise) {
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
        fraction depositCont = new fraction(toString());
        depositCont.content = depositStack.pop();
        return depositCont.content;
    }

    /**
     * 将符号压入节点栈且计算结果
     */
    private void push(String symbol, Stack<Deposit> nodeStack) {
        Deposit left = nodeStack.pop();
        Deposit right = nodeStack.pop();
        recSymbols node = new recSymbols(symbol, left, right);
        fraction fracal = new fraction(symbol);
        node.setValue(fracal.calculate(symbol, left.getValue(), right.getValue()));
        nodeStack.push(node);
    }
}
