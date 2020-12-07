package entity.blogs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Builder
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "blog_pages")
public class Blog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String slug;
    private String preview_text;
    private String detail_text;
    private String type;
    private Integer like_count;
    private Boolean important;
    private Boolean active;
    private String published_at;
    private Timestamp created_at;
    private Timestamp updated_at;
    private Long created_by;
    private Long updated_by;
    private String meta_title;
    private String meta_description;
    @Transient
    private List<Integer> tags;
    @Transient
    private Long preview_picture;

}
