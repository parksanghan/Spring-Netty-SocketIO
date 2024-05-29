package org.socketio.demo.domain.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "authuser")

public class AuthUser {
    @Id
    String username;
    @Column(name ="password" , nullable = false, unique = true,length = 20)
    String password;
    @Column(name ="displayname" , nullable = false, unique = true,length = 20)
    String displayName;
}
