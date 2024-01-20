package br.com.maciel.vagas.modules.candidate.controller;

import br.com.maciel.vagas.modules.candidate.entities.CandidateEntity;
import br.com.maciel.vagas.modules.candidate.useCases.CreateCandidateUseCase;
import br.com.maciel.vagas.modules.candidate.useCases.ProfileCandidateUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

  @Autowired
  private CreateCandidateUseCase createCandidateUseCase;

  @Autowired
  private ProfileCandidateUseCase profileCandidateUseCase;

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
  public ResponseEntity<Object> get(HttpServletRequest request) {
    var idCanidate = request.getAttribute("candidate_id");

    try {
      var profile = this.profileCandidateUseCase
          .execute(UUID.fromString(idCanidate.toString()));

      return ResponseEntity.ok().body(profile);

    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
