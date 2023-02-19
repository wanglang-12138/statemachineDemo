package com.example.statemachinedemo.model;

public enum OrderStatus {

    开始("开始"),
    待付款("待付款"),
    待商家确认("待商家确认"),
    订单关闭("订单关闭"),
    制作中("制作中"),
    制作完成("制作完成"),
    Choice("Choice"),
    待制作("待制作"),
    待配送("待配送"),
    红烧肉待制作("红烧肉待制作"),
    红烧肉完成("红烧肉完成"),
    清蒸鱼待制作("清蒸鱼待制作"),
    清蒸鱼完成("清蒸鱼完成"),
    结束("结束"),
    退款("退款"),
    待退款("待退款"),
    退款中("退款中"),
    退款成功("退款成功");


    String description;


    public String getDescription() {
        return description;
    }

    OrderStatus(String description) {
        this.description = description;
    }

}
