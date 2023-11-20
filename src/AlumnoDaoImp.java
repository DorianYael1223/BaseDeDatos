import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AlumnoDaoImp implements AlumnoDao {

    // Get an Alumno by ID
    @Override
    public Alumno get(int id) throws SQLException {
        Connection con = DataBase.getConnection();

        Alumno alumno = null;
        String sql = "SELECT id, noCuenta, nombre, apellido, altura, situacionAcademica FROM ingenieria WHERE noCuenta = ?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            int noCuenta = rs.getInt("noCuenta");
            String nombre = rs.getString("nombre");
            String apellido = rs.getString("apellido");
            double altura = rs.getFloat("altura");
            SituacionAcademica situacionAcademica = SituacionAcademica.valueOf(rs.getString("situacionAcademica"));

            alumno = new Alumno(noCuenta, nombre, apellido, altura, situacionAcademica);
        }

        DataBase.closeResultSet(rs);
        DataBase.closePreparedStatement(ps);
        DataBase.closeConnection(con);

        return alumno;
    }

    // Get all Alumnos
    @Override
    public List<Alumno> getAll() throws SQLException {
        Connection con = DataBase.getConnection();
        String sql = "SELECT id, noCuenta, nombre, apellido, altura, situacionAcademica FROM ingenieria";

        List<Alumno> alumnos = new ArrayList<>();

        Statement stmt = con.createStatement();

        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            int noCuenta = rs.getInt("noCuenta");
            String nombre = rs.getString("nombre");
            String apellido = rs.getString("apellido");
            double altura = rs.getFloat("altura");
            SituacionAcademica situacionAcademica = SituacionAcademica.valueOf(rs.getString("situacionAcademica"));

            Alumno alumno = new Alumno(noCuenta, nombre, apellido, altura, situacionAcademica);

            alumnos.add(alumno);
        }

        return alumnos;
    }

    // Insert an Alumno
    @Override
    public int insert(Alumno alumno) throws SQLException {
        Connection con = DataBase.getConnection();

        String sql = "INSERT INTO ingenieria (noCuenta, nombre, apellido, altura, situacionAcademica) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, alumno.getNoCuenta());
        ps.setString(2, alumno.getNombre());
        ps.setString(3, alumno.getApellido());
        ps.setDouble(4, alumno.getAltura());
        ps.setString(5, alumno.getSituacionAcademica().toString());

        int result = ps.executeUpdate();

        DataBase.closePreparedStatement(ps);
        DataBase.closeConnection(con);

        return result;
    }

    // Update an Alumno
    @Override
    public int update(Alumno alumno) throws SQLException {
        Connection con = DataBase.getConnection();
        String sql = "UPDATE ingenieria SET nombre = ?, apellido = ?, altura = ?, situacionAcademica = ? WHERE noCuenta = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, alumno.getNombre());
            ps.setString(2, alumno.getApellido());
            ps.setDouble(3, alumno.getAltura());
            ps.setString(4, alumno.getSituacionAcademica().toString());
            ps.setInt(5, alumno.getNoCuenta());
            return ps.executeUpdate();
        } finally {
            DataBase.closeConnection(con);
        }
    }

    // Delete an Alumno
    @Override
    public int delete(Alumno alumno) throws SQLException {
        Connection con = DataBase.getConnection();
        String sql = "DELETE FROM ingenieria WHERE noCuenta = ?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, alumno.getNoCuenta());
        int result = ps.executeUpdate();
        // Close resources
        DataBase.closePreparedStatement(ps);
        DataBase.closeConnection(con);
        // Return the deletion result
        return result;
    }
}
