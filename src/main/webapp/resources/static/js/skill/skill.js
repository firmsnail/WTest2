function addSkill(tokName, tokValue) {
    var skillName = $('#skill-name').val();
    var description = $('#skill-description').val();
    var url = "/skill/addSkill?"+tokName+"="+tokValue;
    var params = {skillName:skillName, description:description};
    $.post(url, params, function (data) {
    	switch(data){
			case -2:
				alert('Both of skill name and description can not be empty!');
			break;
			case -1:
				alert('Skill already existed!');
			break;
			default:
				var opt = new Option(skillName, data);
				$('select[name="skills"]')[0].options.add(opt);
				alert('Add skill success!');
		}
    });
    //window.parent.location.reload();
    //top.document.location.reload();
    $('#skillModal').modal('toggle');
    //top.location.reload();
    //closeModal('#dmodal');
    //input.val('');
}

