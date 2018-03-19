package org.saburov.services.conference.controller;

import org.saburov.services.conference.entity.ConferenceUser;
import org.saburov.services.conference.entity.Presentation;
import org.saburov.services.conference.entity.Schedule;
import org.saburov.services.conference.repository.ConferenceUserRepository;
import org.saburov.services.conference.repository.PresentationRepository;
import org.saburov.services.conference.repository.RoomRepository;
import org.saburov.services.conference.repository.ScheduleRepository;
import org.saburov.services.conference.service.ConferenceService;
import org.saburov.services.conference.utils.ButtonAccessConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Set;

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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        ButtonAccessConfigurator configurator = conferenceService.initButtonAccess(username);
        model.addAttribute("usersAccess", configurator.isConferenceUsersListAvailable());
        model.addAttribute("presentationAccess", configurator.isPresentationListAvailable());
        return "users";
    }

    @RequestMapping("/schedule")
    public String getSchedule(Model model) {
        Iterable<Schedule> schedules = scheduleRepository.findAllByOrderByPresentationRoom();
        model.addAttribute("schedules", schedules);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        ButtonAccessConfigurator configurator = conferenceService.initButtonAccess(username);
        model.addAttribute("usersAccess", configurator.isConferenceUsersListAvailable());
        model.addAttribute("presentationAccess", configurator.isPresentationListAvailable());
        return "schedule";
    }

    @RequestMapping("/presentations")
    public String getPresentations(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Set<Presentation> presentations = conferenceService.getPresentations(username);
        model.addAttribute("presentations", presentations);
        ButtonAccessConfigurator configurator = conferenceService.initButtonAccess(username);
        model.addAttribute("usersAccess", configurator.isConferenceUsersListAvailable());
        model.addAttribute("presentationAccess", configurator.isPresentationListAvailable());
        return "presentations";
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String save(ConferenceUser user) {
        conferenceService.saveNewUser(user);
        return "redirect:/login";
    }

    @RequestMapping(value = "/addPresentation")
    public String addPresentation() {
        return "addPresentation";
    }

    @RequestMapping(value = "/savePresentation")
    public String savePresentation(Presentation presentation) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        conferenceService.savePresentation(presentation, username);
        return "redirect:/presentations";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deletePresentation(@PathVariable("id") Long presentationId, Model model) {
        conferenceService.deletePresentation(presentationId);
        return "redirect:/presentations";
    }

    @RequestMapping(value = "/apischedule", method = RequestMethod.GET)
    public ResponseEntity getSchedules() {
        return ResponseEntity.ok(conferenceService.getSchedulesMap());
    }
}
