// Auto update layout
(function() {
	window.layoutHelpers.setAutoUpdate(true);
	window.attachMaterialRippleOnLoad();
})();

// Collapse menu
(function() {
	if ($('#layout-sidenav').hasClass('sidenav-horizontal') || window.layoutHelpers.isSmallScreen()) {
		return;
	}

	try {
		window.layoutHelpers.setCollapsed(
			localStorage.getItem('layoutCollapsed') === 'true',
			false
		);
	} catch (e) { }
})();

$(function() {
	// Initialize sidenav
	$('#layout-sidenav').each(function() {
		new SideNav(this, {
			orientation: $(this).hasClass('sidenav-horizontal') ? 'horizontal' : 'vertical'
		});
	});

	// Initialize sidenav togglers
	$('body').on('click', '.layout-sidenav-toggle', function(e) {
		e.preventDefault();
		window.layoutHelpers.toggleCollapsed();
		if (!window.layoutHelpers.isSmallScreen()) {
			try { localStorage.setItem('layoutCollapsed', String(window.layoutHelpers.isCollapsed())); } catch (e) { }
		}
	});

	if ($('html').attr('dir') === 'rtl') {
		$('#layout-navbar .dropdown-menu').toggleClass('dropdown-menu-right');
	}
});
function setValueDeleteId(id) {
	$("#data_delete").val(id)

}
/*function editForm(roles,id){
	$("#update-form").css("display","block");
		  $("#add-form").css("display","none");

	formRoles=$("#update-form").find("input[name='roles']")
	$("#update-form").find("input[name='userId']").attr('value',id)
	$("#button-reset-pass").attr('href', function(i, href) {
  return href + '?userId='+id;
});
	$.each(formRoles, function(  index,e ) {
		ele=$(e)
		if(roles.some((role)=>ele.data('role')==role.roleName)){
			console.log(ele)
			ele.attr('checked', true);
		}
	});
}*/
function submitForm(idForm) {
	$("#" + idForm).submit();
}
$(".cancel-form").click((e) => {
	e.preventDefault();

	$("#update-form").css("display", "none");
	$("#add-form").css("display", "none");
})

function addForm() {
	$("#add-form").css("display", "block");
	$("#update-form").css("display", "none");
}
$(function() {
	// setTimeout() function will be fired after page is loaded
	// it will wait for 5 sec. and then will fire
	// $("#successMessage").hide() function
	setTimeout(function() {
		$("#message").hide('blind', {}, 500)
	}, 5000);
});