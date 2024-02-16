package com.hsp.tankgame;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

public class HspTankGame01 extends JFrame {

    MyPanel mp =null;
    Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {

        HspTankGame01 hspTankGame01 = new HspTankGame01();
    }
    public HspTankGame01(){
        System.out.println("请输入选择 1 表示新游戏，2 表示继续上局");
        String key = scanner.next();
        mp = new MyPanel(key);
        Thread thread =  new Thread(mp);
        thread.start();
        this.add(mp);
        this.setSize(1300,750);
        this.addKeyListener(mp); //让JFrame 监听键盘事件
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                Recorder.keepRecord(); // 记录数据
                System.exit(0);
            }
        });
    }
}
