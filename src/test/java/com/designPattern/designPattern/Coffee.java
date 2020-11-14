package com.designPattern.designPattern;

import com.designPattern.decoratorPattern.BitterCoffee;
import com.designPattern.decoratorPattern.CoffeeDecorator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Coffee {

    @Test
    public void addSugerIntoCoffee (){
        BitterCoffee coffee = new BitterCoffee();// 点了一杯苦咖啡
        coffee = new CoffeeDecorator(coffee); // 给咖啡加了点糖
        coffee.printMaterial(); // 糖 咖啡
    }
}
