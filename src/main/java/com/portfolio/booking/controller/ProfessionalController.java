package com.portfolio.booking.controller;

import com.portfolio.booking.dto.request.ProfessionalRequest;
import com.portfolio.booking.dto.response.ProfessionalResponse;
import com.portfolio.booking.model.Professional;
import com.portfolio.booking.service.ProfessionalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/professionals")
public class ProfessionalController {

    private final ProfessionalService professionalService;

    public ProfessionalController(ProfessionalService professionalService)
    {
        this.professionalService = professionalService;
    }

    @PostMapping
    public ResponseEntity<ProfessionalResponse> registerProfessional(@RequestBody ProfessionalRequest request)
    {
        Professional professional = new Professional(
                null,
                request.name(),
                request.email(),
                request.specialty()
        );

        Professional registeredProfessional = professionalService.registerProfessional(professional);
        ProfessionalResponse response = ProfessionalResponse.from(registeredProfessional);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProfessionalResponse>> getAllProfessionals()
    {
        List<ProfessionalResponse> professionals = professionalService.getAllProfessionals()
                .stream().map(ProfessionalResponse::from).toList();
        return ResponseEntity.ok(professionals);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessionalResponse> getProfessionalById(@PathVariable Long id) {
        Professional professional = professionalService.getProfessionalById(id);
        ProfessionalResponse response = ProfessionalResponse.from(professional);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessionalResponse> updateProfessional(@PathVariable Long id, @RequestBody ProfessionalRequest request) {
        Professional professional = new Professional(
                id,
                request.name(),
                request.email(),
                request.specialty()
        );

        Professional updatedProfessional = professionalService.updateProfessional(id, professional);
        ProfessionalResponse response = ProfessionalResponse.from(updatedProfessional);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<ProfessionalResponse> activateProfessional(@PathVariable Long id) {
        Professional professional = professionalService.activateProfessional(id);
        ProfessionalResponse response = ProfessionalResponse.from(professional);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ProfessionalResponse> deactivateProfessional(@PathVariable Long id) {
        Professional professional = professionalService.deactivateProfessional(id);
        ProfessionalResponse response = ProfessionalResponse.from(professional);
        return ResponseEntity.ok(response);
    }
}
