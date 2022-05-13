<%@ page import="com.main.user.resources.UserResource"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<meta charset="ISO-8859-1">
<title>UserInput</title>
<script src="javascript/main.js"></script>
<style>
*{
	padding:0;
	margin:0;
	box-sizing:border-box;
	list-style:none;
	text-decoration:none;
	
	}
	body{
	background:url("image/1.jpg");
	font-family: montserrat;
		}
	ul {
  list-style-type: none;
  margin: 0;
  padding: 0;
}

li {
  display: inline;
  float: right;
  padding:0 5px 25px 5px;
  margin-bottom:40px;
  margin-right:100px;
font-size:20px;
font-weight:bold;

}
ul li a{
text-decoration:none;
color:white;}
	.row{
	background-color: rgba(255,255,255);
	width:100%;
	box-shadow:12px 12px 22px ;
	overlay:0.5;
	border-radius:10px;
	}
	button{
	}
	. btnUpdate, . btnDelete{
		border:none;
		outline:none;
		height:50px;
		width:70%;
		background-color:black;
		color:white;
		border-radius:4px;
		font-weight:bold;
		margin-top:5%;
		 display: inline-block;
  		margin:2px;
	}
	
	.btnUpdate:hover{
	background:white;
	border:3px solid black;
	color:black;
	}
	col{
	width:100%;}
	
</style>
</head>
<body>


<ul>
  <li><a href="#news">Table View</a></li>
  <li><a href="#contact">User Registration</a></li>
  <li style="float:left; margin-left:100px; color:white; font-size:45px;">Electro Grid</li>
</ul>

<section class ="Form my-5 mx-5">
<div class="container"> 
<center>
 <div class="row gx-0">

 <div class = "col px-5 pt-5 ">

 <center>
 <h1 class="font-weight-bold py-3 mt-2">User Details</h1></center>
<br>
<div class ="sample" id="user">
</div>

 </div>
 </div>
 </center>
 </div>
</section>

<script>

$.ajax({
        url: "http://localhost:8081/electro_grid_user/rest/users/get-users/" ,
        type: 'GET',
        dataType:'text',
        success: function(response){
        	console.log(response);
        	$(".sample").append(response);
        	
        },
        error: function (request, message, error) {
        	$(".sample").append("<h1>failed to load </h1>");
        	console.log(request);
        	console.log(message);
        	console.log(error);
        }
    });
    
function deleteUser(id){
	$.ajax({
		url:'http://localhost:8081/electro_grid_user/rest/users/delete-user/'+ id, 
		type:'DELETE',
		success: function(){
			alert ('record has been deleted');
			window.location = "http://localhost:8081/electro_grid_user/tabelView.jsp";
			
			
		}
	})
}




</script>

</body>

</html>