/**
 *  Admin panel Js
 */

$(".confirmDelete").click(function(event){
	event.preventDefault(); 
	var url = $(this).attr('href');
	console.log(url);
	$("#dialog-confirm").dialog({
		resizable: false,
		height:200,
		modal: true,
		buttons: {
			Delete: function() {

				$( this ).dialog( "close" );
				location.href = url;

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

}); 


function previewImage(idImg, idPreviewDiv) {
	var oFReader = new FileReader();
	oFReader.readAsDataURL(document.getElementById(idImg).files[0]);

	oFReader.onload = function (oFREvent) {
		document.getElementById(idPreviewDiv).src = oFREvent.target.result;
	};
};
$("#profilPicture").change(function(){
	//   readURL(this);
	previewImage("profilPicture", "profilPicturePreview");
	$('#remove').show();
});
$('#remove').click(function(){
	$('#profilPicture').val('');
	$("#profilPicturePreview").attr('src','holder.js/100%x150');
	$(this).hide();
})


$("#picture").change(function(){
	//   readURL(this);
	previewImage("picture", "picturePreview");
	$('#removePicture').show();
});
$('#removePicture').click(function(){
	$('#picture').val('');
	$("#picturePreview").attr('src','holder.js/100%x150');
	$(this).hide();
})



/*$(document).on("submit","#uploadPhotoForm",function(event){
	event.preventDefault(); // prevent this form from being submited
	var form = document.getElementById("uploadPhotoForm");
	var oMyForm = new FormData(form);
	// oMyForm.append("file", file2.files[0]);

	$.ajax({
		url: form.action,
		data: oMyForm,
		dataType: 'text',
		processData: false,
		contentType: false,
		type: 'POST',
		success: function(data){
			console.log(data);
			$('#listPhotos-bloc').html(data);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			  console.log(textStatus, errorThrown);
			  $('#errors').html(textStatus  + " " + errorThrown);
			}
	});
	form.reset();
	$('#picture').val('');
});
*/

$(document).on("click",".deletePhoto",function(event){
	event.preventDefault(); 
	var url = $(this).attr('href');
	console.log(url);
	$("#dialog-confirm").dialog({
		resizable: false,
		height:200,
		modal: true,
		buttons: {
			Delete: function() {
				$.ajax({
					url: url,
					dataType: 'html',					       
					type: 'GET',
					success: function(data){
						//console.log(data);
						$('#listPhotos-bloc').html(data);
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
	
});

/*$(document).on("click",".editPhoto",function(event){
	event.preventDefault(); 
	var url = $(this).attr('href');
	console.log(url);
	$.ajax({
		url: url,
		dataType: 'html',					       
		type: 'GET',
		success: function(data){
			console.log(data);
			$('#listPhotos-bloc').html(data);
		},
		error : function() {
			console.log('Deconnexion du serveur');
		}
	});

});*/



/**
 * SERVICES
 */
/*$(document).on("submit","#addServiceForm",function(event){
	event.preventDefault(); // prevent this form from being submited
	var form = document.getElementById("addServiceForm");
	var oMyForm = new FormData(form);
	// oMyForm.append("file", file2.files[0]);

	$.ajax({
		url: form.action,
		data: oMyForm,
		dataType: 'text',
		processData: false,
		contentType: false,
		type: 'POST',
		success: function(data){
			console.log(data);
			$('#listServices-bloc').html(data);
		}
	});
	form.reset();
});*/


/*$(document).on("click",".editService",function(event){
	event.preventDefault(); 
	var url = $(this).attr('href');
	console.log(url);
	$.ajax({
		url: url,
		dataType: 'html',					       
		type: 'GET',
		success: function(data){
			console.log(data);
			$('#listServices-bloc').html(data);
		},
		error : function() {
			console.log('Deconnexion du serveur');
		}
	});
});*/

$(document).on("click",".deleteService",function(event){
	event.preventDefault(); 
	var url = $(this).attr('href');
	console.log(url);
	$("#dialog-confirm").dialog({
		resizable: false,
		height:200,
		modal: true,
		buttons: {
			Delete: function() {
				$.ajax({
					url: url,
					dataType: 'html',					       
					type: 'GET',
					success: function(data){
						//console.log(data);
						$('#listServices-bloc').html(data);
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
	
});

$(document).delegate('*[data-toggle="lightbox"]', 'click', function(event) {
    event.preventDefault();
    $(this).ekkoLightbox();
}); 
