package ru.spbau202.lupuleac.Calculator;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import ru.spbau202.lupuleac.Stack.Stack;

import java.util.HashMap;
import java.util.function.BinaryOperator;

/**
 * Class which represents calculator.
 * Takes the expression to calculate, transforms it into reversed Polish notation
 * and then evaluates the expression using Stack.
 */
public class Calculator {
    private Stack<Integer> values;
    private Stack<Character> operators;

    private static final HashMap<Character, Integer> PRIORITIES = new HashMap<>();
    private static final HashMap<Character, BinaryOperator<Integer>> OPERATIONS =
            new HashMap<>();

    /**
     * Maps the operator presented (character)
     * to the relevant binary operation and to its priority.
     */
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

    /**
     * Initializes the Calculator assigning stacks used for calculations.
     *
     * @param values    stack for values which will during calculations
     * @param operators stack for operators which appear during input parse.
     */
    public Calculator(Stack<Integer> values, Stack<Character> operators) {
        this.values = values;
        this.operators = operators;
    }

    /**
     * Transforms given expression into reversed Polish notation.
     *
     * @param input is the expression to be transformed
     * @return the expression in reversed Polish notation
     */
    @NotNull
    public String parseInputToPolishNotation(@NotNull String input) {
        operators.clear();
        StringBuilder result = new StringBuilder();
        String expression = "(" + input + ")";
        for (char c : expression.toCharArray()) {
            if (Character.isDigit(c) || c == ' ') {
                result.append(c);
                continue;
            }
            if (c == '(') {
                operators.push(c);
                continue;
            }
            while (!operators.isEmpty() && PRIORITIES.get(c) <= PRIORITIES.get(operators.top())) {
                char top = operators.pop();
                if (c == ')' && top == '(') {
                    break;
                }
                result.append(' ');
                result.append(top);
            }
            if (c != ')') {
                operators.push(c);
            }
        }
        return StringUtils.join(result.toString().split("[ ]+"), " ");
    }

    /**
     * Takes the expression in reversed Polish notation,
     * and evaluates it.
     *
     * @param expressionInPolishNotation is expression to be evaluated
     * @return result of evaluation
     */
    public int calculate(@NotNull String expressionInPolishNotation) {
        values.clear();
        Integer currentValue = null;
        for (char c : expressionInPolishNotation.toCharArray()) {
            if (Character.isDigit(c)) {
                if (currentValue == null) {
                    currentValue = Character.getNumericValue(c);
                } else {
                    currentValue = currentValue * 10 + Character.getNumericValue(c);
                }
            } else {
                if (c == ' ') {
                    if (currentValue != null) {
                        values.push(currentValue);
                        currentValue = null;
                    }
                    continue;
                }
                int rhs = values.pop();
                int lhs = values.pop();
                values.push(OPERATIONS.get(c).apply(lhs, rhs));
            }
        }
        return values.pop();
    }
}
