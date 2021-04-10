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
			class:'',
			style:''
		},
		size:{
			w:0,
			h:0
		},
		styleObj:{}
	},
	lifetimes:{
		attached:function(){
			const _ts = this;
			let dataAttr = this.data.data.attr;

			// 将图片大小处理到对象中
			if(dataAttr.width){
				_ts.data.size.w = +dataAttr.width / config.dpr;
			};

			if(dataAttr.height){
				_ts.data.size.h = +dataAttr.height / config.dpr;
			};

			// 将样式合并到样式对象中
			if(dataAttr.style){
				let re = /;\s{0,}/ig;
				dataAttr.style = dataAttr.style.replace(re,';');
				dataAttr.style.split(';').forEach(item => {
					let itemArr = item.split(':');
					if(/^(width|height)$/i.test(itemArr[0])){
						let num = parseInt(itemArr[1]) || 0,
							key = '';
						// itemArr[1] = num / config.dpr + itemArr[1].replace(num,'');
						switch (itemArr[1].toLocaleLowerCase()) {
							case 'width':
								key = 'w';
							break;
							case 'height':
								key = 'h';
							break;
						};
						_ts.data.size[key] = num / config.dpr;
					}else{
						_ts.data.styleObj[itemArr[0]] = itemArr[1];
					};
				});
			};

			// 设置公式图片
			_ts.setData({
				attr:{
					src:dataAttr.src,
					class:dataAttr.class,
					style:_ts.setStyle(_ts.data.styleObj),
					size:_ts.data.size
				}
			});
		}
	},
	methods: {
		// 设置图片样式
		setStyle:function(o){
			let str = ``;
			for(let key in o){
				str += `${key}:${o[key]};`;
			};
			return str;
		},

		// 图片加载完成设置图片大小
		load:function(e){
			const _ts = this;

			if(!_ts.data.styleObj.width || !_ts.data.styleObj.height){
				_ts.setData({
					size:{
						w:e.detail.width / config.dpr,
						h:e.detail.height / config.dpr
					}
				});
			};
		}
	}
})