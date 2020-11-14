package com.designPattern.decoratorPattern;

import org.springframework.stereotype.Component;

@Component
public class BitterCoffee implements Coffee{

    @Override
    public void printMaterial() {
        System.out.println("咖啡");
    }
}
