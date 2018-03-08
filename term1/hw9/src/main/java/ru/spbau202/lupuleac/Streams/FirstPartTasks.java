package ru.spbau202.lupuleac.Streams;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class FirstPartTasks {

    private FirstPartTasks() {
    }

    // Список названий альбомов
    @NotNull
    public static List<String> allNames(@NotNull Stream<Album> albums) {
        return albums.map(Album::getName).collect(Collectors.toList());
    }

    // Список названий альбомов, отсортированный лексикографически по названию
    @NotNull
    public static List<String> allNamesSorted(@NotNull Stream<Album> albums) {
        return albums.map(Album::getName)
                .sorted()
                .collect(Collectors.toList());
    }

    // Список треков, отсортированный лексикографически по названию, включающий все треки альбомов из 'albums'
    @NotNull
    public static List<String> allTracksSorted(@NotNull Stream<Album> albums) {
        return albums.flatMap(l -> l.getTracks().stream())
                .map(Track::getName)
                .sorted()
                .collect(Collectors.toList());
    }

    // Список альбомов, в которых есть хотя бы один трек с рейтингом более 95, отсортированный по названию
    @NotNull
    public static List<Album> sortedFavorites(@NotNull Stream<Album> albums) {
        return albums.filter(
                p -> p.getTracks()
                        .stream().mapToInt(Track::getRating)
                        .max().orElse(0) > 95
        ).sorted(Comparator.comparing(Album::getName))
                .collect(Collectors.toList());
    }

    // Сгруппировать альбомы по артистам
    public static Map<Artist, List<Album>> groupByArtist(Stream<Album> albums) {
        return albums.collect(Collectors.groupingBy(
                Album::getArtist
        ));
    }

    // Сгруппировать альбомы по артистам (в качестве значения вместо объекта 'Artist' использовать его имя)
    @NotNull
    public static Map<Artist, List<String>> groupByArtistMapName(@NotNull Stream<Album> albums) {
        return albums.collect(Collectors.groupingBy(
                Album::getArtist,
                Collectors.mapping(
                        Album::getName,
                        Collectors.toList()
                )
        ));
    }

    // Число повторяющихся альбомов в потоке
    public static long countAlbumDuplicates(@NotNull Stream<Album> albums) {
        return albums.collect(Collectors.groupingBy(
                Function.identity(),
                Collectors.counting()
        )).entrySet().stream().filter(x -> x.getValue() > 1).count();
    }

    // Альбом, в котором максимум рейтинга минимален
    // (если в альбоме нет ни одного трека, считать, что максимум рейтинга в нем --- 0)
    @NotNull
    public static Optional<Album> minMaxRating(@NotNull Stream<Album> albums) {
        return albums.min(Comparator.comparingInt(
                x -> x.getTracks().stream()
                        .map(Track::getRating)
                        .max(Integer::compare).orElse(0)
        ));
    }

    // Список альбомов, отсортированный по убыванию среднего рейтинга его треков (0, если треков нет)
    @NotNull
    public static List<Album> sortByAverageRating(@NotNull Stream<Album> albums) {
        return albums.sorted(
                Comparator.comparingDouble(x -> -x.getTracks().stream().collect(
                        Collectors.averagingDouble(Track::getRating)
                ))
        ).collect(Collectors.toList());
    }

    // Произведение всех чисел потока по модулю 'modulo'
    // (все числа от 0 до 10000)
    public static int moduloProduction(@NotNull IntStream stream, int modulo) {
        return stream.reduce((x, y) -> x * y % modulo).getAsInt();
    }

    // Вернуть строку, состояющую из конкатенаций переданного массива, и окруженную строками "<", ">"
    // см. тесты
    @NotNull
    public static String joinTo(String... strings) {
        return Arrays.stream(strings).collect(
                Collectors.joining(", ", "<", ">"));
    }

    // Вернуть поток из объектов класса 'clazz'
    @SuppressWarnings("unchecked")
    @NotNull
    public static <R> Stream<R> filterIsInstance(@NotNull Stream<?> s, Class<R> clazz) {
        return s.filter(clazz::isInstance).map(x -> (R) x);
    }
}