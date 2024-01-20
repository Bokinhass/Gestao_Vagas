package br.com.maciel.vagas.modules.company.useCases;

import br.com.maciel.vagas.exceptions.CompanyFoundException;
import br.com.maciel.vagas.modules.company.entities.JobEntity;
import br.com.maciel.vagas.modules.company.repository.ICompanyRepository;
import br.com.maciel.vagas.modules.company.repository.IJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateJobUseCase {

  @Autowired
  private ICompanyRepository companyRepository;

  @Autowired
  private IJobRepository jobRepository;

  public JobEntity execute(JobEntity jobEntity) {
    companyRepository.findById(jobEntity.getCompanyId()).orElseThrow(() -> {
      throw new CompanyFoundException();
    });
    return this.jobRepository.save(jobEntity);
  }
}
