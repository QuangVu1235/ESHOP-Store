var app = angular.module("myApp", ["ngRoute"]);
app.run(function($rootScope, $http) {

	$http.get("/api/currentuser/").then(function(response) {
		$rootScope.user = response.data;
	});

});

app.config(function($routeProvider) {
	$routeProvider
		.when("/profile/profile", {
			templateUrl: "profile",
			controller: "profileCtrl"
		})
		.when("/profile/order", {
			templateUrl: "order",
			controller: "orderCtrl"
		})
		.when("/profile/password", {
			templateUrl: "password",
			controller: "passwordCtrl"
		})
		.when("/blue", {
			templateUrl: "blue.htm"
		})
		.otherwise({ redirectTo: "/profile/profile" });
});

app.controller("orderCtrl", function($scope, $rootScope, $http) {
	$http.get("/api/order/list").then(function(response) {
		$rootScope.orders = response.data;
		console.log(response.data);
	});


});
var loadFile = function(event) {
	var image = document.getElementById('fileupload');
	image.src = URL.createObjectURL(event.target.files[0]);
};
app.controller("profileCtrl", function($scope, $rootScope, $http) {

	$scope.form = {};
	$scope.form = $rootScope.user;
	$scope.update = function() {

		var item = angular.copy($scope.form);
		$http.put("/api/user/${item.id}", item).then(function(response) {
			iziToast.success({
				title: 'OK',
				message: 'Cập nhập thông tin thành công',
			});

		});
	};
	$scope.imageChanged = function(files){HomeAdminController.java
		var data = new FormData();
		data.append('file', files[0]);
		$http.post('/api/upload/images/${files}', data, {
			tranformRequest: angular.identity,
			headers:{'Content-Type': undefined}
		}).then(respone =>{
			alert(respone.data.name);
			$scope.form.imgUrl = respone.data.name;
		}).catch(error =>{
			alert("loi hinh anh");
			console.log(error);
		});
	}


});
app.controller("passwordCtrl", function($scope, $rootScope, $http) {

	$scope.form = {};

	$scope.update = function() {
		var item = angular.copy($scope.form);
		var users = $rootScope.user;
		if (item.newpass == item.confirm) {
			$http.put("/api/user/password/${users.username}", item).then(function(response) {
				if (response.data == "") {
					iziToast.error({
						title: 'Lỗi!!',
						message: 'Sai mật khẩu',
					});
					$scope.form = {};
				} else {
					iziToast.success({
						title: 'OK',
						message: 'Cập nhập mật khẩu thành công',
					});
					$scope.form = {};
				}

			}, function(error) {
				iziToast.error({
					title: 'Lỗi!!',
					message: 'Illegal operation' + error,
				});
			});
		} else {
			iziToast.error({
				title: 'Lỗi!!',
				message: 'Mật khẩu mới không khớp',
			});
		}


	};

});