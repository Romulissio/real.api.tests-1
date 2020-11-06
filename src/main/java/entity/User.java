package entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User implements Serializable {
     private String email;
     private String password;

     public User() {
     }

     public User(String email, String password) {
          this.email = email;
          this.password = password;
     }
}
