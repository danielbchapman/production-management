<% 
	response.setHeader("Cache-Control","no-cache"); 
	response.setHeader("Cache-Control","no-store"); 
	response.setDateHeader("Expires", 0); 
	response.setHeader("Pragma","no-cache");
	session.invalidate();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Login</title>    
    <link type="text/css" rel="stylesheet" href="/./resources/css/production.css" />
  </head>
  
  <body class="center" style="z-Index:-3">
  <img src="floatingImage.png" style="z-Index:-2;position:absolute;left:0px;top:0px;" />
    <br />
    <br />
    <br />
    <div class="center" style="z-Index:-1;width:350px;">  
      <div style="margin:0px;padding:0px;text-align:center;">
        <div 
          class="informationBoxLarge" 
          style="width:100%;text-align:center;margin:0px;padding:20px;padding-top:0px;opacity:0.9;background-image:url('gradient.png');background-repeat:repeat-x;background-color:#fff;filter:alpha(opacity=90);"
        >
          <br/>                   
          <div style="text-align:left;width:75%;margin:auto;padding:20px;">
            <h3>
              Production Database Login           
            </h3>      
	            <p>
	            You have been successfully logged out of the system. Please click 
	            <a href="./" style="color:green;">here</a>
	            to log in.
	            </p>    
          </div>        
        </div>        
      </div>
    </div>
        
    <br/>
    <br/>
    <br/>
    <br/>
  </body>
</html>