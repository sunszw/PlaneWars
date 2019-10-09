package com.study;

import java.awt.image.BufferedImage;

public class Hero extends FlyingObject {
    private static BufferedImage[] images;

    static {
        images = new BufferedImage[2];
        images[0] = FlyingObject.readImage("images/hero0.png");
        images[1] = FlyingObject.readImage("images/hero1.png");
    }

    private int life;
    private int doubleFire;

    public Hero() {
        super(100, 139, World.WIDTH / 2 - 50, 450);
        life = 3;
        doubleFire = 0;
    }

    //英雄机射击的方法
    public Bullet shoot() {
        Bullet bs = null;
        int z = this.width / 4;
        if (doubleFire > 0) {
            bs = new Bullet(this.x + z, this.y);
            bs = new Bullet(this.x + z * 3, this.y);
            doubleFire--;
        } else {
            bs = new Bullet(this.x + z * 2, this.y);
        }
        return bs;
    }

    //英雄机增加生命值的方法
    public void addLife() {
        life++;
    }

    //英雄机增加火力值的方法
    public void addDoubleFire() {
        doubleFire += 20;
    }

    //英雄机减少生命值的方法
    public void subLife() {
        life--;
    }

    //英雄机减少火力值的方法
    public void subDoubleFire() {
        if (doubleFire > 10) {
            doubleFire -= 10;
        } else {
            doubleFire = 0;
        }
    }

    //返回英雄机的生命值
    public int getLife() {
        return life;
    }

    //英雄机随鼠标移动的方法
    public void moveTo(int x, int y) {
        //让英雄机x坐标和鼠标同步
        this.x = x - this.width / 2;
        //让英雄机y坐标和鼠标同步
        this.y = y - this.height / 2;
    }

    //英雄机此方法用于切换图片
    @Override
    public void step() {
    }

    int index = 0;

    @Override
    public BufferedImage getImage() {
        index++;
        return images[index % images.length];
    }

}
