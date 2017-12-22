package com.company;

public class NodeImpl implements Node {

    private String typeName1;
    private String typeName2;
    private double multiplier;

    public NodeImpl(String type1, String type2, double number1, double number2) {
        if (number1 > number2) {
            typeName1 = type2;
            typeName2 = type1;
            multiplier = number1 / number2;
        } else {
            typeName1 = type1;
            typeName2 = type2;
            multiplier = number2 / number1;
        }

    }

    @Override
    public String getTypeName1() {
        return typeName1;
    }

    @Override
    public String getTypeName2() {
        return typeName2;
    }

    @Override
    public double getMultiplier() {
        return multiplier;
    }

    @Override
    public void setTypeName1(String typeName1) {
        this.typeName1 = typeName1;
    }

    @Override
    public void setTypeName2(String typeName2) {
        this.typeName2 = typeName2;
    }

    @Override
    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }

    @Override
    public String toString() {
        return "1 " + typeName1 + " = " + multiplier + " " + typeName2;
    }
}
