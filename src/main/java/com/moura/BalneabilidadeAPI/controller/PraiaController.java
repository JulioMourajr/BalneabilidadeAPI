package com.moura.BalneabilidadeAPI.controller;

import com.moura.BalneabilidadeAPI.model.Praia;
import com.moura.BalneabilidadeAPI.service.PraiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = {
    "http://localhost:5000", 
    "http://localhost:3000", 
    "http://localhost:5173", 
    "http://balneabilidade-alb-1182868018.us-east-1.elb.amazonaws.com",
    "http://balneabilidade-alb-1075453909.us-east-1.elb.amazonaws.com"
})
@RestController
@RequestMapping("/api/praias")
public class PraiaController {

    private final PraiaService praiaService;

    Logger logger = LoggerFactory.getLogger(PraiaController.class);

    @Autowired
    public PraiaController(PraiaService praiaService) {
        this.praiaService = praiaService;
    }

    @GetMapping
    public ResponseEntity<List<Praia>> getAllPraias() {
        logger.debug("Debugando a busca de todas as praias");
        logger.info("Buscando todas as praias");
        logger.warn("Cuidado ao buscar todas as praias");
        logger.error("Erro ao buscar todas as praias");
        logger.trace("Rastreando a busca de todas as praias");
        List<Praia> praias = praiaService.getAllPraias();
        return new ResponseEntity<>(praias, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Praia> getPraiaById(@PathVariable Long id) {
        logger.info("Buscando praia com ID: {}", id);
        logger.debug("Debugando a busca de praia com ID: {}", id);
        logger.warn("Cuidado ao buscar praia com ID: {}", id);
        logger.error("Erro ao buscar praia com ID: {}", id);
        logger.trace("Rastreando a busca de praia com ID: {}", id);
        Optional<Praia> praia = praiaService.getPraiaById(id);
        return praia.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // REMOVIDO: @CrossOrigin duplicado aqui
    @PostMapping
    public ResponseEntity<Praia> createPraia(@RequestBody Praia praia) {
        logger.info("Criando nova praia: {}", praia);
        logger.debug("Debugando a criação de nova praia: {}", praia);
        logger.warn("Cuidado ao criar nova praia: {}", praia);
        logger.error("Erro ao criar nova praia: {}", praia);
        logger.trace("Rastreando a criação de nova praia: {}", praia);
        Praia savedPraia = praiaService.savePraia(praia);
        return new ResponseEntity<>(savedPraia, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Praia> updatePraia(@PathVariable Long id, @RequestBody Praia praia) {
        logger.info("Atualizando praia com ID: {}", id);
        logger.debug("Debugando a atualização de praia com ID: {}", id);
        logger.warn("Cuidado ao atualizar praia com ID: {}", id);
        logger.error("Erro ao atualizar praia com ID: {}", id);
        logger.trace("Rastreando a atualização de praia com ID: {}", id);
        Optional<Praia> existingPraia = praiaService.getPraiaById(id);
        if (!existingPraia.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Praia praiaToUpdate = existingPraia.get();
        praiaToUpdate.setNome(praia.getNome());
        praiaToUpdate.setStatus(praia.getStatus());
        praiaToUpdate.setCoordenadas(praia.getCoordenadas());
        Praia updatedPraia = praiaService.savePraia(praiaToUpdate);
        return new ResponseEntity<>(updatedPraia, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePraia(@PathVariable Long id) {
        logger.info("Deletando praia com ID: {}", id);
        logger.debug("Debugando a deleção de praia com ID: {}", id);
        logger.warn("Cuidado ao deletar praia com ID: {}", id);
        logger.error("Erro ao deletar praia com ID: {}", id);
        logger.trace("Rastreando a deleção de praia com ID: {}", id);
        if (!praiaService.getPraiaById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        praiaService.deletePraia(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Praia>> findByStatus(@PathVariable String status) {
        logger.info("Buscando praias com status: {}", status);
        logger.debug("Debugando a busca de praias com status: {}", status);
        logger.warn("Cuidado ao buscar praias com status: {}", status);
        logger.error("Erro ao buscar praias com status: {}", status);
        logger.trace("Rastreando a busca de praias com status: {}", status);
        List<Praia> praias = praiaService.findByStatus(status);
        return new ResponseEntity<>(praias, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Praia>> searchByNome(@RequestParam String nome) {
        logger.info("Buscando praias com nome: {}", nome);
        logger.debug("Debugando a busca de praias com nome: {}", nome);
        logger.warn("Cuidado ao buscar praias com nome: {}", nome);
        logger.error("Erro ao buscar praias com nome: {}", nome);
        logger.trace("Rastreando a busca de praias com nome: {}", nome);
        List<Praia> praias = praiaService.findByNomeContainingIgnoreCase(nome);
        return new ResponseEntity<>(praias, HttpStatus.OK);
    }

    @PostMapping("/batch")
    public ResponseEntity<List<Praia>> createPraias(@RequestBody List<Praia> praias) {
        logger.info("Criando todas praias: {}", praias);
        logger.debug("Debugando a criação de todas praias: {}", praias);
        logger.warn("Cuidado ao criar todas praias: {}", praias);
        logger.error("Erro ao criar todas praias: {}", praias);
        logger.trace("Rastreando a criação de todas praias: {}", praias);
        List<Praia> savedPraias = praiaService.saveAllPraias(praias);
        return new ResponseEntity<>(savedPraias, HttpStatus.CREATED);
    }
}