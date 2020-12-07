package entity.users;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
@AllArgsConstructor
@Table(name = "model_has_roles")
public class UserModel {

    @Id
    private Long model_id;
    private Long role_id;
    private String model_type = "App\\Models\\User";

}
