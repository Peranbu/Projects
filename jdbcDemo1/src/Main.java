import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;

public class Main{
    /*
    Import the package
    Load and register the driver
    Establish a connection
    create a statement(statement(sql injection,everytime compile takes more time),
    PreparedStatement,CallableStatement
    Execute the statement/sql query
    process the results
    close the connection
    */

    /*
    prepared statement
    one time compile(pre compiled)
    stores data in the cache and updates the value
    avoid sql injection no quotations
    */
    public static void main(String[] args) throws SQLException{
        //Class.forName("com.mysql.cj.jdbc.Driver");
        String url="jdbc:mysql://localhost:3306/jdbcforyoutube";
        String username="root";
        String password="Peranbu=123??";
        Connection con=DriverManager.getConnection(url,username,password);

        //---------------statement ----------------------------
        //Statement st=con.createStatement(); //create statement

        //String sql="select * from student";
        //ResultSet rs=st.executeQuery(sql); //execute the statement

        /*
        rs.next(); //skipping the header
        //System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3));
        System.out.println(rs.getInt("rno")+" "+rs.getString("name")+" "+rs.getString("location"));
        rs.next();
        System.out.println(rs.getInt("rno")+" "+rs.getString("name")+" "+rs.getString("location"));
        rs.next();
        System.out.println(rs.getInt("rno")+" "+rs.getString("name")+" "+rs.getString("location"));
        */

        /*
        String sql="create table course(cid int,cname varchar(30))";
        st.executeUpdate(sql);
        System.out.println("Connection established");
         */

        /*
        String sql="insert into student values(25,'rf','chennai')";
        int val=st.executeUpdate(sql);
        System.out.println(val + " rows affected");
        con.close(); //close the connection
         */

        /*
        String sql="update student set location='chennai' where rno=1";
        int val=st.executeUpdate(sql);
        System.out.println(val + " rows affected");
        con.close(); //close the connection
        */

        /*
        String sql="delete from student where rno=1";
        int val=st.executeUpdate(sql);
        System.out.println(val + " rows affected");
        con.close(); //close the connection
        */

        /*
        int rno=1;
        String name="rabada";
        String location="south";

        //String sql="insert into student values(rno,name,location)"; -consider everything as string inside the ""
        String sql="insert into student values("+rno+",'"+name+"','"+location+"')";
        int val=st.executeUpdate(sql);

        System.out.println(val+"rows affected");
        */

        // ----------prepared statement----------------
        /*
        int rno=20;
        String name="smith";
        String location="australia";

        String sql="insert into student values(?,?,?)";
        PreparedStatement st=con.prepareCall(sql);

        st.setInt(1,rno);
        st.setString(2,name);
        st.setString(3,location);
        int val=st.executeUpdate();
        System.out.println(val+"rows affected");
        */

        String sql="select * from student where rno=?";
        PreparedStatement st=con.prepareStatement(sql);
        st.setInt(1,22);
        ResultSet rs=st.executeQuery();
        while(rs.next()){
            System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3));
        }

    }
}
