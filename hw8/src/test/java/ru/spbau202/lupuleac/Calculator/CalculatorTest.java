package ru.spbau202.lupuleac.Calculator;

import org.junit.Test;
import ru.spbau202.lupuleac.Stack.Stack;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CalculatorTest {
    @SuppressWarnings("unchecked")
    private Stack<Integer> mockedValues = mock(Stack.class);
    @SuppressWarnings("unchecked")
    private Stack<Character> mockedOperators = mock(Stack.class);

    @Test
    public void parseInputToPolishNotation() throws Exception {
        String expression = "0 + 1";
        when(mockedOperators.isEmpty())
                .thenReturn(true, false, false);
        when(mockedOperators.pop())
                .thenReturn('+', '(');
        when(mockedOperators.top())
                .thenReturn('(', '+', '(');
        Calculator calculator = new Calculator(mockedValues, mockedOperators);
        assertEquals("0 1 +", calculator.parseInputToPolishNotation(expression));

    }

    @Test
    public void parseInputWithTwoDigitNumber() throws Exception {
        String expression = "42 + 56";
        when(mockedOperators.isEmpty())
                .thenReturn(true, false, false);
        when(mockedOperators.pop())
                .thenReturn('+', '(');
        when(mockedOperators.top())
                .thenReturn('(', '+', '(');
        Calculator calculator = new Calculator(mockedValues, mockedOperators);
        assertEquals("42 56 +", calculator.parseInputToPolishNotation(expression));
    }

    @Test
    public void calculate() throws Exception {
    }

    @Test
    public void parseInputWithoutMock() throws Exception {
        Calculator calculator = new Calculator(new Stack<>(), new Stack<>());
        String expression = "(5 + 7) * 12 + 4";
        System.out.println(calculator.parseInputToPolishNotation(expression));
        assertEquals(148, calculator.calculate("5 7 + 12 * 4 +"));
    }

}