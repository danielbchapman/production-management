<!DOCTYPE html> 
<html> 
	<head> 
		<title>Production &lt;&lt; Login</title>		 
		<meta name="viewport" content="width=device-width, initial-scale=1"> 
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		
		<script type="text/javascript" src="/./mobile/resources/js/jquery-1.6.4.min.js"></script>
		<script type="text/javascript" src="/./mobile/resources/js/jquery.mobile-1.0rc2.min.js"></script>
		
		<link type="text/css" rel="stylesheet" href="/./mobile/resources/css/jquery.mobile-1.0rc2.min.css"/>
		
	</head> 

	<body data-content-theme="C"> 
		<div data-role="page" data-theme="b" data-content-theme="b">
			<div data-role="header" style="text-align: center">
				Production Database Login
			</div>
			
			<div data-role="content" data-content-theme="C">
				<form method=post action="j_security_check">
					<label for="j_username">User ID:</label>
					<input 
						type="text"
						id="j_username"
						name= "j_username"
						value=""
					>
					<label for="j_password">Password:</label>
					<input 
						type="password"
						id="j_password"
						name= "j_password" 
						value=""
					>
					<input type="submit" value="Login" />
					
					<p> 
						The production database is a secure application. Please log in using your email address and
						password. If you require a password, please contact the Production Manager or Company Manager.
					</p>
				</form>
				
				<script type="text/javascript">
					$("[type='submit']").button();
				</script>
			</div>
			
			<div data-role="footer">
			</div>
		</div>
	</body>
</html>