package dasturlash.uz.entity;

import dasturlash.uz.enums.GeneralStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "profile")
@Entity
@Getter
@Setter
public class ProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "photo_id")
    private String photoId;

    @OneToOne()
    @JoinColumn(name = "photo_id", updatable = false, insertable = false)
    private Attach photo;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private GeneralStatus status; //ACTIVE, BLOCK

    @Column(name = "visible")
    private Boolean visible = Boolean.TRUE;

    @Column(name = "created_date")
    private LocalDateTime createdDate;


    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @OneToMany(mappedBy = "profile", fetch = FetchType.LAZY)
    private List<ProfileRoleEntity> roleList;


}
