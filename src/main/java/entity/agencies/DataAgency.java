package entity.agencies;

import com.fasterxml.jackson.annotation.JsonInclude;
import entity.utils.LinksData;
import entity.utils.MetaData;
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
    private LinksData links;
    private MetaData meta;

}
