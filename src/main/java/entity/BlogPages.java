package entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import entity.users.Users;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@Table(name = "blog_pages")
public class BlogPages implements Serializable {

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
    private Timestamp published_at;
    private Timestamp created_at;
    private Timestamp updated_at;
    private String meta_title;
    private String meta_description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by")
    private Users created_by;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "updated_by")
    private Users updated_by;

    @Override
    public String toString() {
        return "BlogPages{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", slug='" + slug + '\'' +
                ", preview_text='" + preview_text + '\'' +
                ", detail_text='" + detail_text + '\'' +
                ", type='" + type + '\'' +
                ", like_count=" + like_count +
                ", important=" + important +
                ", active=" + active +
                ", published_at=" + published_at +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", meta_title='" + meta_title + '\'' +
                ", meta_description='" + meta_description + '\'' +
                '}';
    }
}
