package com.company;

import java.util.ArrayList;


public class ChainConversionImpl implements ChainConversion {


    private ArrayList<Node> nodes = new ArrayList<>();
    private ArrayList<String> setNames = new ArrayList<>();


    public ChainConversionImpl(Node node) {
        nodes.add(node);
        setNames.add(node.getTypeName1());
        setNames.add(node.getTypeName2());
    }


    @Override
    public void addChain(Node node) {
        String typeName1 = node.getTypeName1();
        String typeName2 = node.getTypeName2();
        double multiplier = node.getMultiplier();
        if (setNames.contains(typeName1)) {
            int indexType1 = setNames.indexOf(typeName1);
            if (indexType1 == setNames.size() - 1) {
                nodes.add(node);
                setNames.add(typeName2);
                return;
            }
            for (int i = indexType1; i < nodes.size(); i++) {
                node = nodes.get(i);
                multiplier /= node.getMultiplier();
                if (multiplier < 1) {
                    nodes.add(i + 1, new NodeImpl(typeName2, node.getTypeName2(), 1, 1 / multiplier));
                    node.setTypeName2(typeName2);
                    node.setMultiplier(multiplier * node.getMultiplier());
                    setNames.add(i + 1, typeName2);
                    return;
                }
            }
            nodes.add(new NodeImpl(typeName2, node.getTypeName2(), 1, 1 / multiplier));
            setNames.add(typeName2);
        } else {
            int indexType2 = setNames.indexOf(typeName2);
            if (indexType2 == 0) {
                nodes.add(0, node);
                setNames.add(0, typeName1);
                return;
            }
            for (int i = indexType2 - 1; i >= 0; i--) {
                node = nodes.get(i);
                multiplier /= node.getMultiplier();
                if (multiplier < 1) {
                    nodes.add(i + 1, new NodeImpl(typeName1, node.getTypeName2(), 1, multiplier * node.getMultiplier()));
                    node.setMultiplier(1 / multiplier);
                    node.setTypeName2(typeName1);
                    setNames.add(i + 1, typeName1);
                    return;
                }
            }
            nodes.add(0, new NodeImpl(typeName1, node.getTypeName1(), 1, multiplier));
            setNames.add(0, typeName1);
        }
    }


    public void addAllChain(ChainConversionImpl chainConversion) {
        ArrayList<Node> nodes = chainConversion.nodes;
        int index = 0;
        for (int i = 0; i < nodes.size(); i++) {
            if (isInclude(nodes.get(i).getTypeName1(), nodes.get(i).getTypeName1())) {
                index = i;
            }
        }
        for (int i = index; i < nodes.size(); i++) {
            addChain(nodes.get(i));
        }
        for (int i = index - 1; i >= 0; i++) {
            addChain(nodes.get(i));
        }
    }


    @Override
    public boolean isInclude(String typeName1, String typeName2) {
        return !isExist(typeName1, typeName2) && (setNames.contains(typeName1) || setNames.contains(typeName2));
    }

    @Override
    public boolean isExist(String typeName1, String typeName2) {
        return setNames.contains(typeName1) && setNames.contains(typeName2);
    }


    @Override
    public double calculate(Node node) {
        double multiplier = node.getMultiplier();
        int indexFirstInclude = setNames.indexOf(node.getTypeName1());
        int indexSecondInclude = setNames.indexOf(node.getTypeName2());
        if (indexFirstInclude <= indexSecondInclude) {
            for (int i = indexFirstInclude; i <= indexSecondInclude - 1; i++) {
                multiplier *= nodes.get(i).getMultiplier();
            }
        } else {
            for (int i = indexSecondInclude; i >= indexFirstInclude - 1; i--) {
                multiplier /= nodes.get(i).getMultiplier();
            }
        }

        return multiplier;
    }

    @Override
    public int size() {
        return setNames.size();
    }

    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < nodes.size(); i++) {
            s += nodes.get(i).toString() + "\n";
        }
        return s;
    }
}