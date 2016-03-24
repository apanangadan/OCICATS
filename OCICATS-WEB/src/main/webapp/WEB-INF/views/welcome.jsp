<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
	<title>Welcome</title>
</head>

<body>
	<section>
		<nav class="navbar navbar-default">
			<div class="container-fluid">
				<div class="navbar-header">
					<a class="navbar-brand" href="<spring:url value="/"/>">OCICATS</a>
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
		
	<section>
		<div class="jumbotron">
			<div class="container">
				<h1>${greeting} </h1>
				<p>${tagline} </p>
			</div>
		</div>
	</section>
	
</body>

</html>