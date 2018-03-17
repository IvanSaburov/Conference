package org.saburov.services.conference.controller;

import org.saburov.services.conference.entity.ConferenceUser;
import org.saburov.services.conference.entity.Room;
import org.saburov.services.conference.entity.Schedule;
import org.saburov.services.conference.repository.ConferenceUserRepository;
import org.saburov.services.conference.repository.PresentationRepository;
import org.saburov.services.conference.repository.RoomRepository;
import org.saburov.services.conference.repository.ScheduleRepository;
import org.saburov.services.conference.service.ConferenceService;
import org.saburov.services.conference.utils.ButtonAccessConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class ConferenceController {
    @Autowired
    ConferenceService conferenceService;

    @Autowired
    ConferenceUserRepository userRepository;

    @Autowired
    PresentationRepository presentationRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    ScheduleRepository scheduleRepository;

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/registration")
    public String registration() {
        return "registration";
    }

    @RequestMapping("/users")
    public String index(Model model) {
        List<ConferenceUser> users = (List<ConferenceUser>) userRepository.findAll();
        Iterable<Schedule> w = scheduleRepository.findAll();
        model.addAttribute("users", users);
        return "users";
    }

    @RequestMapping("/presentations")
    public String getPresentations(Model model) {
        Iterable<Schedule> schedules = scheduleRepository.findAllByOrderByPresentationRoom();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        model.addAttribute("schedules", schedules);
        ButtonAccessConfigurator configurator = conferenceService.initButtonAccess(username);
        model.addAttribute("usersAccess", configurator.isConferenceUsersListAvailable());
        model.addAttribute("presentationAccess", configurator.isPresentationListAvailable());
        return "presentations";
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String save(ConferenceUser user){
        conferenceService.saveNewUser(user);
        return "redirect:/login";
    }
}
