package br.com.maciel.vagas.exceptions;

public class UserFoundException extends RuntimeException {
  public UserFoundException() {
    super("User already exists.");
  }
}
