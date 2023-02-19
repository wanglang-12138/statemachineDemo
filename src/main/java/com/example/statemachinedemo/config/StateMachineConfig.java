package com.example.statemachinedemo.config;

import com.example.statemachinedemo.action.*;
import com.example.statemachinedemo.model.OrderEvents;
import com.example.statemachinedemo.model.OrderStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;
import org.springframework.statemachine.monitor.StateMachineMonitor;

@Configuration
@EnableStateMachineFactory
public class StateMachineConfig extends StateMachineConfigurerAdapter<OrderStatus, OrderEvents> {

	@Override
	public void configure(StateMachineConfigurationConfigurer<OrderStatus, OrderEvents> config)
			throws Exception {
		config
				.withMonitoring()
				.monitor(stateMachineMonitor());
	}

	@Override
	public void configure(StateMachineStateConfigurer<OrderStatus, OrderEvents> states)
			throws Exception {
		states
				.withStates()
				.initial(OrderStatus.开始)
				.state(OrderStatus.待付款)
				.choice(OrderStatus.Choice)
				.state(OrderStatus.待商家确认)
				.fork(OrderStatus.待制作)
				.state(OrderStatus.制作中)
				.join(OrderStatus.制作完成)
				.state(OrderStatus.待配送)
				.state(OrderStatus.退款)
				.state(OrderStatus.订单关闭)
				.state(OrderStatus.退款)
				.end(OrderStatus.结束)
				.and()
				.withStates()
					.parent(OrderStatus.制作中)
					.initial(OrderStatus.清蒸鱼待制作)
					.end(OrderStatus.清蒸鱼完成)
					.and()
				.withStates()
					.parent(OrderStatus.制作中)
					.initial(OrderStatus.红烧肉待制作)
					.end(OrderStatus.红烧肉完成)
					.and()
				.withStates()
					.parent(OrderStatus.退款)
					.initial(OrderStatus.待退款)
					.choice(OrderStatus.退款中)
					.end(OrderStatus.退款成功);


	}

	@Override
	public void configure(StateMachineTransitionConfigurer<OrderStatus, OrderEvents> transitions)
			throws Exception {
		transitions
				.withExternal()
					.source(OrderStatus.开始)
					.target(OrderStatus.待付款)
					.event(OrderEvents.点单)
					.action(orderAction())
					.and()
				.withExternal()
					.source(OrderStatus.待付款)
					.target(OrderStatus.Choice)
					.event(OrderEvents.付款)
					.action(payAction())
					.and()
				.withExternal()
					.source(OrderStatus.待付款)
					.target(OrderStatus.订单关闭)
					.timerOnce(60000)
					.action(timeOutAction())
					.and()
				.withChoice()
					.source(OrderStatus.Choice)
					.first(OrderStatus.待制作,needAgree(),agreeAction())
					.last(OrderStatus.待商家确认)
					.and()
				.withExternal()
					.source(OrderStatus.待商家确认)
					.target(OrderStatus.待制作)
					.event(OrderEvents.商家接单)
					.action(agreeAction())
					.and()
				.withInternal()
					.source(OrderStatus.待商家确认)
					.action(timerAction())
					.timer(5000)
					.and()
				.withFork()
					.source(OrderStatus.待制作)
					.target(OrderStatus.制作中)
					.and()
				.withExternal()
					.source(OrderStatus.红烧肉待制作)
					.target(OrderStatus.红烧肉完成)
					.event(OrderEvents.红烧肉制作)
					.action(meatAction())
					.and()
				.withExternal()
					.source(OrderStatus.清蒸鱼待制作)
					.target(OrderStatus.清蒸鱼完成)
					.event(OrderEvents.清蒸鱼制作)
					.action(fishAction())
					.and()
				.withJoin()
					.source(OrderStatus.制作中)
					.target(OrderStatus.制作完成)
					.and()
				.withExternal()
					.source(OrderStatus.制作完成)
					.target(OrderStatus.待配送)
					.and()
				.withExternal()
					.source(OrderStatus.待配送)
					.target(OrderStatus.结束)
					.event(OrderEvents.骑手配送完成)
					.action(orderFinishAction())
					.and()
				.withExternal()
					.source(OrderStatus.待商家确认)
					.target(OrderStatus.待退款)
					.event(OrderEvents.商家拒绝接单)
					.action(refuseAction())
					.and()
				.withExternal()
					.source(OrderStatus.待退款)
					.target(OrderStatus.退款中)
					.event(OrderEvents.商家退款)
					.and()
				.withChoice()
					.source(OrderStatus.退款中)
					.first(OrderStatus.退款成功, refundAccess(),refundAccessAction())
					.last(OrderStatus.待退款,refundFailAction())
					.and()
				.withExternal()
					.source(OrderStatus.退款成功)
					.target(OrderStatus.订单关闭)
					.and()
				.withExternal()
					.source(OrderStatus.订单关闭)
					.target(OrderStatus.结束)
					.timerOnce(10000);

	}

	@Bean
	public Guard<OrderStatus, OrderEvents> needAgree() {
		return new Guard<OrderStatus, OrderEvents>() {

			@Override
			public boolean evaluate(StateContext<OrderStatus, OrderEvents> context) {
				if(context.getStateMachine().getId().equals("A")) {
					return false;
				} else {
					return true;
				}
			}
		};
	}

	@Bean
	public Guard<OrderStatus, OrderEvents> refundAccess() {
		final int[] i = new int[]{0};
		return new Guard<OrderStatus, OrderEvents>() {

			@Override
			public boolean evaluate(StateContext<OrderStatus, OrderEvents> context) {
				if(i[0] == 0) {
					i[0]++;
					return false;
				}
				return true;
			}
		};
	}

	@Bean
	public OrderAction orderAction() {
		return new OrderAction();
	}

	@Bean
	public TimerAction timerAction() {
		return new TimerAction();
	}

	@Bean
	public PayAction payAction() {
		return new PayAction();
	}

	@Bean
	public TimeOutAction timeOutAction() {
		return new TimeOutAction();
	}

	@Bean
	public AgreeAction agreeAction() {
		return new AgreeAction();
	}

	@Bean
	public OrderFinishAction orderFinishAction() {
		return new OrderFinishAction();
	}

	@Bean
	public RefundFailAction refundFailAction() {
		return new RefundFailAction();
	}

	@Bean
	public MeatAction meatAction() {
		return new MeatAction();
	}

	@Bean
	public FishAction fishAction() {
		return new FishAction();
	}

	@Bean
	public RefuseAction refuseAction() {
		return new RefuseAction();
	}

	@Bean
	public RefundAccessAction refundAccessAction() {
		return new RefundAccessAction();
	}

	@Bean
	public StateMachineMonitor<OrderStatus, OrderEvents> stateMachineMonitor() {
		return new TestStateMachineMonitor();
	}
}