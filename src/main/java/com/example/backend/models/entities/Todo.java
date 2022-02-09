package com.example.backend.models.entities;

import com.example.backend.models.audits.AuditModel;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "todos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Todo extends AuditModel {

    @Column(name = "description", nullable = false)
    private String description;


    @Temporal(TemporalType.DATE)
    @Column(name = "current_at", nullable = false)
    private Date currentAt;


    @Column(name = "done")
    private int done;

    @ManyToOne
    @NotNull(message = "user is required")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private User user;

    @Override
    public void prePersist() {
        super.prePersist();
        this.done = 0;
    }
}
