<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
	<meta name="_csrf_parameter" content="_csrf" />
    <meta name="_csrf_header" content="X-CSRF-TOKEN" />
    <meta name="_csrf" content="e62835df-f1a0-49ea-bce7-bf96f998119c" />
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" />
	
	<title>Products</title>
</head>

<body>
	<section>
		<nav class="navbar navbar-default">
			<div class="container-fluid">
				<div class="navbar-header">
					<a class="navbar-brand" href="<spring:url value="/"/>">My Website</a>
				</div>
				
				<div>
					<ul class="nav navbar-nav">
						<li class="active"><a href="<spring:url value="/home" />">Home</a></li>
						<li><a href="<spring:url value="/products"/>">Products</a></li>
						<li><a href="<spring:url value="/products/add"/>">Add Product</a></li>
						<li><a href="<spring:url value="/cart"/>">Cart</a></li>
					</ul>
				</div>
			</div>			
		</nav>
	</section>

	<section>
		<div class="jumbotron">
			<div class="container">
				<h1>Products</h1>
				<p>Add Products</p>
			</div>		
		</div>
	</section>
	
	<div class="container">
		<div class="row">
			<div class="col-md-4 col-md-offset-4">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">Please sign in</h3>
					</div>
					<div class="panel-body">
						<c:if test="${not empty error}">
							<div class="alert alert-danger">
								<spring:message code="AbstractUserDetailsAuthenticationProvider.badCredentials" /><br />
							</div>
						</c:if>
						
						<form action="<c:url value="/login"></c:url>" method="post">		
							<fieldset>
								<div class="form-group">
									<input class="form-control" placeholder="User Name" name='j_username' type="text" />
								</div>
						
								<div class="form-group">
									<input class="form-control" placeholder="Password" name='j_password' type="password" value="" />
								</div>
								<input class="btn btn-lg btn-success btn-block" type="submit" value="Login" />
							</fieldset>
							<%-- <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> --%>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	
</body>
</html>