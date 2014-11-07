package com.danielbchapman.production.beans;

import javax.ejb.Stateless;

import org.theactingcompany.persistence.Backup;
import org.theactingcompany.persistence.Indentifiable;

import com.danielbchapman.production.entity.EntityInstance;

/**
 * Session Bean implementation class BackupBean
 */
@Stateless
public class BackupBean implements BackupBeanRemote
{

	/**
	 * Default constructor.
	 */
	public BackupBean()
	{
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.beans.BackupBeanRemote#backupTable(java.lang.String)
	 */
	@Override
	public String backupTables(String... names)
	{
		String xml = Backup.backupToXml("production", EntityInstance.getDelegate(), names);
		return xml;
	}

	@Override
	public String backupTables(Class<? extends Indentifiable>... args)
	{
		String[] names = new String[args.length];
		for(int i = 0; i < args.length; i++)
			names[i] = args[i].getSimpleName().replaceAll("class", "").replaceAll("\\.", "");

		return backupTables(names);
	}

}
