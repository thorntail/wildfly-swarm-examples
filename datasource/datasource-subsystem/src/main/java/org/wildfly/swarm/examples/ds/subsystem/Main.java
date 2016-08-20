package org.wildfly.swarm.examples.ds.subsystem;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.datasources.DatasourcesFraction;
import org.wildfly.swarm.jaxrs.JAXRSArchive;

/**
 * @author Bob McWhirter
 */
public class Main {

    static String driverModule;

    public static void main(String[] args) throws Exception {

        Swarm swarm = new Swarm();

        String useDB = System.getProperty("swarm.use.db", "h2");

        // Configure the Datasources subsystem with a driver
        // and a datasource
        switch (useDB.toLowerCase()) {
            case "h2":
                swarm.fraction(datasourceWithH2());
                driverModule = "com.h2database.h2";
                break;
            case "postgresql" :
                swarm.fraction(datasourceWithPostgresql());
                driverModule = "org.postgresql";
                break;
            case "mysql" :
                swarm.fraction(datasourceWithMysql());
                driverModule = "com.mysql";
                break;
            default:
                swarm.fraction(datasourceWithH2());
                driverModule = "com.h2database.h2";
        }

        // Start the swarm
        swarm.start();
        JAXRSArchive appDeployment = ShrinkWrap.create(JAXRSArchive.class);
        appDeployment.addResource(MyResource.class);
        appDeployment.addModule(driverModule);

        // Deploy your app
        swarm.deploy(appDeployment);

    }

    private static DatasourcesFraction datasourceWithH2() {
        return new DatasourcesFraction()
                .jdbcDriver("h2", (d) -> {
                    d.driverClassName("org.h2.Driver");
                    d.xaDatasourceClass("org.h2.jdbcx.JdbcDataSource");
                    d.driverModuleName("com.h2database.h2");
                })
                .dataSource("ExampleDS", (ds) -> {
                    ds.driverName("h2");
                    ds.connectionUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
                    ds.userName("sa");
                    ds.password("sa");
                });
    }

    private static DatasourcesFraction datasourceWithPostgresql() {
        return new DatasourcesFraction()
                .jdbcDriver("org.postgresql", (d) -> {
                    d.driverClassName("org.postgresql.Driver");
                    d.xaDatasourceClass("org.postgresql.xa.PGXADataSource");
                    d.driverModuleName("org.postgresql");
                })
                .dataSource("ExampleDS", (ds) -> {
                    ds.driverName("org.postgresql");
                    ds.connectionUrl("jdbc:postgresql://localhost:5432/postgres");
                    ds.userName("postgres");
                    ds.password("postgres");
                });
    }

    private static DatasourcesFraction datasourceWithMysql() {
        return new DatasourcesFraction()
                .jdbcDriver("com.mysql", (d) -> {
                    d.driverClassName("com.mysql.jdbc.Driver");
                    d.xaDatasourceClass("com.mysql.jdbc.jdbc2.optional.MysqlXADataSource");
                    d.driverModuleName("com.mysql");
                })
                .dataSource("ExampleDS", (ds) -> {
                    ds.driverName("com.mysql");
                    ds.connectionUrl("jdbc:mysql://localhost:3306/mysql");
                    ds.userName("root");
                    ds.password("root");
                });
    }

}
