package com.myzonesoft.todo.microservice.repository;

import com.myzonesoft.todo.microservice.model.TodoTaskComments;
import com.myzonesoft.todo.microservice.util.TodoApplicationConstants;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.List;

public class TodoTaskCommentsDAO extends AbstractDAO<TodoTaskComments> implements TodoApplicationConstants {
//public class TodoTaskCommentsDAO implements TodoApplicationConstants {
    private static final Logger LOGGER = LoggerFactory.getLogger(TasksDAO.class);
    private final String className = this.getClass().getSimpleName();

    public TodoTaskCommentsDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<TodoTaskComments> getAllTodoTaskComments() {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.info(MessageFormat.format(LOGGER_ENTRY, className, methodName));
        LOGGER.info(MessageFormat.format(LOGGER_EXIT, className, methodName));
        return list(namedTypedQuery("com.myzonesoft.todo.microservice.model.TodoTaskComments.findAll"));
    }

    public void createTodoTaskComments(TodoTaskComments todoTaskComment) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.info(MessageFormat.format(LOGGER_ENTRY, className, methodName));

        todoTaskComment.setCreationDate(LocalDate.now());
        persist(todoTaskComment);

        LOGGER.info(MessageFormat.format(LOGGER_EXIT, className, methodName));
    }

    public void deleteTodoTaskComments(TodoTaskComments todoTaskComments) {
        currentSession().delete(todoTaskComments);
    }
}
