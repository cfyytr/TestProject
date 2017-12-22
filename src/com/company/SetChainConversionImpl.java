package com.company;

import java.util.ArrayList;

public class SetChainConversionImpl implements SetChainConversion {

    private ArrayList<ChainConversion> chainConversions = new ArrayList<>();


    @Override
    public boolean addConversion(Node node) {
        String typeName1 = node.getTypeName1();
        String typeName2 = node.getTypeName2();
        if (typeName1.equals(typeName2))
            return false;
        int indexChain = -1;
        for (int i = 0; i < chainConversions.size(); i++) {
            ChainConversion chain = chainConversions.get(i);
            if (chain.isExist(typeName1, typeName2))
                return false;
            if (chain.isInclude(typeName1, typeName2))
                if (indexChain == -1) {
                    chain.addChain(node);
                    indexChain = i;
                } else {
                    mergeChainConversions(i, indexChain);
                }
        }
        if (indexChain == -1) {
            chainConversions.add(new ChainConversionImpl(node));
        }
        return true;
    }


    private void mergeChainConversions(int index1, int index2) {
        ChainConversion conversion1 = chainConversions.get(index1);
        ChainConversion conversion2 = chainConversions.get(index2);
        if (conversion1.size() < conversion2.size()) {
            ChainConversion temp = conversion1;
            conversion1 = conversion2;
            conversion2 = temp;
            chainConversions.remove(index1);
        } else chainConversions.remove(index2);
        ((ChainConversionImpl) conversion1).addAllChain((ChainConversionImpl) conversion2);
    }


    @Override
    public double calculate(Node node) {
        for (int i = 0; i < chainConversions.size(); i++) {
            ChainConversion conversion = chainConversions.get(i);
            if (conversion.isExist(node.getTypeName1(), node.getTypeName2()))
                return conversion.calculate(node);
        }
        return 0;
    }

    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < chainConversions.size(); i++) {
            s += chainConversions.get(i).toString() + "\n";
        }
        return s;
    }

    @Override
    public boolean isExist(Node node) {
        for (int i = 0; i < chainConversions.size(); i++) {
            if (chainConversions.get(i).isExist(node.getTypeName1(), node.getTypeName2()))
                return true;
        }
        return false;
    }
}
