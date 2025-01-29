package dasturlash.uz.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "attaches")
public class Attach {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "path")
    private String path;

    @Column(name = "extension", length = 10)
    private String extension;

    @Column(name = "origen_name", length = 100)
    private String origenName;

    @Column(name = "size")
    private Long size;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "visible")
    private Boolean visible = true;

    @OneToMany(mappedBy = "attach", cascade = CascadeType.ALL)
    private List<PostAttaches> postAttaches = new ArrayList<>();
}
