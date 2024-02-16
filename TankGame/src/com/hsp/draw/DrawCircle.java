package com.hsp.draw;

import javax.swing.*;
import java.awt.*;

public class DrawCircle extends JFrame{  //JFrame对应窗口，理解成画框
    // 定义一个面板
    private MyPanel mp = null;
    public static void main(String[] args) {
        new DrawCircle();
    }

    public DrawCircle(){
        mp = new MyPanel();
        this.add(mp);
        //设置窗口大小次哦啊
        this.setSize(400,300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //点击叉号时候程序退出
        this.setVisible(true);
    }
}

//定义一个面板Myp
//Graphics g 理解成一只画笔
// 提供了很多方法

class MyPanel extends JPanel {

    @Override
    public void paint(Graphics g){
        super.paint(g);
        g.drawOval(10,10,100,100);
    }
}
