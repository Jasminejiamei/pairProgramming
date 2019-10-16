package view;

import service.EntryJudge;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JudgeView extends JFrame {


    private JLabel title,result;
    private JButton confirm;
    private JLabel subjectNum,intArea,file1,file2;
    private JButton btn1,btn2;
    private JPanel down = new JPanel();


    public JudgeView() {
        //创建一个容器
        Container ct;
        ct=this.getContentPane();

        this.setSize(600, 450);//基本设置
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        title = new JLabel("判断对错");
        title.setFont(new Font("微软雅黑",Font.BOLD, 20));
        title.setBounds(200,20,280,60);
        //生成题目数
        subjectNum = new JLabel("请选择题目文件：");
        subjectNum.setFont(new Font("微软雅黑",Font.BOLD, 16));
        subjectNum.setBounds(70,100,160,50);
        btn1 = new JButton ("选择文件");
        btn1.setBounds(240,100,120,40);
        btn1.addActionListener(new ChooseFile1ActionListener());
        file1 = new JLabel();
        file1.setFont(new Font("微软雅黑",Font.BOLD, 14));
        file1.setBounds(390,100,130,50);
        //整数范围
        intArea = new JLabel("请输入答案文件：");
        intArea.setFont(new Font("微软雅黑",Font.BOLD, 16));
        intArea.setBounds(70,150,160,50);
        btn2 = new JButton ("选择文件");
        btn2.setBounds(240,150,120,40);
        btn2.addActionListener(new ChooseFile2ActionListener());

        file2 = new JLabel();
        file2.setFont(new Font("微软雅黑",Font.BOLD, 14));
        file2.setBounds(390,150,130,50);

        confirm = new JButton("确定");
        confirm.setBounds(250,270, 60, 50);
        confirm.addActionListener(new ConfirmActionListener());
        //设置底部panel
        down.setBounds(130, 330, 280, 50);
        //设置底部panel背景透明
        down.setBackground(null);
        down.setOpaque(false);
        result= new JLabel();
        result.setFont(new Font("微软雅黑",Font.BOLD, 18));
        //添加组件
        down.add(result);
        ct.add(title);
        ct.add(subjectNum);
        ct.add(btn1);
        ct.add(file1);
        ct.add(intArea);
        ct.add(btn2);
        ct.add(file2);

        ct.add(confirm);
        ct.add(down);

        this.setVisible(true);//显示窗口
        this.setLocationRelativeTo(null);//基本设置  把窗口位置设置到屏幕中心
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  //关闭窗口
    }
    class ChooseFile1ActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            JFileChooser chooser = new JFileChooser();             //设置选择器
            chooser.setMultiSelectionEnabled(true);             //设为多选
            int returnVal = chooser.showDialog(new JLabel(),"选择");
            String filename = chooser.getSelectedFile().getName();      //获取绝对路径

            file1.setText(filename);

        }
    }
    class ChooseFile2ActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            JFileChooser chooser2 = new JFileChooser();             //设置选择器
            chooser2.setMultiSelectionEnabled(true);             //设为多选
            int returnVal = chooser2.showDialog(new JLabel(),"选择");

            String filename = chooser2.getSelectedFile().getName();      //获取绝对路径

            file2.setText(filename);
        }
    }
    class ConfirmActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            //获取结果
            String [] args = new String[]{"-e", "Exercises.txt","-a","Answers.txt"};
            EntryJudge ej = new EntryJudge();
            boolean res = ej.Entry(args);
            //获取命令执行结果
            if(res == true) {
                result.setText("结果：生成题目成功" );

            } else{
                result.setText("结果：生成题目失败" );

            }
        }
    }
}
