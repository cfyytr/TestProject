package com.company;

import java.util.ArrayList;

public interface ChainConversion {

    public void addChain(Node node);

    public boolean isInclude(String typeName1, String typeName2);

    public double calculate(Node node);

    public boolean isExist(String typeName1, String typeName2);

    public int size();
}
