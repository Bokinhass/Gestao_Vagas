package br.com.maciel.vagas.modules.candidate.useCases;

import br.com.maciel.vagas.modules.candidate.controller.ICandidateRepository;
import br.com.maciel.vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfileCandidateUseCase {

  @Autowired
  private ICandidateRepository candidateRepository;

  public ProfileCandidateResponseDTO execute(UUID idCandidate) {
    var candidate = this.candidateRepository.findById(idCandidate)
        .orElseThrow(() -> {
          throw new UsernameNotFoundException("User not found.");
        });

    var candidateDTO = ProfileCandidateResponseDTO
        .builder()
        .description(candidate.getDescription())
        .username(candidate.getUsername())
        .email(candidate.getEmail())
        .name(candidate.getName())
        .id(String.valueOf(candidate.getId()))
        .build();

    return candidateDTO;
  }
}
