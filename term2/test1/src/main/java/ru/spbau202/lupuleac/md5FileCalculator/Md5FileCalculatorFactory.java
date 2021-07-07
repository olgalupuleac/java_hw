package ru.spbau202.lupuleac.md5FileCalculator;

public class Md5FileCalculatorFactory {
    public static Md5FileCalculator getMdFileSingleThreadedCalculator(){
        return new Md5FileSingleThreadedCalculator();
    }

    public static Md5FileCalculator getMdFileForkJoinCalculator(){
        return new Md5FileForkJoinCalculator();
    }
}
