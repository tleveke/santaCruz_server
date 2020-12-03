package com.ynov.nantes.soap.service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      return new User(username, "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
              new ArrayList<>());
  }

}

//Ici j'ai fais une implémentation qui récupère les détails de l'utilisateur à partir d'une liste d'utilisateurs codée en dur 
//TODO on pourrait en effet ajouter une implémentation DAO pour récupérer les détails de l'utilisateur à partir de la base de données'