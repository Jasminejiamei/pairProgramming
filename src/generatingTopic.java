import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class generatingTopic{
    int maxNum = 100; //生成题目的最大值
    int denArea = 20; //分母的范围
    int maxCount = 100;//生成题目的数值最大值
    String exeFileName;  // 题目文件名
    String ansFileName;  // 答案文件名
    int trueNum;  //正确数目
    int wrongNum;    //错误数目

    //生成随机分数
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
    /**
     * 随机生成分数
     */
    private  fraction creation(){
        if(randomBoolean()){
            return new fraction((random(maxNum)),1);
        } else {
            if(randomBoolean()){
                int den = random(denArea);
                int mol = random(den*maxNum);
                return new fraction(den,mol);
            }else{
                int den = random(denArea);
                return new fraction(random(den),den);
            }
        }
    }
    /**
     * 随机生成一道题目
     */
}
