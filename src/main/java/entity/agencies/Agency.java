package entity.agencies;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Agency implements Serializable, Comparable<Agency>{

    private Long id;
    private String name;
    private Long inn;
    private boolean active;
    private String created_at;
    private String updated_at;

    public int compareTo(Agency o) {
        return 0;
    }

    public static int compareData(Agency a, Agency b){

        return a.getId().compareTo(b.getId());
    }
}
