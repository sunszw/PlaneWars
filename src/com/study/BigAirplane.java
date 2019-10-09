package com.study;

import java.awt.image.BufferedImage;

public class BigAirplane extends FlyingObject implements Score {
    private static BufferedImage[] images;

    static {
        images = new BufferedImage[5];
        images[0] = FlyingObject.readImage("images/bigairplane.png");
        for (int i = 1; i < images.length; i++) {
            images[i] = FlyingObject.readImage("images/boom" + i + ".png");
        }
    }

    private int speed;

    public BigAirplane() {
        super(66, 89);
        speed = 2;
    }

    @Override
    public void step() {
        y += speed;
    }

    int index = 0;

    @Override
    public BufferedImage getImage() {
        if (isLive()) {
            return images[0];
        } else if (isDead()) {
            index++;
            if (index == images.length - 1) {
                state = REMOVE;
            }
            return images[index];
        }
        return null;
    }

    @Override
    public int getScore() {
        return 20;
    }
}
