package ru.tolymhlv.testrest.domains;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Immutable;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Immutable
@Data
@NoArgsConstructor
@Table(indexes = { @Index(name = "date_time", columnList = "date_time") })
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "page_id")
    private String pageId;

    @Column(name = "date_time")
    private LocalDateTime date;

    public Visit(@NonNull final String userId,
                 @NonNull final String pageId,
                 @NonNull final LocalDateTime date) {

        this.userId = userId;
        this.pageId = pageId;
        this.date = date;
    }
}
