package com.rakshith.geodatahub.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

@Entity
@Table(name = "datasets")
public class Dataset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String slug;

    @NotBlank
    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    private String category;
    private String mapUrl;
    private String downloadUrl;
    private String rawDownloadUrl;
    private String downloadNote;
    private String repoUrl;

    @Column(length = 2000)
    private String sourceLinksJson;

    @Column(length = 3000)
    private String introText;

    @Column(length = 4000)
    private String methodology;

    @Column(length = 6000)
    private String findingsJson;

    @Column(length = 2000)
    private String limitations;

    private String license;
    private LocalDate dateAdded;

    public Dataset() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getMapUrl() { return mapUrl; }
    public void setMapUrl(String mapUrl) { this.mapUrl = mapUrl; }

    public String getDownloadUrl() { return downloadUrl; }
    public void setDownloadUrl(String downloadUrl) { this.downloadUrl = downloadUrl; }

    public String getRawDownloadUrl() { return rawDownloadUrl; }
    public void setRawDownloadUrl(String rawDownloadUrl) { this.rawDownloadUrl = rawDownloadUrl; }

    public String getDownloadNote() { return downloadNote; }
    public void setDownloadNote(String downloadNote) { this.downloadNote = downloadNote; }

    public String getRepoUrl() { return repoUrl; }
    public void setRepoUrl(String repoUrl) { this.repoUrl = repoUrl; }

    public String getSourceLinksJson() { return sourceLinksJson; }
    public void setSourceLinksJson(String sourceLinksJson) { this.sourceLinksJson = sourceLinksJson; }

    public String getIntroText() { return introText; }
    public void setIntroText(String introText) { this.introText = introText; }

    public String getMethodology() { return methodology; }
    public void setMethodology(String methodology) { this.methodology = methodology; }

    public String getFindingsJson() { return findingsJson; }
    public void setFindingsJson(String findingsJson) { this.findingsJson = findingsJson; }

    public String getLimitations() { return limitations; }
    public void setLimitations(String limitations) { this.limitations = limitations; }

    public String getLicense() { return license; }
    public void setLicense(String license) { this.license = license; }

    public LocalDate getDateAdded() { return dateAdded; }
    public void setDateAdded(LocalDate dateAdded) { this.dateAdded = dateAdded; }
}