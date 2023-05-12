import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.ResultSet;

public class AGENDA {

    static final String DB_URL = "jdbc:mysql://10.230.109.197/AGENDA";
    static final String USER = "root";
    static final String PASS = "";
    static final String QUERY = "";

    public static void main(String[] args) {
        String BasedeDatos = "AGENDA";
        String sql = "";
        //Realizammos la conexión con la base de datos
        try (Connection conn = DriverManager.getConnection((DB_URL), USER, PASS); Statement stmt = conn.createStatement();) {
            //Eliminamos la tabla antes para que no de errores al volver a ejecutarla si esta creada
            sql = "DROP TABLE CLIENTE";
            stmt.executeUpdate(sql);
            System.out.println("Tabla cliente borrada satisfactoriamente...");
            System.out.println("Tabla cliente creadsa satisfactoriamente...");
            //Crteamos la tbal en la base de datos
            Statement stat = conn.createStatement();
            sql = "CREATE TABLE  CLIENTE ("
                    + "	ID INTEGER(38),"
                    + "	NOMBRE VARCHAR(25) NOT NULL,"
                    + "	TELEFONO INTEGER(25) NOT NULL"
                    + ");";
            
            stmt.executeUpdate(sql);
            
            // Insertar datos de ejemplo en la tabla CLIENTE
            System.out.println("Insertados datos en la tabla...");
            sql = "INSERT INTO CLIENTE VALUES (1, 'Zara',619508282)";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO CLIENTE VALUES (2, 'Mahnaz', 692525248)";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO CLIENTE VALUES (3, 'Zaid', 692536285)";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO CLIENTE VALUES(4, 'Sumit', 648952563)";

            Scanner scanner = new Scanner(System.in);
            int opcion;
            
            // Mostramos el menú
            do {
                System.out.println("");
                System.out.println("**---- MENU ----**");
                System.out.println("1. Mostrar todos los contactos");
                System.out.println("2. Añadir un contacto");
                System.out.println("3. Editar un contacto");
                System.out.println("4. Eliminar un contacto");
                System.out.println("0. Salir");
                System.out.print("Elige una opción: ");
                opcion = scanner.nextInt();
                
                switch (opcion) {
                    case 1:
                        mostrarContactos(stmt);
                        break;
                    case 2:
                        agregarContacto(stmt, scanner);
                        break;
                    case 3:
                        editarContacto(stmt, scanner);
                        break;
                    case 4:
                        eliminarContacto(stmt, scanner);
                        break;
                    case 0:
                        System.out.println("Saliendo...");
                        break;
                    default:
                        System.out.println("Opción inválida. Por favor, elige una opción válida.");
                        break;
                }
            } while (opcion != 0);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

public static void mostrarContactos(Statement stmt) throws SQLException {
    //Realizamos la SELECT que nos dara todos los datos
    String sql = "SELECT * FROM CLIENTE";
    ResultSet rs = stmt.executeQuery(sql);
    System.out.println("**---- CONTACTOS ----**");
    //Realizamos el bucle para sacar todos los valores
    while (rs.next()) {
        int id = rs.getInt("ID");
        String nombre = rs.getString("NOMBRE");
        int telefono = rs.getInt("TELEFONO");
        System.out.println("ID: " + id + ", Nombre: " + nombre + ", Teléfono: " + telefono);
    }
    rs.close();
}

public static void agregarContacto(Statement stmt, Scanner scanner) throws SQLException {
    System.out.println("**---- AGREGAR CONTACTO ----**");
    System.out.print("ID: ");
    int id = scanner.nextInt();
    System.out.print("Nombre: ");
    String nombre = scanner.next();
    System.out.print("Teléfono: ");
    int telefono = scanner.nextInt();

    String sql = "INSERT INTO CLIENTE VALUES (" + id + ", '" + nombre + "', " + telefono + ")";
    stmt.executeUpdate(sql);
    System.out.println("Contacto agregado satisfactoriamente.");
}

public static void editarContacto(Statement stmt, Scanner scanner) throws SQLException {
    System.out.println("**---- EDITAR CONTACTO ----**");
    System.out.print("ID del contacto a editar: ");
    int id = scanner.nextInt();

    String sql = "SELECT * FROM CLIENTE WHERE ID = " + id;
    ResultSet rs = stmt.executeQuery(sql);

    if (rs.next()) {
        System.out.print("Nuevo nombre: ");
        String nuevoNombre = scanner.next();
        System.out.print("Nuevo teléfono: ");
        int nuevoTelefono = scanner.nextInt();

        sql = "UPDATE CLIENTE SET NOMBRE = '" + nuevoNombre + "', TELEFONO = " + nuevoTelefono + " WHERE ID = " + id;
        stmt.executeUpdate(sql);
        System.out.println("Contacto editado satisfactoriamente.");
    } else {
        System.out.println("No se encontró ningún contacto con el ID especificado.");
    }

    rs.close();
}

public static void eliminarContacto(Statement stmt, Scanner scanner) throws SQLException {
    System.out.println("**---- ELIMINAR CONTACTO ----**");
    System.out.print("ID del contacto a eliminar: ");
    int id = scanner.nextInt();

    String sql = "SELECT * FROM CLIENTE WHERE ID = " + id;
    ResultSet rs = stmt.executeQuery(sql);

    if (rs.next()) {
        sql = "DELETE FROM CLIENTE WHERE ID = " + id;
        stmt.executeUpdate(sql);
        System.out.println("Contacto eliminado satisfactoriamente.");
    } else {
        System.out.println("No se encontró ningún contacto con el ID especificado.");
    }

    rs.close();
}

}
