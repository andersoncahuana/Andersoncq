package pe.edu.upeu.repository;

import pe.edu.upeu.model.Votante;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VotanteRepository {
    private static VotanteRepository instance;
    private final List<Votante> votantes;

    private VotanteRepository() {
        this.votantes = new ArrayList<>();
    }

    public static VotanteRepository getInstance() {
        if (instance == null) {
            instance = new VotanteRepository();
        }
        return instance;
    }

    public void save(Votante votante) {
        votantes.add(votante);
    }

    public List<Votante> findAll() {
        return new ArrayList<>(votantes); // Return a copy
    }

    public void update(Votante votante) {
        for (int i = 0; i < votantes.size(); i++) {
            if (votantes.get(i).getFolio().equals(votante.getFolio())) {
                votantes.set(i, votante);
                return;
            }
        }
    }

    public void delete(String folio) {
        votantes.removeIf(v -> v.getFolio().equals(folio));
    }

    public Optional<Votante> findByFolio(String folio) {
        return votantes.stream()
                .filter(v -> v.getFolio().equals(folio))
                .findFirst();
    }
}
