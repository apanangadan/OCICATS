<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta name="_csrf_parameter" content="_csrf" />
	<meta name="_csrf_header" content="X-CSRF-TOKEN" />
	<meta name="_csrf" content="dea86ae8-58ea-4310-bde1-59805352dec7" />
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" />
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
						<li><a href="<spring:url value="/collecting/search"/>">Collecting Data</a></li>
						<li><a href="<spring:url value="/topics"/>">Topics</a></li>
						<li><a href="<spring:url value="/cart"/>">Sentiment Analysis</a></li>
					</ul>
				</div>
			</div>			
		</nav>
	</section>

	<section>
		<div class="jumbotron"  style="padding-right: 30px">
			<div class="container">
				<h1>Search Twitter</h1>
				<p></p>
			</div>		
		</div>
	</section>
	
	<section class="container">
		<form:form modelAttribute="newQuery" class="form-horizontal" >
			<fieldset>
				
				<div class="form-group">
					<label class="control-label col-lg-2 col-lg-2" for="productId"> 
						Search Words
					 </label>
					<div class="col-lg-10">
						<form:input id="query" path="query" type="text" class="form:input-large" />
					</div>
				</div>
				
				<div class="form-group">
					<label class="control-label col-lg-2 col-lg-2" for="name">
						Start Date
					</label>
					<div class="col-lg-10">
						<form:input id="startDate" path="startDate" type="text" class="form:input-large" />
					</div>
				</div>
				
				<div class="form-group">
					<label class="control-label col-lg-2 col-lg-2" for="unitPrice">
						End Date
					</label>
					<div class="col-lg-10">
						<form:input id="endDate" path="endDate" type="text" class="form:input-large" />
					</div>
				</div>
				
				<div class="form-group">
					<div class="col-lg-offset-2 col-lg-10">
						<input type="submit" id="btnAdd" class="btn btn-primary" value="Search" />
					</div>
				</div>
			</fieldset>
		</form:form>
	</section>

	
</body>
</html>