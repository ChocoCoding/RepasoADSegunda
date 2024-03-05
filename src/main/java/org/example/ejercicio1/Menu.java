package org.example.ejercicio1;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
        static final String dbURL = "jdbc:postgresql://localhost:5432/hospital";
        static final String user = "postgres";
        static final String password = "root";


        //Consultas
        private static final String SQL_LISTAR_PACIENTE_ID = "SELECT paciente_id, (datos_personales).nombre as Nombre, (datos_personales).edad as Edad, (paciente_info).numero_historia as NumeroHistoria, (paciente_info).grupo_sanguineo as GrupoS from objetos.pacientes WHERE paciente_id = ?";
        private static final String SQL_LISTAR_PACIENTES = "SELECT paciente_id, (datos_personales).nombre as Nombre, (datos_personales).edad as Edad, (paciente_info).numero_historia as NumeroHistoria, (paciente_info).grupo_sanguineo as GrupoS from objetos.pacientes";
        private static final String SQL_LISTAR_MEDICO_ID = "SELECT medico_id, (datos_personales).nombre as Nombre, (datos_personales).edad as Edad, (medico_info).codigo_medico as codM, (medico_info).especialidad as especialidad from objetos.medicos WHERE medico_id = ?";
        private static final String SQL_LISTAR_MEDICO = "SELECT medico_id, (datos_personales).nombre as Nombre, (datos_personales).edad as Edad, (medico_info).codigo_medico as codM, (medico_info).especialidad as especialidad from objetos.medicos";
        private static final String SQL_LISTAR_CITA_MEDICA_PACIENTE_ID = "SELECT cita_id,fecha_hora, paciente_id,medico_id from objetos.citasmedicas WHERE paciente_id = ?";
        private static final String SQL_LISTAR_CITA_MEDICA_MEDICO_ID = "SELECT cita_id,fecha_hora, paciente_id,medico_id from objetos.citasmedicas WHERE medico_id = ?";
        private static final String SQL_LISTAR_EXAMEN_MEDICO_PACIENTE_ID = "SELECT examen_id,paciente_id, (examen_info).nombre_examen as nombreE,(examen_info).fecha_realizacion as fechaR,(examen_info).resultado as resultado from objetos.examenesmedicos WHERE paciente_id = ?";
        private static final String SQL_LISTAR_PACIENTE_GRUPO_SANGUINEO = "SELECT paciente_id, (datos_personales).nombre as Nombre, (datos_personales).edad as Edad, (paciente_info).numero_historia as NumeroHistoria, (paciente_info).grupo_sanguineo as GrupoS from objetos.pacientes WHERE (paciente_info).grupo_sanguineo = ?";
        private static final String SQL_MEDICO_PACIENTE = "SELECT med.medico_id, (datos_personales).nombre as Nombre, (datos_personales).edad as Edad, (medico_info).codigo_medico as codM, (medico_info).especialidad as especialidad from objetos.Medicos as med INNER JOIN objetos.Citasmedicas as cit on med.medico_id = cit.medico_id WHERE cit.paciente_id = ?";

        //INSERT,MOD,DEL
        private static final String SQL_INSERTAR_MEDICO = "INSERT INTO objetos.medicos (datos_personales,medico_info) VALUES (ROW(?,?),ROW(?,?))";
        private static final String SQL_INSERTAR_PACIENTE = "INSERT INTO objetos.pacientes (datos_personales,paciente_info) VALUES (ROW(?,?),ROW(?,?))";
        private static final String SQL_INSERTAR_CITA_MEDICA = "INSERT INTO objetos.citasmedicas (fecha_hora,paciente_id,medico_id) VALUES (?,?,?)";
        private static final String SQL_INSERTAR_EXAMEN_MEDICO = "INSERT INTO objetos.examenesmedicos (paciente_id,examen_info) VALUES (?,ROW(?,?,?))";

        //DEL
        private static final String SQL_ELIMINAR_MEDICO = "BEGIN;DELETE FROM objetos.citasmedicas WHERE medico_id = ?;DELETE from objetos.medicos WHERE medico_id = ?;COMMIT;";

        //UPDATE
        private static final String SQL_MODIFICAR_MEDICO = "UPDATE objetos.medicos SET datos_personales = (ROW(?,?)), medico_info = (ROW(?,?)) WHERE medico_id = ?";

    public static void dialog(){
            try(Connection conn = DriverManager.getConnection(dbURL,user,password)) {
                crearCitasMedicas(tablaExiste(conn),conn);
                int opt = 10000;
                do {
                    System.out.println("1.- Insertar un Medico");
                    System.out.println("2.- Modificar un Medico");
                    System.out.println("3.- Eliminar un Medico");
                    System.out.println("4.- Insertar un Paciente");
                    System.out.println("5.- Modificar un Paciente");
                    System.out.println("6.- Eliminar un Paciente");
                    System.out.println("7.- Insertar un ExamenesMedico");
                    System.out.println("8.- Modificar un ExamenesMedico");
                    System.out.println("9.- Eliminar un ExamenesMedico");
                    System.out.println("10.- Insertar un CitasMedicas");
                    System.out.println("11.- Modificar un CitasMedicas");
                    System.out.println("12.- Eliminar un CitasMedicas");
                    System.out.println("13.- Listar la información de un Paciente  buscándolo por id.");
                    System.out.println("14.- Listar toda la información de todos los Paciente.");
                    System.out.println("15.- Listar la información de un Medicos buscándolo por id.");
                    System.out.println("16.- Listar toda la información de todos los Medicos.");
                    System.out.println("17.- Listar toda la información de un CitasMedicas buscándolo por id de Paciente.");
                    System.out.println("18.- Listar toda la información de un CitasMedicas buscándolo por id de Médico.");
                    System.out.println("19.- Listar todos los ExamenesMedicos de un Paciente.");
                    System.out.println("20.- Listar la información de los Pacientes buscándolos por grupo_sanguineo");
                    System.out.println("21.- Listar la información de los Medicos que atendieron a un determinado Paciente (se dispone del identificador del Paciente)");

                    opt = pedirInt("Introduce una opción: ");

                    switch (opt){
                        case 1:
                            insertarMedico(conn);
                            break;
                        case 2:
                            modificarMedico(conn);
                            break;
                        case 3:
                            eliminarMedico(conn);
                            break;
                        case 4:
                            insertarPaciente(conn);
                            break;
                        case 5:
                            break;
                        case 6:
                            break;
                        case 7:
                            insertarExamenMedico(conn);
                            break;
                        case 8:
                            break;
                        case 9:
                            break;
                        case 10:
                            insertarCitaMedica(conn);
                            break;
                        case 11:
                            break;
                        case 12:
                            break;
                        case 13:
                            listarPacienteID(conn);
                            break;
                        case 14:
                            listarPacientes(conn);
                            break;
                        case 15:
                            listarMedicoID(conn);
                            break;
                        case 16:
                            listarMedicos(conn);
                            break;
                        case 17:
                            listarCitasPorPaciente(conn);
                            break;
                        case 18:
                            listarCitasPorMedico(conn);
                            break;
                        case 19:
                            listarExamenesMedicosPaciente(conn);
                            break;
                        case 20:
                            listarPacientesGrupoSanguineo(conn);
                            break;
                        case 21:
                            listarMedicoPaciente(conn);
                            break;
                        default:
                            System.out.println("No es una opcion válida!!!!");

                    }
                }while (opt != 0);

            } catch (SQLException e) {
                e.printStackTrace();
            }catch (InputMismatchException e){
                System.out.println("ERROR!!!!! El dato introducido no es correcto");
            }
        }

    private static void modificarMedico(Connection conn) {

    }

    private static void eliminarMedico(Connection conn) {
        int idM = pedirInt("Introduce el id");

        try {
            PreparedStatement ps = conn.prepareStatement(SQL_ELIMINAR_MEDICO);
            ps.setInt(1,idM);
            ps.setInt(2,idM);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private static void insertarExamenMedico(Connection conn) {
        int idP = pedirInt("Introduce la idPaciente");
        String nombre = pedirString("Introduce el nombre del examen");
        String fecha = pedirString("Introduce la fecha  formato -> dd-mm-yyyy");
        String res = pedirString("Introduce resultado");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse(fecha,formatter);

        try {
            PreparedStatement ps = conn.prepareStatement(SQL_INSERTAR_EXAMEN_MEDICO);
            ps.setInt(1,idP);
            ps.setString(2, nombre);
            ps.setDate(3, Date.valueOf(date));
            ps.setString(4,res);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private static void insertarCitaMedica(Connection conn) {
        String fecha = pedirString("Introduce la fecha y la hora en el siguiente formato -> yyyy-mm-dd hh:mm:ss");
        int idP = pedirInt("Introduce la idPaciente");
        int idM = pedirInt("Introduce la idMedico");

        try {
            PreparedStatement ps = conn.prepareStatement(SQL_INSERTAR_CITA_MEDICA);
            ps.setTimestamp(1, Timestamp.valueOf(fecha));
            ps.setInt(2,idP);
            ps.setInt(3,idM);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void insertarPaciente(Connection conn) {
        String nombre = pedirString("Introduce el nombre");
        int edad = pedirInt("Introduce la edad");
        String ns = pedirString("Introduce el numero_historia");
        String gp = pedirString("Introduce grupo_sanguineo");

        try {
            PreparedStatement ps = conn.prepareStatement(SQL_INSERTAR_PACIENTE);
            ps.setString(1,nombre);
            ps.setInt(2,edad);
            ps.setString(3,ns);
            ps.setString(4,gp);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void insertarMedico(Connection conn) {
        String nombre = pedirString("Introduce el nombre");
        int edad = pedirInt("Introduce la edad");
        String codigo = pedirString("Introduce el codigo");
        String gp = pedirString("Introduce especialidad");

        try {
            PreparedStatement ps = conn.prepareStatement(SQL_INSERTAR_MEDICO);
            ps.setString(1,nombre);
            ps.setInt(2,edad);
            ps.setString(3,codigo);
            ps.setString(4,gp);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void listarMedicoPaciente(Connection conn) {
        try {
            int idP = pedirInt("Introduce la id del paciente");
            PreparedStatement ps = conn.prepareStatement(SQL_MEDICO_PACIENTE);
            ps.setInt(1,idP);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                System.out.println(rs.getString("Nombre"));
                System.out.println(rs.getInt("Edad"));
                System.out.println(rs.getString("codM"));
                System.out.println(rs.getString("especialidad"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private static void listarPacientesGrupoSanguineo(Connection conn) {
        try {
            String gp = pedirString("Introduce el grupo Sanguineo del paciente");
            PreparedStatement ps = conn.prepareStatement(SQL_LISTAR_PACIENTE_GRUPO_SANGUINEO);
            ps.setString(1,gp);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                System.out.println(rs.getString("Nombre"));
                System.out.println(rs.getInt("Edad"));
                System.out.println(rs.getString("NumeroHistoria"));
                System.out.println(rs.getString("GrupoS"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private static void listarExamenesMedicosPaciente(Connection conn) {
        try {
            int idP = pedirInt("Introduce la id del paciente");
            PreparedStatement ps = conn.prepareStatement(SQL_LISTAR_EXAMEN_MEDICO_PACIENTE_ID);
            ps.setInt(1,idP);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                System.out.println(rs.getString("examen_id"));
                System.out.println(rs.getInt("paciente_id"));
                System.out.println(rs.getString("nombreE"));
                System.out.println(rs.getDate("fechaR"));
                System.out.println(rs.getString("resultado"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void listarCitasPorMedico(Connection conn) {
        try {
            int idM = pedirInt("Introduce la id del medico");
            PreparedStatement ps = conn.prepareStatement(SQL_LISTAR_CITA_MEDICA_MEDICO_ID);
            ps.setInt(1,idM);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                System.out.println(rs.getString("cita_id"));
                System.out.println(rs.getDate("fecha_hora"));
                System.out.println(rs.getString("paciente_id"));
                System.out.println(rs.getString("medico_id"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private static void listarCitasPorPaciente(Connection conn) {
        try {
            int idP = pedirInt("Introduce la id del paciente");
            PreparedStatement ps = conn.prepareStatement(SQL_LISTAR_CITA_MEDICA_PACIENTE_ID);
            ps.setInt(1,idP);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                System.out.println(rs.getString("cita_id"));
                System.out.println(rs.getDate("fecha_hora"));
                System.out.println(rs.getString("paciente_id"));
                System.out.println(rs.getString("medico_id"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private static void listarMedicos(Connection conn) {
        try {
            PreparedStatement ps = conn.prepareStatement(SQL_LISTAR_MEDICO);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                System.out.println(rs.getString("Nombre"));
                System.out.println(rs.getInt("Edad"));
                System.out.println(rs.getString("codM"));
                System.out.println(rs.getString("especialidad"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void listarMedicoID(Connection conn) {
        try {
            int idM = pedirInt("Introduce la id del medico");
            PreparedStatement ps = conn.prepareStatement(SQL_LISTAR_MEDICO_ID);
            ps.setInt(1,idM);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                System.out.println(rs.getString("Nombre"));
                System.out.println(rs.getInt("Edad"));
                System.out.println(rs.getString("codM"));
                System.out.println(rs.getString("especialidad"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void listarPacientes(Connection conn) {
        try {
            PreparedStatement ps = conn.prepareStatement(SQL_LISTAR_PACIENTES);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                System.out.println(rs.getString("Nombre"));
                System.out.println(rs.getInt("Edad"));
                System.out.println(rs.getString("NumeroHistoria"));
                System.out.println(rs.getString("GrupoS"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    private static void listarPacienteID(Connection conn) {
        try {
            int idP = pedirInt("Introduce la id del paciente");
            PreparedStatement ps = conn.prepareStatement(SQL_LISTAR_PACIENTE_ID);
            ps.setInt(1,idP);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                System.out.println(rs.getString("Nombre"));
                System.out.println(rs.getInt("Edad"));
                System.out.println(rs.getString("NumeroHistoria"));
                System.out.println(rs.getString("GrupoS"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean tablaExiste(Connection conn) {
        try {
            PreparedStatement ps = conn.prepareStatement("select * from pg_tables where tablename='citasmedicas';");
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void crearCitasMedicas(Boolean tablaExist,Connection conn) {
            if (!tablaExist){
                try {
                    PreparedStatement ps = conn.prepareStatement("CREATE TABLE objetos.CitasMedicas (cita_id serial PRIMARY KEY,fecha_hora TIMESTAMP,paciente_id INT REFERENCES objetos.Pacientes(paciente_id),medico_id INT REFERENCES objetos.Medicos(medico_id));");
                    ps.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }else System.out.println("La tabla ya existe");
    }

    private static String pedirString(String mensaje) {
        Scanner sc = new Scanner(System.in);
        System.out.println(mensaje);
        return sc.nextLine();
    }

    private static int pedirInt(String mensaje) {
        Scanner sc = new Scanner(System.in);
        System.out.println(mensaje);
        return sc.nextInt();
    }

    private static BigDecimal pedirDecimal(String mensaje) {
        Scanner sc = new Scanner(System.in);
        System.out.println(mensaje);
        return sc.nextBigDecimal();
    }
}
