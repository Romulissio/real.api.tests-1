package entity.agencies;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MetaAgencies implements Serializable {

    private String current_page;
    private String from;
    private String last_page;
    private List<LinksMetaAgencies> links;
    private String path;
    private String per_page;
    private String to;
    private String total;

}
