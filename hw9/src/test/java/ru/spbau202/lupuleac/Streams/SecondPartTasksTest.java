package ru.spbau202.lupuleac.Streams;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static ru.spbau202.lupuleac.Streams.SecondPartTasks.*;

public class SecondPartTasksTest {

    @Test
    public void testFindQuotes() {
        ArrayList<String> paths = new ArrayList<String>(){{
            add("src/test/DustInTheWind.txt");
            add("src/test/AtTheHarbour.txt");
            add("src/test/ITalkToTheWind.txt");
        }};
        ArrayList<String> expected = new ArrayList<String>();
        //first file
        expected.add("Dust in the wind");
        expected.add("All they are is dust in the wind");
        expected.add("Dust in the wind");
        expected.add("All we are is dust in the wind");
        expected.add("Dust in the wind");
        expected.add("All we are is dust in the wind");
        expected.add("All we are is dust in the wind");
        expected.add("Dust in the wind");
        expected.add("Everything is dust in the wind");
        expected.add("Everything is dust in the wind");
        expected.add("The wind");
        //second file
        expected.add("Howling winds and the raging waves");
        //third file
        for(int i = 0; i < 4; i++){
            expected.add("I talk to the wind");
            expected.add("I talk to the wind");
            expected.add("The wind does not hear, the wind cannot hear");
        }
        assertEquals(expected, findQuotes(paths, "wind"));
    }

    @Test
    public void testPiDividedBy4() {
        assertTrue(Math.abs(Math.PI / 4 - piDividedBy4()) < 1e-3);
    }

    @Test
    public void testFindPrinter() {
        Map<String, List<String>> compositions = new HashMap<>();
        ArrayList<String> shakespeare = new ArrayList<>();
        shakespeare.add("Romeo and Juliet");
        shakespeare.add("Hamlet");
        shakespeare.add("King Lear");
        compositions.put("William Shakespeare", shakespeare);
        ArrayList<String> dante = new ArrayList<>();
        dante.add("Divine Comedy");
        compositions.put("Dante Alighieri", dante);
        assertEquals("William Shakespeare", findPrinter(compositions));
    }

    @Test
    public void testCalculateGlobalOrder() {
        List<Map<String, Integer>> orders = new ArrayList<>();
        Map<String, Integer> firstOrder = new HashMap<>();
        firstOrder.put("milk", 10);
        firstOrder.put("cake", 14);
        firstOrder.put("tea", 6);
        Map<String, Integer> secondOrder = new HashMap<>();
        secondOrder.put("tea", 33);
        secondOrder.put("fish", 1);
        secondOrder.put("milk", 7);
        secondOrder.put("bread", 8);
        orders.add(firstOrder);
        orders.add(secondOrder);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("milk", 17);
        expected.put("cake", 14);
        expected.put("tea", 39);
        expected.put("fish", 1);
        expected.put("bread", 8);
        assertEquals(expected, calculateGlobalOrder(orders));
    }
}