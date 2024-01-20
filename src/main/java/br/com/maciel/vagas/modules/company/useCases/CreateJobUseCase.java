package br.com.maciel.vagas.modules.company.useCases;

import br.com.maciel.vagas.modules.company.entities.JobEntity;
import br.com.maciel.vagas.modules.company.repository.IJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateJobUseCase {

  @Autowired
  private IJobRepository jobRepository;

  public JobEntity execute(JobEntity jobEntity) {
    return this.jobRepository.save(jobEntity);
  }
}
