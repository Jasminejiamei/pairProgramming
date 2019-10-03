import java.io.*;

public class aboutFile{
    /**
     * 写入文件中
     */
    public static void writeFile(String content, String fileName) {
        File file = new File(fileName);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))){
            if(!file.exists()){
                file.createNewFile();
            }
            bw.write(content);
            bw.flush();
        } catch (IOException e) {
            System.out.println("文件操作失败...");
        }
    }
    /**
     * 读文件内容
     * @param callBack 回调接口，分别处理每一行
     * @param exerciseFileName 题目文件
     * @param answerFileName 答案文件
     */
    public static void readFile(ReaderCallBack callBack, String exerciseFileName, String answerFileName) {
        File exerciseFile = new File(exerciseFileName);
        File answerFile = new File(answerFileName);
        if(!exerciseFile.exists() || !answerFile.exists()) {
            System.out.println("文件不存在，请重试");
            return;
        }
        try (BufferedReader br1 = new BufferedReader(new FileReader(exerciseFileName));
             BufferedReader br2 = new BufferedReader(new FileReader(answerFileName))) {
            String line1, line2;
            while ((line1 = br1.readLine()) != null && (line2 = br2.readLine()) != null) {
                callBack.deal(line1, line2);
            }
        } catch (IOException e) {
            System.out.println("读取文件失败...");
        }
    }

    public interface ReaderCallBack {
        void deal(String exercise, String answer) throws IOException;
    }
}
