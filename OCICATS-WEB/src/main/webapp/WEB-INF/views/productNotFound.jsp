<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" >
		<title>Welcome</title>
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
					<h1 class="alert alert-danger"> ${invalidProductId}</h1>
				</div>
			</div>
		</section>
		
		<section>
			<div class="container">
				<p>${url}</p>
				<p>${exception}</p>
			</div>
			
			<div class="container">
				<p>
					<a href="<spring:url value="/products" />" class="btn btn-primary">
						<span class="glyphicon-hand-left glyphicon"></span> Products
					</a>
				</p>
			</div>
		</section>
		
	</body>
</html>