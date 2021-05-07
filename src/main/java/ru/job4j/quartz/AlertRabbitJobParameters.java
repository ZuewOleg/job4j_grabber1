package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Properties;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

public class AlertRabbitJobParameters {
    private static final Logger LOG = LoggerFactory.getLogger(AlertRabbitJobParameters.class.getName());
    private Connection connection;
    private int interval;

    public AlertRabbitJobParameters() throws Exception {
        initConnection();
    }

    public int getInterval() {
        return interval;
    }

    private void initConnection() throws Exception { // создание подключения
        Properties cfg = new Properties();
        try (InputStream in = AlertRabbitJobParameters.class.getClassLoader().getResourceAsStream(
                "rabbit.properties")) {
            cfg.load(in);
            interval = Integer.parseInt(cfg.getProperty("rabbit.interval"));
            Class.forName("org.postgresql.Driver");
            Connection cn = DriverManager.getConnection(
                    cfg.getProperty("jdbc.url"),
                    cfg.getProperty("jdbc.username"),
                    cfg.getProperty("jdbc.password"));
        }
    }

    public static void main(String[] args) throws Exception {
        AlertRabbitJobParameters rabbit = new AlertRabbitJobParameters();
        try {
            int interval = rabbit.getInterval();
            // Конфигурирование
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            // При создании Job мы указываем параметры data. В них мы передаем ссылку на connect
            JobDataMap data = new JobDataMap();
            data.put("connect", rabbit.connection);
            JobDetail job = newJob(Rabbit.class)
                    .usingJobData(data)
                    .build();
            // // Создание расписания
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

            // После выполнение работы в списке будут две даты
            Thread.sleep(10000);
            scheduler.shutdown();
        } catch (Exception e) {
            LOG.error("Exception", e);
        }
    }

    public static class Rabbit implements Job {

        public Rabbit() {
            System.out.println(hashCode());
        }

        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            System.out.println("Rabbit runs here ...");
            // Чтобы получить объекты из context используется следующий вызов
            Connection cn = (Connection) context.getJobDetail().getJobDataMap().get("connect");
            try (PreparedStatement statement = cn.prepareStatement(
                    "insert into rabbit(created_date) values(now())")) {
                statement.execute();
            } catch (Exception e) {
                LOG.error("Exception", e);
            }
        }
    }
}
