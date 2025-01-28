package dasturlash.uz.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "post_attaches")  // Changed from "post-attaches" to "post_attaches"
public class PostAttaches {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "photo_id")
    private String photoId;

    @Column(name = "post_id")
    private Long postId;  // Changed to Long to match Post entity's ID type

    @ManyToOne
    @JoinColumn(name = "post_id", insertable = false, updatable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "photo_id", insertable = false, updatable = false)
    private Attach attach;
}