'use strict';

$(function() {
	$('#btn-update').click(function() {
		return confirm('ユーザー情報を更新しますか？');
	});
	
	$('#btn-delete').click(function() {
		return confirm('ユーザー情報を削除しますか？');
	});
});