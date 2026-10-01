package com.rakshith.geodatahub.controller;

import com.rakshith.geodatahub.entity.Dataset;
import com.rakshith.geodatahub.repository.DatasetRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/datasets")
@CrossOrigin(origins = "http://localhost:5173")
public class DatasetController {

    private final DatasetRepository repository;

    public DatasetController(DatasetRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Dataset> listAll() {
        return repository.findAll();
    }

    @GetMapping("/{slug}")
    public ResponseEntity<Dataset> getBySlug(@PathVariable String slug) {
        return repository.findBySlug(slug)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Dataset create(@Valid @RequestBody Dataset dataset) {
        return repository.save(dataset);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Dataset> update(@PathVariable Long id, @Valid @RequestBody Dataset updated) {
        return repository.findById(id).map(existing -> {
            existing.setSlug(updated.getSlug());
            existing.setTitle(updated.getTitle());
            existing.setDescription(updated.getDescription());
            existing.setCategory(updated.getCategory());
            existing.setMapUrl(updated.getMapUrl());
            existing.setDownloadUrl(updated.getDownloadUrl());
            existing.setRawDownloadUrl(updated.getRawDownloadUrl());
            existing.setDownloadNote(updated.getDownloadNote());
            existing.setRepoUrl(updated.getRepoUrl());
            existing.setSourceLinksJson(updated.getSourceLinksJson());
            existing.setIntroText(updated.getIntroText());
            existing.setMethodology(updated.getMethodology());
            existing.setFindingsJson(updated.getFindingsJson());
            existing.setLimitations(updated.getLimitations());
            existing.setLicense(updated.getLicense());
            existing.setDateAdded(updated.getDateAdded());
            return ResponseEntity.ok(repository.save(existing));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}