const config = require('../config');
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
	data: {
		attr:{
			src:'',
			class:''
		},
		size:{
			w:0,
			h:0
		}
	},
	lifetimes:{
		attached:function(){
			const _ts = this;
			let dataAttr = this.data.data.attr;

			// 设置公式图片
			_ts.setData({
				attr:{
					src:`${config.yuml.api}=${dataAttr.value}&theme=${global._theme}`,
					class:`${dataAttr.class}`
				}
			});
		}
	},
	methods: {
		load:function(e){
			const _ts = this;
			// 公式图片加载完成则根据其图片大小、类型计算其显示的合适大小
			let scale = 20,
				w = e.detail.width / scale,
				h = e.detail.height / scale;
			_ts.setData({
				size:{
					w:w,
					h:h
				}
			});
		}
	}
})