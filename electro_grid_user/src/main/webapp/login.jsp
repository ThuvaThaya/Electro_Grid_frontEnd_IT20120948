<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.6.0.min.js"></script>
<meta charset="ISO-8859-1">
<title>Login</title>
<style>
*{
	padding:0;
	margin:0;
	box-sizing:border-box;
	
	}
	body{
		background:rgb(219,226,226);
		}
	.row{
	background:white;
	border-radius:30px;
	box-shadow:12px 12px 22px grey;
	}
	img{
	border-top-left-radius:30px;
	border-bottom-left-radius:30px;
	}
	.btn1{
		border:none;
		outline:none;
		height:50px;
		width:100%;
		background-color:black;
		color:white;
		border-radius:4px;
		font-weight:bold;
		margin-top:5%;
	}
	.btn1:hover{
	background:white;
	border:3px solid black;
	color:black;
	}
	
</style>
</head>
<body>
<section class ="Form my-5 mx-5">
<div class="container"> 
 <div class="row gx-0">
 <div class="col-lg-5 ">
 <img alt="" src="image/1.jpg" class ="img-fluid">
 </div>
 <div class = "col-lg-7 px-5 pt-5">
 <h1 class="font-weight-bold py-3 mt-5">Login</h1>
 <h4>Sign in to your account</h4>
 <form>
 <div class="form-row my-5">
 <div class = "col-lg-7">
 <input type="email" class="form-control mt-5 my-4 p-2" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Enter email">
 </div>
 </div>
 <div class="form-row">
 <div class = "col-lg-7">
     <input type="password" class="form-control my-4 p-2" id="exampleInputPassword1" placeholder="Password">
  </div>
 </div>
 <div class="form-row">
 <div class = "col-lg-7 ">
   <button type="button" class="btn1 mt-3 mb-5">Login</button>
 </div>
 </div>
</form>
 </div>
 </div>
 </div>
</section>

</body>
</html>