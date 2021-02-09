package com.study.designPattern.decoratorPattern;

public class CoffeeDecorator extends BitterCoffee {
    /**
     * 持有一个咖啡对象
     */
    private final Coffee coffee;

    public CoffeeDecorator(Coffee coffee) {
        this.coffee = coffee;
    }

    @Override
    public void printMaterial() {
        coffee.printMaterial();
        addSugar();
    }

    public void addSugar(){
        System.out.println("加糖");
    }

}
