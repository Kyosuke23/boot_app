'use strict';

$(function() {
	$('#btn-signup').click(function() {
		signupUser();
	});
	
	function signupUser() {
		removeValidResult();
		const formData = $('#signup-form').serializeArray();
	
		$.ajax({
			type: 'POST'
			, chache: false
			, url: '/user/signup/rest'
			, data: formData
			, dataType: 'json'
		}).done(function(data) {
			if(data.result === 90) {
				$.each(data.errors, function(key, value) {
					reflectValidResult(key, value);
				});
			} else if(data.result === 0) {
				alert('ユーザー情報を登録しました');
				window.location.href = '/login';
			}
		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert('ユーザー情報の登録に失敗しました');
		}).always(function() {
			// 
		});
	};
	
	function removeValidResult() {
		$('.is-invalid').removeClass('is-invalid');
		$('.invalid-feedback').remove();
		$('.text-danger').remove();
	};
	
	function reflectValidResult(key, value) {
		if(key === 'gender') {
			$('input[name=' + key + ']').addClass('is-invalid');
			$('input[name=' + key + ']').parent().parent().append('<div class="text-danger">' + value + '</div>');
		} else {
			$('input[id=' + key + ']').addClass('is-invalid');
			$('input[id=' + key + ']').after('<div class="invalid-feedback">' + value + '</div>');
		}
	}
});