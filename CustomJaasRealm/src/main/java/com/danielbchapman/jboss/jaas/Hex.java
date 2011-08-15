package com.danielbchapman.jboss.jaas;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hex
{
	private final static char[] characters = 
		{
		'0',
		'1',
		'2',
		'3',
		'4',
		'5',
		'6',
		'7',
		'8',
		'9',
		'a',
		'b',
		'c',
		'd',
		'e',
		'f'
		};

	/**
	 * Create a string representation of the byte[]
	 * @param bytes
	 * @return a string representing the bytes.
	 * 
	 */
	public static String fromBytes(byte[] bytes)
	{
		char[] chars = new char[bytes.length * 2];
		
		for(int i = 0, j = 0; i < bytes.length; i++)
		{
			
			int first= (int) (((bytes[i]) & 0xF0) >> 4);
			int last = (int) ((bytes[i]) & 0x0F);
			chars[j++] = characters[first];
			chars[j++] = characters[last];
		}
		return new String(chars);
	}
	
	/**
	 * Take a string and return it as byte[] (UTF-8)
	 * @param s the string to parse
	 * @return The string as bytes.  
	 * 
	 */
	public static byte[] toBytes(String s)
	{
		if(s == null)
			return null;
		
		try
		{
			return s.getBytes("UTF-8");
		}
		catch (UnsupportedEncodingException e) //WILL NOT BE TRIGGERED
		{
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	/**
	 * A simple test method.
	 * @param args
	 * @throws UnsupportedEncodingException UTF-8 is supported, period.  
	 * 
	 */
	public static void main(String[] args) throws UnsupportedEncodingException
	{
		byte[] bytes = new byte[]{
				0x00,
				0x01,
				0x02,
				0x03,
				0x04,
				0x05,
				0x16,
				0x07,
				0x08,
				0x09,
				(byte) 0xfa,
				(byte) 0xab,
				(byte) 0xcd,
				(byte) 0xef,
				};
		System.out.println(fromBytes(bytes));
	}
	
	/**
	 * A very basic one way hash that is unsalted and basically
	 * useless if someone tries to attack it. On the other hand
	 * it does prevent someone from staring at plaintext so I'll call
	 * it a hell of a step up. This could be fixed by overriding the 
	 * security handler and updating the logic, but I deem this security "hole"
	 * closed for the moment.
	 * @param toHash the string(password) to hash
	 * @return a string representing the bytes in <code>sha256(UTF-8)</code>
	 * 
	 */
	public static String sha256(String toHash)
	{
		try
		{
			byte[] bytes = MessageDigest.getInstance("SHA-256").digest(toHash.getBytes("UTF-8"));
			return fromBytes(bytes);
		}
		catch (NoSuchAlgorithmException e)
		{
			throw new RuntimeException(e.getMessage(), e);
		}
		catch (UnsupportedEncodingException e)
		{
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}
