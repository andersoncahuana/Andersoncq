package pe.edu.upeu.service;

import pe.edu.upeu.model.Votante;

import java.util.List;
import java.util.Optional;

public interface VotanteServiceInter {
    void registrarVotante(Votante votante);
    List<Votante> obtenerTodos();
    void actualizarVotante(Votante votante);
    void eliminarVotante(String folio);
    Optional<Votante> buscarPorFolio(String folio);
    List<Votante> buscarPorNombre(String nombre);
}
