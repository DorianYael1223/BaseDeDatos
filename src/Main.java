import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AlumnoDao alumnoDao = new AlumnoDaoImp();
        int opcion;
        Scanner consola = new Scanner(System.in);

        do {
            System.out.println("-------------------");
            System.out.println("Menu de operaciones");
            System.out.println("1.-Agregar un Alumno.");
            System.out.println("2.-Buscar un Alumno.");
            System.out.println("3.-Actualizar Alumno.");
            System.out.println("4.-Borrar Alumno.");
            System.out.println("5.-Mostrar Alumnos");
            System.out.println("6.-Salir.");
            System.out.println("Elige tu opcion:");
            opcion = consola.nextInt();

            switch (opcion) {
                case 1:
                    ingresarUsuario(alumnoDao, consola);
                    guardarAlumnosEnArchivo(alumnoDao, "alumnos.txt");
                    break;
                case 2:
                    buscarUsuario(alumnoDao, consola);
                    break;
                case 3:
                    actualizarUsuario(alumnoDao, consola);
                    guardarAlumnosEnArchivo(alumnoDao, "alumnos.txt");
                    break;
                case 4:
                    borrarAlumno(alumnoDao, consola);
                    guardarAlumnosEnArchivo(alumnoDao, "alumnos.txt");
                    break;
                case 5:
                    mostrarAlumno(alumnoDao, consola);
                    break;
                case 6:
                    System.out.println("Saliendo....");
                    break;
                default:
                    System.out.println("Opcion invalida.");
                    break;
            }
        } while (opcion != 6);
    }

    private static void ingresarUsuario(AlumnoDao alumnoDao, Scanner consola) {
        int noCuenta;
        String nombre, apellido, situacionAcademica;
        double altura;

        System.out.println("Nuevo Alumno:");
        System.out.println("Ingresa el no.Cuenta:");
        noCuenta = consola.nextInt();
        consola.nextLine(); // Limpiar el buffer de la entrada

        System.out.println("Ingresa el nombre:");
        nombre = consola.nextLine();

        System.out.println("Ingresa el apellido:");
        apellido = consola.nextLine();

        System.out.println("Ingresa la altura:");
        altura = consola.nextDouble();
        consola.nextLine(); // Limpiar el buffer de la entrada

        System.out.println("Ingresa el estado Academico:");
        System.out.println("REGULAR, IRREGULAR o BAJA");
        situacionAcademica = consola.nextLine();
        SituacionAcademica situacionAcademica1 = SituacionAcademica.valueOf(situacionAcademica.toUpperCase());

        Alumno nuevoAlumno = new Alumno(noCuenta, nombre, apellido, altura, situacionAcademica1);

        try {
            int resultadoInsercion = alumnoDao.insert(nuevoAlumno);
            System.out.println("Resultado de la inserción: " + resultadoInsercion);
        } catch (Exception e) {
            System.out.println("Error al insertar el alumno: " + e.getMessage());
        }
    }

    private static void buscarUsuario(AlumnoDao alumnoDao, Scanner consola) {
        int noCuenta;
        System.out.println("Alumno a buscar");
        System.out.println("Introduce el no.Cuenta:");
        noCuenta = consola.nextInt();
        System.out.println();

        try {
            Alumno alumnoBuscado = alumnoDao.get(noCuenta);
            if (alumnoBuscado != null) {
                System.out.println("Alumno encontrado:\n" + alumnoBuscado);
            } else {
                System.out.println("No se encontró ningún alumno con el No.Cuenta proporcionado.");
            }
        } catch (Exception e) {
            System.out.println("Error al buscar al alumno " + e.getMessage());
        }
    }

    private static void actualizarUsuario(AlumnoDao alumnoDao, Scanner consola) {
        int noCuentaActualizacion;
        System.out.println("Ingrese el No.Cuenta del alumno a actualizar: ");
        noCuentaActualizacion = consola.nextInt();
        consola.nextLine();

        try {
            Alumno alumnoaActualizar = alumnoDao.get(noCuentaActualizacion);
            if (alumnoaActualizar != null) {
                System.out.println("Alumno " + alumnoaActualizar);

                String nombre, apellido, situacionAcademica;
                double altura;

                System.out.println("--Nuevos datos Alumno:--");
                System.out.println("Ingresa el nombre: ");
                nombre = consola.nextLine();
                System.out.println("Ingresa el apellido:");
                apellido = consola.nextLine();
                System.out.println("Ingresa la altura:");
                altura = consola.nextDouble();
                System.out.println("Ingresa el estado Academico:");
                System.out.println("REGULAR , IRREGULAR O BAJA");
                consola.nextLine();
                situacionAcademica = consola.nextLine();
                SituacionAcademica situacionAcademica1 = SituacionAcademica.valueOf(situacionAcademica.toUpperCase());

                alumnoaActualizar.setNombre(nombre);
                alumnoaActualizar.setApellido(apellido);
                alumnoaActualizar.setAltura(altura);
                alumnoaActualizar.setSituacionAcademica(situacionAcademica1);
                int resultadoActualizacion = alumnoDao.update(alumnoaActualizar);
                System.out.println("Resultado de la actualización: " + resultadoActualizacion);
                System.out.println("Alumno después de la actualización: " + alumnoaActualizar);
            } else {
                System.out.println("No se encontro el alumno");
            }
        } catch (Exception e) {
            System.out.println("Error al actualizar " + e.getMessage());
        }
    }

    private static void borrarAlumno(AlumnoDao alumnoDao, Scanner consola) {
        int noCuenta;
        System.out.println("Eliminar alumno");
        System.out.println("Numero de cuenta del alumno a eliminar:");
        noCuenta = consola.nextInt();
        consola.nextLine();
        try {
            Alumno alumnoAEliminar = alumnoDao.get(noCuenta);
            String opcion;
            if (alumnoAEliminar != null) {
                System.out.println("Estas seguro de esta accion: S/N");
                opcion = consola.nextLine();
                if (opcion.equalsIgnoreCase("S")) {
                    int eliminar = alumnoDao.delete(alumnoAEliminar);
                    System.out.println("Alumno eliminado con exito " + eliminar);
                } else if (opcion.equalsIgnoreCase("N")) {
                    System.out.println("Operacion cancelada");
                } else {
                    System.out.println("No se encontro el alumno");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar al alumno " + e.getMessage());
        }
    }

    private static void mostrarAlumno(AlumnoDao alumnoDao, Scanner consola) {
        try {
            List<Alumno> alumnos = alumnoDao.getAll();
            if (!alumnos.isEmpty()) {
                System.out.println("Lista de todos los alumnos:");
                for (Alumno a : alumnos) {
                    System.out.println(a);
                }
            } else {
                System.out.println("No hay alumnos registrados.");
            }
        } catch (Exception e) {
            System.out.println("Error al obtener la lista de alumnos: " +
                    e.getMessage());
        }
    }

    private static void guardarAlumnosEnArchivo(AlumnoDao alumnoDao, String nombreArchivo) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivo))) {
            List<Alumno> alumnos = alumnoDao.getAll();

            for (Alumno alumno : alumnos) {
                String linea = String.format("%d,%s,%s,%.2f,%s",
                        alumno.getNoCuenta(), alumno.getNombre(), alumno.getApellido(),
                        alumno.getAltura(), alumno.getSituacionAcademica());
                writer.println(linea);
            }

            System.out.println("Datos guardados en el archivo.");
        } catch (IOException | SQLException e) {
            System.out.println("Error al guardar los datos en el archivo: " + e.getMessage());
        }
    }
}
