package com.study;

import java.awt.image.BufferedImage;

public class Bullet extends FlyingObject {
    private static BufferedImage image;

    static {
        image = FlyingObject.readImage("images/bullet.png");
    }

    private int speed;

    public Bullet(int x, int y) {
        super(8, 20, x, y);
        speed = 6;
    }

    @Override
    public void step() {
        y -= speed;
    }

    @Override
    public BufferedImage getImage() {
        if (isLive()) {
            return image;
        } else if (isDead()) {
            state = REMOVE;
        }
        return null;
    }

    @Override
    public boolean outOfBounds() {

        return this.y < -this.height;
    }

}
