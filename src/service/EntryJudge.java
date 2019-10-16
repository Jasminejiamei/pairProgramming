package service;

import java.util.HashMap;
import java.util.Map;

public class EntryJudge{
//    private static boolean tag;
//    public String[] args;

    public boolean Entry(String[] args){

        /**
         * 给我这个args:args = new String[]{"-n", maxCount, "-r", IntArea, "-d", denArea}; 其中三个参数都要是int类型
         */
        boolean tag = false;
        if(args.length == 0 || args.length % 2 != 0)  {
            tag = false;
            return tag;
        }

        Map<String, String> params = checkParams(args);

        CreateAth opera = new CreateAth(params);
        Judge check =  new Judge(params);
        if(params.containsKey("-e")&&params.containsKey("-a")){
            check.Judge();
            tag = true;
            return tag;
        } else if(params.containsKey("-n") || params.containsKey("-r") || params.containsKey("-d")) {
            opera.createAth();
            tag = true;
            return tag;
        }
        return tag;
    }
    private Map<String, String> checkParams(String[] args) {
        Map<String, String> params = new HashMap<>();
        for (int i = 0; i < args.length; i = i + 2) {
            params.put(args[i], args[i+1]);
        }
        return params;

    }
}
