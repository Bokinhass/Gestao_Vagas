package br.com.maciel.vagas.modules.candidate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileCanditeResponseDTO {

  private String description;
  private String username;
  private String email;
  private UUID id;
  private String name;
}
