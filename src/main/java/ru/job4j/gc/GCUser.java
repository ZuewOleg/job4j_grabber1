package ru.job4j.gc;

public class GCUser {

    private static final long KB = 1024; // килобайты
    private static final long B = 1; // байты
    private static final long MB = KB * KB; // мегабайты
    private static final Runtime ENVIRONMENT = Runtime.getRuntime();

    public static void info() {
        final long freeMemory = ENVIRONMENT.freeMemory(); // возвращает количество свободной памяти в байтах
        final long totalMemory = ENVIRONMENT.totalMemory(); // возвращает общее количество памяти которое может быть использовано
        final long maxMemory = ENVIRONMENT.maxMemory(); // возвращает максимальное количество памяти которое может быть использовано
        System.out.println("=== Environment state ===");
        System.out.printf("Free: %d%n", freeMemory / MB);
        System.out.printf("Total: %d%n", totalMemory / MB);
        System.out.printf("Max: %d%n", maxMemory / MB);
    }

    public static void main(String[] args) throws Throwable {
        System.out.println("До создания объектов");
        info();
        for (int i = 1; i < 10000; i++) {
            new User("Name" + i, i);
        }
        System.out.println("После создания объектов");
        info();
        System.out.println("После сборщика мусора");
        System.gc(); // вызов сборки мусора
        info();
    }
}
