package pe.edu.upeu.msgestionalumno.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.upeu.msgestionalumno.dto.AlumnoRequest;
import pe.edu.upeu.msgestionalumno.dto.AlumnoResponse;
import pe.edu.upeu.msgestionalumno.entity.AlumnoEntity;
import pe.edu.upeu.msgestionalumno.errors.AlumnoNotFoundException;
import pe.edu.upeu.msgestionalumno.mapper.AlumnoMapper;
import pe.edu.upeu.msgestionalumno.repository.AlumnoRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlumnoService {

    private final AlumnoRepository repository;
    private final AlumnoMapper mapper;

    public List<AlumnoResponse> listar() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public AlumnoResponse buscarPorId(Long id) {
        AlumnoEntity entity = repository.findById(id)
                .orElseThrow(() -> new AlumnoNotFoundException(id));
        if ("INACTIVO".equals(entity.getEstado())) {
            throw new IllegalArgumentException("El alumno con id " + id + " está INACTIVO y no puede ser consultado.");
        }
        return mapper.toResponse(entity);
    }

    public AlumnoResponse crear(AlumnoRequest request) {
        repository.findByDni(request.getDni()).ifPresent(a -> {
            throw new IllegalArgumentException("Ya existe un alumno con DNI: " + request.getDni());
        });
        AlumnoEntity entity = mapper.toEntity(request);
        entity.setEstado("ACTIVO");
        return mapper.toResponse(repository.save(entity));
    }

    public AlumnoResponse actualizar(Long id, AlumnoRequest request) {
        AlumnoEntity entity = repository.findById(id)
                .orElseThrow(() -> new AlumnoNotFoundException(id));
        if ("INACTIVO".equals(entity.getEstado())) {
            throw new IllegalArgumentException("El alumno con id " + id + " está INACTIVO y no puede ser modificado.");
        }
        mapper.updateEntity(entity, request);
        return mapper.toResponse(repository.save(entity));
    }

    public void eliminar(Long id) {
        AlumnoEntity entity = repository.findById(id)
                .orElseThrow(() -> new AlumnoNotFoundException(id));
        entity.setEstado("INACTIVO");
        repository.save(entity);
    }

    public List<AlumnoResponse> buscarPorNombre(String nombre) {
        List<AlumnoEntity> resultado = repository.findByNombreContainingIgnoreCase(nombre);
        if (resultado.isEmpty()) {
            throw new IllegalArgumentException("No se encontró ningún alumno con el nombre: '" + nombre + "'");
        }
        return resultado.stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
}