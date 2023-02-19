package com.example.statemachinedemo.model;

public enum OrderEvents {

    点单("点单"),
    付款("付款"),
    商家接单("待商家确认"),
    商家拒绝接单("订单关闭"),
    红烧肉制作("红烧肉制作"),
    清蒸鱼制作("清蒸鱼制作"),
    骑手配送完成("骑手配送完成"),
    商家退款("商家退款");


    String description;


    public String getDescription() {
        return description;
    }

    OrderEvents(String description) {
        this.description = description;
    }

}
