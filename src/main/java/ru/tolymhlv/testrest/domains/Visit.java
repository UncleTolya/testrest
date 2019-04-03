package ru.tolymhlv.testrest.domains;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    public Visit(final String userId, final String pageId, final LocalDateTime date) {
        this.userId = userId;
        this.pageId = pageId;
        this.date = date;
    }

}
