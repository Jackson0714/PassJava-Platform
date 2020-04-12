(function() {
	window.Router = function() {
		var self = this;

		self.hashList = {}; /* 路由表 */
		self.index = null;
		self.key = '!';

		window.onhashchange = function() {
			self.reload();
		};
	};

	/**
	 * 添加路由,如果路由已经存在则会覆盖
	 * @param addr: 地址
	 * @param callback: 回调函数，调用回调函数的时候同时也会传入相应参数
	 */
	Router.prototype.add = function(addr, callback) {
		var self = this;

		self.hashList[addr] = callback;
	};

	/**
	 * 删除路由
	 * @param addr: 地址
	 */
	Router.prototype.remove = function(addr) {
		var self = this;

		delete self.hashList[addr];
	};

	/**
	 * 设置主页地址
	 * @param index: 主页地址
	 */
	Router.prototype.setIndex = function(index) {
		var self = this;

		self.index = index;
	};


	/**
	 * 跳转到指定地址
	 * @param addr: 地址值
	 */
	Router.prototype.go = function(addr) {
		var self = this;

		window.location.hash = '#' + self.key + addr;
	};

	/**
	 * 重载页面
	 */
	Router.prototype.reload = function() {
		var self = this;

		var hash = window.location.hash.replace('#' + self.key, '');
		//var addr = hash.split('/')[0];
		var addr = hash;
		var cb = getCb(addr, self.hashList);
		if(cb != false) {
			var arr = hash.split('/');
			arr.shift();
			cb.apply(self, arr);
		}
		else {
			self.index && self.go(self.index);	
		}
	};

	/**
	 * 开始路由，实际上只是为了当直接访问路由路由地址的时候能够及时调用回调
	 */
	Router.prototype.start = function() {
		var self = this;

		self.reload();
	}

	/**
	 * 获取callback
	 * @return false or callback
	 */
	function getCb(addr, hashList) {
		for(var key in hashList) {
			if(key == addr) {
				return hashList[key]	
			}
		}
		return false;
	}
})();