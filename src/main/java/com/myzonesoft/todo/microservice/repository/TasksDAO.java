package com.myzonesoft.todo.microservice.repository;

import com.myzonesoft.todo.microservice.model.Tasks;
import com.myzonesoft.todo.microservice.model.TodoTaskComments;
import com.myzonesoft.todo.microservice.util.TodoApplicationConstants;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.*;

public class TasksDAO extends AbstractDAO<Tasks> implements TodoApplicationConstants {
//public class TasksDAO implements TodoApplicationConstants {
    private static final Logger LOGGER = LoggerFactory.getLogger(TasksDAO.class);
    private final String className = this.getClass().getSimpleName();

    /*public static HashMap<Integer, Tasks> tasksHashMap = new HashMap<>();
    static{
        Set<TodoTaskComments> todoTaskCommentsSet = new HashSet<>();
        todoTaskCommentsSet.add(TodoTaskComments.builder()
                .todoTaskCommentsId(2L)
                .taskComments("Hardcode Comment")
                .creationDate(LocalDate.now())
                .build());
        tasksHashMap.put(1, Tasks.builder()
                .systemTasksId(1L)
                .title("Test from Dropwizard")
                .description("Hardcoded in Dropwizard")
                .creationDate(LocalDate.now())
                .dueDate(LocalDate.now())
                .status("NOT_STARTED")
                .todoTaskCommentsSet(todoTaskCommentsSet)
                .build());
    }

    public List<Tasks> getAllTodoTasks() {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.info(MessageFormat.format(LOGGER_ENTRY, className, methodName));
        LOGGER.info(MessageFormat.format(LOGGER_EXIT, className, methodName));
        return new ArrayList<>(tasksHashMap.values());
    }*/

    private final TodoTaskCommentsDAO todoTaskCommentsDAO;

    public TasksDAO(SessionFactory sessionFactory) {

        super(sessionFactory);
        this.todoTaskCommentsDAO = new TodoTaskCommentsDAO(sessionFactory);
    }

    public List<Tasks> getAllTodoTasks() {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.info(MessageFormat.format(LOGGER_ENTRY, className, methodName));
        LOGGER.info(MessageFormat.format(LOGGER_EXIT, className, methodName));
        return list(namedTypedQuery("com.myzonesoft.todo.microservice.model.Tasks.findAll"));
    }

    public Optional<Tasks> getById(Long systemTasksId) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.info(MessageFormat.format(LOGGER_ENTRY, className, methodName));
        LOGGER.info(MessageFormat.format(LOGGER_EXIT, className, methodName));
        return Optional.ofNullable(get(systemTasksId));
    }

    public Tasks createOrUpdateTask(Tasks task) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.info(MessageFormat.format(LOGGER_ENTRY, className, methodName));

        //Get the list of TodoTaskComments from the Request Body
        Set<TodoTaskComments> todoTaskCommentsSet = task.getTodoTaskCommentsSet();

        if(todoTaskCommentsSet != null) {
            for(TodoTaskComments todoTaskComment: todoTaskCommentsSet){
                if (todoTaskComment != null && !todoTaskComment.getTaskComments().isEmpty()) {
                    todoTaskComment.setTodoTask(task);
                    todoTaskCommentsDAO.createTodoTaskComments(todoTaskComment);
                }
            }
        }

        //Set the Creation Date only during initial creation of the task
        if(task.getCreationDate() == null)
            task.setCreationDate(LocalDate.now());

        LOGGER.info(MessageFormat.format(LOGGER_EXIT, className, methodName));
        return persist(task);
    }

    public boolean deleteTask(Long id){
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.info(MessageFormat.format(LOGGER_ENTRY, className, methodName));

        boolean isDeleted = false;
        try{
            //Delete the TaskComments before deleting the tasks.
            Optional<Tasks> deletionTask = getById(id);
            if(deletionTask.isPresent()){
                Set<TodoTaskComments> todoTaskCommentsSet = deletionTask.get().getTodoTaskCommentsSet();
                if (todoTaskCommentsSet != null) {
                    for (TodoTaskComments todoTaskComment : todoTaskCommentsSet) {
                        todoTaskCommentsDAO.deleteTodoTaskComments(todoTaskComment);
                    }
                }
                currentSession().delete(deletionTask.get());
                isDeleted = true;
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
        LOGGER.info(MessageFormat.format(LOGGER_EXIT, className, methodName));
        return isDeleted;
    }
}
