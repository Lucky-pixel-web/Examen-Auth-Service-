package pe.edu.upeu.msgestionalumno;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.edu.upeu.msgestionalumno.entity.AlumnoEntity;
import pe.edu.upeu.msgestionalumno.errors.AlumnoNotFoundException;
import pe.edu.upeu.msgestionalumno.mapper.AlumnoMapper;
import pe.edu.upeu.msgestionalumno.repository.AlumnoRepository;
import pe.edu.upeu.msgestionalumno.service.AlumnoService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlumnoServiceTest {

    @Mock
    private AlumnoRepository repository;

    @Mock
    private AlumnoMapper mapper;

    @InjectMocks
    private AlumnoService service;

    @Test
    void buscarPorId_noExiste_lanzaExcepcion() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(AlumnoNotFoundException.class, () -> service.buscarPorId(99L));
    }

    @Test
    void buscarPorId_inactivo_lanzaExcepcion() {
        AlumnoEntity entity = new AlumnoEntity();
        entity.setId(1L);
        entity.setEstado("INACTIVO");
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        assertThrows(IllegalArgumentException.class, () -> service.buscarPorId(1L));
    }

    @Test
    void crear_dniDuplicado_lanzaExcepcion() {
        AlumnoEntity existente = new AlumnoEntity();
        existente.setDni("12345678");
        var request = new pe.edu.upeu.msgestionalumno.dto.AlumnoRequest();
        request.setDni("12345678");
        when(repository.findByDni("12345678")).thenReturn(Optional.of(existente));
        assertThrows(IllegalArgumentException.class, () -> service.crear(request));
    }
}