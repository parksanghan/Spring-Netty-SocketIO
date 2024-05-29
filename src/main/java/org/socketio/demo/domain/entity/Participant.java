package org.socketio.demo.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@RequiredArgsConstructor
@Getter@Setter
@Table(name = "\"participant\"")
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "username", nullable = false, length = 50 )
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id",
    foreignKey =@ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Room room;

    @CreationTimestamp
    private LocalDateTime created;


}
