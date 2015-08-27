/**
 *  Edit profile  Js
 */

$(document).on("submit","#addPhotoForm",function(event){
	event.preventDefault(); // prevent this form from being submited
	var form = document.getElementById("addPhotoForm");
	var oMyForm = new FormData(form);
	
	$.ajax({
		url: form.action,
		data: oMyForm,
		dataType: 'text',
		processData: false,
		contentType: false,
		type: 'POST',
		success: function(data){
			console.log(data);
			$('#photos').html(data);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			  console.log(textStatus, errorThrown);
			  $('#errorMessage').html(textStatus  + " " + errorThrown);
			  $('#errors').removeAttr('style')
			  $('#errors').show("slow").delay(5000).hide("slow");
			}
	});
	
	return false;
});


$(document).on("click",".deleteProfilePhoto",function(event){
	event.preventDefault(); 
	var url = $(this).attr('href');
	console.log(url);
	$("#dialog-confirm").dialog({
		resizable: false,
		height:200,
		width : 400,
		modal: true,
		buttons: {
			Delete: function() {
				$.ajax({
					url: url,
					dataType: 'html',					       
					type: 'GET',
					success: function(data){
						console.log(data);
						$('#photos').html(data);
					},
					error : function() {
						console.log('Deconnexion du serveur');
					}
				});
				$( this ).dialog( "close" );


			},
			Cancel: function() {
				$( this ).dialog( "close" );
			}
		},
		open: function() {
			$('.ui-dialog-buttonpane').find('button:contains("Cancel")').addClass('btn-primary');
			$('.ui-dialog-buttonpane').find('button:contains("Delete")').addClass('btn-default');
		}
	});
	event.stopPropagation();
	return false;
});

$(document).on("click",".editProfilePhoto",function(event){
	event.preventDefault(); 
	var url = $(this).attr('href');
	console.log(url);
	$.ajax({
		url: url,
		dataType: 'html',					       
		type: 'GET',
		success: function(data){
			console.log(data);
			$('#photos').html(data);
			$("#editPhotoBox").show("slow");
			$("#showAddPhotoForm").hide("slow");
		},
		error : function() {
			console.log('Deconnexion du serveur');
		}
	});
	event.stopPropagation();
	return false;
});

$(document).on("click","#showAddPhotoForm",function(){
	resetAddPhotoForm();
	$("#editPhotoBox").show("slow");
	$("#showAddPhotoForm").hide("slow");
});

	
	
$(document).on("click","#hideAddPhotoForm",function(){
	resetAddPhotoForm();
	$('#profilPicturePreview').remove();
	$("#editPhotoBox").hide("slow");
	$("#showAddPhotoForm").show("slow");
});

function resetAddPhotoForm() {
	$('#idPhoto').val('');
	$('#addPhotoForm input.idPhoto').val('');
	$('#addPhotoForm textarea[name="description"]').val('');
	$('#addPhotoForm input:checkbox').removeAttr('checked');
}
/**
 * SERVICES
 */
$(document).on("submit","#addServiceForm",function(event){
	event.preventDefault(); // prevent this form from being submited
	var form = document.getElementById("addServiceForm");
	var oMyForm = new FormData(form);
	

	$.ajax({
		url: form.action,
		data: oMyForm,
		dataType: 'text',
		processData: false,
		contentType: false,
		type: 'POST',
		success: function(data){
			console.log(data);
			$('#services').html(data);
			resetAddServiceForm() ;
		}
	});
	return false;
});


$(document).on("click",".editProfileService",function(event){
	event.preventDefault(); 
	var url = $(this).attr('href');
	console.log(url);
	$.ajax({
		url: url,
		dataType: 'html',					       
		type: 'GET',
		success: function(data){
			console.log("edit service data : " + data);
			$('#services').html(data);
			$("#editServiceBox").show("slow");
			$("#showAddServiceForm").hide("slow");
			
		},
		error : function() {
			console.log('Deconnexion du serveur');
		}
	});
	event.stopPropagation();
	return false;
});

$(document).on("click",".deleteProfileService",function(event){
	event.preventDefault(); 
	
	var url = $(this).attr('href');
	console.log(url);
	$("#dialog-confirm").dialog({
		resizable: false,
		height:200,
		width : 400,
		modal: true,
		buttons: {
			Delete: function() {
				$.ajax({
					url: url,
					dataType: 'html',					       
					type: 'GET',
					success: function(data){
						console.log(data);
						$('#services').html(data);
					},
					error : function() {
						console.log('Deconnexion du serveur');
					}
				});
				$( this ).dialog( "close" );


			},
			Cancel: function() {
				$( this ).dialog( "close" );
			}
		},
		open: function() {
			$('.ui-dialog-buttonpane').find('button:contains("Cancel")').addClass('btn-primary');
			$('.ui-dialog-buttonpane').find('button:contains("Delete")').addClass('btn-default');
		}
	});
	event.stopPropagation();
	return false;
});


	
	
	$(document).on("click","#showAddServiceForm",function(){
		$("#editServiceBox").show("slow");
		$(this).hide("slow");
	});
	
	
	$(document).on("click","#hideAddServiceForm",function(){
		resetAddServiceForm();		
		$("#editServiceBox").hide("slow");
		$("#showAddServiceForm").show("slow");
	});


	function resetAddServiceForm() {
		$('#idService').val('');
		$('#addServiceForm input.idService').val('');
		$('#addServiceForm select[name="idcService"]').find('option:selected').removeAttr("selected");
		$('#addServiceForm textarea[name="description"]').val('');
		$('#addServiceForm input:checkbox').removeAttr('checked');
}

	
