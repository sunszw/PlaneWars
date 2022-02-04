package com.sun;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.*;

public class World extends JPanel {

    private static final long serialVersionUID = 1L;
    public static final int WIDTH = 420;
    public static final int HEIGHT = 700;
    public static final int START = 0;
    public static final int RUNNING = 1;
    public static final int PAUSE = 2;
    public static final int GAME_OVER = 3;
    private int state = START;

    private static final BufferedImage start;
    private static final BufferedImage pause;
    private static final BufferedImage gameOver;

    static {
        start = FlyingObject.readImage("images/start.png");
        pause = FlyingObject.readImage("images/pause.png");
        gameOver = FlyingObject.readImage("images/gameover.png");
    }

    Hero hero = new Hero();
    Sky sky = new Sky();

    final Collection<FlyingObject> enemies = new ArrayList<>();
    final Collection<Bullet> bullets = new ArrayList<>();

    private int score;

    //游戏开始方法
    public void start() {
        //创建一个监听对象
        MouseAdapter mad = new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                //如果游戏在运行状态执行,达到暂停、游戏结束的效果
                if (state == RUNNING) {
                    hero.moveTo(x, y);
                }
            }

            //监听鼠标点击事件
            @Override
            public void mouseClicked(MouseEvent e) {
                switch (state) {
                    case START:
                        state = RUNNING;
                        break;
                    case GAME_OVER:
                        state = START;
                        //重置游戏窗口
                        score = 0;
                        hero = new Hero();
                        sky = new Sky();
                        enemies.clear();
                        bullets.clear();
                        break;
                }
            }

            //监听鼠标移出事件
            @Override
            public void mouseExited(MouseEvent e) {
                if (state == RUNNING) {
                    state = PAUSE;
                }
            }

            //监听鼠标移入事件
            @Override
            public void mouseEntered(MouseEvent e) {
                if (state == PAUSE) {
                    state = RUNNING;
                }
            }
        };
        //监听器注册窗口事件
        this.addMouseListener(mad);
        this.addMouseMotionListener(mad);

        //定时任务
        int inerval = 10;
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                //如果游戏在运行状态执行,达到暂停、游戏结束的效果
                if (state == RUNNING) {
                    enterAction();
                    stepAction();
                    shootAction();
                    bulletHitAction();
                    heroHitAction();
                    outOfBoundsAction();
                    checkGameAction();
                }
                repaint();//发生变化重新绘制
            }
        };
        timer.schedule(task, 0, inerval);
    }

    //随机生成敌机
    public FlyingObject nextEnemy() {
        FlyingObject flys;
        Random ran = new Random();
        int num = ran.nextInt(100);
        if (num < 20) {
            flys = new Bee();
        } else if (num < 60) {
            flys = new BigAirplane();
        } else {
            flys = new Airplane();
        }
        return flys;
    }

    int enter = 0;

    //飞行物移动
    public void stepAction() {
        sky.step();
        for (FlyingObject fo : enemies) {
            fo.step();
        }
        for (Bullet bs : bullets) {
            bs.step();
        }
    }

    //敌机进场
    public void enterAction() {
        enter++;
        if (enter % 20 == 0) {
            synchronized (enemies) {
                FlyingObject fly = nextEnemy();
                enemies.add(fly);
            }
        }
    }

    int shoot = 0;

    //射击的方法
    public void shootAction() {
        shoot++;
        if (shoot % 10 == 0) {
            synchronized (bullets) {
                bullets.add(hero.shoot());
            }
        }
    }

    //子弹与敌机碰撞监测
    public void bulletHitAction() {
        for (Bullet bt : bullets) {
            for (FlyingObject fo : enemies) {
                if (bt.isLive() && fo.isLive() && fo.hit(bt)) {
                    bt.dead();
                    fo.dead();
                    if (fo instanceof Score) {
                        score += ((Score) fo).getScore();
                    }
                    if (fo instanceof Award) {
                        int type = ((Award) fo).getAwardType();
                        switch (type) {
                            case Award.DOUBLE_FIRE:
                                hero.addDoubleFire();
                                break;
                            case Award.LIFE:
                                hero.addLife();
                                break;
                        }
                    }
                }
            }
        }
    }

    //英雄机与敌机碰撞监测
    public void heroHitAction() {
        for (FlyingObject fo : enemies) {
            if (fo.isLive() && hero.isLive() && fo.hit(hero)) {
                fo.dead();
                hero.subLife();
                hero.subDoubleFire();
            }
        }
    }

    //删除越界对象
    public void outOfBoundsAction() {
        //遍历集合，删除越界的对象
        synchronized (enemies) {
            enemies.removeIf(fo -> fo.outOfBounds() && fo.isRemove());
        }
        synchronized (bullets) {
            bullets.removeIf(b -> b.outOfBounds() && b.isRemove());
        }
    }

    //监测游戏是否结束
    public void checkGameAction() {
        if (hero.getLife() <= 0) {
            state = GAME_OVER;
        }
    }

    //触发所有绘制内容，绘制窗口。窗口运行时会自动调用此方法，因此方法名要一致
    public void paint(Graphics g) {
        //注意绘制有顺序，先绘制的在底层，后绘制的在上层
        sky.paintObject(g);
        hero.paintObject(g);
        for (FlyingObject fo : enemies) {
            fo.paintObject(g);
        }
        for (Bullet b : bullets) {
            b.paintObject(g);
        }

        //将分数显示在窗口中
        g.drawString("SCORE: " + score, 10, 30);
        //将生命值显示在窗口中
        g.drawString("LIFE: " + hero.getLife(), 10, 50);

        //根据状态绘制状态图片
        switch (state) {
            case START:
                g.drawImage(start, 0, 0, null);
                break;
            case PAUSE:
                g.drawImage(pause, 0, 0, null);
                break;
            case GAME_OVER:
                g.drawImage(gameOver, 0, 0, null);
                break;
        }
    }



}
