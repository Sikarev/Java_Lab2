package com.company;

import ser.SimpleExpressionSolver;

import java.util.Scanner;

public class Main {
    private static boolean isCorrect(String expression) throws Exception {

        int depth = 0;
        for (int i = 0 ; i< expression.length(); i++){
            if(expression.charAt(i) == '('){
                depth++;
            } else if(expression.charAt(i) == ')') {
                depth--;
            }
        }
        if(depth != 0){
            return false;
        }
        return true;
    }
    public static void main(String args[])
    {
        try {
            Scanner scanner = new Scanner(System.in);
            boolean isGoodInput = false;
            System.out.print("Enter a string: ");
            String str =null;
            while(!isGoodInput){
                str = scanner.nextLine();
                if(isCorrect(str)){
                    isGoodInput = true;
                } else {
                    System.out.println("Your expression is not correct. Try again: ");
                }
            }
            SimpleExpressionSolver solver = new SimpleExpressionSolver();
            System.out.print(solver.calculate(str));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }
}