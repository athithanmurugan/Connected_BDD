import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        /* Load JDBC Driver. */
        try {
            Class.forName( "oracle.jdbc.OracleDriver" );
            // Class.forName("oracle.jdbc.OracleDriver") ;
        } catch ( ClassNotFoundException e ) {
            e.printStackTrace();
        }
        String url = "jdbc:oracle:thin:SYSTEM/oracle@localhost:1521:xe";
        String user = "SYSTEM";
        String pass = "oracle";
        Connection connexion = null;
        try {
            connexion = DriverManager.getConnection( url, user, pass );


            /* Requests to bdd will be here */
            //System.out.println("Bdd Connected");

            //displayDepartment(connexion);                //exercise = 1
            //Scanner sc = new Scanner(System.in);
            //System.out.println("Enter an Employee ID : ");
            //int EmpID = sc.nextInt();

            //System.out.println("Enter an Department Number : ");
            //int Deptno = sc.nextInt();
            //moveDepartment(connexion, EmpID, Deptno); // exercise = 2

            //displayTable(connexion, "emp");                // exercise = 3


            //moveDepartmentPrepared(connexion, EmpID, Deptno); // exercise = 5


        } catch ( SQLException e ) {
            e.printStackTrace();
        } finally {
            if ( connexion != null )
                try {
                    connexion.close();
                } catch ( SQLException ignore ) {
                    ignore.printStackTrace();
                }
        }






    }


    public static void displayDepartment(Connection connexion) throws SQLException {
        Statement statement = connexion.createStatement();
        ResultSet resultat = statement.
                executeQuery("SELECT deptno, dname, loc FROM dept");

        while (resultat.next()) {
            int deptno = resultat.getInt("deptno");
            String dname = resultat.getString("dname");
            String Location = resultat.getString("loc");

            System.out.println("Department " + deptno + " is for "
                    + dname + " and located in : " + Location);
        }
        resultat.close();
    }


    public static void moveDepartment(Connection connection, int Empno, int Deptno) throws SQLException
    {
        Statement statement = connection.createStatement();
        int rowsUpdated = statement.executeUpdate("UPDATE emp SET deptno = " + Deptno + " WHERE empno = " + Empno);

        System.out.print("Employee N " + Empno + " is in the Department N : " + Deptno);
    }

    public static void moveDepartmentPrepared(Connection connection, int Empno, int Deptno) throws SQLException
    {
        String command = "UPDATE EMP set DEPTNO = ? WHERE EMPNO = ?";
        try (PreparedStatement updatemp = connection.prepareStatement(command)) {
            updatemp.setLong(1, Deptno);
            updatemp.setLong(2, Empno);
            updatemp.execute();
            System.out.print("Employee N " + Empno + " is in the Department N : " + Deptno);
        } catch(Exception err)
        {
            err.printStackTrace();
        }

    }




    public static void displayTable(Connection connection, String tableName) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName);
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        for (int i = 1; i <= columnCount; i++) {
            System.out.print(metaData.getColumnName(i) + " | ");
        }
        System.out.println();

        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(resultSet.getString(i) + " | ");
            }
            System.out.println();
        }
    }


}
