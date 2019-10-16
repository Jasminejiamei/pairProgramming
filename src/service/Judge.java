package service;

import po.Fraction;
import util.FileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 答案判错类
 */
public class Judge{
    private int trueNum;  //正确数目
    private int wrongNum;    //错误数目
    private String exerciseFileName;  // 题目文件名
    private String answerFileName;  // 答案文件名

    public Judge(Map<String, String> params) {
        for (String str : params.keySet()) {
            if (str.equals("-e")) {
                exerciseFileName = params.get(str);
            } else if (str.equals("-a")) {
                answerFileName = params.get(str);
            }
        }
    }
    /**
     * 判断错误 ，并把错误写入文件
     */
    public void Judge() {
        long start = System.currentTimeMillis();
        List<String> correctNums = new ArrayList<>();
        List<String> wrongNums = new ArrayList<>();
        FileUtil.readFile((exercise, answer) -> {
            String[] strs1 = exercise.split("\\."); //匹配每一行
            String[] strs2 = answer.split("\\.");
            if (strs1[0].equals(strs2[0])) {
                CreateAth exes = new CreateAth(false);
                exes.build(strs1[1].trim());  //去掉两端的空格后，将后缀表达式生成树变成前缀的，
                if (exes.getResult().equals(new Fraction(strs2[1].trim()))) {   //答案两边都相等，继续执行下面的
                    correctNums.add(strs1[0]);
                    trueNum++;
                } else {
                    wrongNums.add(strs1[0]);
                    wrongNum++;
                }
            }
        }, exerciseFileName, answerFileName);
        FileUtil.writeFile(printResult(correctNums, wrongNums), "Correction.txt");
        long end = System.currentTimeMillis();
        System.out.println("题目答案对错统计存在当前目录下的Correction.txt文件下，耗时为：" + (end - start) + "ms");
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
}
