package br.com.maciel.vagas.modules.candidate.useCases;

import br.com.maciel.vagas.modules.candidate.dto.AuthCandidateRequestDTO;
import br.com.maciel.vagas.modules.candidate.dto.AuthCandidateResponseDTO;
import br.com.maciel.vagas.modules.candidate.repository.ICandidateRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class AuthCandidateUseCase {

  @Value("${security:token:secretCandidate}")
  private String secretKey;

  @Autowired
  private ICandidateRepository candidateRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO authCandidateRequestDTO) throws AuthenticationException {
    var candidate = this.candidateRepository.findByUsername(authCandidateRequestDTO.username())
        .orElseThrow(() -> {
          throw new UsernameNotFoundException("Username or password incorrect");
        });

    var passwordMatches = this.passwordEncoder
        .matches(authCandidateRequestDTO.password(), candidate.getPassword());

    if (!passwordMatches) {
      throw new AuthenticationException("");
    }

    Algorithm algorithm = Algorithm.HMAC256(secretKey);
    var expiresIn = Instant.now().plus(Duration.ofHours(2));

    var token = JWT.create().withIssuer("javagas")
        .withExpiresAt(expiresIn)
        .withSubject(candidate.getId().toString())
        .withClaim("roles", List.of("candidate"))
        .sign(algorithm);

    var authCandidateResponse = AuthCandidateResponseDTO.builder()
        .access_token(token)
        .expires_in(expiresIn.toEpochMilli())
        .build();

    return authCandidateResponse;
  }
}
