package edu.oregonstate.mist.departments

import edu.oregonstate.mist.api.Application
import edu.oregonstate.mist.api.Configuration
import edu.oregonstate.mist.departments.db.DepartmentDAO
import edu.oregonstate.mist.departments.db.DepartmentMockDAO
import edu.oregonstate.mist.departments.db.DeptDAO
import edu.oregonstate.mist.departments.resources.DepartmentsResource
import io.dropwizard.jdbi.DBIFactory
import io.dropwizard.setup.Environment
import org.skife.jdbi.v2.DBI

/**
 * Main application class.
 */
class DepartmentsApplication extends Application<DepartmentsConfiguration> {
    /**
     * Parses command-line arguments and runs the application.
     *
     * @param configuration
     * @param environment
     */
    @Override
    public void run(DepartmentsConfiguration configuration, Environment environment) {
        this.setup(configuration, environment)
        DepartmentDAO deptDAO = getDeptDAO(configuration, environment)

        environment.jersey().register(new DepartmentsResource(deptDAO))
        // @todo: register healthcheck
    }

    private static DepartmentDAO getDeptDAO(DepartmentsConfiguration configuration,
                                            Environment environment) {
        DepartmentDAO deptDAO
        if(configuration.useTestDAO) {
            deptDAO = new DepartmentMockDAO(1000)
        } else {
            DBIFactory factory = new DBIFactory()
            DBI jdbi = factory.build(environment, configuration.getDatabase(), "jdbi")
            deptDAO = (DepartmentDAO) jdbi.onDemand(DepartmentDAO.class)
        }

        deptDAO
    }

    /**
     * Instantiates the application class with command-line arguments.
     *
     * @param arguments
     * @throws Exception
     */
    public static void main(String[] arguments) throws Exception {
        new DepartmentsApplication().run(arguments)
    }
}
