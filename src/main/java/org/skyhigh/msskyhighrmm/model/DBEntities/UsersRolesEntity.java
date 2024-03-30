package org.skyhigh.msskyhighrmm.model.DBEntities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users_roles")
public class UsersRolesEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    UniversalUserEntity user_id;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    UserGroupRolesEntity role_id;
}
