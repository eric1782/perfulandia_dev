package com.perfulandia.usuarioservice.service;

import com.perfulandia.usuarioservice.model.Usuario;
import com.perfulandia.usuarioservice.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) //activa la extencion de mockito
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService; //crea una instancia de servicio FUERA del before each
                    //sirve para ahorrar el tener que definirlo fuera del beforeeach, util para implementacion de mas servicios o funcionalidades a futuro
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Test User");
        usuario.setCorreo("test@example.com");
        usuario.setRol("Usuario");
    }

    // Test 1
    @Test
    @DisplayName("Test 1: Guardar un usuario")
    void cuandoGuardarUsuario_deberiaRetornarUsuario() {
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario usuarioGuardado = usuarioService.guardar(usuario);

        assertNotNull(usuarioGuardado);
        assertEquals("Test User", usuarioGuardado.getNombre());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    // Test 2
    @Test
    @DisplayName("Test 2: Listar todos los usuarios")
    void cuandoListarUsuarios_deberiaRetornarListaDeUsuarios() {
        // Given
        Usuario usuario2 = new Usuario();
        usuario2.setId(2L);
        usuario2.setNombre("Test User 2");
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario, usuario2));

        // When
        List<Usuario> listaUsuarios = usuarioService.listar();

        // Then
        assertNotNull(listaUsuarios);
        assertEquals(2, listaUsuarios.size());
    }

    // Test 3
    @Test
    @DisplayName("Test 3: Obtener usuario por ID")
    void cuandoObtenerPorId_deberiaRetornarUsuario() {
        // Given
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        // When
        Usuario usuarioEncontrado = usuarioService.obtenerPorId(1L);

        // Then
        assertNotNull(usuarioEncontrado);
        assertEquals(1L, usuarioEncontrado.getId());
    }

    // Test 4
    @Test
    @DisplayName("Test 4: Eliminar un usuario")
    void cuandoEliminarUsuario_noDeberiaLanzarExcepcion() {
        // Given
        Long usuarioId = 1L;
        doNothing().when(usuarioRepository).deleteById(usuarioId);

        // When & Then
        assertDoesNotThrow(() -> usuarioService.eliminar(usuarioId));
        verify(usuarioRepository, times(1)).deleteById(usuarioId);
    }
}