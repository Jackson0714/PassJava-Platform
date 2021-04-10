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
	},
	methods: {
		_change:function(...arg){
			if(global._events && typeof global._events.change === 'function'){
				global._events.change(...arg);
			}
		}
	}
})