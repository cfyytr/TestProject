package com.company;

import java.io.*;
import java.util.ArrayList;


public class Controller {

    private BufferedReader reader;
    private SetChainConversion setChainConversion;
    private final String SPACE = " ";
    private final String MESSAGE_ERROR_CONVERSION = "Conversion not possible.";
    private final String MESSAGE_ERROR_INPUT = "Conversion not possible.";
    private final String EQUAL = "=";
    private final String QUESTION = "?";
    ArrayList<String> result = new ArrayList<>();

    public Controller(InputStream inputStream, SetChainConversion setChainConversion) {
        reader = new BufferedReader(new InputStreamReader(inputStream));
        this.setChainConversion = setChainConversion;
    }

    public void run() throws IOException {
        considerKnowConversions();
        considerNotKnowConversions();
        printResult();
    }

    private void printResult() {
        for (int i = 0; i < result.size(); i++) {
            System.out.println(result.get(i));
        }
    }

    private void considerNotKnowConversions() throws IOException {
        String s = reader.readLine();
        Node node;
        do {
            try {
                String[] strings = s.split(SPACE);
                if (strings.length < 5 && !strings[2].equals(EQUAL) && !strings[3].equals(QUESTION))
                    throw new InputException();
                node = new NodeImpl(strings[1], strings[4], 1, Double.parseDouble(strings[0]));
                if (setChainConversion.isExist(node)) {
                    result.add(strings[0] + " " + node.getTypeName1() + " " + setChainConversion.calculate(node) + " " + node.getTypeName2());
                } else result.add(MESSAGE_ERROR_CONVERSION);
            } catch (Exception e) {
                System.out.println(MESSAGE_ERROR_INPUT);
            }
            s = reader.readLine();
        } while (!s.equals(""));
    }


    private void considerKnowConversions() throws IOException {
        Node node;
        String s = reader.readLine();
        do {
            try {
                String[] strings = s.split(SPACE);
                if (strings.length < 5 && !strings[2].equals(EQUAL))
                    throw new InputException();
                node = new NodeImpl(strings[1], strings[4], Double.parseDouble(strings[0]), Double.parseDouble(strings[3]));
                setChainConversion.addConversion(node);
            } catch (Exception e) {
                System.out.println(MESSAGE_ERROR_INPUT);
            }
            s = reader.readLine();
        } while (!s.equals(""));
        System.out.println(setChainConversion.toString());
    }
}
