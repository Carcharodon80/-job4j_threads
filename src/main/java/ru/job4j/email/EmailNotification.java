package ru.job4j.email;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private final ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    /**
     * готовит данные к отправке и добавляет задачу по отправке в Executor (и запускает её)
     */
    public void emailTo(User user) {
        String subject = "Notification " + user.getUsername() + " to email " + user.getEmail();
        String body = "Add a new event to " + user.getUsername() + ".";
        pool.submit(() -> send(subject, body, user.getEmail()));
    }

    /**
     * shutdown() - Упорядоченное завершение работы,
     * при котором ранее отправленные задачи выполняются, а новые задачи не принимаются
     */
    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String subject, String body, String email) {

    }
}
