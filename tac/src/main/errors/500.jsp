<% 
	response.setHeader("Cache-Control","no-cache"); 
	response.setHeader("Cache-Control","no-store"); 
	response.setDateHeader("Expires", 0); 
	response.setHeader("Pragma","no-cache");
	session.invalidate();
%>	
<html>
	<h3>Error 500 - Custom Page</h3>
	<p>
		Application go boom--I need to actually write this page.
		<br />
<% 
  out.println("Logged in as: " + request.getUserPrincipal());
%>
		<br />
	</p>
	<a href="./">Return to Application</a>
</html>       