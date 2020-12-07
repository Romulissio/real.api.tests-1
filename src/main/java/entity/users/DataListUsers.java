package entity.users;

import com.fasterxml.jackson.annotation.JsonInclude;
import entity.utils.LinksData;
import entity.utils.MetaData;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DataListUsers implements Serializable {

    private List<Users> data;
    private LinksData links;
    private MetaData meta;

}
