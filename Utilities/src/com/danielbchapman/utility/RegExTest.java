package com.danielbchapman.utility;

public class RegExTest
{
	public static void main(String[] args)
	{
		String letters = "abcdefghijklmnopqrstuvwxyz";
		String upper = letters.toUpperCase();
		String symbol = "!@#$%^&*()_+{}[]-=`~';:<>?,./\\\"";
		String all = "0123456789" + letters + upper + symbol;

		System.out.println(all);
		System.out.println(all.replaceAll("[^0-9.]", ""));
		System.out.println(all.replaceAll("[^a-zA-Z]", ""));
	}
}
