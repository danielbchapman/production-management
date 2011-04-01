package com.duastechnologies.jboss.login;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.sun.istack.internal.NotNull;


/**
 * A user role in the general security strategy. This is linked by the user name
 * but has entity aspects which allow updates via a simple EJB layer without native SQL.
 ***************************************************************************
 * @author Daniel B. Chapman 
 * <br /><i><b>Light Assistant</b></i> copyright Daniel B. Chapman
 * @since Jan 7, 2011 2011
 * @version 2 Development
 * @link http://www.lightassistant.com
 ***************************************************************************
 */
@Entity
@Table(
    name="role",
    uniqueConstraints=@UniqueConstraint(columnNames = {"user_user", "role"})
    )
public class Role implements Serializable
{
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @NotNull
  @ManyToOne(targetEntity=User.class)
  private User user;
  @NotNull
  private String role;
  public Long getId()
  {
    return id;
  }
  public void setId(Long id)
  {
    this.id = id;
  }
  public User getUser()
  {
    return user;
  }
  public void setUser(User user)
  {
    this.user = user;
  }
  public String getRole()
  {
    return role;
  }
  public void setRole(String role)
  {
    this.role = role;
  }
}
