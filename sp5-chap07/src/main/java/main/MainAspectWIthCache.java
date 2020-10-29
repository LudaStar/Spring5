package main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import aspect.AppCtxWithCache;
import chap07.Calculator;

public class MainAspectWIthCache {

	public static void main(String[] args) {
		
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppCtxWithCache.class);

		Calculator cal = ctx.getBean("calculator", Calculator.class);
		cal.factorial(7);
		cal.factorial(7);
		cal.factorial(5);
		cal.factorial(5);
		ctx.close();
	}
	

}
