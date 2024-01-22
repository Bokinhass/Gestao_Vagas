package br.com.maciel.vagas.modules.candidate.useCases;

import br.com.maciel.vagas.modules.company.entities.JobEntity;
import br.com.maciel.vagas.modules.company.repository.IJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListAllJobsByFilterUseCase {

  @Autowired
  private IJobRepository jobRepository;

  public List<JobEntity> execute(String filter) {
    return this.jobRepository.findByDescriptionContainingIgnoreCase(filter);
  }
}
