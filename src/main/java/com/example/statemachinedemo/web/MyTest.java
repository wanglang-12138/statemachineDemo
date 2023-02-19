package com.example.statemachinedemo.web;

import com.example.statemachinedemo.model.OrderEvents;
import com.example.statemachinedemo.model.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MyTest {


    @Autowired
    private StateMachineFactory<OrderStatus, OrderEvents> factory;

    private Map<String, StateMachine> stringStateMachineMap = new HashMap<>();

    public StateMachine<OrderStatus, OrderEvents> method(String name) {
        if(stringStateMachineMap.containsKey(name)) {
            return stringStateMachineMap.get(name);
        }

        StateMachine<OrderStatus, OrderEvents> stateMachine = factory.getStateMachine(name);
        stateMachine.start();
        stringStateMachineMap.put(name, stateMachine);
        return stateMachine;
    }

    @GetMapping("/点单")
    private String start(@RequestParam String name) {
        StateMachine<OrderStatus, OrderEvents> stateMachine = method(name);
        boolean b = stateMachine.sendEvent(OrderEvents.点单);
        if(b) {
            return "用户:" + name + "点单成功,当前状态：" + stateMachine.getState().getId();
        } else {
            return "用户:" + name + "点单失败,当前状态：" + stateMachine.getState().getId();
        }

    }

    @GetMapping("/付款")
    private String giveMoney(@RequestParam String name) {
        StateMachine<OrderStatus, OrderEvents> stateMachine = method(name);

        boolean b = stateMachine.sendEvent(OrderEvents.付款);

        if(b) {
            return "用户:" + name + "付款成功,当前状态：" + stateMachine.getState().getId();
        } else {
            return "用户:" + name + "付款失败,当前状态：" + stateMachine.getState().getId();
        }

    }

    @GetMapping("/商家确认订单")
    private String accept(@RequestParam String name) {
        StateMachine<OrderStatus, OrderEvents> stateMachine = method(name);

        boolean b = stateMachine.sendEvent(OrderEvents.商家接单);

        if(b) {
            return "商家确认订单成功,当前状态：" + stateMachine.getState().getId();
        } else {
            return "商家确认订单失败,当前状态：" + stateMachine.getState().getId();
        }

    }

    @GetMapping("/商家拒绝接单")
    private String refuse(@RequestParam String name) {
        StateMachine<OrderStatus, OrderEvents> stateMachine = method(name);

        boolean b = stateMachine.sendEvent(OrderEvents.商家拒绝接单);

        if(b) {
            return "商家拒绝接单成功,当前状态：" + stateMachine.getState().getId();
        } else {
            return "商家拒绝接单失败,当前状态：" + stateMachine.getState().getId();
        }

    }

    @GetMapping("/商家退款")
    private String refund(@RequestParam String name) {
        StateMachine<OrderStatus, OrderEvents> stateMachine = method(name);

        boolean b = stateMachine.sendEvent(OrderEvents.商家退款);

        if(b) {
            return "商家退款成功,当前状态：" + stateMachine.getState().getId();
        } else {
            return "商家退款失败,当前状态：" + stateMachine.getState().getId();
        }

    }

    @GetMapping("/厨师制作肉")
    private String makeA(@RequestParam String name) {
        StateMachine<OrderStatus, OrderEvents> stateMachine = method(name);

        boolean b = stateMachine.sendEvent(OrderEvents.红烧肉制作);

        if(b) {
            return "厨师制作肉成功,当前状态：" + stateMachine.getState().getId();
        } else {
            return "厨师制作肉失败,当前状态：" + stateMachine.getState().getId();
        }

    }

    @GetMapping("/厨师制作鱼")
    private String makeB(@RequestParam String name) {
        StateMachine<OrderStatus, OrderEvents> stateMachine = method(name);

        boolean b = stateMachine.sendEvent(OrderEvents.清蒸鱼制作);

        if(b) {
            return "厨师制作鱼成功,当前状态：" + stateMachine.getState().getId();
        } else {
            return "厨师制作鱼失败,当前状态：" + stateMachine.getState().getId();
        }

    }

    @GetMapping("/骑手配送完成")
    private String delivery(@RequestParam String name) {
        StateMachine<OrderStatus, OrderEvents> stateMachine = method(name);

        boolean b = stateMachine.sendEvent(OrderEvents.骑手配送完成);

        if(b) {
            return "骑手配送完成成功,当前状态：" + stateMachine.getState().getId();
        } else {
            return "骑手配送完成失败,当前状态：" + stateMachine.getState().getId();
        }

    }




    @GetMapping("/获取当前状态")
    private String getInfo(@RequestParam String name) {
        StateMachine<OrderStatus, OrderEvents> stateMachine = method(name);

        return "最终状态：" + stateMachine.getState().getId();
    }

}
