package entity.users;

import com.fasterxml.jackson.annotation.JsonInclude;
import entity.agencies.Agency;
import lombok.*;

import javax.persistence.Entity;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
@AllArgsConstructor
public class UserRolesAgencyPermissions implements Serializable, Comparable{


    private Long id;
    private String email;
    private String first_name;
    private String last_name;
    private String middle_name;
    private String phone;
    private Boolean confirmed;
    private Boolean is_admin;
    private Boolean active;
    private Timestamp created_at;
    private Timestamp updated_at;
    private String type;
    private List<Permissions> permissions;
    private List<Roles> roles;
    private List<Objects> notifications;
    private Agency agency;

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public int compareToFirstName(UserRolesAgencyPermissions o) {
        if(this.first_name != null){
            return this.first_name.compareTo(o.getFirst_name());
        }
        return 0;
    }

    public int compareToMiddleName(UserRolesAgencyPermissions o) {
        if(this.middle_name != null){
            return this.middle_name.compareTo(o.getMiddle_name());
        }
        return 0;
    }

    public int compareToLastNameName(UserRolesAgencyPermissions o) {
        if(this.last_name != null){
            return this.last_name.compareTo(o.getLast_name());
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRolesAgencyPermissions that = (UserRolesAgencyPermissions) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(email, that.email) &&
                Objects.equals(first_name, that.first_name) &&
                Objects.equals(last_name, that.last_name) &&
                Objects.equals(middle_name, that.middle_name) &&
                Objects.equals(phone, that.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, first_name, last_name, middle_name, phone);
    }
}
