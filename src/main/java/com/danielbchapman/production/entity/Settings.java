package com.danielbchapman.production.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
public class Settings extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	@Column(name="settings_key", length=64, unique=true)
	String key;
	@Column(name="settings_value", length=512)
	String value;
	
	public Settings()
	{
	}
	
	public Settings(String key, String value)
	{
		setKey(key);
		setValue(value);
	}
}
