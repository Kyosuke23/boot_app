'use strict';

$(function() {
	// 時刻インプット時のイベント
	$('.start_time, .end_time, .rest_time').blur(function() {
		setWorkingTime($(this));
	});
	
	// 保存ボタン押下時のイベント
	$('#btn-save').click(function() {
		save();
	});
	
	/**
	 * 保存処理
	 * 開始・終了時刻の値をリクエストデータ用に設定する
	 */
	function save() {
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
						+ '-' + (('0' + (end_time_dt.getMonth() + 1)).slice(-2))
						+ '-' + (('0' + (end_time_dt.getDate() + 1)).slice(-2))
						+ ' ' + end_time_td.val();
				}
				$(this).find('.end_time_hidden').val(end_time);
			}
		});
	};
	
	/**
	 * 勤務時間の設定処理
	 * 各種時刻が更新された時、勤務時間を再計算して表示を変更
	 */
	function setWorkingTime(e) {
		// 勤務時間
		let working_time = '';
		
		// 開始・終了時刻と休憩時間の取得
		const row = e.parents('tr');
		const start_time = row.find('.start_time_hidden').val();
		const end_time = row.find('.end_time_hidden').val();
		const rest_time = row.find('.rest_time').val(); // 休憩時間はhidden属性ではない
		console.log(start_time);
		console.log(end_time);
		console.log(rest_time);
		
		// 全ての時刻に入力があれば勤務時間を再計算
		if(start_time && end_time && rest_time) {
			// 休憩時間を秒に変換
			const rest_h = parseInt(rest_time.substring(0, 2));
			const rest_m = parseInt(rest_time.substring(3, 5));
			const rest_s = rest_h * 60 * 60 + rest_m * 60; // 秒変換された休憩時間
			console.log(rest_h);
			console.log(rest_m);
			console.log(rest_s);
			
			// 勤務時間を終了時刻 - 開始時刻 - 休憩時間で算出（秒）
			working_time = (new Date(end_time) - new Date(start_time)) / 1000 - rest_s;
			
			// 勤務時間を時間単位に変換
			working_time = secToHHMM(working_time);
			console.log(working_time);
		}
		
		// 勤務時間を再描画して処理終了
		row.find('.working_time').text(working_time);
		return;
	};
	
	/**
	 * 秒をhh:mm形式に変換する
	 */
	function secToHHMM(sec) {
		// 時・分に分解
		const h = Math.floor(sec / 3600);
		const m = Math.floor(sec % 3600 / 60);
		
		// 時・分をそれぞれ2桁に合わせる
		let hh = h;
		// 時間が3桁未満の時のみ0埋め
		if(h < 100) {
			hh = (`00${h}`).slice(-2);
		}
		const mm = (`00${m}`).slice(-2);
		
		// 時・分を結合して時刻形式に整形
		const time = `${hh}:${mm}`;
		
		// 結果を返却
		return time;
	};
});