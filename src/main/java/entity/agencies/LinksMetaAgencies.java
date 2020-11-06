package entity.agencies;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LinksMetaAgencies implements Serializable {

    private String url;
    private String label;
    private boolean active;

    @Override
    public String toString() {
        return "LinksMetaAgencies{" +
                "url='" + url + '\'' +
                ", label='" + label + '\'' +
                ", active=" + active +
                '}';
    }
}
