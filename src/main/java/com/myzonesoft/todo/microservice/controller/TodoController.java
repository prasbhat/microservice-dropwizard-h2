package com.myzonesoft.todo.microservice.controller;

import com.myzonesoft.todo.microservice.model.Tasks;
import com.myzonesoft.todo.microservice.repository.TasksDAO;
import com.myzonesoft.todo.microservice.util.TodoApplicationConstants;
import io.dropwizard.hibernate.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Path("/todo-app/tasks")
@Produces(MediaType.APPLICATION_JSON)
public class TodoController implements TodoApplicationConstants {
    //Variable declarations for logging
    private static final Logger LOGGER = LoggerFactory.getLogger(TodoController.class);
    private final String className = this.getClass().getSimpleName();

    /*private final TasksDAO tasksDAO = new TasksDAO();

    public TodoController() { }

    @GET
    public Response getAllTodoTasks() {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.info(MessageFormat.format(LOGGER_ENTRY, className, methodName));
        LOGGER.info(MessageFormat.format(LOGGER_EXIT, className, methodName));
        return Response.ok(tasksDAO.getAllTodoTasks()).build();
    }*/

    private final TasksDAO tasksDAO;

    public TodoController(TasksDAO tasksDAO) {
        this.tasksDAO = tasksDAO;
    }

    @GET
    @UnitOfWork
    public Response getAllTodoTasks() {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.info(MessageFormat.format(LOGGER_ENTRY, className, methodName));
        LOGGER.info(MessageFormat.format(LOGGER_EXIT, className, methodName));
        return Response.ok(tasksDAO.getAllTodoTasks()).build();
    }

    @GET
    @Path("/{id}")
    @UnitOfWork
    public Optional<Tasks> getById(@PathParam("id") Long id) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.info(MessageFormat.format(LOGGER_ENTRY, className, methodName));
        LOGGER.info(MessageFormat.format(LOGGER_EXIT, className, methodName));
        return tasksDAO.getById(id);
    }

    @POST
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTasks(Tasks task) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.info(MessageFormat.format(LOGGER_ENTRY, className, methodName));
        LOGGER.info(MessageFormat.format(LOGGER_EXIT, className, methodName));
        return Response.accepted(tasksDAO.createOrUpdateTask(task)).build() ;
    }

    @PUT
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTasks(Tasks tasks) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.info(MessageFormat.format(LOGGER_ENTRY, className, methodName));
        LOGGER.info(MessageFormat.format(LOGGER_EXIT, className, methodName));
        return Response.accepted(tasksDAO.createOrUpdateTask(tasks)).build();
    }

    @DELETE
    @Path("/{id}")
    @UnitOfWork
    public Response deleteTask(@PathParam("id") Long id) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.info(MessageFormat.format(LOGGER_ENTRY, className, methodName));
        try{
            if(tasksDAO.deleteTask(id))
                return Response.noContent().build();
        } catch (IllegalArgumentException exception) {
            LOGGER.error(exception.getMessage());
            return Response.serverError().build();
        } finally {
            LOGGER.info(MessageFormat.format(LOGGER_EXIT, className, methodName));
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/status")
    @UnitOfWork
    public List<String> getTodoStatusAsList() {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.info(MessageFormat.format(LOGGER_ENTRY, className, methodName));

        List<String> taskStatusList = Stream.of(TASK_STATUS.values()).map(TASK_STATUS::name).collect(Collectors.toList());

        LOGGER.debug("statusList=="+taskStatusList);
        LOGGER.info(MessageFormat.format(LOGGER_EXIT, className, methodName));
        return taskStatusList;
    }
}
