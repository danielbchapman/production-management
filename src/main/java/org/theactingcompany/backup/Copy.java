package org.theactingcompany.backup;

import org.theactingcompany.persistence.AbstractEntityInstance;
import org.theactingcompany.persistence.Indentifiable;

import com.danielbchapman.production.entity.EntityInstance;
import com.danielbchapman.production.entity.Season;

public class Copy
{
	public static void main(String ... args)
	{
		System.out.println("-------STARTING BACKUP");
		
		TargetInstance target = new TargetInstance();
		
		/** FREE OBJECTS */
//		for(City c : )
		/** DEPENDENCIES ON SEASONS */
		for(Season s : EntityInstance.getAll(Season.class))
		{
			s = target.saveObject(strip(s));
			
		}
		
	}
	
	
	public static <T extends Indentifiable> T strip(T t)
	{
		t.setId(null);
		return t; 
	}
	public static class TargetInstance extends AbstractEntityInstance
	{
		@Override
		protected String getPersistenceUnitId()
		{
			return "target";
		}

		@Override
		protected ClassLoader getClassLoader()
		{
			return TargetInstance.this.getClassLoader();
		}

		@Override
		protected boolean isContainerManaged()
		{
			return false;
		}
		
	}
}
