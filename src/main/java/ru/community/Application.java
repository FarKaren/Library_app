package ru.community;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import ru.community.service.Notification;


@EnableScheduling
@RequiredArgsConstructor
@SpringBootApplication
public class Application {

    private final Notification notification;
    private final BookBindingRepository bookBindingRepository;


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Scheduled(cron = "0 0 14 * * ?")
    @EventListener(ApplicationReadyEvent.class)
    public void sendMail(){
        List<BookBinding> bookBindingList = bookBindingRepository.findAll();

        for (BookBinding bb : bookBindingList) {
            if("ПРОСРОЧЕНО".equals(bb.getStatus.getDescription)){
                Reader reader = bb.getReader();
                notification.sendMail(reader.getEmail(), "Subject", "Body");
            }
        }
    }

}
