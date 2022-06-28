package com.myzonesoft.todo.microservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.joda.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@NamedQueries({
        @NamedQuery(name = "com.myzonesoft.todo.microservice.model.Tasks.findAll",
                query = "select e from Tasks e")
})
public class Tasks {

    /**
     * Unique Identifier for the To-do task
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long systemTasksId;
    /**
     * Title for the To-do task
     */
    private String title;
    /**
     * Description for the To-do task
     */
    private String description;
    /**
     * Creation date is System generated
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate creationDate;
    /**
     * Due date for the To-do task
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dueDate;
    /**
     * Status of the To-do task
     */
    private String status;

    /**
     * To-do task comments model
     */
    @OneToMany(mappedBy = "todoTask", fetch = FetchType.EAGER)
    private Set<TodoTaskComments> todoTaskCommentsSet;
}
