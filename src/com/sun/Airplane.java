package com.sun;

import java.awt.image.BufferedImage;

public class Airplane extends FlyingObject implements Score {
    private static final BufferedImage[] images;

    static {
        images = new BufferedImage[5];
        images[0] = FlyingObject.readImage("images/airplane.png");
        for (int i = 1; i < images.length; i++) {
            images[i] = FlyingObject.readImage("images/boom" + i + ".png");
        }
    }

    private final int speed;

    public Airplane() {
        super(48, 50);
        speed = 1;
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
        return 10;
    }

}
