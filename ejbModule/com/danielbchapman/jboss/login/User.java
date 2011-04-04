package com.danielbchapman.jboss.login;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User implements Serializable
{
  private static final long serialVersionUID = 1L;
  @Id
  @Column(length=80, name="user")
  private String user;
  @Column(length=80, name="password")
  private String password;
  @OneToMany(targetEntity=Role.class)
  private List<Role> roles;
  
  public String getUser()
  {
    return user;
  }
  public void setUser(String user)
  {
    this.user = user;
  }
  public String getPassword()
  {
    return password;
  }
  public void setPassword(String password)
  {
    this.password = password;
  }
  public List<Role> getRoles()
  {
    return roles;
  }
  public void setRoles(List<Role> roles)
  {
    this.roles = roles;
  }
}
