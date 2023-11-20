import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        AlumnoDao alumnoDao = new AlumnoDaoImp();

		//Alta
		Alumno alumno = new Alumno(13, "carlos", "bernal", 1.60, SituacionAcademica.REGULAR);
		int result=alumnoDao.insert(alumno);		
		System.out.println(result);

        //busqueda
        Alumno alumno2= null;
        alumno2 = alumnoDao.get(13);
		System.out.println(alumno2);

        //busqueda todos 
        List<Alumno> alumnos = new ArrayList<Alumno>();

        alumnos = alumnoDao.getAll();

        for (Alumno a : alumnos) {
            System.out.println(a);
            
        }


    }
}
