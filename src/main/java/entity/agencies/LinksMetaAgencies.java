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
public class LinksMetaAgencies implements Serializable {

    private String url;
    private String label;
    private boolean active;
}
