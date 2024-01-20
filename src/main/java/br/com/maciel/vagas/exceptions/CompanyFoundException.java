package br.com.maciel.vagas.exceptions;

public class CompanyFoundException extends RuntimeException {
  public CompanyFoundException() {
    super("Company already exists.");
  }
}
