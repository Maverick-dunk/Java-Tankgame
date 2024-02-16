package com.hsp.tankgame;

public class Tank {
    private int x;
    private int y;


    private int speed =1; //速度
    boolean islive = true;

    private int direct; //坦克方向 0上 1右，2下，3左

    public void moveUp(){
        y-=speed;
    }
    public void moveRight(){
        x+=speed;
    }
    public void moveDown(){
        y+=speed;
    }
    public void moveLeft(){
        x-=speed;
    }

    public Tank(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }
    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }


}
