<%@ page import="com.main.user.resources.UserResource"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="javascript/main.js"></script>
<meta charset="ISO-8859-1">
<title>UserInput</title>
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
	width:75%;
	box-shadow:12px 12px 22px ;
	overlay:0.5;
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
	col{
	width:100%;}
	
</style>
</head>
<body>


<ul>
  <li><a href="login.jsp">Logout</a></li>
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
 <h1 class="font-weight-bold py-3 mt-2">Registation Form</h1></center>
 <form id="formUser" name ="formUser" method ="post" action="UserInput.jsp">
  <div class="form-row">
 <div class = "col">
 <input type="text" id="fName" name ="fName" class="form-control mt-5 my-4 p-2"   placeholder="Enter First Name">
 </div>
 </div>
  <div class="form-row">
 <div class = "col">
 <input type="text" id="lName" name ="lName" class="form-control mt-5 my-4 p-2"  placeholder="Enter Last Name">
 </div>
 </div>
  <div class="form-row">
 <div class = "col">
 <input type="email" id="emailId" name ="emailId" class="form-control mt-5 my-4 p-2"  placeholder="Enter Email">
 </div>
 </div>
  <div class="form-row">
 <div class = "col">
 <input type="password" id="pWord" name ="pWord" class="form-control mt-5 my-4 p-2"  placeholder="Enter Password">
 </div>
 </div>
  <div class="form-row">
 <div class = "col">
 <input type="text" id="cNum" name ="cNum" class="form-control mt-5 my-4 p-2"  placeholder="Enter Phone Number">
 </div>
 </div>
  <div class="form-row">
 <div class = "col">
 <input type="text" id="Nic" name ="Nic" class="form-control mt-5 my-4 p-2"  placeholder="Enter NIC">
 </div>
 </div>
 <div class="form-row">
 <div class = "col">
 <input type="text" id="branchId" name ="branchId" class="form-control mt-5 my-4 p-2"  placeholder="Enter Branch ID">
 </div>
 </div>
 <div class="form-row">
 <div class = "col ">
 <input type="button" class="btn1 mt-3 mb-5" id ="btnSave" value="Submit" >
</div>
 </div>
</form>
 </div>
 </div>
 </center>
 </div>

</section>

<script>

const url = new URL(window.location);
const id = url.searchParams.get("id");
$.ajax({
    url: "http://localhost:8081/electro_grid_user/rest/users/get-user/"+id ,
    type: 'GET',
    dataType:'json',
    success: function(response){
    	
    	console.log(response);
    	const data = response[0];
    	$('#fName').val(data.firstName);
		  	$('#lName').val(data.lastName);
		  	$('#emailId').val(data.email);
		  	$('#pWord').val(data.password);
		  	$('#cNum').val(data.phoneNo);
		  	$('#Nic').val(data.nic);
		  	$('#branchId').val(data.branchId);
		  	
		  	
    	
    },
    error: function (request, message, error) {
    	alert(error);
        	console.log(request);
    	console.log(message);
    	console.log(error);
    }
    
});

function validateUserForm(){
	if($("#fName").val().trim()=="")
	{
		return "Please Input First Name";
	}
	if($("#lName").val().trim()=="")
	{
		return "Please Input Last Name";
	}
	if($("#emailId").val().trim()=="")
	{
		return "Please Input Email Address";
	}
	if($("#pWord").val().trim()=="")
	{
		return "Please Input Password";
	}
	if($("#cNum").val().trim()=="")
	{
		return "Please Input Contact Number";
	}
	if($("#Nic").val().trim()=="")
	{
		return "Please Input NIC";
	}
	if($("#branchId").val().trim()=="")
	{
		return "Please Input Branch ID";
	}
	return true;
}

$(document).ready(function(){
	var user ={};
	$('#btnSave').click(function(){
		user.firstName = $('#fName').val();
		user.lastName = $('#lName').val();
		user.email = $('#emailId').val();
		user.password = $('#pWord').val();
		user.phoneNo = $('#cNum').val();
		user.nic = $('#Nic').val();
		user.branchId = $('#branchId').val();
		var userObj = JSON.stringify(user);
		
		if(id>0){
			$.ajax({
				url:'http://localhost:8081/electro_grid_user/rest/users/update-user/'+id,
				method:'PUT',
				data: JSON.stringify({...user,user_id:id}),
				contentType: 'application/json; charset=utf-8',
				success: function(){
					alert('Saved Successfully');
					 window.location = "http://localhost:8081/electro_grid_user/tabelView.jsp";
				},error: function ( error) {
		        	alert(error);
		        }
			
		})
		}
		else{
			$.ajax({
				url:'http://localhost:8081/electro_grid_user/rest/users/add-user/',
				method:'POST',
				data: userObj,
				contentType: 'application/json; charset=utf-8',
				success: function(){
					alert('Saved Successfully');
					 $("#formUser")[0].reset();
					 window.location = "http://localhost:8081/electro_grid_user/tabelView.jsp";
				},error: function ( error) {
		        	alert(error);
		        }
			
		})
		}
		
		
	})
		

})



</script>
</body>
</html>