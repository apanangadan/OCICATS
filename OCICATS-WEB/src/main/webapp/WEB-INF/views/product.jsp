<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" >
	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.0.1/angular.min.js"></script>
	<script src="/webstore/resources/js/controllers.js"></script>
	<title>Product</title>
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
				<h1>Product</h1>
			</div>		
		</div>
	</section>
	
	<section class="container" data-ng-app="cartApp">
		<div class="row">
			<div class="col-md-5">
				<img alt="image" src="<spring:url value="/resources/images/${product.productId}.png"></spring:url>" style="width:100%">
			</div>
			<div class="col-md-5">	
				<h3>${product.name}</h3>
				<p>${product.description}</p>
				<p>
					<strong>Item Code: </strong>
					<span class="label label-warning">${product.productId}</span>
				</p>
				<p>
					<strong>Manufacturer: </strong> 
					${product.manufacturer}
				</p>
				<p>
					<strong>Catrgory: </strong> 
					${product.category}
				</p>
				<p>
					<strong>Available units in stock: </strong> 
					${product.unitsInStock}
				</p>
				<h4>${product.unitPrice} USD</h4>
				<p data-ng-controller="cartCtrl">
					<a href="<c:url value="/products" />" class="btn btn-default">
						<span class="glyphicon glyphicon-hand-left" ></span> Back
					</a>
				
					<a href="#" class="btn btn-warning btn-large" data-ng-click="addToCart('${product.productId}')">
						<span class="glyphicon glyphicon-shopping-cart" ></span> Order Now
					</a>
					
					<a href="<spring:url value="/cart" />" class="btn btn-default">
						<span class="glyphicon-hand-right glyphicon"></span> View Cart 
					</a>
				</p>							
			</div>
		</div>
	</section>
	
</body>

</html>