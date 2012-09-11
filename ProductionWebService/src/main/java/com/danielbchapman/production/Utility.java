package com.danielbchapman.production;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.WeakHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

import org.theactingcompany.security.SecurityFilter;

import com.danielbchapman.jboss.login.LoginBean;
import com.danielbchapman.jboss.login.Roles;

public class Utility
{
	private final static WeakHashMap<Class<?>, Object> RESOURCE_MANAGER = new WeakHashMap<Class<?>, Object>();
	private final static boolean TOMCAT_6 = true;
	private final static boolean JEE6 = true;
	private static ResourceBundle MESSAGE;
	private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd-yyyy");
	private final static SimpleDateFormat DATETIME_FORMAT = new SimpleDateFormat(
			"MM-dd-yyyy hh:mm:ss");
	private final static SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("hh:mm:ss");
	private static Logger FACES_MESSAGE_LOGGER = java.util.logging.Logger
			.getLogger("Faces Message Logger");
	/** A list of all the states to be used in combo boxes */
	public static SelectItem[] ALL_STATES = new SelectItem[] {

	new SelectItem("AL", "Alabama AL"), new SelectItem("AK", "Alaska AK"),
			new SelectItem("AZ", "Arizona AZ"), new SelectItem("AR", "Arkansas AR"),
			new SelectItem("CA", "California CA"), new SelectItem("CO", "Colorado CO"),
			new SelectItem("CT", "Connecticut CT"), new SelectItem("DE", "Delaware DE"),
			new SelectItem("FL", "Florida FL"), new SelectItem("GA", "Georgia GA"),
			new SelectItem("HI", "Hawaii HI"), new SelectItem("ID", "Idaho ID"),
			new SelectItem("IL", "Illinois IL"), new SelectItem("IN", "Indiana IN"),
			new SelectItem("IA", "Iowa IA"), new SelectItem("KS", "Kansas KS"),
			new SelectItem("KY", "Kentucky KY"), new SelectItem("LA", "Louisiana LA"),
			new SelectItem("ME", "Maine ME"), new SelectItem("MD", "Maryland MD"),
			new SelectItem("MA", "Massachusetts MA"), new SelectItem("MI", "Michigan MI"),
			new SelectItem("MN", "Minnesota MN"), new SelectItem("MS", "Mississippi MS"),
			new SelectItem("MO", "Missouri MO"), new SelectItem("MT", "Montana MT"),
			new SelectItem("NE", "Nebraska NE"), new SelectItem("NV", "Nevada NV"),
			new SelectItem("NH", "New Hampshire NH"), new SelectItem("NJ", "New Jersey NJ"),
			new SelectItem("NM", "New Mexico NM"), new SelectItem("NY", "New York NY"),
			new SelectItem("NC", "North Carolina NC"), new SelectItem("ND", "North Dakota ND"),
			new SelectItem("OH", "Ohio OH"), new SelectItem("OK", "Oklahoma OK"),
			new SelectItem("OR", "Oregon OR"), new SelectItem("PW", "Palau PW"),
			new SelectItem("PA", "Pennsylvania PA"), new SelectItem("PR", "Puerto Rico PR"),
			new SelectItem("RI", "Rhode Island RI"), new SelectItem("SC", "South Carolina SC"),
			new SelectItem("SD", "South Dakota SD"), new SelectItem("TN", "Tennessee TN"),
			new SelectItem("TX", "Texas TX"), new SelectItem("UT", "Utah UT"),
			new SelectItem("VT", "Vermont VT"), new SelectItem("VI", "Virgin Islands VI"),
			new SelectItem("VA", "Virginia VA"), new SelectItem("WA", "Washington WA"),
			new SelectItem("WV", "West Virginia WV"), new SelectItem("WI", "Wisconsin WI"),
			new SelectItem("WY", "Wyoming WY"), new SelectItem("AS", "American Samoa AS"),
			new SelectItem("DC", "District of Columbia DC"), new SelectItem("GU", "Guam GU"),
			new SelectItem("MH", "Marshall Islands MH"),
			new SelectItem("MP", "Northern Mariana Islands MP"),
			new SelectItem("FM", "Federated States of Micronesia FM"),
			new SelectItem("ON", "Ontario ON"), new SelectItem("QC", "Quebec QC"),
			new SelectItem("NS", "Nova Scotia"), new SelectItem("NB", "New Brunswick"),
			new SelectItem("MB", "Manitoba"), new SelectItem("BC", "British Columbia"),
			new SelectItem("PE", "Prince Edward Island"), new SelectItem("SK", "Saskatchewan"),
			new SelectItem("AB", "Alberta"), new SelectItem("NL", "Newfoundland and Labrador") };
	public static SelectItem[] ALL_STATES_SHORT = new SelectItem[] { new SelectItem("AL"),
			new SelectItem("AK"), new SelectItem("AZ"), new SelectItem("AR"), new SelectItem("CA"),
			new SelectItem("CO"), new SelectItem("CT"), new SelectItem("DE"), new SelectItem("FL"),
			new SelectItem("GA"), new SelectItem("HI"), new SelectItem("ID"), new SelectItem("IL"),
			new SelectItem("IN"), new SelectItem("IA"), new SelectItem("KS"), new SelectItem("KY"),
			new SelectItem("LA"), new SelectItem("ME"), new SelectItem("MD"), new SelectItem("MA"),
			new SelectItem("MI"), new SelectItem("MN"), new SelectItem("MS"), new SelectItem("MO"),
			new SelectItem("MT"), new SelectItem("NE"), new SelectItem("NV"), new SelectItem("NH"),
			new SelectItem("NJ"), new SelectItem("NM"), new SelectItem("NY"), new SelectItem("NC"),
			new SelectItem("ND"), new SelectItem("OH"), new SelectItem("OK"), new SelectItem("OR"),
			new SelectItem("PW"), new SelectItem("PA"), new SelectItem("PR"), new SelectItem("RI"),
			new SelectItem("SC"), new SelectItem("SD"), new SelectItem("TN"), new SelectItem("TX"),
			new SelectItem("UT"), new SelectItem("VT"), new SelectItem("VI"), new SelectItem("VA"),
			new SelectItem("WA"), new SelectItem("WV"), new SelectItem("WI"), new SelectItem("WY"),
			new SelectItem("AS"), new SelectItem("DC"), new SelectItem("GU"), new SelectItem("MH"),
			new SelectItem("MP"), new SelectItem("FM"), new SelectItem("ON"), new SelectItem("QC"),
			new SelectItem("NS"), new SelectItem("NB"), new SelectItem("MB"), new SelectItem("BC"),
			new SelectItem("PE"), new SelectItem("SK"), new SelectItem("AB"), new SelectItem("NL"), };

	@SuppressWarnings("unchecked")
	public static <T> T[] array(Collection<T> collection, Class<T> clazz)
	{
		if(collection.isEmpty())
			return (T[]) Array.newInstance(clazz, 0);
			
		T[] ret = (T[]) Array.newInstance(clazz, collection.size());
		
		int i = 0;
		for(T t : collection)
			ret[i++] = t;
		
		return ((T[]) ret);
	}
	
	public static void close(Closeable resource)
	{
		if(resource != null)
		{
			try
			{
				resource.close();
			}
			catch(IOException e)
			{
				// Do your thing with the exception. Print it, log it or mail it.
				e.printStackTrace();
			}
		}
	}

	public final static void createMimesFromList()
	{
		StringBuilder out = new StringBuilder();

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				Utility.class.getResourceAsStream("mimes")));
		String line;
		try
		{
			for(; (line = reader.readLine()) != null;)
			{
				String[] parts = line.split("\t");
				closeElement(parts[0], parts[1], out);
			}

			System.out.println(out);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public final static void createSecurityConstraints()
	{
		StringBuilder out = new StringBuilder();

		out.append("\t<auth-constraint>\n");
		for(Roles r : Roles.values())
		{
			out.append("\t\t<role-name>");
			out.append(r.toString());
			out.append("</role-name>\n");
		}
		out.append("\t</auth-constraint>\n");
		// <security-role>
		// <role-name>user_authentication</role-name>
		// </security-role>

		for(Roles r : Roles.values())
		{
			out.append("\t<security-role>\n");
			out.append("\t\t<role-name>");
			out.append(r.toString());
			out.append("</role-name>\n");
			out.append("\t</security-role>\n");
		}

		System.out.println(out);
	}

	public static String formatDate(Date date)
	{
		return DATE_FORMAT.format(date);
	}

	public static String formatDateTimestamp(Date date)
	{
		return DATETIME_FORMAT.format(date);
	}

	public static String formatTime(Date date)
	{
		return TIME_FORMAT.format(date);
	}

	public static Application getApplication()
	{
		return FacesContext.getCurrentInstance().getApplication();
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> clazz)
	{
		char[] characters = clazz.getSimpleName().toCharArray();
		characters[0] = Character.toLowerCase(characters[0]);
		return (T) getBean(new String(characters));
	}

	/**
	 * @param name
	 *          the managed bean name
	 * @return a link to the object in the web
	 */
	public static Object getBean(String name)
	{
		return FacesContext.getCurrentInstance().getELContext().getELResolver()
				.getValue(FacesContext.getCurrentInstance().getELContext(), null, name);
	}

	public static void clearBean(String name)
	{
		FacesContext.getCurrentInstance().getELContext().getELResolver()
		.setValue(
				FacesContext.getCurrentInstance().getELContext(), null, name, null);
	}
	
	public static void clearBean(Class<?> clazz)
	{
		char[] characters = clazz.getSimpleName().toCharArray();
		characters[0] = Character.toLowerCase(characters[0]);
		clearBean(new String(characters));
	}
	/**
	 * @return a reference to the EL Context
	 */
	public static ELContext getELContext()
	{
		return FacesContext.getCurrentInstance().getELContext();
	}

	/**
	 * @return a reference to the expression factory
	 */
	public static ExpressionFactory getExpressionFactory()
	{
		return FacesContext.getCurrentInstance().getApplication().getExpressionFactory();
	}

	/**
	 * Get a DAO from the context
	 * 
	 * @param <T>
	 * @param clazz
	 * @return <Return Description>
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getObjectFromContext(Class<T> clazz, Namespace namespace)
	{
		if(TOMCAT_6)
		{
			T instance = (T) RESOURCE_MANAGER.get(clazz);
			
			if(instance != null)
				return instance;
			
			String name = clazz.getName().replace("Remote", "");
			try
			{
				Class<? extends T>instanceClass = (Class<? extends T>) Class.forName(name);
				instance = instanceClass.newInstance();
				
				RESOURCE_MANAGER.put(clazz, instance);	
				return instance;
			}
			catch(ClassNotFoundException e)
			{
				throw new RuntimeException("Class not found, please check the configuration", e);
			}
			catch(InstantiationException e)
			{
				throw new RuntimeException("Class failed to initialize, odds are it has a constructor.\nBAD DEV, NO BISCUIT", e);
			}
			catch(IllegalAccessException e)
			{
				throw new RuntimeException("JVM Illegal Access - CRITICAL ERROR", e);
			}				

			 
		}
		// java:app/ProductionEJB/OptionsDao!com.danielbchapman.production.beans.OptionsDaoRemote
		String lookup;
		if(JEE6)
			lookup = "java:global/" + namespace.toEnterpriseSix() + "/"
					+ clazz.getSimpleName().replaceAll("Remote", "") + "!" + clazz.getName();
		else
			lookup = "ProductionEE5/" + clazz.getSimpleName().replaceAll("Remote", "") + "/remote";

		T ret = (T) getObjectFromContext(lookup);
		return ret;
	}

	/**
	 * @param jndiName
	 *          the name of your context eg: <b>myEar/myDao/remoteOrLocal</b>
	 * @return the object
	 * @throws RuntimeNamingException
	 *           when you can't find things
	 */
	public static Object getObjectFromContext(String jndiName) throws RuntimeNamingException
	{
		if(TOMCAT_6)
			throw new RuntimeException("Under Tomcat 6 initialization this application does not have a local context for the container");
		
		try
		{
			return new InitialContext().lookup(jndiName);
		}
		catch(NamingException e)
		{
			throw new RuntimeNamingException(e);
		}
	}

	/**
	 * @param name
	 *          the key name
	 * @return the value paired with the key
	 */
	public static String getResource(String name)
	{
		if(MESSAGE == null)
			MESSAGE = ResourceBundle.getBundle("com.danielbchapman.production.international");

		try
		{
			return MESSAGE.getString(name);
		}
		catch(Exception e)
		{
			return "***" + name + "***";
		}
	}

	/**
	 * @return a reference to the session. Creates a session if none exists.
	 */
	public static HttpSession getSession()
	{
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	}

	public static String getUserPrinciple()
	{
		return FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
	}

	// public static MethodExpression getMethodExpression(String expression)
	// {
	//
	// }

	/**
	 * @param expression
	 * @return a ValueExpression based on this object
	 */
	public static ValueExpression getValueExpression(String expression, Class<?> clazz)
	{
		return getExpressionFactory().createValueExpression(getELContext(), expression, clazz);
	}

	/**
	 * @param string
	 *          the string to test
	 * @return true if the string is null or contains only whitespace.
	 */
	public final static boolean isEmpty(String string)
	{
		if(string == null)
			return true;

		if(string.trim().length() == 0)
			return true;

		return false;
	}

	/**
	 * Create a critical log message, like something that might be deleted and need recovery etc...
	 * 
	 * @param string
	 *          the value to log
	 */
	public static void logCritical(String string)
	{
		Logger.getLogger("APPLICATION_CRITICAL").log(Level.SEVERE, string);
	}

	public static void login()
	{
		getSession().setAttribute(SecurityFilter.PRODUCTION_AUTH_TOKEN, SecurityFilter.PRODUCTION_AUTH_VALUE);
	}
	public static void logOut()
	{
		getSession().invalidate();
	}

	public static void main(String[] args)
	{
		System.out.println();
		System.out.println();
		System.out.println("<!-- CONSTRAINT GENERATION -->");
		createSecurityConstraints();
		System.out.println();
		System.out.println();
		System.out.println("<!-- MIME GENERATION -->");
		createMimesFromList();
	}

	public static void raiseError(String summary, String message)
	{
		raiseMessage(FacesMessage.SEVERITY_ERROR, summary, message);
		FACES_MESSAGE_LOGGER.severe(message + " [" + summary + "]");
	}

	public static void raiseFatal(String summary, String message)
	{
		raiseMessage(FacesMessage.SEVERITY_FATAL, summary, message);
		FACES_MESSAGE_LOGGER.severe(message + " [" + summary + "]");
	}

	public static void raiseInfo(String summary, String message)
	{
		raiseMessage(FacesMessage.SEVERITY_INFO, summary, message);
		if(FACES_MESSAGE_LOGGER.isLoggable(Level.INFO))
			FACES_MESSAGE_LOGGER.info(message + " [" + summary + "]");
	}

	public static void raiseWarning(String summary, String message)
	{
		raiseMessage(FacesMessage.SEVERITY_WARN, summary, message);
		if(FACES_MESSAGE_LOGGER.isLoggable(Level.WARNING))
			FACES_MESSAGE_LOGGER.warning(message + " [" + summary + "]");
	}

	public static void redirect(String url)
	{
		try
		{
			FacesContext.getCurrentInstance().getExternalContext().redirect(url);
		}
		catch(IOException e)
		{
			throw new RuntimeException("[Unable to redirect] " + e.getMessage(), e);
		}
	}

	/**
	 * @param string
	 *          the string to evaluate
	 * @return true if the length specified, else false and raises a faces message
	 */
	public static boolean validateLength(String string, int length)
	{
		if(string != null && string.length() > length)
			return true;

		if(string == null)
			raiseWarning("Invalid Length", "You must enter at least " + length
					+ " characters. Null values presented.");

		if(string == null)
			raiseWarning("Invalid Length", "You must enter at least " + length + " characters.");

		return false;
	}

	public static boolean validateLength(String string, int length, String errorMessage)
	{
		if(string != null && string.length() > length)
			return true;

		if(string == null)
			raiseWarning("Invalid Length", errorMessage + " You must enter at least " + length
					+ " characters. Null values presented.");

		if(string == null)
			raiseWarning("Invalid Length", errorMessage + " You must enter at least " + length
					+ " characters.");

		return false;
	}
	
	/**
	 * @param args an array of (T) types
	 * @return the collection of the array by type
	 */
	public static <T> Collection<T> asCollection(T ... args)
	{
		return asList(args);
	}
	
	/**
	 * @param args an array of (T) types
	 * @return an ArrayList of type (T)
	 */
	public static<T> ArrayList<T> asList(T ... args)
	{
		if(args == null)
			return new ArrayList<T>();
		
		ArrayList<T> arrayList = new ArrayList<T>();
		
		for(int i = 0; i < args.length; i++)
			arrayList.add(args[i]);

		return arrayList;
	}
	
	/**
	 * @param args an array of (T) types
	 * @return a HashSet of type (T)
	 */	
	public static <T> HashSet<T> asSet(T ... args)
	{
		if(args == null)
			return new HashSet<T>();
		
		HashSet<T> set = new HashSet<T>();
		
		for(int i = 0; i < args.length; i++)
			set.add(args[i]);
		
		return set;
	}

	/**
	 * @param obj any object
	 * @return true if the object isn't null and if it isn't a boolean equal to false.
	 */
	public final static boolean TRUE(Object obj)
	{
		if(obj == null)
			return false;
		
		if(obj instanceof Boolean)
			return ((Boolean) obj).booleanValue();
		
		return true;
	}
	
	/**
	 * A null-safe compareTo call that can be used statically. It essentially
	 * passes off the checks.
	 * @param one
	 * @param two
	 * @return
	 */
	public static <T extends Comparable<T>> int compareTo(T one, T two)
	{
		if(one == null && two == null)
			return 0;
		if(one == null)
			return -1;
		if(two == null)
			return 1;
		else
			return one.compareTo(two);
	}
	private final static void closeElement(String extension, String mime, StringBuilder build)
	{
		String map = "\t<mime-mapping>\n";
		String mapClose = "\t</mime-mapping>\n";
		String type = "\t\t<mime-type>";
		String typeClose = "</mime-type>\n";
		String ext = "\t\t<extension>";
		String extClose = "</extension>\n";

		build.append(map);
		build.append(ext);
		build.append(extension);
		build.append(extClose);
		build.append(type);
		build.append(mime);
		build.append(typeClose);
		build.append(mapClose);
	}
	public static String colorToStringHex(Color color)
	{
		return colorToStringHex(color.getRed(), color.getGreen(), color.getBlue());
	}
	 
	public static String colorToStringHex(int red, int green, int blue)
	{
		//@formatter:off
		return 
				  Integer.toHexString(0xF & red >> 4)
				+ Integer.toHexString(0x0F & red)
				+ Integer.toHexString(0xF & green >> 4)
				+ Integer.toHexString(0x0F & green)
				+ Integer.toHexString(0xF & blue >> 4)
				+ Integer.toHexString(0x0F & blue);
		//@formatter:on				
	}

	public static Color stringHexToColor(String color)
	{
		if(color == null)
			return new Color(Color.RED.getRGB());
		
		char[] colors = color.toCharArray();
		if(colors.length < 6)
		{
			System.out.println("Color not set " + color + " was not of length 6");
			return new Color(Color.RED.getRGB());
		}
			
		
		int red = Integer.parseInt(new String(new char[] { colors[0], colors[1] }));
		int green = Integer.parseInt(new String(new char[] { colors[2], colors[3] }));
		int blue = Integer.parseInt(new String(new char[] { colors[4], colors[5] }));
		
		//Intentional, do not optimize, this way the NFException will be thrown before setting values.
		return new Color(red, green, blue, 255);
	}	

	private static void raiseMessage(FacesMessage.Severity severity, String summary, String message)
	{
		FacesContext.getCurrentInstance()
				.addMessage(null, new FacesMessage(severity, summary, message));
	}

	public enum Namespace
	{
		//@formatter:off
		PRODUCTION("Production/ProductionEJB.jar", "ProductionEE6/ProductionEJB" ),
		INVENTORY("Production/ProductionEJB.jar", "ProductionEE6/ProductionEJB" ),
		LOGIN("Production/ProductionEJB.jar", "ProductionEE6/ProductionEJB" ),
		BUGS("Production/ProductionEJB.jar", "ProductionEE6/ProductionEJB" ),
		HELP("Production/ProductionEJB.jar", "ProductionEE6/ProductionEJB" );
		
//		INVENTORY("Production/InventoryEJB.jar", "ProductionEE6/InventoryEJB"), 
//		LOGIN("Production/GenericLoginEJB.jar", "ProductionEE6/GenericLoginEJB"), 
//		BUGS("Production/BugsEJB.jar", "ProductionEE6/BugsEJB"), 
//		HELP("Production/HelpEJB.jar", "ProductionEE6/HelpEJB");
		//@formatter:on
		String ee5;
		String ee6;

		Namespace(String ee5, String ee6)
		{
			this.ee5 = ee5;
			this.ee6 = ee6;
		}

		public String toEnterpriseSix()
		{
			return ee6;
		}

		@Override
		public String toString()
		{
			return ee5;
		}
	}
	
	public static Integer safeInteger(String val)
	{
		try
		{
			return Integer.valueOf(val);
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	public static Long safeLong(String val)
	{
		try
		{
			return Long.valueOf(val);
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	public static boolean minimum(String key, int length)
	{
		if(key == null)
			return false;
		
		if(key.length() >= length)
			return true;
		return false;
	}
	
}
