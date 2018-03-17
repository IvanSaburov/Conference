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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class ConferenceApplication {
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
            conferenceUser.setDate(new Date());
            conferenceUser.setRole(RolesConfig.getAdminRole());
            ConferenceUser conferenceUser1 = new ConferenceUser("test", MD5Util.convertPasswordToHash("q"));
            conferenceUser1.setAge(31);
            conferenceUser1.setCity("Perm");
            conferenceUser1.setEmail("test@mail.ru");
            conferenceUser1.setFirstName("Петр");
            conferenceUser1.setLastName("Сидоров");
            conferenceUser1.setDate(new Date());
            conferenceUser1.setRole(RolesConfig.getPresenterRole());
//            userRepository.save(conferenceUser);

            Room room1 = new Room(11);
            Room room2 = new Room(12);
            Room room3 = new Room(2);
            roomRepository.save(room1);
            roomRepository.save(room2);
            roomRepository.save(room3);

            Presentation presentation1 = new Presentation();
            presentation1.setTittle("Best presentation");
            presentation1.setDescription("bla bla bla about presentatin");
//            presentation1.setRoom(room1);

            Presentation presentation2 = new Presentation();
            presentation2.setTittle("Тестовая презентация");
            presentation2.setDescription("Описание тестовой презентации");
//            presentation2.setRoom(room2);

            presentationRepository.save(presentation1);
            presentationRepository.save(presentation2);

            Set<Presentation> presentations = new HashSet<>();
            presentations.add(presentation1);
            presentations.add(presentation2);

            conferenceUser.setPresentations(presentations);
            conferenceUser1.setPresentations(presentations);
            userRepository.save(conferenceUser);
            userRepository.save(conferenceUser1);

            Schedule schedule = new Schedule();
            schedule.setPresentation(presentation1);
            schedule.setPresentationRoom(room1);
            schedule.setPresentationTime(new Date());

            Schedule schedule1 = new Schedule();
            schedule1.setPresentation(presentation2);
            schedule1.setPresentationRoom(room2);
            schedule1.setPresentationTime(new Date());

            Schedule schedule2 = new Schedule();
            schedule2.setPresentation(presentation2);
            schedule2.setPresentationRoom(room1);
            schedule2.setPresentationTime(new Date());

            Schedule schedule3 = new Schedule();
            schedule3.setPresentation(presentation1);
            schedule3.setPresentationRoom(room3);
            schedule3.setPresentationTime(new Date());

            scheduleRepository.save(schedule);
            scheduleRepository.save(schedule1);
            scheduleRepository.save(schedule2);
            scheduleRepository.save(schedule3);

        };
    }
}
