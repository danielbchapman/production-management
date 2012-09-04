package org.theactingcompany.core.bugs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

import com.danielbchapman.production.entity.BaseEntity;

/**
 * A class that represents a bug, task of feature. It contains the fields needed to
 * store information to report and replace the bug.
 *
 ***************************************************************************
 * @author Daniel B. Chapman 
 * @since Jul 25, 2011
 * @link http://www.theactingcompany.org
 ***************************************************************************
 */
@Entity
public class Bug extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	private boolean contactReporter;
	@Lob
	private String description;
	@Lob
	private String howToReproduce;
	private Priority priority;
	@Column(length=120)
	private String reporter;
	@Column(length=120)
	private String reporterEmail;
	@Column(length=20)
	private String reporterPhone;
	private boolean resolved;
	@Lob
	private String stackTrace;
	@Column(length=120)
	private String status;
	private Type type;
	
	public String getDescription()
	{
		return description;
	}

	public String getHowToReproduce()
	{
		return howToReproduce;
	}

	public Priority getPriority()
	{
		return priority;
	}

	public String getReporter()
	{
		return reporter;
	}

	public String getReporterEmail()
	{
		return reporterEmail;
	}

	public String getReporterPhone()
	{
		return reporterPhone;
	}

	public String getStackTrace()
	{
		return stackTrace;
	}

	public String getStatus()
	{
		return status;
	}

	public Type getType()
	{
		return type;
	}

	public boolean isContactReporter()
	{
		return contactReporter;
	}

	public boolean isResolved()
	{
		return resolved;
	}

	public void setContactReporter(boolean contactReporter)
	{
		this.contactReporter = contactReporter;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}
	public void setHowToReproduce(String howToReproduce)
	{
		this.howToReproduce = howToReproduce;
	}	
	public void setPriority(Priority priority)
	{
		this.priority = priority;
	}
	public void setReporter(String reporter)
	{
		this.reporter = reporter;
	}
	public void setReporterEmail(String reporterEmail)
	{
		this.reporterEmail = reporterEmail;
	}
	public void setReporterPhone(String reporterPhone)
	{
		this.reporterPhone = reporterPhone;
	}
	public void setResolved(boolean resolved)
	{
		this.resolved = resolved;
	}
	public void setStackTrace(String stackTrace)
	{
		this.stackTrace = stackTrace;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	public void setType(Type type)
	{
		this.type = type;
	}
	
	public enum Priority
	{
		CRITICAL("CRITICAL", 4),
		HIGH("HIGH", 3),
		LOW("LOW", 1),
		MEDIUM("MEDIUM", 2),
		TRIVIAL("TRIVIAL", 0),
		;
		
		String description;
		
		Integer value;
		Priority(String description, Integer value)
		{
			this.description = description;
			this.value = value;
		}

		/**
		 * @param i the Integer to parse
		 * @return the priority associated with the value or the description of this Integer  
		 */
		public Priority fromInteger(Integer i)
		{
			if(i == null)
				return LOW;
			
			for(Priority p : values())
				if(p.value.equals(i))
					return p;
			
			return LOW;
		}
		
		/**
		 * @param o the object to parse
		 * @return the priority associated with the value or the description of this object  
		 * 
		 */
		public Priority fromObject(Object o)
		{
			if(o == null)
				return LOW;
			
			if(o instanceof String)
				return fromString((String)o);
			
			if(o instanceof Integer)
				return fromInteger((Integer) o);
			
			return LOW;
		}
		
		/**
		 * @param s the String to parse
		 * @return the priority associated with the value or the description of this Integer  
		 */
		public Priority fromString(String s)
		{
			if(s == null)
				return LOW;
			
			for(Priority p : values())
				if(p.description.equals(s))
					return p;
			
			return LOW;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		public String toString()
		{
			return description;
		}
	}
	
	public enum Type
	{
		BUG("BUG", 1),
		ENHANCEMENT("ENHANCEMENT", 4),
		NEW_FEATURE("NEW_FEATURE", 2),
		REQUEST("REQUEST", 3),
		VISUAL_ERROR("VISUAL_ERROR", 0),
		;
		
		String description;
		
		Integer value;
		Type(String description, Integer value)
		{
			this.description = description;
			this.value = value;
		}

		/**
		 * @param i the Integer to parse
		 * @return the Type associated with the value or the description of this Integer  
		 */
		public Type fromInteger(Integer i)
		{
			if(i == null)
				return REQUEST;
			
			for(Type p : values())
				if(p.value.equals(i))
					return p;
			
			return REQUEST;
		}
		
		/**
		 * @param o the object to parse
		 * @return the type associated with the value or the description of this object  
		 * 
		 */
		public Type fromObject(Object o)
		{
			if(o == null)
				return REQUEST;
			
			if(o instanceof String)
				return fromString((String)o);
			
			if(o instanceof Integer)
				return fromInteger((Integer) o);
			
			return REQUEST;
		}
		
		/**
		 * @param s the String to parse
		 * @return the Type associated with the value or the description of this Integer  
		 */
		public Type fromString(String s)
		{
			if(s == null)
				return REQUEST;
			
			for(Type p : values())
				if(p.description.equals(s))
					return p;
			
			return REQUEST;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		public String toString()
		{
			return description;
		}
	}
}
