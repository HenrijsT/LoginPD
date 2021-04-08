<!DOCTYPE html >
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="sat, 01 Dec 2001 00:00:00 GMT">
<title>LoginPD homepage</title>
<link href="static/css/bootstrap.min.css" rel="stylesheet">
<link href="static/css/style.css" rel="stylesheet">
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>
<body>
	<div role="navigation">
		<div class="navbar navbar-inverse">
			<a href="/" class="navbar-brand">LoginPD</a>
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<li><a href="/show-users">All Users</a></li>
					<li><a href="/logout">Logout</a></li>
				</ul>
			</div>
		</div>
	</div>

	<c:choose>
    		<c:when test="${mode=='MODE_HOME' }">
    			<div class="container" id="homediv">
    				<div class="jumbotron text-center">
    					<h1>This was made by Henrijs Treiguts</h1>
    				</div>
    			</div>
    		</c:when>

    		<c:when test="${mode=='ALL_USERS' }">
            			<div class="container text-center" id="tasksDiv">
            				<h3>All Users</h3>
            				<hr>
            				<div class="table-responsive">
            					<table class="table table-striped table-bordered">
            						<thead>
            							<tr>
            								<th>Id</th>
            								<th>UserName</th>
            								<th>Full Name</th>
            								<th>Age</th>
            								<th>Registration Date</th>
            							</tr>
            						</thead>
            						<tbody>
            							<c:forEach var="user" items="${users }">
            								<tr>
            									<td>${user.id}</td>
            									<td>${user.username}</td>
            									<td>${user.fullName}</td>
            									<td>${user.age}</td>
            									<td>${user.regDate}</td>
            								</tr>
            							</c:forEach>
            						</tbody>
            					</table>
            				</div>
            			</div>
            		</c:when>
    	</c:choose>

	<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
	<script src="static/js/jquery-1.11.1.min.js"></script>
	<script src="static/js/bootstrap.min.js"></script>
</body>
</html>