package com.example.statemachinedemo.config;

import com.example.statemachinedemo.model.OrderEvents;
import com.example.statemachinedemo.model.OrderStatus;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.monitor.AbstractStateMachineMonitor;
import org.springframework.statemachine.transition.Transition;

public class TestStateMachineMonitor extends AbstractStateMachineMonitor<OrderStatus, OrderEvents> {

	@Override
	public void transition(StateMachine<OrderStatus, OrderEvents> stateMachine, Transition<OrderStatus, OrderEvents> transition,
						   long duration) {
		if(transition.getSource() == null) {
			return;
		}
		System.out.println("状态机状态转换监控：状态机发生状态转换,由状态：" + transition.getSource().getId() + " ——> 状态：" + transition.getTarget().getId());
	}

	@Override
	public void action(StateMachine<OrderStatus, OrderEvents> stateMachine,
					   Action<OrderStatus, OrderEvents> action, long duration) {
//		System.out.println("状态机动作监控");
	}
}