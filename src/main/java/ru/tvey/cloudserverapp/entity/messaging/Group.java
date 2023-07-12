package ru.tvey.cloudserverapp.entity.messaging;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.tvey.cloudserverapp.entity.user.User;

import java.util.Set;

@Entity
@Table(name = "groups")
@Data
@AllArgsConstructor
public class Group {
    public Group() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private long id;

    @NotBlank
    @Column(name = "group_name")
    private String name;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "group_member",
            joinColumns = @JoinColumn(name ="group_id", referencedColumnName = "group_id"),
            inverseJoinColumns = @JoinColumn(name ="user_id", referencedColumnName = "user_id")
    )
   private Set<User> groupMembers;

}
