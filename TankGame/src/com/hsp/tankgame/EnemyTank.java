package com.hsp.tankgame;

import java.util.Vector;

public class EnemyTank extends Tank implements  Runnable{
    Vector<Shot> shots = new Vector<>();

    public void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        this.enemyTanks = enemyTanks;
    }

    //编写方法是否和 enemyTanks中的其他
    public boolean isTouchEnemyTank(){
        switch (this.getDirect()){
            case 0:
                for(int i=0; i<enemyTanks.size(); i++){
                    //从vector中取出一个敌人坦克
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //不和自己进行比较
                    if(enemyTank != this){
                        if(enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2 ){
                            //...
                            if(this.getX() >= enemyTank.getX()
                                    && this.getX()<= enemyTank.getX()+40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY()+60){
                                return true;

                            }
                        }
                        if(enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3 ){

                        }
                    }
                }
                break;
        }
        return false;
    }

    Vector<EnemyTank> enemyTanks = new Vector<>();
    boolean isLive = true;
    public EnemyTank(int x, int y) {
        super(x, y);
    }

    //敌人坦克自由移动方法
    @Override
    public void run() {
        while (true){

            if(isLive && shots.size() < 5){

                Shot s = null;
                //判断坦克方向，创建对应的子弹
                switch (getDirect()){
                    case 0:
                        s = new Shot(getX() + 20, getY(), 0);
                        break;
                    case 1:
                        s = new Shot(getX()+60, getY()+20,1);
                        break;
                    case 2:
                        s = new Shot(getX()+20, getY()+60,2);
                        break;
                    case 3:
                        s = new Shot(getX(), getY()+20,3);
                        break;
                }

                shots.add(s);
                new Thread(s).start();
            }

            //根据坦克方向来继续移动
            switch(getDirect()){
                case 0:
                    for(int i =0; i<50; i++){
                        if(getY()>1){
                            moveUp();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 1:
                    for(int i =0; i<50; i++){
                        if(getX() +60<1000){
                            moveRight();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 2:
                    for(int i =0; i<50; i++){
                        if(getY()+60 <750){
                            moveDown();
                        }

                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 3:
                    for(int i =0; i<50; i++){
                        if(getX() > 0){
                            moveLeft();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
            }

            // 随机改变方向---存在一些问题
            setDirect((int) (Math.random() * 4));


            if(!isLive){
                break; //退出线程
            }

        }

    }
}
