package quntear.dec.user;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.sql.DataSource;

import org.apache.commons.io.IOUtils;

@Singleton
@Startup
public class Dbms {

	@Resource(lookup = "jdbc/__default")
	private DataSource dataSource;

	@PostConstruct
	public void initialize() {
		executeUpdate("sqls/drop.sql");
		executeUpdate("sqls/create.sql");
	}
	
	@PreDestroy
	private void destroy() {
		executeUpdate("sqls/drop.sql");
	}
	
	private void executeUpdate(String sqlPath) {
		try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement();) {
			String sql = IOUtils.toString(getModule().getResourceAsStream(sqlPath), UTF_8); 
			stmt.executeUpdate(sql);
			
			System.out.println("successfully execute statement: " + sql);
		} catch (IOException | SQLException e) {
			System.err.println("unable to execute sql: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private Module getModule() {
		return this.getClass().getModule();
	}
}
