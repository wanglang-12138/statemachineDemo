package com.example.statemachinedemo.action;

import com.example.statemachinedemo.model.OrderEvents;
import com.example.statemachinedemo.model.OrderStatus;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

public class AgreeAction implements Action<OrderStatus, OrderEvents> {

    @Override
    public void execute(StateContext<OrderStatus, OrderEvents> context) {
        // do something in every 1 sec
        System.out.println("用户：" + context.getStateMachine().getId() + " 的订单被商家接受 | " + context.getEvent().getDescription());
    }
}
