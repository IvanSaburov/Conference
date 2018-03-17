package org.saburov.services.conference.repository;

import org.saburov.services.conference.entity.Room;
import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<Room, Long> {
}
