package com.moura.BalneabilidadeAPI.controller;

import com.moura.BalneabilidadeAPI.model.Praia;
import com.moura.BalneabilidadeAPI.service.PraiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    public PraiaController(PraiaService praiaService) {
        this.praiaService = praiaService;
    }

    @GetMapping
    public ResponseEntity<List<Praia>> getAllPraias() {
        List<Praia> praias = praiaService.getAllPraias();
        return new ResponseEntity<>(praias, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Praia> getPraiaById(@PathVariable Long id) {
        Optional<Praia> praia = praiaService.getPraiaById(id);
        return praia.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // REMOVIDO: @CrossOrigin duplicado aqui
    @PostMapping
    public ResponseEntity<Praia> createPraia(@RequestBody Praia praia) {
        Praia savedPraia = praiaService.savePraia(praia);
        return new ResponseEntity<>(savedPraia, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Praia> updatePraia(@PathVariable Long id, @RequestBody Praia praia) {
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
        if (!praiaService.getPraiaById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        praiaService.deletePraia(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Praia>> findByStatus(@PathVariable String status) {
        List<Praia> praias = praiaService.findByStatus(status);
        return new ResponseEntity<>(praias, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Praia>> searchByNome(@RequestParam String nome) {
        List<Praia> praias = praiaService.findByNomeContainingIgnoreCase(nome);
        return new ResponseEntity<>(praias, HttpStatus.OK);
    }

    @PostMapping("/batch")
    public ResponseEntity<List<Praia>> createPraias(@RequestBody List<Praia> praias) {
        List<Praia> savedPraias = praiaService.saveAllPraias(praias);
        return new ResponseEntity<>(savedPraias, HttpStatus.CREATED);
    }
}