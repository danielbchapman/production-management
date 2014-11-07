package com.danielbchapman.utility.xmlsanity;

import java.io.File;

import org.xml.sax.SAXParseException;

public class Error
{
	SAXParseException sax;
	File file;

	Error(File file, SAXParseException sax)
	{
		this.file = file;
		this.sax = sax;
	}
	
	public String printError()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("LINE: [");
		builder.append(sax.getLineNumber());
		builder.append("] ");
		builder.append(sax.getMessage());
		builder.append("\n\tPublic Id\n\t ");
		builder.append(sax.getPublicId());
		builder.append("\tSystem Id\n\t ");
		builder.append(sax.getSystemId());
		return builder.toString();
	}
}
