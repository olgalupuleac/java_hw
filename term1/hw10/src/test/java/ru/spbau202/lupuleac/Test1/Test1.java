package ru.spbau202.lupuleac.Test1;

import java.io.Serializable;

public class Test1<S, T extends Comparable<? super S> & Serializable> {
    int t;
    int v;
    {
        v = 6;
        t = 3;
    }
    <T extends  Comparable<T>> void myM() {

    }
    public Test1(){
        t = 5;
    }
    ru.spbau202.lupuleac.Test1.Test1 get(){
        return null;
    }
    public void set(){
    }
    public static void main(String[] args){
        Test1 test = new Test1();
        System.out.println(test.t);
        System.out.println(test.v);
    }

    protected <T> T comp(Comparable<? super T> arg){
        return null;
    }
}
