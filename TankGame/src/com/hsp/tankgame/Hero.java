package com.hsp.tankgame;

import java.util.Vector;

public class Hero extends Tank{
    Shot shot = null;
    Vector<Shot> shots = new Vector<>();
    public Hero(int x, int y){
        super(x,y);
    }

    public void shotEnemyTank(){

        if(shots.size()  == 100){  //控制面板上的子弹最多为100颗
            return;
        }

        //创建shot对象
        switch (getDirect()){
            case 0:
                 shot = new Shot(getX()+20, getY(),0);
                 break;
            case 1:
                shot = new Shot(getX()+60, getY()+20,1);
                break;
            case 2:
                shot = new Shot(getX()+20, getY()+60,2);
                break;
            case 3:
                shot = new Shot(getX(), getY()+20,3);
                break;

        }
        shots.add(shot);
        new Thread(shot).start(); //起东西拿好层
    }
}
