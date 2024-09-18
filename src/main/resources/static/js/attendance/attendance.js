'use strict';

$(function() {
	// 保存ボタン押下時のイベント
	$('#btn-save').click(function() {
		$('#result-table tbody tr').each(function() {
			// 開始・終了時刻の取得
			const start_time_td = $(this).find('.start_time');
			const end_time_td = $(this).find('.end_time');
			
			// 開始・終了時刻を日付型に変換してhidden項目に設定
			if(start_time_td.val() && end_time_td.val()) {
				// 今日の日付
				const date = $(this).find('.date_hidden').val();
				
				// 開始時刻の設定
				const start_time = date + ' ' + start_time_td.val();　
				$(this).find('.start_time_hidden').val(start_time);
				
				// 終了時刻の設定
				let end_time = date + ' ' + end_time_td.val();
				// 終了時刻 < 開始時刻 の場合は、終了時刻を翌日に設定する
				if(new Date(end_time) < new Date(start_time)) {
					const end_time_dt = new Date(end_time);
					end_time = 
						end_time_dt.getFullYear()
						+ '-' + (end_time_dt.getMonth() + 1)
						+ '-' + (end_time_dt.getDate() + 1)
						+ ' ' + end_time_td.val();
				}
				$(this).find('.end_time_hidden').val(end_time);
			}
			
		});
	});
});