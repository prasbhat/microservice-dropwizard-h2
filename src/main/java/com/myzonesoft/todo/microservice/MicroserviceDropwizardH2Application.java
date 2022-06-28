package com.myzonesoft.todo.microservice;

import com.myzonesoft.todo.microservice.configuration.TodoConfiguration;
import com.myzonesoft.todo.microservice.controller.TodoController;
import com.myzonesoft.todo.microservice.model.Tasks;
import com.myzonesoft.todo.microservice.model.TodoTaskComments;
import com.myzonesoft.todo.microservice.repository.TasksDAO;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.jersey.jackson.JsonProcessingExceptionMapper;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

public class MicroserviceDropwizardH2Application extends Application<TodoConfiguration> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MicroserviceDropwizardH2Application.class);

    public static void main(String[] args) throws Exception {
        new MicroserviceDropwizardH2Application().run(args);
    }

    @Override
    public void run(TodoConfiguration todoConfiguration, Environment environment) throws Exception {
        LOGGER.info("Registering REST resources");
//        environment.jersey().register(new TodoController());

        //--- Start of Hibernate Bundle registration --//
        final TasksDAO tasksDAO = new TasksDAO(hibernateBundle.getSessionFactory());
        environment.jersey().register(new TodoController(tasksDAO));
        //--- End of Hibernate Bundle registration --//

        //Helps in better debugging, as exceptions would be explained better
        environment.jersey().register(new JsonProcessingExceptionMapper(true));

        //--- Start of the CORS Configuration --//
        final FilterRegistration.Dynamic cors =
                environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "http://localhost:3000");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "X-Requested-With,Content-Type,Accept,Origin,Authorization");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "OPTIONS,GET,PUT,POST,DELETE,HEAD");
        cors.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, "true");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        //--- End of the CORS Configuration --//
    }

    private final HibernateBundle<TodoConfiguration> hibernateBundle = new HibernateBundle<TodoConfiguration>(Tasks.class, TodoTaskComments.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(TodoConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    @Override
    public void initialize(Bootstrap<TodoConfiguration> bootstrap) {
        bootstrap.addBundle(hibernateBundle);
    }
}
