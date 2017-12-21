package ru.spbau202.lupuleac.Reflector;

import org.junit.Test;
import ru.spbau202.lupuleac.Test1.Test1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import static org.junit.Assert.*;

public class ReflectorTest {
    @Test
    public void printStructure() throws Exception {
        //Reflector.printStructure(HashMap.class);
        Reflector.printStructure(ArrayList.class);
        //Reflector.printStructure(TreeMap.class);
        Reflector.printStructure(Test1.class);
    }

}