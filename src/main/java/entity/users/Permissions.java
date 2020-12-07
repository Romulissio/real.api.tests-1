package entity.users;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)

@AllArgsConstructor
@ToString(exclude = "roles")
@Entity
@Table(name = "permissions")
public class Permissions implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String guard_name;
    private Timestamp created_at;
    private Timestamp updated_at;

    @ManyToMany(mappedBy = "permissions", fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST}, targetEntity = Roles.class)
    private List<Roles> roles;


    public Permissions() {
    }

    public Permissions(String name) {
        this.name = name;
    }
}
