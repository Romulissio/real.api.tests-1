package entity.agencies;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LinksAgencies implements Serializable {

    private String first;
    private String last;
    private String prev;
    private String next;
}
