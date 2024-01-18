package br.com.maciel.vagas.modules.company.controller;

import br.com.maciel.vagas.modules.company.entities.JobEntity;
import br.com.maciel.vagas.modules.company.useCases.CreateJobUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/job")
public class JobController {

  @Autowired
  private CreateJobUseCase createJobUseCase;

  @PostMapping("/create")
  public JobEntity create(@Valid @RequestBody JobEntity jobEntity) {
    return this.createJobUseCase.execute(jobEntity);
  }
}
