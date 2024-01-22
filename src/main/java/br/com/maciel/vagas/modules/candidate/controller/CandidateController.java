package br.com.maciel.vagas.modules.candidate.controller;

import br.com.maciel.vagas.modules.candidate.entities.CandidateEntity;
import br.com.maciel.vagas.modules.candidate.useCases.CreateCandidateUseCase;
import br.com.maciel.vagas.modules.candidate.useCases.ListAllJobsByFilterUseCase;
import br.com.maciel.vagas.modules.candidate.useCases.ProfileCandidateUseCase;
import br.com.maciel.vagas.modules.company.entities.JobEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

  @Autowired
  private CreateCandidateUseCase createCandidateUseCase;

  @Autowired
  private ProfileCandidateUseCase profileCandidateUseCase;

  @Autowired
  private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;

  @PostMapping("/create")
  public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity) {
    try {
      var result = this.createCandidateUseCase.execute(candidateEntity);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/profile")
  @PreAuthorize("hasRole('CANDIDATE)")
  public ResponseEntity<Object> profile(HttpServletRequest request) {
    var idCandidate = request.getAttribute("candidate_id");

    try {
      var profile = this.profileCandidateUseCase
          .execute(UUID.fromString(idCandidate.toString()));

      return ResponseEntity.ok().body(profile);

    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/job")
  @PreAuthorize("hasRole(CANDIDATE)")
  @Tag(name = "Candidato", description = "Informações do candidato")
  @Operation(summary = "Listagem de vagas disponíveis para o candidato", description = "Função responável em listar todas as vagas")
  public List<JobEntity> findJobByFilter(@RequestParam String filter) {
    return this.listAllJobsByFilterUseCase.execute(filter);
  }
}
