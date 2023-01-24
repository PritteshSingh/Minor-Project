<!DOCTYPE html>
<html>
<head>
	<title>Feedback Form</title>
  <style>
		body {
			background-color: #f2f2f2;
			font-family: Arial, sans-serif;
			text-align: center;
		}
		h1 {
			color: #4c4c4c;
			margin-top: 50px;
			margin-bottom: 30px;
		}
		form {
			background-color: #fff;
			padding: 30px;
			border-radius: 10px;
			box-shadow: 2px 2px 10px #ccc;
			margin: 0 auto;
			width: 500px;
			text-align: left;
		}
		label {
			font-weight: bold;
			margin-bottom: 10px;
			display: block;
		}
		input[type="radio"] {
			margin-right: 10px;
		}
		textarea {
			width: 100%;
			padding: 10px;
			margin-bottom: 20px;
			border: 1px solid #ccc;
			border-radius: 5px;
			resize: none;
		}
		input[type="submit"] {
			background-color: #4c4c4c;
			color: #fff;
			padding: 10px 20px;
			border: none;
			border-radius: 5px;
			cursor: pointer;
		}
		input[type="submit"]:hover {
			background-color: #333;
		}
	</style>
</head>
<body>
	<h1>Feedback Form</h1>
	<form method = "post" action="feedback">
		<label>1. Rate your user interface experience:</label>
		<input type="radio" name="q1" value="1"> 1
		<input type="radio" name="q1" value="2"> 2
		<input type="radio" name="q1" value="3"> 3
		<input type="radio" name="q1" value="4"> 4
		<input type="radio" name="q1" value="5"> 5
		<br><br><br>
		<label>2. Rate your experience of our service:</label>
		<input type="radio" name="q2" value="1"> 1
		<input type="radio" name="q2" value="2"> 2
		<input type="radio" name="q2" value="3"> 3
		<input type="radio" name="q2" value="4"> 4
		<input type="radio" name="q2" value="5"> 5
		<br><br><br>
		<label>3. Would you recommend our service your friends and family:</label>
		<input type="radio" name="q3" value="yes"> Yes
		<input type="radio" name="q3" value="no"> No
		<br><br><br>
		<label>4. Any other suggestions:</label>
		<br>
		<textarea name="suggestions"></textarea>
		<br>
		<input type="submit" value="Submit">
	</form>
</body>
</html>