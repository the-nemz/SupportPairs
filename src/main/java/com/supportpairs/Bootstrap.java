//-------------------------------------------------------------------------------------------------------------//
// Code based on a tutorial by Shekhar Gulati of SparkJava at
// https://blog.openshift.com/developing-single-page-web-applications-using-java-8-spark-mongodb-and-angularjs/
//-------------------------------------------------------------------------------------------------------------//

package com.supportpairs;

import org.sqlite.SQLiteDataSource;
import javax.sql.DataSource;
import static spark.Spark.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Bootstrap {

    public static final String IP_ADDRESS = "localhost";
    public static final int PORT = 8080;

    public static void main(String[] args) throws Exception {

        //Check if the database file exists in the current directory. Abort if not
        DataSource dataSource1 = configureDataSource1();
        if (dataSource1 == null) {
            System.out.printf("Could not find mentee.db in the current directory (%s). Terminating\n",
                    Paths.get(".").toAbsolutePath().normalize());
            System.exit(1);
        }

        DataSource dataSource2 = configureDataSource2();
        if (dataSource2 == null) {
            System.out.printf("Could not find mentor.db in the current directory (%s). Terminating\n",
                    Paths.get(".").toAbsolutePath().normalize());
            System.exit(1);
        }

        //Specify the IP address and Port at which the server should be run
        ipAddress(IP_ADDRESS);
        port(PORT);

        //Specify the sub-directory from which to serve static resources (like html and css)
        staticFileLocation("/public");
        SupportService model = new SupportService(dataSource1, dataSource2);
        new SupportController(model);
    }

    /**
     * Check if the database file exists in the current directory. If it does
     * create a DataSource instance for the file and return it.
     * @return javax.sql.DataSource corresponding to the todo database
     */
    private static DataSource configureDataSource1() {
        Path spPath = Paths.get(".", "mentee.db");
        if ( !(Files.exists(spPath) )) {
            try { Files.createFile(spPath); }
            catch (java.io.IOException ex) {
                System.out.println("Failed to create mentee.db file in current directory. Aborting");
            }
        }
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl("jdbc:sqlite:mentee.db");
        return dataSource;

    }

    /**
     * Check if the database file exists in the current directory. If it does
     * create a DataSource instance for the file and return it.
     * @return javax.sql.DataSource corresponding to the todo database
     */
    private static DataSource configureDataSource2() {
        Path spPath = Paths.get(".", "mentor.db");
        if ( !(Files.exists(spPath) )) {
            try { Files.createFile(spPath); }
            catch (java.io.IOException ex) {
                System.out.println("Failed to create mentor.db file in current directory. Aborting");
            }
        }
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl("jdbc:sqlite:mentor.db");
        return dataSource;

    }
}
