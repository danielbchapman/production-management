package com.danielbchapman.jboss.login;

import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import com.danielbchapman.production.entity.EntityInstance;

/**
 * Session Bean implementation class LoginBean
 */
@Stateless
public class LoginBean implements LoginBeanRemote
{
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public LoginBean()
	{
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.jboss.login.LoginBeanRemote#validateLogin(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public User validateLogin(String username, String password)
	{

		if(password == null || username == null)
			return null;

		if(getUsers().size() < 1)
		{
			addUser("Administrator", "password", asCollection(Roles.values()));
		}

		// //@formatter:off
		// String decryptedPasswordQuery =
		// "CALL Trim( \n" +
		// "  TRAILING Char(0)  \n" +
		// "FROM  \n" +
		// "  Utf8ToString ( \n" +
		// "    Decrypt('AES',  \n" +
		// "            StringToUtf8(?1),  \n" +
		// "           (SELECT password FROM User WHERE user = ?2) \n" +
		// "    ) \n" +
		// "  ) \n" +
		// "); \n" ;
		// //@formatter:on
		//
		// Query q = EntityInstance.getEm().createNativeQuery(decryptedPasswordQuery);
		// q.setParameter(1, username);
		// q.setParameter(2, username);
		//
		// List<String> results = (List<String>) q.getResultList();
		//
		// String decryptedPassword = (String) q.getSingleResult();
		ArrayList<User> users = EntityInstance.getResultList("SELECT u FROM User u WHERE u.user = ?1",
				User.class, username);
		if(users.size() < 1)
			return null;

		User u = users.get(0);
		String base64 = User.base64Hash(password, username);

		if(!base64.equals(u.getPassword()))
			return null;

		return u;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.jboss.login.LoginBeanRemote#getRolesForUser(java.lang.String)
	 */
	@Override
	public ArrayList<Role> getRolesForUser(String username)
	{
		return EntityInstance.getResultList("SELECT r FROM Role r WHERE r.user.user = ?1", Role.class, username);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.jboss.login.LoginBeanRemote#addUser(java.lang.String, java.lang.String,
	 * java.util.Collection)
	 */
	@Override
	public void addUser(String username, String password, Collection<Roles> roles)
	{

		EntityManager em = EntityInstance.getEm();
		em.getTransaction().begin();

		// /* Removed, moving security to straight JAVA via DatabaseServerLoginModuleExtension */
		// Query q = EntityInstance.getEm().createNativeQuery(
		// "Call Encrypt('AES', StringToUtf8(?1), StringToUtf8(?2))"
		// );
		//
		// q.setParameter(1, username);
		// q.setParameter(2, password);
		//
		// byte[] encrypt = (byte[]) q.getSingleResult();

		User u = new User();
		u.setUser(username);
		u.setPassword(User.base64Hash(username, password));

		em.persist(u);
		for(Roles r : roles)
		{
			Role tmp = new Role();
			tmp.setRole(r.getRoleValue());
			tmp.setUser(u);
			em.persist(tmp);
		}

		em.getTransaction().commit();
		em.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.jboss.login.LoginBeanRemote#getUsers()
	 */
	@Override
	public ArrayList<String> getUsers()
	{
		return EntityInstance.getResultList("SELECT u.user FROM User u ORDER BY u.user", String.class);
	}

	@Override
	public User getUser(String username)
	{
		return EntityInstance.getSingleResult("SELECT u FROM User u WHERE u.user = ?1", User.class, username);
	}

	/**
	 * @param args
	 *          an array of (T) types
	 * @return an ArrayList of type (T)
	 */
	public static <T> ArrayList<T> asList(T... args)
	{
		if(args == null)
			return new ArrayList<T>();

		ArrayList<T> arrayList = new ArrayList<T>();

		for(int i = 0; i < args.length; i++)
			arrayList.add(args[i]);

		return arrayList;
	}

	/**
	 * @param args
	 *          an array of (T) types
	 * @return the collection of the array by type
	 */
	public static <T> Collection<T> asCollection(T... args)
	{
		return asList(args);
	}

	@Override
	public void changePassword(String username, String newPass)
	{	
		//ENFORCE PASSWORD POLICY HERE
		if(newPass == null)
			throw new IllegalArgumentException("Password does not meet minimum length. Password is null.");
		if(newPass.length() < 6)
			throw new IllegalArgumentException("Password does not meet minimum length. Password is less than six characters.");
		
		EntityManager em = EntityInstance.getEm();
		em.getTransaction().begin();
		User user = em.find(User.class, username);
		user.setPassword(User.base64Hash(user.getUser(), newPass));
		em.merge(user);
		em.getTransaction().commit();
		em.close();
	}

	@Override
	public void addRoll(User user, Roles role)
	{
		ArrayList<Role> roles = getRolesForUser(user.getUser());
		
		boolean hasRole = false;
		for(Role r : roles)
			if(r.getRole().equals(role.getRoleValue()))
			{
				hasRole = true;
				break;
			}
		
		if(!hasRole)
		{
			EntityManager em = EntityInstance.getEm();
			em.getTransaction().begin();
			em.persist(new Role(user, role.getRoleValue()));
			em.getTransaction().commit();
			em.close();	
		}
	}

	@Override
	public void removeRoll(User user, Roles toRemove)
	{
		Role role = EntityInstance.getSingleResult(
				"SELECT r FROM Role r WHERE r.user = ?1 AND r.role = ?2" , 
				Role.class, 
				user, 
				toRemove.getRoleValue());
		
		if(role != null)
		{
			EntityManager em = EntityInstance.getEm();
			em.getTransaction().begin();
			role = em.merge(role);
			em.remove(role);
			em.getTransaction().commit();
			em.close();
		}
	}

	@Override
	public void deleteUser(User user)
	{ 
		ArrayList<Role> roles = getRolesForUser(user.getUser());
		
		for(int i = 0; i < roles.size(); i++)
			removeRoll(user, Roles.fromString(roles.get(i).getRole()));
		
		EntityManager em = EntityInstance.getEm();
		em.getTransaction().begin();
		user = em.merge(user);
		em.remove(user);
		em.getTransaction().commit();
		em.close();
	}
}
