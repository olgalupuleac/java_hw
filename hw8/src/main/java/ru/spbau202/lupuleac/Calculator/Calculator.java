package ru.spbau202.lupuleac.Calculator;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import ru.spbau202.lupuleac.Stack.Stack;

import java.util.HashMap;
import java.util.function.BinaryOperator;

public class Calculator {
    private Stack<Integer> values;
    private Stack<Character> operators;

    private static final HashMap<Character, Integer> PRIORITIES = new HashMap<>();
    private static final HashMap<Character, BinaryOperator<Integer>> OPERATIONS =
            new HashMap<>();

    static {
        PRIORITIES.put('(', 0);
        PRIORITIES.put('+', 1);
        PRIORITIES.put('-', 1);
        PRIORITIES.put('*', 2);
        PRIORITIES.put('/', 2);
        PRIORITIES.put(')', 0);
        OPERATIONS.put('+', (x, y) -> x + y);
        OPERATIONS.put('-', (x, y) -> x - y);
        OPERATIONS.put('*', (x, y) -> x * y);
        OPERATIONS.put('/', (x, y) -> x / y);
    }

    public Calculator(Stack<Integer> values, Stack<Character> operators){
        this.values = values;
        this.operators = operators;
    }

    @NotNull
    public String parseInputToPolishNotation(@NotNull String input){
        operators.clear();
        StringBuilder result = new StringBuilder();
        String expression = "(" + input + ")";
        for(char c : expression.toCharArray()) {
            if (Character.isDigit(c) || c == ' ') {
                result.append(c);
                continue;
            }
            if(c == '('){
                operators.push(c);
                continue;
            }
            while(!operators.isEmpty() && PRIORITIES.get(c) <= PRIORITIES.get(operators.top())){
                char top = operators.pop();
                if(c == ')' && top == '('){
                    break;
                }
                System.out.printf("c = %c, top = %c\n", c, top);
                result.append(' ');
                result.append(top);
            }
            if(c != ')'){
                operators.push(c);
            }
        }
        return StringUtils.join(result.toString().split("[ ]+"), " ");
    }

    public int calculate(@NotNull String expressionInPolishNotation){
        values.clear();
        Integer currentValue = null;
        for(char c : expressionInPolishNotation.toCharArray()){
            System.out.printf("current char is %c\n", c);
            if(Character.isDigit(c)){
                if(currentValue == null){
                    currentValue = Character.getNumericValue(c);
                }
                else{
                    currentValue = currentValue * 10 + Character.getNumericValue(c);
                }
            }
            else{
                if(c == ' ') {
                    if(currentValue != null){
                        values.push(currentValue);
                        System.out.println(values.top());
                        currentValue = null;
                    }
                    continue;
                }
                int rhs = values.pop();
                int lhs = values.pop();
                System.out.printf("lhs = %d, rhs = %d\n", lhs, rhs);
                values.push(OPERATIONS.get(c).apply(lhs, rhs));
                System.out.printf("values top = %d\n", values.top());
            }
        }
        return values.pop();
    }
}
