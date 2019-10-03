//import java.awt.*;
import java.util.ArrayList;
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
    int maxCount = 100;//生成题目的数值最大值
    String exeFileName;  // 题目文件名
    String ansFileName;  // 答案文件名
    int trueNum;  //正确数目
    int wrongNum;    //错误数目

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
     * 生成随机分数
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

    private ExecutorService executor = Executors.newCachedThreadPool();

//    public void createAth() {
//        StringBuilder exercises = new StringBuilder();
//        StringBuilder answers = new StringBuilder();
//      List<Exercise> list = new ArrayList<>();
//        for (int i = 1; i <= maxCount; i++) {
//            String[] strs = exercise.print().split("=");
//            exercises.append(i).append(". ").append(strs[0]).append("\n");
//            answers.append(i).append(".").append(strs[1]).append("\n");
//            lis
// t.add(exercise);
//        }
//    }
    /**
     * 打印出来
     */
}

