package com.danielbchapman.production.beans;

import javax.ejb.Remote;

@Remote
public interface BackupBeanRemote {
	public String backupTables(Class<? extends org.theactingcompany.persistence.Indentifiable> ... args);
	public String backupTables(String ... names);
}
