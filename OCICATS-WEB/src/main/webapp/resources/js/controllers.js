var cartApp = angular.module("cartApp", []);

cartApp.controller("cartCtrl", function($scope, $http, $window) {

	$scope.initCartId = function() {
		$http.get("topics/list").success(function(data) {
			$scope.files = data;
			// alert(JSON.stringify(data));
		});
	}

	$scope.lda = function(data) {
		$http.post("topics/analyze", data).then(function() {
			$http.get("topics/path").success(function(res) {
				
				$window.open(res);
			});

		});
	}

	/**
	 * json对象转字符串形式
	 */
	function json2str(o) {
		var arr = [];
		var fmt = function(s) {
			if (typeof s == 'object' && s != null)
				return json2str(s);
			return /^(string|number)$/.test(typeof s) ? "'" + s + "'" : s;
		}
		for ( var i in o)
			arr.push("'" + i + "':" + fmt(o[i]));
		return '{' + arr.join(',') + '}';
	}

});