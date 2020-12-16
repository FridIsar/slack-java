import java.sql.*;

public class DatabaseUser {
    private Statement stmt=null;
    private Connection con=null;
    private static final DatabaseUser database=new DatabaseUser();

    private DatabaseUser(){
        String dbUrl = "jdbc:mysql://localhost:3306/slack";
        String pwd="@Kaizoku2";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(dbUrl, "root", pwd);
            stmt = con.createStatement();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public static DatabaseUser getDatabase(){
        return database;
    }

    public void afficherBTable(String nomTable){
        try{
            ResultSet rs= stmt.executeQuery("SELECT * from "+nomTable+" ;");
            ResultSetMetaData rsmd=rs.getMetaData();
            int nbCols = rsmd.getColumnCount();
            System.out.println("\n\nLa table "+nomTable+" contient "+nbCols+" colonnes");
            while (rs.next()) {
                for (int i = 1; i <= nbCols; i++)
                    System.out.print(rs.getString(i) + ( i<nbCols ? " | " : "") );
                System.out.println();
            }
            System.out.println("\n\n");
            rs.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void viderTable(String nomTable){
        String requete = "delete from "+nomTable+" ;";
        try{
            stmt.executeUpdate(requete);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public String hacher(String chaine){
        String res=null;
        try{
            ResultSet rs=stmt.executeQuery( "SELECT MD5( '"+chaine+"' );");
            rs.next();
            res= rs.getString(1);
            System.out.println("hachage :"+res);
            rs.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return res;
    }

    public void addUser(String id,String email, String pwd, String pseudo, String date) {
        String requete = "insert into user values ('" + id + "', '" + pseudo + "', '" + email + "', '" + date + "');";
        String requete2= "insert into password (id_user,mdp) values ('"+id+"', '"+pwd+"');";
        try {
            stmt.executeUpdate(requete);
            stmt.executeUpdate(requete2);
            //System.out.println("\t\t" + requete2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(String id) {
        String requetePass = "delete from password where id_user='"+id+"';";
        String requeteUser = "delete from user where id='"+id+"';";
        try {
            stmt.executeUpdate(requetePass);
            stmt.executeUpdate(requeteUser);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User connexion(String email, String mdp){
        User res = null;
        String requeteId = "Select * from user where email ='"+email+"';";
        String requeteMdp;
        try{
            ResultSet rs=stmt.executeQuery(requeteId);
            rs.next();

            requeteMdp= "Select * from password where id_user ='"+rs.getString(1)+"';";
            Statement stmt2 =null;
            try{
                stmt2 = con.createStatement();
            }catch (SQLException e){
                e.printStackTrace();
            }

            ResultSet rs2=stmt2.executeQuery(requeteMdp);
            rs2.next();

            if(rs2.getString(3).equals(mdp)){
                res= new User( rs.getString(3), rs.getString(2) );
            }
            rs.close();
            rs2.close();
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public boolean existEmail(String email){
        boolean bool=false;
        String requete = "Select * from user where email ='"+email+"';";
        try{
            ResultSet rs=stmt.executeQuery(requete);
            bool=rs.next();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return bool;
    }

    public void updateUser(String id, String email, String pseudo){
        String requeteEmail = "update user set email ='"+email+"' where id ='"+id+"';";
        String requetePseudo = "update user set pseudo ='"+pseudo+"' where id ='"+id+"';";
        try{
            stmt.executeUpdate(requeteEmail);
            stmt.executeUpdate(requetePseudo);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void updatePassword(String idUser, String mdp){
        String requete = "update password set mdp ='"+mdp+"' where id_user='"+idUser+"';";
        try{
            stmt.executeUpdate(requete);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public String getPassword(String idUser){
        String res=null;
        String requete = "select mdp from password where id_user = '"+idUser+"';";

        try {
            ResultSet rs = stmt.executeQuery(requete);
            rs.next();

            res = rs.getString(3);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return res;
    }


}
