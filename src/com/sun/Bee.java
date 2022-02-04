package com.sun;

import java.awt.image.BufferedImage;

public class Bee extends FlyingObject implements Award {
    private static final BufferedImage[] images;

    static {
        images = new BufferedImage[5];
        images[0] = FlyingObject.readImage("images/bee.png");
        for (int i = 1; i < images.length; i++) {
            images[i] = FlyingObject.readImage("images/boom" + i + ".png");
        }
    }

    private int xSpeed;
    private final int ySpeed;
    private final int awardType;//0奖励火力值，1奖励生命值

    public Bee() {
        super(60, 51);
        xSpeed = 4;
        ySpeed = 3;
        awardType = (int) (Math.floor(Math.random() * 2));
    }

    @Override
    public void step() {
        y += ySpeed;
        x += xSpeed;
        if (x <= 0 || x >= World.WIDTH - width) {
            xSpeed *= -1;
        }
    }

    @Override
    public int getAwardType() {
        return awardType;
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

}
