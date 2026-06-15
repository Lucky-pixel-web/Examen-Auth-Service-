package pe.edu.upeu.msgestionalumno.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.msgestionalumno.entity.AlumnoEntity;

import java.util.List;
import java.util.Optional;

public interface AlumnoRepository extends JpaRepository<AlumnoEntity, Long> {
    Optional<AlumnoEntity> findByDni(String dni);
    List<AlumnoEntity> findByNombreContainingIgnoreCase(String nombre);
}