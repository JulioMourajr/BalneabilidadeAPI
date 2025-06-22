package com.moura.BalneabilidadeAPI.service;

import com.moura.BalneabilidadeAPI.model.Praia;
import com.moura.BalneabilidadeAPI.repository.PraiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PraiaService {

    private final PraiaRepository praiaRepository;

    @Autowired
    public PraiaService(PraiaRepository praiaRepository) {
        this.praiaRepository = praiaRepository;
    }

    public List<Praia> getAllPraias() {
        return praiaRepository.findAll();
    }

    public Optional<Praia> getPraiaById(Long id) {
        return praiaRepository.findById(id);
    }

    public Praia savePraia(Praia praia) {
        return praiaRepository.save(praia);
    }

    public List<Praia> saveAllPraias(List<Praia> praias) {
        return praiaRepository.saveAll(praias);
    }

    public void deletePraia(Long id) {
        praiaRepository.deleteById(id);
    }

    public List<Praia> findByStatus(String status) {
        return praiaRepository.findByStatus(status);
    }

    public List<Praia> findByNomeContainingIgnoreCase(String nome) {
        return praiaRepository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Praia> findByCoordenadas(double latitude, double longitude) {
        return praiaRepository.findAll().stream()
                .filter(praia -> praia.getLatitude() == latitude && praia.getLongitude() == longitude)
                .toList();
    }
    public List<Praia> findByCoordenadas(double[] coordenadas) {
        if (coordenadas.length != 2) {
            throw new IllegalArgumentException("Coordenadas devem conter exatamente dois valores: [longitude, latitude]");
        }
        double longitude = coordenadas[0];
        double latitude = coordenadas[1];
        return praiaRepository.findAll().stream()
                .filter(praia -> praia.getLatitude() == latitude && praia.getLongitude() == longitude)
                .toList();  
    }
}
