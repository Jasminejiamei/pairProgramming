package view;

import service.EntryJudge;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GenerateView extends JFrame {


    private JLabel title,result;
    private JButton confirm;
    private JLabel subjectNum,intArea,denArea;
    private JTextField subjectNumField,intAreaField,denAreaField;
    private JPanel down = new JPanel();


    public GenerateView() {
        //创建一个容器
        Container ct;
        ct=this.getContentPane();
//        this.setLayout(null);//设置容器为空布局，绝对定位

        this.setSize(600, 450);//基本设置
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        title = new JLabel("生成题目");
        title.setFont(new Font("微软雅黑",Font.BOLD, 20));
        title.setBounds(200,20,280,60);
        //生成题目数
        subjectNum = new JLabel("请输入生成题目数：");
        subjectNum.setFont(new Font("微软雅黑",Font.BOLD, 16));
        subjectNum.setBounds(70,100,160,50);
        subjectNumField = new JTextField (10);
        subjectNumField.setBounds(260,100,260,40);
        //整数范围
        intArea = new JLabel("请输入整数范围：");
        intArea.setFont(new Font("微软雅黑",Font.BOLD, 16));
        intArea.setBounds(70,150,160,50);
        intAreaField = new JTextField (20);
        intAreaField.setBounds(260,150,260,40);
        //分母范围
        denArea = new JLabel("请输入分数分母的范围：");
        denArea.setFont(new Font("微软雅黑",Font.BOLD, 16));
        denArea.setBounds(70,200,180,50);
        denAreaField = new JTextField (20);
        denAreaField.setBounds(260,200,260,40);
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
        ct.add(subjectNumField);
        ct.add(intArea);
        ct.add(intAreaField);
        ct.add(denArea);
        ct.add(denAreaField);
        ct.add(confirm);
        ct.add(down);

        this.setVisible(true);//显示窗口
        this.setLocationRelativeTo(null);//基本设置  把窗口位置设置到屏幕中心
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  //关闭窗口
    }
    class ConfirmActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            String args1 = subjectNumField.getText();
            String args2 = intAreaField.getText();
            String args3 = denAreaField.getText();

            String [] args  = new String[]{"-n",args1,"-r",args2,"-d",args3};
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
