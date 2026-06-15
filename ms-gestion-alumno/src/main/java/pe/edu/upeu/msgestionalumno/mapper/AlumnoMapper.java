package pe.edu.upeu.msgestionalumno.mapper;

import org.springframework.stereotype.Component;
import pe.edu.upeu.msgestionalumno.dto.AlumnoRequest;
import pe.edu.upeu.msgestionalumno.dto.AlumnoResponse;
import pe.edu.upeu.msgestionalumno.entity.AlumnoEntity;

@Component
public class AlumnoMapper {

    public AlumnoEntity toEntity(AlumnoRequest request) {
        AlumnoEntity entity = new AlumnoEntity();
        entity.setNombre(request.getNombre());
        entity.setApellido(request.getApellido());
        entity.setDni(request.getDni());
        entity.setCorreo(request.getCorreo());
        entity.setTelefono(request.getTelefono());
        return entity;
    }

    public AlumnoResponse toResponse(AlumnoEntity entity) {
        return new AlumnoResponse(
                entity.getId(),
                entity.getNombre(),
                entity.getApellido(),
                entity.getDni(),
                entity.getCorreo(),
                entity.getTelefono(),
                entity.getEstado()
        );
    }

    public void updateEntity(AlumnoEntity entity, AlumnoRequest request) {
        entity.setNombre(request.getNombre());
        entity.setApellido(request.getApellido());
        entity.setDni(request.getDni());
        entity.setCorreo(request.getCorreo());
        entity.setTelefono(request.getTelefono());
    }
}