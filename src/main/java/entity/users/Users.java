package entity.users;

import com.fasterxml.jackson.annotation.JsonInclude;
import entity.BlogPages;
import entity.agencies.Agency;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
@AllArgsConstructor
@Table(name = "users")
public class Users implements Serializable, Comparable<Users> {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
     private String email;
     private String password;
     @Transient
     private String password_confirmation;
     private String first_name;
     private String last_name;
     private String remember_token;
     private String middle_name;
     private Timestamp email_verified_at;
     private String phone;
     private Boolean confirmed;
     private Boolean active;
     private Timestamp created_at;
     private Timestamp updated_at;
     private String type;

     @Transient
     private Boolean is_admin;
     @Transient
     private Set<Permissions> permissions;
     @Transient
     private Agency agency;
     @Transient
     private List<Object> notifications;
     @Transient
     private List<Long> role_ids;


     /** юзер может иметь роли доступа */
     @LazyCollection(LazyCollectionOption.FALSE)
     @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST}, targetEntity = Roles.class)
     @JoinTable(name = "model_has_roles", joinColumns = { @JoinColumn(name = "model_id")}, inverseJoinColumns = { @JoinColumn(name = "role_id")})
     private Set<Roles> roles;

//     /** юзер может быть привязан к одному агенству */
//     @OneToOne(fetch = FetchType.EAGER)
//     @JoinColumn(name = "agency_id")
//     private Agency agency_id;

     @OneToOne(fetch = FetchType.EAGER)
     @JoinColumn(name = "agency_id")
     private Agency agency_id;





     /** юзер может быть автором и эдитором блога */
     @OneToMany(mappedBy = "created_by", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
     private List<BlogPages> created_by;
     @OneToMany(mappedBy = "updated_by", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
     private List<BlogPages> updated_by;


     private static Comparator<String> nullSafeStringComparator = Comparator.nullsLast(String::compareToIgnoreCase);
     private static Comparator<Users> comparatorFirstName = Comparator.comparing(Users::getFirst_name, nullSafeStringComparator);
     private static Comparator<Users> comparatorLastName = Comparator.comparing(Users::getLast_name, nullSafeStringComparator);

     private static Comparator<Users> comparatorFirstNameLastName = Comparator
             .comparing(Users::getFirst_name, nullSafeStringComparator)
             .thenComparing(Users::getLast_name, nullSafeStringComparator);

     public int compareTo(Users that) {
          return comparatorFirstName.compare(this, that);
     }

     public int compareToFirstName(Users o) {
          return comparatorFirstName.compare(this, o);
     }

     public int compareToLastName(Users o) {
          return comparatorLastName.compare(this, o);
     }

     public int compareToFirstNameLastName(Users o) {
          return comparatorFirstNameLastName.compare(this, o);
     }

     @Override
     public boolean equals(Object o) {
          if (this == o) return true;
          if (o == null || getClass() != o.getClass()) return false;
          Users users = (Users) o;
          return Objects.equals(id, users.id) &&
                  Objects.equals(email, users.email) &&
                  Objects.equals(first_name, users.first_name) &&
                  Objects.equals(last_name, users.last_name) &&
                  Objects.equals(middle_name, users.middle_name) &&
                  Objects.equals(phone, users.phone) &&
                  Objects.equals(type, users.type);
     }

     @Override
     public int hashCode() {
          return Objects.hash(id, email, first_name, last_name, middle_name, phone, confirmed, active);
     }


}
