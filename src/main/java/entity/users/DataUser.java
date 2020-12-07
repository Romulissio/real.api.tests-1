package entity.users;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DataUser implements Serializable {

    private UserRolesAgencyPermissions data;
    private Boolean registered;
}
