package org.saburov.services.conference.repository;

import org.saburov.services.conference.entity.ConferenceUser;
import org.springframework.data.repository.CrudRepository;

public interface ConferenceUserRepository  extends CrudRepository<ConferenceUser, Long> {
    ConferenceUser findByUsername(String username);
}
