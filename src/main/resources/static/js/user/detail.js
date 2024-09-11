'use strict';

$(function() {
	$('#btn-update').click(function() {
		return confirm('ユーザー情報を更新しますか？');
	});
	
	$('#btn-delete').click(function() {
		return confirm('ユーザー情報を削除しますか？');
	});
	
	$('#btn-update-rest').click(function() {
		if(confirm('ユーザー情報を更新しますか？')) {
			updateUserRest();
		}
	});
	
	$('#btn-delete-rest').click(function() {
		if(confirm('ユーザー情報を削除しますか？')) {
			deleteUserRest();
		}
	});
	
	function updateUserRest() {
		const formData = $('#user-detail-form').serializeArray();
		$.ajax({
			type: 'PUT'
			, chache: false
			, url: '/user/update'
			, data: formData
			, dataType: 'json'
		}).done(function(data) {
			alert('ユーザー情報を更新しました');
			window.location.href = '/user/list';
		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert('ユーザー情報の更新に失敗しました');
		}).always(function() {
			// 
		});
	};
	
	function deleteUserRest() {
		const formData = $('#user-detail-form').serializeArray();
		console.log(formData);
		$.ajax({
			type: 'DELETE'
			, chache: false
			, url: '/user/delete'
			, data: formData
			, dataType: 'json'
		}).done(function(data) {
			alert('ユーザー情報を削除しました');
			window.location.href = '/user/list';
		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert('ユーザー情報の削除に失敗しました');
		}).always(function() {
			// 
		});
	};
});