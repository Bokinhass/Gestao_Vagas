package br.com.maciel.vagas.modules.company.repository;

import br.com.maciel.vagas.modules.company.entities.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IJobRepository extends JpaRepository<JobEntity, UUID> {
}
