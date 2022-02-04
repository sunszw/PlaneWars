package com.sun;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public abstract class FlyingObject {

    protected int width;
    protected int height;
    protected int x;
    protected int y;

    public int state = LIVE;
    public static final int LIVE = 0;
    public static final int DEAD = 1;
    public static final int REMOVE = 2;

    //小敌机、大敌机、蜜蜂
    public FlyingObject(int width, int height) {
        Random ran = new Random();
        this.width = width;
        this.height = height;
        this.x = ran.nextInt(World.WIDTH - width);
        this.y = -height;
    }

    //英雄机、天空、子弹
    public FlyingObject(int width, int height, int x, int y) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    //飞行物移动方法
    public abstract void step();

    //读取图片
    public static BufferedImage readImage(String fileName) {
        try {
            return ImageIO.read(Objects.requireNonNull(FlyingObject.class.getClassLoader().getResourceAsStream(fileName)));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    //飞行物获取图片(因为飞行物具体获得那张图片无法确定，所以先规定一个抽象方法，再让子类获取图片)
    public abstract BufferedImage getImage();

    //判断是否存活
    public boolean isLive() {
        return state == LIVE;
    }

    //判断是否死亡
    public boolean isDead() {
        return state == DEAD;
    }

    //判断是否离场
    public boolean isRemove() {
        return state == REMOVE;
    }

    //判断飞行物死亡方法
    public void dead() {
        state = DEAD;
    }

    //将图片绘制到窗口中
    public void paintObject(Graphics g) {
        g.drawImage(this.getImage(), x, y, null);
    }

    //检测是否越界方法
    public boolean outOfBounds() {
        return this.y > World.HEIGHT;
    }

    //检测飞行物碰撞方法(当前对象即this表示敌机，目标对象是以参数方式传入，参数是子弹或英雄机)
    public boolean hit(FlyingObject other) {
        int x1 = this.x - other.width;
        int x2 = this.x + this.width;
        int y1 = this.y - other.height;
        int y2 = this.y + this.height;
        int x = other.x;
        int y = other.y;

        return x >= x1 && x <= x2 && y >= y1 && y <= y2;
    }


}
