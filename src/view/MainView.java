package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView {
    public static void main(String[] args) {
        new MainJFrame();
    }
}
class MainJFrame extends JFrame {
    /**
     *   程序主界面
     */
    private static final long serialVersionUID = 1;
    //定义全局变量

    private JLabel title;
    private JButton generate,judge;
    private JLabel result;//定义全局变量
    private JPanel down = new JPanel();

    //创建一个容器
    Container ct;


    MainJFrame(){

        ct=this.getContentPane();
        this.setLayout(null);//设置容器为空布局，绝对定位

        //标题
        title= new JLabel("四则运算题目生成程序");
        title.setFont(new Font("微软雅黑",Font.BOLD, 30));
        title.setBounds(140, 40, 340, 100);

        //生成
        generate = new JButton("生成题目");
        generate.setBounds(120, 220, 140, 40);
        generate.addActionListener(new generateListener());
        //判错
        judge = new JButton("判断对错");
        judge.setBounds(300, 220, 140, 40);
        judge.addActionListener(new judgeListenner());

//        result.setBounds(130, 40, 340, 100);
        //添加组件

        ct.add(title);
        ct.add(generate);
        ct.add(judge);
        ct.add(down);
        this.setTitle("MyApp");
        this.setSize(600, 450);// 设置窗口大小
        this.setLocationRelativeTo(null);//基本设置  把窗口位置设置到屏幕中心
        this.setVisible(true);//显示窗口
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //关闭窗口 当点击窗口的关闭按钮时退出程序（没有这一句，程序不会退出）
    }


    class generateListener implements ActionListener {
        //监听生成按钮点击事件
        public void actionPerformed(ActionEvent e) {

            new GenerateView();
        }

    }
    class judgeListenner implements ActionListener{
        //监听判错按钮点击事件
        public void actionPerformed(ActionEvent e) {
            new JudgeView();

        }
    }
}
