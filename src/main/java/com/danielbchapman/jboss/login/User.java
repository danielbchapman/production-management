package com.danielbchapman.jboss.login;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="jpa_user")
public class User implements Serializable
{
  private static final long serialVersionUID = 1L;
  @Id
  @Column(length=80, name="jpa_user")
  private String user;
  @Column(length=256, name="jpa_password")
  private String password;
  
  public User()
  {
  }
  
	public static byte[] sha512(String user, String pass)
	{
		MessageDigest md;
		try
		{
			md = MessageDigest.getInstance("SHA-512");
			byte[] bUser = md.digest(user.getBytes());
			byte[] bPass = md.digest(pass.getBytes());
			byte[] ret = new byte[bUser.length];
			
			for(int i = 0; i < ret.length; i++)
				ret[i] = (byte)(bUser[i] & bPass[i]);
			
			return ret;
		}
		catch(NoSuchAlgorithmException e)
		{
			throw new RuntimeException("SEVERE: " + e.getMessage(), e);
		}
	}
	
	public static String base64Hash(String username, String password)
	{
		return org.apache.commons.codec.binary.Base64.encodeBase64String(sha512(username, password));
	}
}
