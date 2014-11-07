package com.danielbchapman.production.web.schedule.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.theactingcompany.inventory.entity.EmbeddableImage;

import com.danielbchapman.production.web.schedule.beans.InventoryBean;

/**
 * A class that serves an image based on the ID for a particular class and ID so that it can be
 * displayed in the browser.
 */
public class InventoryImageServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InventoryImageServlet()
	{
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		String inventoryRecordId = request.getParameter("id");

		InventoryBean bean = (InventoryBean) request.getSession().getAttribute("inventoryBean");
		if(bean == null || inventoryRecordId == null)
		{
			InputStream noImage = getClass().getResourceAsStream("errorImage.png");

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			writeBufferedInToOut(noImage, out, 8192);

			renderImage("errorImage.png", out.toByteArray(), request, response);
			return;
		}

		EmbeddableImage image = bean.getImageForClass(Long.valueOf(inventoryRecordId));

		if(image == null || !image.isImageAvailable())
		{
			InputStream noImage = getClass().getResourceAsStream("noImage.png");

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			writeBufferedInToOut(noImage, out, 8192);

			renderImage("errorImage.png", out.toByteArray(), request, response);
			return;
		}

		renderImage(image, request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		// We don't post or care...
	}

	private void renderImage(EmbeddableImage image, HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		response.setHeader("Content-Type", image.getMimeType());
		response.setHeader("Content-Length", Integer.toString(image.getImage().length));
		response.setHeader("Content-Disposition", "inline; filename=\"" + image.getFileName() + "\"");

		InputStream input = new BufferedInputStream(new ByteArrayInputStream(image.getImage()));
		writeBufferedInToOut(input, response.getOutputStream(), 8192);
	}

	private void renderImage(String name, byte[] image, HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		response.setHeader("Content-Type", getServletContext().getMimeType(name));
		response.setHeader("Content-Length", Integer.toString(image.length));
		response.setHeader("Content-Disposition", "inline; filename=\"" + name + "\"");

		InputStream input = new BufferedInputStream(new ByteArrayInputStream(image));
		writeBufferedInToOut(input, response.getOutputStream(), 8192);
	}

	private void writeBufferedInToOut(InputStream in, OutputStream out, int buffer)
	{
		BufferedInputStream inBuf = null;
		BufferedOutputStream outBuf = null;

		byte[] buf = new byte[buffer];// sure, why not?
		int length;
		try
		{
			inBuf = new BufferedInputStream(in);
			outBuf = new BufferedOutputStream(out);
			while((length = inBuf.read(buf)) > 0)
			{
				outBuf.write(buf, 0, length);
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(outBuf != null)
				try
				{
					outBuf.close();
				}
				catch(IOException logOrIgnore)
				{
				}
			if(inBuf != null)
				try
				{
					inBuf.close();
				}
				catch(IOException logOrIgnore)
				{
				}
		}
	}
}
