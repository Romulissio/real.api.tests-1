package entity.agencies;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class DataAgency implements Serializable {

    private List<Agency> data;
    private LinksAgencies links;
    private MetaAgencies meta;

}
