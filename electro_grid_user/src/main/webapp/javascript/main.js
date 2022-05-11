function update(id){
		$.ajax({
		url:'http://localhost:8081/electro_grid_user/rest/users/update-user/' +id,
		method:'GET',
		 dataType:'text',
		 success: function(data){
			  $()
		 }
		
		})
	}