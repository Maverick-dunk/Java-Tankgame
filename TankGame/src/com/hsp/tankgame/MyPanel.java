package com.hsp.tankgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.InterruptedIOException;
import java.util.Vector;

/*
java事件处理采用的是委派事件模型，当事件发生时

为了让Panel不停重回子弹
 */

public class MyPanel extends JPanel implements KeyListener, Runnable {
    //
    Hero hero = null;
    Vector<EnemyTank> enemyTanks =new Vector<>();
    Vector<Node> nodes = new Vector<>();

    Vector<Boom> booms = new Vector<>();
    int enemTankSize = 3; // 修改敌人坦克数量

    //定义三张炸弹图片
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;
    Image image4 = null;

    public MyPanel(String key){

        nodes = Recorder.getNodesAndEnemyTankRec();

        Recorder.setEnemyTanks(enemyTanks); //将MyPanel的enemyTanks设置给Recorder的enemTanks
        hero = new Hero(500,100);  //初始化自己的坦克
        hero.setSpeed(3);

        switch(key){
            case "1":
                //初始化敌人的坦克J
                for(int i =0; i<enemTankSize; i++){
                    EnemyTank enemyTank = new EnemyTank(100 * (i + 1), 0);
                    enemyTank.setDirect(2);
                    //给敌人加入一颗子弹
                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());
                    //加入enemyTank的Vector中去
                    enemyTank.shots.add(shot);
                    new Thread(shot).start();
                    new Thread(enemyTank).start();
                    enemyTanks.add(enemyTank);
                }

                break;
            case "2":
                for(int i =0; i<nodes.size(); i++){
                    Node node = nodes.get(i);
                    EnemyTank enemyTank = new EnemyTank(node.getX(), node.getY());
                    enemyTank.setDirect(node.getDirect());
                    //给敌人加入一颗子弹
                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());
                    //加入enemyTank的Vector中去
                    enemyTank.shots.add(shot);
                    new Thread(shot).start();
                    new Thread(enemyTank).start();
                    enemyTanks.add(enemyTank);
                }
                break;
        }


        //初始化图片对象
        image1 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/boom1.png"));
        image2 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/boom2.png"));
        image3 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/boom3.png"));
        image4 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/boom4.png"));

    }

    public void showInfo(Graphics g){
        //画出玩家的总成绩
        g.setColor(Color.BLACK);
        Font font = new Font("宋体",Font.BOLD,25);
        g.setFont(font);
        g.drawString("您累积击毁敌方坦克", 1020, 30);
        drawTank(1020,60,  g,0,0);
        g.setColor(Color.black);
        g.drawString(Recorder.getAllEnemyTankNum()+"",1080,100);
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        g.fillRect(0,0,1000,750); //填充矩形，默认黑色
        showInfo(g);
        if(hero != null && hero.islive){
            drawTank(hero.getX(), hero.getY(),g, hero.getDirect(),0);
        }


//        if(hero.shot != null && hero.shot.islive != false){
//            g.fill3DRect(hero.shot.x, hero.shot.y , 5, 5, false);
//        }

        for(int i =0; i<hero.shots.size(); i++){
            Shot shot = hero.shots.get(i);
            if(shot != null && shot.islive){
                g.fill3DRect(shot.x,shot.y, 5,5,false);
            }else{
                hero.shots.remove(shot);
            }
        }

        //jihe zhong
        for (int i =0; i< booms.size(); i++){
            Boom boom = booms.get(i);
            //根据当前对象的live值
            if(boom.life > 10){
                g.drawImage(image4, boom.x, boom.y,60,60,this);
            } else if (boom.life>7) {
                g.drawImage(image3, boom.x, boom.y,60,60,this);
            } else if (boom.life>2) {
                g.drawImage(image2, boom.x, boom.y,60,60,this);
            }else{
                g.drawImage(image1, boom.x, boom.y,60,60,this);
            }
            boom.lifeDown();
            if(boom.life ==0){
                booms.remove(boom);
            }
        }

        for(int i =0; i<enemyTanks.size(); i++){
            EnemyTank enemyTank = enemyTanks.get(i);
            if(enemyTank.isLive ){ //当敌人坦克还活着的时候
                drawTank(enemyTank.getX(),enemyTank.getY(),g, enemyTank.getDirect(),1);
                //画出坦克子弹
                for(int j =0; j<enemyTank.shots.size(); j++){
                    //取出子弹
                    Shot shot = enemyTank.shots.get(j);
                    //绘制
                    if(shot.islive){
                        g.fill3DRect(shot.x, shot.y , 5, 5, false);
                    }else {
                        enemyTank.shots.remove(shot);
                    }
                }
            }else{

            }

        }

    }


    public void drawTank(int x , int y, Graphics g, int direct, int type){

        switch (type){
            case 0:
                g.setColor(Color.cyan);
                break;
            case 1:
                g.setColor(Color.yellow);
                break;
        }

        //根据坦克方向来绘制坦克

        switch(direct){
            case 0: //表示向上
                g.fill3DRect(x, y, 10, 60, false);
                g.fill3DRect(x + 30, y, 10, 60, false);
                g.fill3DRect(x + 10, y+10, 20, 40, false); // 盖子
                g.fillOval(x + 10, y+20, 20, 20);
                g.drawLine(x+20,y+30, x+20, y);  //画出炮筒
                break;
            case 1: //表示向右
                g.fill3DRect(x, y, 60, 10, false);
                g.fill3DRect(x , y+30, 60, 10, false);
                g.fill3DRect(x + 10, y+10, 40, 20, false); // 盖子
                g.fillOval(x + 20, y+10, 20, 20);
                g.drawLine(x+30,y+20, x+60, y+20);  //画出炮筒
                break;
            case 2: //表示向下
                g.fill3DRect(x, y, 10, 60, false);
                g.fill3DRect(x + 30, y, 10, 60, false);
                g.fill3DRect(x + 10, y+10, 20, 40, false); // 盖子
                g.fillOval(x + 10, y+20, 20, 20);
                g.drawLine(x+20,y+30, x+20, y+60);  //画出炮筒
                break;
            case 3: //表示向左
                g.fill3DRect(x, y, 60, 10, false);
                g.fill3DRect(x , y+30, 60, 10, false);
                g.fill3DRect(x + 10, y+10, 40, 20, false); // 盖子
                g.fillOval(x + 20, y+10, 20, 20);
                g.drawLine(x+30,y+20, x, y+20);  //画出炮筒
                break;

            default:
        }

    }

    //编写方法，判断我方子弹是否击中
    public  void hitTank(Shot s, Tank enemyTank){
        switch(enemyTank.getDirect()){
            case 0: //向上

            case 2: //向下
                if(s.x>enemyTank.getX() && s.x< enemyTank.getX()+40
                && s.y> enemyTank.getY() && s.y < enemyTank.getY() +60){
                    s.islive = false;
                    enemyTank.islive = false;
                    // 被击中之后从列表中删除被集中的坦克
                    enemyTanks.remove(enemyTank);
                    if(enemyTank instanceof EnemyTank){
                        Recorder.addAllEnemyTankNum();
                    }

                    //创建Boom对象
                    Boom boom = new Boom(enemyTank.getX(), enemyTank.getY());
                    booms.add(boom);
                }
                break;
            case 1: //向右

            case 3: //向左
                if(s.x>enemyTank.getX() && s.x< enemyTank.getX()+60
                        && s.y> enemyTank.getY() && s.y < enemyTank.getY() +40) {
                    s.islive = false;
                    enemyTank.islive = false;
                    // 被击中之后从列表中删除被集中的坦克
                    enemyTanks.remove(enemyTank);
                    if(enemyTank instanceof EnemyTank){
                        Recorder.addAllEnemyTankNum();
                    }
                    Boom boom = new Boom(enemyTank.getX(), enemyTank.getY());
                    booms.add(boom);
                }
                break;
        }
    }

    public void hitHero(){
        for(int i =0; i<enemyTanks.size(); i++){
            EnemyTank enemyTank = enemyTanks.get(i);
            //遍历enemyTank
            for(int j =0; j<enemyTank.shots.size(); j++){
                Shot shot = enemyTank.shots.get(j);
                if(hero.islive && shot.islive){
                    hitTank(shot, hero);
                }
            }

        }
    }


    public void hitEnemyTank(){
        for(int j=0; j<hero.shots.size(); j++){
            Shot shot = hero.shots.get(j);
            if(shot != null && shot.islive){
                for(int i =0; i<enemyTanks.size(); i++){
                    EnemyTank enemyTank = enemyTanks.get(i);
                    hitTank(shot,enemyTank);
                }
            }
        }


    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W){
            hero.setDirect(0);
            //修改坦克坐标
            if(hero.getY() > 0){
                hero.moveUp();
            }

        }else if(e.getKeyCode() == KeyEvent.VK_D){
            hero.setDirect(1);
            if(hero.getX() +60 <1000){
                hero.moveRight();
            }

        }else if(e.getKeyCode() == KeyEvent.VK_S){
            hero.setDirect(2);
            if(hero.getY()+60 < 750){
                hero.moveDown();
            }

        }else if(e.getKeyCode() == KeyEvent.VK_A){
            hero.setDirect(3);
            if(hero.getX()>0){
                hero.moveLeft();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_J){

            hero.shotEnemyTank();

        }
        //面板重绘
        this.repaint();

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


    // t2线程不停重绘面板 更新子弹效果
    @Override
    public void run() {
        while(true){
            try{
                Thread.sleep(50);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

            // 判断是否击中了敌人的坦克
            hitEnemyTank();
            //判断敌人坦克是否集中我们
            hitHero();
            this.repaint();
        }



    }
}
