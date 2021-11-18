package ser;

import java.util.ArrayList;
import java.util.Collections;

public class SimpleExpressionSolver {
    private final char[] kOperators = {'+','-','*','/'};


    private ArrayList<Integer> getDepth(String expression) {
        ArrayList<Integer> result = new ArrayList<Integer>(Collections.nCopies(expression.length(), 0));

        int layerId = 0;
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '(') {
                layerId++;
                result.set(i, layerId);
            } else if (expression.charAt(i) == ')') {
                result.set(i, layerId);
                layerId--;
            } else {
                result.set(i, layerId);
            }
        }
        return result;
    }
    private boolean fitDepth(ArrayList<Integer> depth){
        boolean isFound = false;
        for (Integer integer : depth) {
            if (integer < 1) {
                isFound = true;
                break;
            }
        }
        for (int i = 0 ; i < depth.size() ; i++) {
            depth.set(i,depth.get(i)-1);
        }
        return true;
    }
    private String fitExpression(ArrayList<Integer> depth, String expression) {
        if (fitDepth(depth)) {
            expression = expression.substring(1, expression.length() - 1);
            depth.remove(depth.size()-1);
            depth.remove(0);
        }
        return expression;
    }
    private boolean hasOperator(String expression) {
        for (char kOperator : kOperators) {
            if (expression.indexOf(kOperator) != -1) {
                return true;
            }
        }
        return false;
    }
    private boolean hasFirstFloorOperator(String expression,
                                          ArrayList<Integer> depth) {
        for (int i = 0; i < expression.length(); i++) {
            if (depth.get(i) == 0) {
                for (char kOperator : kOperators) {
                    if (expression.charAt(i) == kOperator) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public double calculate(String expression){
        expression=expression.replaceAll("\\s+","");
        ArrayList<Integer> depth = getDepth(expression);
        return calculate_method(expression,depth);
    }
    private double calculate_method(String expression, ArrayList<Integer> depth) {
        if (!hasOperator(expression)) {
            return Double.parseDouble(expression);
        }
        while (!hasFirstFloorOperator(expression, depth)) {
            expression = fitExpression(depth, expression);
        }
        String left = null, right = null;
        int operatorId = 0;
        boolean isFound = false;
        for (int k = 0; k < kOperators.length && !isFound; k++) {
            if (expression.indexOf(kOperators[k]) != -1) {
                int operatorPos = 0;

                for (int j = 0; j < kOperators.length && !isFound; j++) {
                    for (int i = 0; i < expression.length() && !isFound; i++) {
                        if (depth.get(i) == 0) {
                            if (expression.charAt(i) == kOperators[j]) {
                                operatorPos = i;
                                operatorId = j;
                                isFound = true;
                            }
                        }
                    }
                }

                left = expression.substring(0, operatorPos);
                right =
                        expression.substring(operatorPos + 1);
            }
        }
        switch (operatorId) {
            case 0 -> {
                return calculate_method(left, getDepth(left)) +
                        calculate_method(right, getDepth(right));
            }
            case 1 -> {
                return calculate_method(left, getDepth(left)) -
                        calculate_method(right, getDepth(right));
            }
            case 2 -> {
                return calculate_method(left, getDepth(left)) *
                        calculate_method(right, getDepth(right));
            }
            case 3 -> {
                return calculate_method(left, getDepth(left)) /
                        calculate_method(right, getDepth(right));
            }
        }
        return 0.0;
    }
}