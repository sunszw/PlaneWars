package com.study;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Sky extends FlyingObject {
    private static BufferedImage image;

    static {
        image = FlyingObject.readImage("images/background.png");
    }

    private int y1;
    private int speed;

    public Sky() {
        super(World.WIDTH, World.HEIGHT, 0, 0);
        speed = 5;
        y1 = -World.HEIGHT;
    }

    @Override
    public void step() {
        y += speed;
        y1 += speed;
        if (y >= World.HEIGHT) {
            y = -World.HEIGHT;
        }

        if (y1 >= World.HEIGHT) {
            y1 = -World.HEIGHT;
        }
    }

    @Override
    public BufferedImage getImage() {
        return image;
    }

    //重写父类中的绘制方法，因为天空需要绘制两份
    @Override
    public void paintObject(Graphics g) {
        g.drawImage(getImage(), x, y, null);
        g.drawImage(getImage(), x, y1, null);
    }

}
