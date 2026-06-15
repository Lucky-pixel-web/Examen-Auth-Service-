package pe.edu.upeu.msgestionalumno.errors;

public class AlumnoNotFoundException extends RuntimeException {
    public AlumnoNotFoundException(Long id) {
        super("No existe el alumno con id: " + id);
    }
}