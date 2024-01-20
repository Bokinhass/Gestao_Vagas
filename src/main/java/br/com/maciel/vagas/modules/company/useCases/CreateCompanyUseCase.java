package br.com.maciel.vagas.modules.company.useCases;

import br.com.maciel.vagas.exceptions.CompanyFoundException;
import br.com.maciel.vagas.modules.company.entities.CompanyEntity;
import br.com.maciel.vagas.modules.company.repository.ICompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateCompanyUseCase {

  @Autowired
  private ICompanyRepository companyRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public CompanyEntity execute(CompanyEntity companyEntity) {
    this.companyRepository
        .findByUsernameOrEmail(companyEntity.getUsername(), companyEntity.getEmail())
        .ifPresent((company) -> {
          throw new CompanyFoundException();
        });

    var password = passwordEncoder.encode(companyEntity.getPassword());
    companyEntity.setPassword(password);

    return this.companyRepository.save(companyEntity);
  }
}
