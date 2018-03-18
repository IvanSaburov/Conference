package org.saburov.services.conference;

import org.saburov.services.conference.config.RolesConfig;
import org.saburov.services.conference.entity.ConferenceUser;
import org.saburov.services.conference.entity.Presentation;
import org.saburov.services.conference.entity.Room;
import org.saburov.services.conference.entity.Schedule;
import org.saburov.services.conference.repository.ConferenceUserRepository;
import org.saburov.services.conference.repository.PresentationRepository;
import org.saburov.services.conference.repository.RoomRepository;
import org.saburov.services.conference.repository.ScheduleRepository;
import org.saburov.services.conference.utils.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;

@SpringBootApplication
public class ConferenceApplication {

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("TimeZone"));
    }

    private static final Logger log = LoggerFactory.getLogger(ConferenceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ConferenceApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(ConferenceUserRepository userRepository, PresentationRepository presentationRepository,
                                      RoomRepository roomRepository, ScheduleRepository scheduleRepository) {
        return (args) -> {
            ConferenceUser conferenceUser = new ConferenceUser("ivan", MD5Util.convertPasswordToHash("q"));
            conferenceUser.setAge(28);
            conferenceUser.setCity("Perm");
            conferenceUser.setEmail("ivan.saburov@mail.ru");
            conferenceUser.setFirstName("Иван");
            conferenceUser.setLastName("Сабуров");
            conferenceUser.setRole(RolesConfig.getAdminRole());
            ConferenceUser conferenceUser1 = new ConferenceUser("test", MD5Util.convertPasswordToHash("1"));
            conferenceUser1.setAge(31);
            conferenceUser1.setCity("Perm");
            conferenceUser1.setEmail("test@mail.ru");
            conferenceUser1.setFirstName("Петр");
            conferenceUser1.setLastName("Сидоров");
            conferenceUser1.setRole(RolesConfig.getPresenterRole());

            Room room1 = new Room(11);
            Room room2 = new Room(12);
            Room room3 = new Room(2);
            roomRepository.save(room1);
            roomRepository.save(room2);
            roomRepository.save(room3);

            Presentation presentation1 = new Presentation();
            presentation1.setTittle("Best presentation");
            presentation1.setDescription("bla bla bla about presentatin");

            Presentation presentation2 = new Presentation();
            presentation2.setTittle("Тестовая презентация");
            presentation2.setDescription("Описание тестовой презентации");

            Presentation presentation3 = new Presentation();
            presentation3.setTittle("Презентация об итальянском язык");
            presentation3.setDescription("Ragazzi, andiamo, andiamo. Tutto va bene");


            presentationRepository.save(presentation1);
            presentationRepository.save(presentation2);
            presentationRepository.save(presentation3);

            Set<Presentation> presentations = new HashSet<>();
            presentations.add(presentation1);
            presentations.add(presentation2);
            Set<Presentation> presentations1 = new HashSet<>(presentations);
            presentations1.add(presentation3);

            conferenceUser.setPresentations(presentations1);
            conferenceUser1.setPresentations(presentations);
            userRepository.save(conferenceUser);
            userRepository.save(conferenceUser1);

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            Schedule schedule = new Schedule();
            schedule.setPresentation(presentation1);
            schedule.setPresentationRoom(room1);
            schedule.setPresentationTime(df.parse("2018-03-19 12:00"));

            Schedule schedule1 = new Schedule();
            schedule1.setPresentation(presentation2);
            schedule1.setPresentationRoom(room2);
            schedule1.setPresentationTime(df.parse("2018-03-19 13:30"));

            Schedule schedule2 = new Schedule();
            schedule2.setPresentation(presentation2);
            schedule2.setPresentationRoom(room1);
            schedule2.setPresentationTime(df.parse("2018-03-19 10:30"));

            Schedule schedule3 = new Schedule();
            schedule3.setPresentation(presentation3);
            schedule3.setPresentationRoom(room3);
            schedule3.setPresentationTime(df.parse("2018-03-19 11:30"));

            scheduleRepository.save(schedule);
            scheduleRepository.save(schedule1);
            scheduleRepository.save(schedule2);
            scheduleRepository.save(schedule3);

        };
    }
}
