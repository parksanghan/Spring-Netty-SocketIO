package org.socketio.demo.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@RequiredArgsConstructor
@Getter@Setter
@Table(name = "\"room\"")

public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private Long roomId;

    @Column(name = "roomname", nullable = false, length = 50,unique = true)
    private String roomName;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Participant> participantList;
    // 이렇게하면 여러명의 참가자의 id 를 저장합니다.


}
