import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        args = new String[]{"-n", "10", "-r", "10", "-d", "10"};
        Map<String, String> params = checkParams(args);
        fraction opera = new fraction(params);
        Judge judge =  new Judge();
        if(params.containsKey("-e")&&params.containsKey("-a")){
            judge.Judge();
        } else if(params.containsKey("-n") || params.containsKey("-r") || params.containsKey("-d")) {
            opera.createAth();
        }else {
            System.out.println("参数输入错误，请重试\n" + "必须输入参数：\n" + "-n: 生成题目的个数\n" +
                    "-r: 生成题目的数值大小\n" + "-e <exercisefile>.txt -a <answerfile>.txt: " +
                    "对给定的题目文件和答案文件，判定答案中的对错并进行数量统计");
        }
    }

    private static Map<String, String> checkParams(String... args) {
        Map<String, String> params = new HashMap<>();
        if(args.length == 0)  {
            throw new RuntimeException("必须输入参数：\n" + "-n: 生成题目的个数\n" +
                    "-r: 生成题目的数值大小\n" + "-e <exercisefile>.txt -a <answerfile>.txt: " +
                    "对给定的题目文件和答案文件，判定答案中的对错并进行数量统计");
        } else {
            if(args.length % 2 != 0) {
                throw new RuntimeException("参数格式输入错误...");
            }
            for (int i = 0; i < args.length; i = i + 2) {
                params.put(args[i], args[i+1]);
            }
        }
        return params;
    }
}
