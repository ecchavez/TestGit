package mx.com.grupogigante.gestionvivienda.resources.conexion;
import  org.springframework.jdbc.core.*;  
import  org.springframework.jdbc.datasource.*;  

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

public class DBConnection {
	
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate connectDataBase() {
		//desarrollo
		String  dburl  =  "jdbc:db2://10.1.15.161:50002/db_ggi";
		String  name  =  "vivienda";
		String  pass  =  "viviendaggi";
		
		/*produccion
		String  dburl  =  "jdbc:db2://10.1.15.160:50003/DB_GGI";  
		String  name  =  "vivienda";
		String  pass  =  "v1v13nd4w34pp";*/
				
		/*String  dburl  =  "jdbc:mysql://localhost:3306/gestionvivienda";  
		String  name  =  "root";  
		String  pass  =  "spr3c10";*/
		  
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName("com.ibm.db2.jcc.DB2Driver");
		//dataSource.setDriverClassName("com.mysql.jdbc.Driver");
	    dataSource.setUrl(dburl);
	    dataSource.setUsername(name);
	    dataSource.setPassword(pass);
	    return jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public JdbcTemplate connectMSSQLDataBase() {	  
		//desarrollo
		String  dburl = "jdbc:sqlserver://10.1.91.137;database=vivienda;";
		String  name  =  "ggvivienda";
		String  pass  =  "vivienda12";
		
		/*produccion
		String  dburl = "jdbc:sqlserver://10.1.91.134;database=vivienda;";
		String  name  =  "ggvivienda";
		String  pass  =  "vivienda13";*/
		
		/*SQLServerDataSource ds = new SQLServerDataSource();
		ds.setUser("MyUserName");
		ds.setPassword("*****");
		ds.setServerName("localhost");
		ds.setPortNumber(1433); 
		ds.setDatabaseName("AdventureWorks");
		Connection con = ds.getConnection();*/

		DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	    dataSource.setUrl(dburl);
	    dataSource.setUsername(name);
	    dataSource.setPassword(pass);
	    return jdbcTemplate = new JdbcTemplate(dataSource);
	}
}	

