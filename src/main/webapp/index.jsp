<!DOCTYPE html>
<html lang="en">
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<head>
		<title>Session scheduler</title>
		<!-- Latest compiled and minified CSS -->
		<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
	</head>
	<body>
		<div class="container">
		<br/>
			<form role="form" action="/schedule" method="post">
				<div class="form-group">
					<label>Enter list of sessions (if left blank, test input will be used)</label>
				</div>
				<div class="form-group">
					<textarea name="sessions" id="sessions" class="form-control" rows="5"></textarea>
				</div>
				<div class="form-group">
					<button type="submit" class="btn btn-primary">Schedule</button>
				</div>
			</form>
			<c:if test="${not empty tracks}">
				<c:forEach items="${tracks}" var="track">
					<label>${track.key}</label>
					<br/><br/>
					<table class="table table-bordered">
						<tr>
							<th>Time</th>
							<th>Session</th>
						</tr>
						<c:forEach items="${track.value}" var="session">
							<tr>
								<td>${session.key}</td>
								<td>${session.value}</td>
							</tr>
						</c:forEach>
					</table>
				</c:forEach>
			</c:if>
		</div>
	</body>
</html>