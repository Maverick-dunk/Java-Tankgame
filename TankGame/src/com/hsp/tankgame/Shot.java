package com.hsp.tankgame;

public class Shot implements Runnable{
    int x; //记录子弹
    int y;
    int direct = 0;
    int speed = 2;
    boolean islive = true; //子弹是否还存活

    public Shot(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }


    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            switch (direct){
                case 0:
                    y-=speed;
                    break;
                case 1:
                    x+=speed;
                    break;
                case 2:
                    y+=speed;
                    break;
                case 3:
                    x-=speed;
                    break;
            }
            //输出x,y的坐标
            System.out.println("子弹 x=" +x +"y="+y);
            if(!(x >=0 && x<=1000 && y>=0 && y<=750 && islive)){ // 判断是否击中坦克
                islive = false;
                System.out.println("子弹线程退出");
                break; //退出子弹线程
            }
        }
    }


}
