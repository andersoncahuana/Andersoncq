package pe.edu.upeu.service;

import pe.edu.upeu.model.Votante;
import pe.edu.upeu.repository.VotanteRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class VotanteServiceImp implements VotanteServiceInter {

    private final VotanteRepository repository;

    public VotanteServiceImp() {
        this.repository = VotanteRepository.getInstance();
    }

    @Override
    public void registrarVotante(Votante votante) {
        repository.save(votante);
    }

    @Override
    public List<Votante> obtenerTodos() {
        return repository.findAll();
    }

    @Override
    public void actualizarVotante(Votante votante) {
        repository.update(votante);
    }

    @Override
    public void eliminarVotante(String folio) {
        repository.delete(folio);
    }

    @Override
    public Optional<Votante> buscarPorFolio(String folio) {
        return repository.findByFolio(folio);
    }

    @Override
    public List<Votante> buscarPorNombre(String nombre) {
        return repository.findAll().stream()
                .filter(v -> v.getNombreCompleto().toLowerCase().contains(nombre.toLowerCase()))
                .collect(Collectors.toList());
    }
}
