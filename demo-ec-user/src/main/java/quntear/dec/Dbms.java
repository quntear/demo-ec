package quntear.dec;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.sql.DataSource;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@Singleton
@Startup
public class Dbms {

	@Resource(lookup = "jdbc/__default")
	private DataSource dataSource;

	@Inject
	@ConfigProperty(name = "quntear.dec.sql.create-table-user")
	private String createUserTableStmt;

	@PostConstruct
	public void initialize() {
		createUserTable();
	}
	
	private void createUserTable() {
		try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement();) {
			stmt.executeUpdate(createUserTableStmt);
			
			System.out.println("successfully create table user");
		} catch (SQLException e) {
			System.err.println("unable to create table user: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	@PreDestroy
	private void destroy() {
		dropUserTable();
	}

	private void dropUserTable() {
		try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement();) {
			stmt.executeUpdate("DROP TABLE user");
			
			System.out.println("successfully drop table user");
		} catch (SQLException e) {
			System.err.println("unable to drop table user: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
