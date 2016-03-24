<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
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
				<h1>Search</h1>
				<p></p>
			</div>		
		</div>
	</section>
	
	<section class="container">
		<div class="row">
			<c:forEach items="${products}" var="product">
				<div class="col-sm-6 col-md-3" style="padding-bottom: 15px">
					<div class="thumbnail">
						<img alt="image" src="<c:url value="/resources/images/${product.productId}.png"></c:url>" style="width:100%">
						<div class="caption">
							<h3>${product.name}</h3>
							<p>${product.description}</p>
							<p>${product.unitPrice} USD</p>
							<p>Available: ${product.unitsInStock} units in stock</p>
							<p>
								<a href="<spring:url value="/products/product?id=${product.productId}"/>" class="btn btn-primary">
									<span class="glyphicon-info-sign glyphicon"></span> Details
								</a>
							</p>
						</div>
					</div>			
				</div>
			</c:forEach>
		</div>
	</section>
	
</body>
</html>