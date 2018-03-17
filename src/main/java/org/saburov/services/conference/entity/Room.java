package org.saburov.services.conference.entity;


import javax.persistence.*;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long roomid;

    @Column(name="number")
    private Integer number;
    @Column(name="capacity")
    private Integer capacity;

//    private Schedule schedule;
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room", cascade = CascadeType.ALL)
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room", cascade = CascadeType.ALL)
//    private Set<Presentation>presentations;

    public Room() {
    }

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "book_detail_id")
//    public Schedule getSchedule() {
//        return schedule;
//    }
//
//    public void setSchedule(Schedule schedule) {
//        this.schedule = schedule;
//    }

    public Room(Integer number) {
        this.number = number;
    }

    public long getRoomid() {
        return roomid;
    }

    public void setRoomid(long roomid) {
        this.roomid = roomid;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

//    @OneToMany(targetEntity=Presentation.class,mappedBy = "room", cascade = CascadeType.ALL)/
//    @ElementCollection(targetClass=Presentation.class)
//    public Set<Presentation> getPresentations() {
//        return presentations;
//    }
//
//    public void setPresentations(Set<Presentation> presentations) {
//        this.presentations = presentations;
//    }
}
