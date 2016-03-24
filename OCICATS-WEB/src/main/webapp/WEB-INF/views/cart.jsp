<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" />
	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.0.1/angular.min.js"></script>
	<script src="/OCICATS-WEB/resources/js/controllers.js"></script>
	<title>Cart</title>
</head>

<body>
	<section>
		<section>
		<nav class="navbar navbar-default">
			<div class="container-fluid">
				<div class="navbar-header">
					<a class="navbar-brand" href="<spring:url value="/"/>">My Website</a>
				</div>
				
				<div>
					<ul class="nav navbar-nav">
						<li class="active"><a href="<spring:url value="/home" />">Home</a></li>
						<li><a href="<spring:url value="/collecting/search"/>">Collecting Data</a></li>
						<li><a href="<spring:url value="/topics"/>">Topics</a></li>
						<li><a href="<spring:url value="/cart"/>">Sentiment Analysis</a></li>
					</ul>
				</div>
			</div>			
		</nav>
	</section>
	
		<div class="jumbotron">
			<div class="container">
				<h1>Topics</h1>
				<p>Please choose a dataset</p>
			</div>	
		</div>
	</section>
	
	<section class="container" data-ng-app="cartApp">
		<div data-ng-controller="cartCtrl" data-ng-init="initCartId()">
			<div>
				
				
				<a href="#" class="btn btn-success pull-right">
					<span class="glyphicon-shopping-cart glyphicon"></span> Check out 
				</a>
			</div>
			
			<table class="table table-hover">
				<tr>
					<th>Dataset name</th>
					
					
				</tr>
				
				<tr data-ng-repeat="item in files">
					<td>{{item}}</td>				
					<td>
						<a href="#" class="label label-danger" data-ng-click="removeFromCart(item)">
							<span class="glyphicon glyphicon-remove"></span> Remove
						</a>
						<a href="#" class="label label-danger" data-ng-click="lda(item)">
							<span class="glyphicon glyphicon-ok"></span> Analyze
						</a>
					</td>
				</tr>
				
				
			</table>
			
			
		</div>
	</section>
	
</body>
</html>