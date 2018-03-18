package org.saburov.services.conference.repository;


import org.saburov.services.conference.entity.Room;
import org.saburov.services.conference.entity.Schedule;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScheduleRepository extends CrudRepository<Schedule, Long> {
    @Query("select s from Schedule s order by s.presentationRoom.number")
    public List<Schedule> findAllByOrderByPresentationRoom();

    @Query("select s from Schedule s where s.presentationRoom = :room order by s.presentationTime")
    public List<Schedule> findAllByPresentationTime(@Param("room") Room room);

}
