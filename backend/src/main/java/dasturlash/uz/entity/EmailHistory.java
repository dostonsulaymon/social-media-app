package dasturlash.uz.entity;

import dasturlash.uz.enums.MessageStatus;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.Data;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "email_history")
public class EmailHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "to_account")
    private String toAccount;

    @Column(name = "from_account")
    private String fromAccount;

    @Column(name = "subject")
    private String subject;

    @Column(name = "message", columnDefinition = "text", length = 65535)
    private String message;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "message_status")
    private MessageStatus messageStatus;

    @Column(name = "attempt_count")
    private Integer attemptCount;

    @Column(name = "verification_code")
    private String verificationCode;

    @Column(name = "profile_id")
    private Long profileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", updatable = false, insertable = false)
    private ProfileEntity profile;

    @Column(name = "sent_at")
    @Timestamp
    private LocalDateTime sentAt;


}
