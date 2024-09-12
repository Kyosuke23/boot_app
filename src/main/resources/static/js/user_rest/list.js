'use strict';

let userData = null;
let table = null;

$(function() {
	search();
	
	$('#btn-search').click(function() {
		search();
	});
});

function search() {
	const formData = $('#user-search-form').serializeArray();
	
	$.ajax({
		type: 'GET'
		, chache: false
		, url: '/user/rest/list'
		, data: formData
		, dataType: 'json'
		, contentType: 'application/json; charset=UTF-8'
		, timeout: 5000
	}).done(function(data) {
		userData = data;
		createDataTables();
	}).fail(function(jqXHR, textStatus, errorThrown) {
		alert('検索処理に失敗しました');
	}).always(function() {
		// 
	});
};

function createDataTables() {
	if(table !== null) {
		table.destroy();
	}
	
	table = $('#user-list-table').DataTable({
		language: {
			url: '/webjars/datatables-plugins/i18n/ja.json'
		}
		, bFilter: false // hidden search box
		, aLengthMenu: [10, 50, 100] // data length
		, data: userData
		, columns: [
			{data: 'userId'}
			, {data: 'userName'}
			, {
				data: 'birthday'
				, render: function(data, type, row) {
					const date = new Date(data);
					const year = date.getFullYear();
					const month = date.getMonth() + 1;
					const day = date.getDate();
					return year + '/' + month + '/' + day;
				}
			}
			, {data: 'age'}
			, {
				data: 'gender'
				, render: function(data, type, row) {
					let gender = '';
					if(data === 1) {
						gender = '男性';
					} else {
						gender = '女性';
					}
					return gender;
				}
			}
			, {
				data: 'userId'
				, render: function(data, type, row) {
					const url = '<a href="/user/rest/detail/' + data + '">詳細</a>';
					return url;
				}
			}
			
		]
	});
};