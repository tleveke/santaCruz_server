package com.ynov.nantes.soap.model;

import java.io.Serializable;

public class JwtRequest implements Serializable {

  private static final long serialVersionUID = 5926468583005150707L;
  
  private String username;
  private String password;
  
  //nécessite un constructeur par défaut pour l'analyse JSON  public JwtRequest()
  {
    
  }
//Classe de stockage de nom d'utilisateur et de mot de passe
  public JwtRequest(String username, String password) {
    this.setUsername(username);
    this.setPassword(password);
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}