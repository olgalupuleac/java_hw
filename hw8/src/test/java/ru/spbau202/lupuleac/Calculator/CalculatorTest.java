package ru.spbau202.lupuleac.Calculator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import ru.spbau202.lupuleac.Stack.Stack;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CalculatorTest {
    private Stack<Integer> mockedValues;
    private Stack<Character> mockedOperators;
    private Calculator calculator;

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() {
        mockedValues = mock(Stack.class);
        mockedOperators = mock(Stack.class);
        calculator = new Calculator(mockedValues, mockedOperators);
    }

    @Test
    public void parseInputToPolishNotation() throws Exception {
        String expression = "0 + 1";
        when(mockedOperators.isEmpty())
                .thenReturn(true, false, false);
        when(mockedOperators.pop())
                .thenReturn('+', '(');
        when(mockedOperators.top())
                .thenReturn('(', '+', '(');
        assertEquals("0 1 +",
                calculator.parseInputToPolishNotation(expression));
        InOrder inOrder = inOrder(mockedOperators);
        inOrder.verify(mockedOperators).clear();
        inOrder.verify(mockedOperators).push('(');
        inOrder.verify(mockedOperators).isEmpty();
        inOrder.verify(mockedOperators).push('+');
        inOrder.verify(mockedOperators).isEmpty();
        inOrder.verify(mockedOperators).pop();
        inOrder.verify(mockedOperators).isEmpty();
        inOrder.verify(mockedOperators).pop();
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
        assertEquals("42 56 +",
                calculator.parseInputToPolishNotation(expression));
    }

    @Test
    public void calculate() throws Exception {
        String expression = "1 0 -";
        when(mockedValues.pop())
                .thenReturn(0, 1, 1);
        assertEquals(1, calculator.calculate(expression));
        InOrder inOrder = inOrder(mockedValues);
        inOrder.verify(mockedValues).clear();
        inOrder.verify(mockedValues).push(1);
        inOrder.verify(mockedValues).push(0);
        inOrder.verify(mockedValues).push(1);
    }

    @Test
    public void parseInputWithoutMock() throws Exception {
        Calculator calculator = new Calculator(new Stack<>(), new Stack<>());
        String expression = "7 * (1 - 1)";
        assertEquals(0, calculator.
                calculate(calculator.parseInputToPolishNotation(expression)));
    }

}