package br.com.maciel.vagas.modules.candidate.useCases;

import br.com.maciel.vagas.modules.candidate.controller.ICandidateRepository;
import br.com.maciel.vagas.modules.candidate.dto.AuthCandidadeResponseDTO;
import br.com.maciel.vagas.modules.candidate.dto.AuthCandidateRequestDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

@Service
public class AuthCandidateUseCase {

  @Value("${security:token:key}")
  private String secretKey;

  @Autowired
  private ICandidateRepository candidateRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public AuthCandidadeResponseDTO execute(AuthCandidateRequestDTO authCandidateRequestDTO) throws AuthenticationException {

    var candidate = this.candidateRepository.findByUsername(authCandidateRequestDTO.username())
        .orElseThrow(() -> {
          throw new UsernameNotFoundException("Username or password incorrect");
        });

    var passwordMatches = this.passwordEncoder
        .matches(authCandidateRequestDTO.password(), candidate.getPassword());

    if (!passwordMatches) {
      throw new AuthenticationException();
    }

    Algorithm algorithm = Algorithm.HMAC256(secretKey);
    var expiresIn = Instant.now().plus(Duration.ofHours(2));
    var token = JWT.create().withIssuer("javagas")
        .withExpiresAt(expiresIn)
        .withSubject(candidate.getId().toString())
        .withClaim("roles", Arrays.asList("candidate"))
        .sign(algorithm);

    var authCandidateResponse = AuthCandidadeResponseDTO.builder()
        .access_token(token)
        .expire_in(expiresIn.toEpochMilli())
        .build();
    return authCandidateResponse;

  }
}
