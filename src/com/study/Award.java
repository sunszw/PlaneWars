package com.study;

//奖励接口
public interface Award {

    public static final int DOUBLE_FIRE = 0;
    public static final int LIFE = 1;

    public abstract int getAwardType();

}
