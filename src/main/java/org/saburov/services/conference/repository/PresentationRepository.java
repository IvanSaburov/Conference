package org.saburov.services.conference.repository;

import org.saburov.services.conference.entity.Presentation;
import org.springframework.data.repository.CrudRepository;

public interface PresentationRepository extends CrudRepository<Presentation, Long> {
    Presentation findByPresentationid(Long presentationid);
}
