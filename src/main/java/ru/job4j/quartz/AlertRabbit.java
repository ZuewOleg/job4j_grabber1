package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

public class AlertRabbit {
    public static void main(String[] args) {
        int interval = timeInterval(new File("src/main/resources/rabbit.properties")); // читаем файл
        try {
            // Конфигурирование
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            // Создание задачи
            JobDetail job = newJob(Rabbit.class).build();
            // Создание расписания
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(interval)
                    .repeatForever();
            // Конструкция выше настраивает периодичность запуска.
            // В нашем случае, мы будем запускать задачу через 10 секунд(как указанно в файле rabbit.properties)
            // и делать это бесконечно.

            // Задача выполняется через триггер
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            // Здесь можно указать, когда начинать запуск. Мы хотим сделать это сразу.

            // Загрузка задачи и триггера в планировщик
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }

    private static int timeInterval(File file) {
        int rsl = 0;
        List<String> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.lines().forEach(s -> {
                String[] index = s.split("=");
                if (index.length == 2) {
                    result.add(index[1]);
                }
            });
            rsl = Integer.parseInt(result.get(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rsl;
    }

    // quartz каждый раз создает объект с типом org.quartz.Job.
    // Вам нужно создать класс реализующий этот интерфейс.
    //Внутри этого класса нужно описать требуемые действия.
    public static class Rabbit implements Job {
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            // В нашем случае - это вывод на консоль текста.
            System.out.println("Rabbit runs here ...");
        }
    }
}
