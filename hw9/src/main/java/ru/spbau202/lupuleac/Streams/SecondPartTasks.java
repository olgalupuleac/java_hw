package ru.spbau202.lupuleac.Streams;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class SecondPartTasks {

    private SecondPartTasks() {
    }

    // Найти строки из переданных файлов, в которых встречается указанная подстрока.
    public static List<String> findQuotes(
            List<String> paths, CharSequence sequence) {
        return paths.stream().flatMap(
                x -> {
                    try {
                        return Files.lines(Paths.get(x));
                    } catch (IOException e) {
                        System.err.print("Cannot read file ");
                        System.err.println(x);
                        return Stream.empty();
                    }
                }).map(Object::toString)
                .filter(x -> x.toLowerCase().contains(
                        sequence.toString().toLowerCase())
                ).collect(Collectors.toList());
    }

    // В квадрат с длиной стороны 1 вписана мишень.
    // Стрелок атакует мишень и каждый раз попадает в произвольную точку квадрата.
    // Надо промоделировать этот процесс с помощью класса java.util.Random и посчитать, какова вероятность попасть в мишень.
    public static double piDividedBy4() {
        Random random = new Random();
        return Stream.generate(
                () -> new Point2D.Double(
                        2 * random.nextDouble() - 1, 2 * random.nextDouble() - 1
                )
        )
                .limit(10000000).collect(
                        Collectors.averagingInt(
                                p -> (p.x * p.x + p.y * p.y < 1) ? 1 : 0
                        )
                );
    }

    // Дано отображение из имени автора в список с содержанием его произведений.
    // Надо вычислить, чья общая длина произведений наибольшая.
    public static String findPrinter(Map<String, List<String>> compositions) {
        //throw new UnsupportedOperationException();
        return compositions.entrySet().stream().max(
                Comparator.comparing(
                        x -> x.getValue().stream()
                                .collect(
                                        Collectors.joining()
                                ).length()))
                .map(Map.Entry::getKey).orElse(null);
    }

    // Вы крупный поставщик продуктов. Каждая торговая сеть делает вам заказ в виде Map<Товар, Количество>.
    // Необходимо вычислить, какой товар и в каком количестве надо поставить.
    public static Map<String, Integer> calculateGlobalOrder(List<Map<String, Integer>> orders) {
        return orders.stream()
                .flatMap(x -> x.entrySet().stream())
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.summingInt(Map.Entry::getValue)
                ));
    }
}
