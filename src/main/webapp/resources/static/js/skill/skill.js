function addSkill(tokName, tokValue) {
    var skillName = $('#skill-name').val();
    var description = $('#skill-description').val();
    var url = "/skill/addSkill?"+tokName+"="+tokValue;
    var params = {skillName:skillName, description:description};
    $.post(url, params, function (data) {
    	switch(data){
			case 'success':
				alert('Add skill success!');
				window.location.reload(true); 
			break;
			case 'empty':
				alert('Both of skill name and description can not be empty!');
			break;
			case 'exist':
				alert('Skill already existed!');
			break;
		}
    });
    //window.parent.location.reload();
    //top.document.location.reload();
    $('#skillModal').modal('toggle');
    //top.location.reload();
    //closeModal('#dmodal');
    //input.val('');
}

