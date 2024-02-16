package com.hsp.tankgame;

/**
 *
 */
public class Boom {
    int x, y;
    int life = 15;

    boolean isLive = true;


    public Boom(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //减少炸弹生命值
    public void lifeDown(){
        if(life > 0){
            life--;
        }else{
            isLive = false;
        }
    }
}
