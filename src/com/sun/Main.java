package com.sun;

import javax.swing.*;

public class Main extends World {
    public static void main(String[] args) {
        World world = new World();
        ///创建窗口对象
        JFrame jFrame = new JFrame("Flying");
        //将World对象放入窗口
        jFrame.add(world);
        //设置窗口宽高
        jFrame.setSize(World.WIDTH, World.HEIGHT);
        //设置窗口关闭时结束程序
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置窗口初始位置(居中)
        jFrame.setLocationRelativeTo(null);
        //设置窗口显示
        jFrame.setVisible(true);
        //启动程序
        world.start();
    }
}
