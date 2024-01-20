package br.com.maciel.vagas.modules.candidate.useCases;

import br.com.maciel.vagas.modules.candidate.dto.ProfileCanditeResponseDTO;
import br.com.maciel.vagas.modules.candidate.repository.ICandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfileCandidateUseCase {

  @Autowired
  private ICandidateRepository candidateRepository;

  public ProfileCanditeResponseDTO execute(UUID idCandidate) {
    var candidate = this.candidateRepository.findById(idCandidate)
        .orElseThrow(() -> {
          throw new UsernameNotFoundException("User not found");
        });

    var candidateDTO = ProfileCanditeResponseDTO
        .builder()
        .description(candidate.getDescription())
        .username(candidate.getUsername())
        .email(candidate.getEmail())
        .id(candidate.getId())
        .name(candidate.getName())
        .build();

    return candidateDTO;
  }
}
