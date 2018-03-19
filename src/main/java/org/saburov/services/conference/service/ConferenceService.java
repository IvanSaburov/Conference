package org.saburov.services.conference.service;

import org.saburov.services.conference.config.RolesConfig;
import org.saburov.services.conference.entity.ConferenceUser;
import org.saburov.services.conference.entity.Presentation;
import org.saburov.services.conference.entity.Room;
import org.saburov.services.conference.repository.ConferenceUserRepository;
import org.saburov.services.conference.repository.PresentationRepository;
import org.saburov.services.conference.repository.RoomRepository;
import org.saburov.services.conference.repository.ScheduleRepository;
import org.saburov.services.conference.utils.ButtonAccessConfigurator;
import org.saburov.services.conference.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class ConferenceService {
    @Autowired
    ConferenceUserRepository userRepository;

    @Autowired
    PresentationRepository presentationRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    ScheduleRepository scheduleRepository;

    public void saveNewUser(ConferenceUser user) {
        user.setRole(RolesConfig.getListenerRole());
        user.setPassword(MD5Util.convertPasswordToHash(user.getPassword()));
        userRepository.save(user);
    }

    public ButtonAccessConfigurator initButtonAccess(String username) {
        ButtonAccessConfigurator configurator = new ButtonAccessConfigurator();
        ConferenceUser currentUser = userRepository.findByUsername(username);
        if (RolesConfig.getAdminRole().equals(currentUser.getRole())) {
            configurator.setIsConferenceUsersListAvailable(true);
            configurator.setIsPresentationListAvailable(true);
        } else if (RolesConfig.getPresenterRole().equals(currentUser.getRole())) {
            configurator.setIsConferenceUsersListAvailable(false);
            configurator.setIsPresentationListAvailable(true);
        }
        return configurator;
    }

    public Set<Presentation> getPresentations(String username) {
        return userRepository.findByUsername(username).getPresentations();
    }

    public void savePresentation(Presentation presentation, String username){
        presentation.setConferenceUsers(new HashSet<>());
        presentationRepository.save(presentation);
        ConferenceUser user = userRepository.findByUsername(username);
        user.getPresentations().add(presentation);
        userRepository.save(user);
    }


    public void deletePresentation(Long presentationId) {
        presentationRepository.delete(presentationId);
    }

    public Map<Integer, Object> getSchedulesMap() {
        Map<Integer, Object> result = new HashMap<>();
        Iterable<Room> roomList = roomRepository.findAll();
        for (Room room : roomList) {
            result.put(room.getNumber(), scheduleRepository.findAllByPresentationTime(room));
        }

        return result;
    }

}
