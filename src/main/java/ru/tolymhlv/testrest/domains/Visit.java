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
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String userId;
    private String pageId;

    @Column(name = "date")
    private LocalDateTime date;

    public Visit(@NonNull final String userId,
                 @NonNull final String pageId,
                 @NonNull final LocalDateTime date) {

        this.userId = userId;
        this.pageId = pageId;
        this.date = date;
    }

}
