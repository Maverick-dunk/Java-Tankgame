package com.hsp.tankgame;

import java.io.*;
import java.util.Vector;

/**
 * 记录相关信息，和文件进行交互
 */
public class Recorder {

    // 记录击毁的坦克数量
    private static int allEnemyTankNum = 0;
    //定义IO对象，准备写数据到文件中去
    private static FileWriter fw = null;
    private static BufferedWriter bw = null;

    private static BufferedReader br = null;
    private static String recordFile = "e:\\myRecord.txt";

    private static Vector<EnemyTank> enemyTanks = null;

    private static Vector<Node> nodes = new Vector<>();

    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }
    // 读取文件恢复信息
    public static Vector<Node> getNodesAndEnemyTankRec(){
        try {
            br = new BufferedReader(new FileReader(recordFile));
            allEnemyTankNum = Integer.parseInt(br.readLine()) ;
            String line = "";
            while ((line = br.readLine())!= null){
                String[] xyd = line.split(" ");
                Node node = new Node(Integer.parseInt(xyd[0]), Integer.parseInt(xyd[1]), Integer.parseInt(xyd[2]));
                nodes.add(node);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            if(br!= null){
                try {
                    br.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return nodes;

    }



    public static int getAllEnemyTankNum() {
        return allEnemyTankNum;
    }

    public static void setAllEnemyTankNum(int allEnemyTankNum) {
        Recorder.allEnemyTankNum = allEnemyTankNum;
    }

    //增加一个方法，保存
    public static void keepRecord()  {
        try {
            bw = new BufferedWriter(new FileWriter(recordFile));
            bw.write(allEnemyTankNum + "\r\n");
            for(int i =0; i<enemyTanks.size(); i++){
                //取出敌人坦克
                EnemyTank enemyTank = enemyTanks.get(i);
                if(enemyTank.isLive){ //建议判断一下
                    //保存该enemyTank的信息
                    String record = enemyTank.getX() + " " +enemyTank.getY() + " " +enemyTank.getDirect();
                    bw.write(record +"\r\n");
                }
            }

        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(bw != null){
                try {
                    bw.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

    public static  void addAllEnemyTankNum(){
        Recorder.allEnemyTankNum++;
    }
}
