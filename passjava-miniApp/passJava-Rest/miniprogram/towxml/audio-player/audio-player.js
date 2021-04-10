const Audio = require('./Audio');
Component({
	options: {
		styleIsolation: 'shared'
	},
	properties: {
		data: {
			type: Object,
			value: {}
		}
	},
	lifetimes:{
		// 页面生命周期
		attached:function(){
			const _ts = this,
				audio = _ts.audio = new Audio(this.data.data);

			audio.eventPlay = function(){
				_ts.setData({tips:{state:'h2w__audio--play',text:'Playing'}});
			};
			audio.eventCanplay = function(){
				_ts.setData({tips:{state:'h2w__audio--readyed',text:'Readyed'}});
			};
			audio.eventTimeUpdate = function(duration,currentTime){
				_ts.setData({time:{currentTime:currentTime,duration:duration,schedule:Math.round(_ts.audio.currentTime) / Math.round(_ts.audio.duration) * 100 + '%'}});
			};
			audio.eventPause = function(){
				_ts.setData({tips:{state:'h2w__audio--pause',text:'Pause'}});
			};
			audio.eventStop = function(){
				_ts.setData({tips:{state:'h2w__audio--end',text:'End'}});
			};





			// // 更新播放状态
			// _ts.audio.onTimeUpdate = function(duration,currentTime){
			// 	_ts.setData({
			// 		playerData:{
			// 			state:'h2w__audio--play',
			// 			tips:'Playing',
			// 			currentTime:currentTime,
			// 			duration:duration,
			// 			schedule:_ts.audio.currentTime / _ts.audio.duration * 100 + '%'
			// 		}
			// 	});
			// };

			// _ts.audio.onPause = function(){
			// 	_ts.setData({playerData:{state:'h2w__audio--pause',tips:'Pause'}});
			// };

			// _ts.audio.onCanplay = function(){
			// 	_ts.setData({playerData:{state:'h2w__audio--readyed',tips:'Readyed'}});
			// };

			// _ts.audio.onError = function(){
			// 	_ts.setData({playerData:{state:'h2w__audio--error',tips:'Error'}});
			// };

			// _ts.audio.onEnded = ()=>{
			// 	_ts.setData({playerData:{state:'h2w__audio--end',tips:'End'}});
			// };

		},
		moved:function(){
			_ts.audio.destroy();
		},
		detached:()=>{

		},
	},
	data: {
		tips:{
			state:'',
			text:'--'
		},
		time: {
			currentTime:'00:00:00',
			duration:'00:00:00',
			schedule:'0%'
		}
	},
	methods: {
		playAndPause: function () {
			const _ts = this,
				audio = _ts.audio;

			// console.log(audio);

			audio.isTouch = true;
			if(audio.status === 'update' || audio.status === 'play'){
				// console.log('pause');
				audio.pause();
			}else{
				// console.log('play');
				audio.play();
			};
		}
	}
})