package entity.agencies;

import com.fasterxml.jackson.annotation.JsonInclude;
import entity.users.Users;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString(exclude = "users")
@Entity
@Table(name = "agencies")
public class Agency implements Serializable, Comparable<Agency>{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long inn;
    private boolean active;
    private Timestamp created_at;
    private Timestamp updated_at;

    @Transient
    @OneToMany(mappedBy = "agency_id", fetch = FetchType.EAGER)
    private List<Users> users;




    private static Comparator<String> nullSafeStringComparator = Comparator.nullsLast(String::compareToIgnoreCase);
    private static Comparator<Agency> compareToName = Comparator.comparing(Agency::getName, nullSafeStringComparator);
    private static Comparator<Agency> compareToInn = Comparator.comparing(Agency::getInn);
    private static Comparator<Agency> compareToActive = Comparator.comparing(Agency::isActive);
    private static Comparator<Agency> compareToCreatedAt = Comparator.comparing(Agency::getCreated_at);

    public int compareToCreatedAt(Agency o) {
        return compareToCreatedAt.compare(this, o);
    }

    public int compareToActive(Agency o) {
        return compareToActive.compare(this, o);
    }

    public int compareToInn(Agency o) {
        return compareToInn.compare(this, o);
    }

    public int compareToName(Agency o) {
        return compareToName.compare(this, o);
    }

    public int compareTo(Agency o) {
        return id.compareTo(o.getId());
    }

    public static int compareData(Agency a, Agency b){

        return a.getId().compareTo(b.getId());
    }

    public Agency(Long id, String name, Long inn, boolean active, Timestamp created_at, Timestamp updated_at) {
        this.id = id;
        this.name = name;
        this.inn = inn;
        this.active = active;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Agency agency = (Agency) o;
        return active == agency.active &&
                Objects.equals(id, agency.id) &&
                Objects.equals(name, agency.name) &&
                Objects.equals(inn, agency.inn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, inn, active);
    }
}
