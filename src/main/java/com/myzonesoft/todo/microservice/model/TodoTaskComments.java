package com.myzonesoft.todo.microservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.joda.time.LocalDate;

import javax.persistence.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@NamedQueries({
        @NamedQuery(name = "com.myzonesoft.todo.microservice.model.TodoTaskComments.findAll",
                query = "select e from TodoTaskComments e")
})
public class TodoTaskComments {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long todoTaskCommentsId;

    private String taskComments;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate creationDate;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="systemTasksId", nullable=false)
    private Tasks todoTask;
}
