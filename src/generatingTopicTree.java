import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class generatingTopicTree{
    private fraction generate;
    public Deposit content;
    public generatingTopicTree(fraction generate,boolean isBuild){
        this.generate = generate;
        if(isBuild){
            ThreadLocalRandom random = ThreadLocalRandom.current();
            int kind = random.nextInt(4);
            if (kind == 0) kind = 1;
//            content = build(kind);
            while (content.getValue().Zero()){
//                content =build(kind);
            }
        }
    }
    /**
     * 获取表达式，
     * 打印题目与答案
     */

    public String print(){
        return print(content) +  " = " + content.getValue();
    }
    public String print(Deposit node){

    }

}
