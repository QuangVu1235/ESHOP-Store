var app = angular.module("myApp", ["ngRoute", "datatables"]);
app.run(function($rootScope, $http) {

	$http.get("/api/currentuser/").then(function(response) {
		$rootScope.user = response.data;
	});
	$rootScope.datatableVI = {
		"sEmptyTable": "Không có dữ liệu trong bảng",
		"sInfo": "Hiển thị từ _START_ đến _END_ trong tổng số _TOTAL_ mục",
		"sInfoEmpty": "Hiển thị từ 0 đến 0 trong tổng số 0 mục",
		"sInfoFiltered": "(lọc từ _MAX_ mục)",
		"sInfoPostFix": "",
		"sInfoThousands": ".",
		"sLengthMenu": "Hiển thị _MENU_",
		"sLoadingRecords": "Đang tải...",
		"sProcessing": "Đang xử lý...",
		"sSearch": "Tìm kiếm:",
		"sZeroRecords": "Không tìm thấy dữ liệu",
		"oPaginate": {
			"sFirst": "Đầu",
			"sLast": "Cuối",
			"sNext": "Tiếp",
			"sPrevious": "Trước"
		},
		"oAria": {
			"sSortAscending": ": kích hoạt để sắp xếp cột tăng dần",
			"sSortDescending": ": kích hoạt để sắp xếp cột giảm dần"
		}
	};


});

app.config(function($routeProvider) {
	$routeProvider
		.when("/admin/index", {
			templateUrl: "index",
		})
		.when("/admin/order", {
			templateUrl: "order",
			controller: "orderCtrl"
		})
		.when("/admin/profile", {
			templateUrl: "profile",
			controller: "profileCtrl"
		})
		.when("/admin/password", {
			templateUrl: "password",
			controller: "passwordCtrl"
		})
		.when("/admin/product", {
			templateUrl: "product",
			controller: "productCtrl"
		})
		.when("/admin/user", {
			templateUrl: "user",
			controller: "userCtrl"
		})
		.when("/admin/auth", {
			templateUrl: "auth",
			controller: "authCtrl"
		})
		.when("/admin/stas", {
			templateUrl: "stas",
			controller: "stasCtrl"
		})
		.otherwise({ redirectTo: "/admin/stas" });
});

app.controller("stasCtrl", function($scope, $rootScope, $http) {

	$scope.getall = function() {
		$http.get('/api/stas/').then(function(respone) {
			console.log(respone.data);
			var totalPriceLast6Months = respone.data;
			const ctx = document.getElementById('canvasChart').getContext('2d');
			const myChart = new Chart(ctx, {
				type: 'line',
				data: {
					labels: totalPriceLast6Months[0],
					datasets: [{
						label: 'Total revenue last 6 months',
						data: totalPriceLast6Months[1],
						backgroundColor: [
							'rgba(255, 99, 132, 0.2)',
							'rgba(54, 162, 235, 0.2)',
							'rgba(255, 206, 86, 0.2)',
							'rgba(75, 192, 192, 0.2)',
							'rgba(153, 102, 255, 0.2)',
							'rgba(255, 159, 64, 0.2)'
						],
						borderColor: [
							'rgba(255, 99, 132, 1)',
							'rgba(54, 162, 235, 1)',
							'rgba(255, 206, 86, 1)',
							'rgba(75, 192, 192, 1)',
							'rgba(153, 102, 255, 1)',
							'rgba(255, 159, 64, 1)'
						],
						borderWidth: 4
					}]
				},
				options: {
					responsive: true,
					maintainAspectRatio: false,
					scales: {
						yAxes: [{
							ticks: {
								beginAtZero: false,
								callback: function(value, index, values) {
									return value.toLocaleString("vi-VN", { style: "currency", currency: "VND" });
								}
							}
						}]
					}
				}
			});
		})
	}
	$scope.getall();

});

app.controller("orderCtrl", function($scope, $rootScope, $http) {
	$scope.dtOptions = {
		pageLength: 4,

	};
	$scope.order = {};
	$http.get("/api/order/all").then(function(response) {
		$rootScope.orders = response.data;
		$scope.order = $rootScope.orders;

	});

	$scope.update = function(item) {

		var id = item.order.id;
		$http.put("/api/order/update/" + id, item).then(function(response) {
			iziToast.success({
				title: 'OK',
				message: 'Cập nhập đơn hàng thành công',
			});
			$http.get("/api/order/all").then(function(response) {
				$rootScope.orders = response.data;
				$scope.order = $rootScope.orders;
			});
		});
	}
	$scope.active = function(item) {
		var id = item.order.id;
		$http.put("/api/order/active/" + id, item).then(function(response) {
			iziToast.success({
				title: 'OK',
				message: 'Cập nhập đơn hàng thành công',
			});
			$http.get("/api/order/all").then(function(response) {
				$rootScope.orders = response.data;
				$scope.order = $rootScope.orders;
			});

		});
	}



});
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
	$scope.imageChanged = function(files) {
		var data = new FormData();
		data.append('file', files[0]);
		$http.post('/api/upload/images/${files}', data, {
			tranformRequest: angular.identity,
			headers: { 'Content-Type': undefined }
		}).then(respone => {
			alert(respone.data.name);
			$scope.form.imgUrl = respone.data.name;
		}).catch(error => {
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
app.controller("productCtrl", function($scope, $rootScope, $http) {
	$scope.dtOptions = {
		pageLength: 4,

	};
	$scope.form = {};
	$scope.products = {};
	$('#update').attr('disabled', 'disabled');
	$scope.load = function() {
		$http.get('/api/product/all').then(function(response) {

			$scope.products = response.data;
		});

		$http.get('/api/product/isdelete').then(function(response) {

			$scope.productfalse = response.data;
		});

		$http.get('/api/type/all').then(function(response) {
			$scope.types = response.data;
		});
	}
	$scope.load();
	$scope.loadall = function() {
		$http.get('/api/product/all').then(function(response) {

			$scope.products = response.data;
		});
		$http.get('/api/product/isdelete').then(function(response) {
			$scope.productfalse = response.data;
		});
	}

	$scope.create = function() {
		var e = document.getElementById('select-input').value;
		var d = document.getElementById('is-delete').value;
		var item = angular.copy($scope.form);
		console.log(item);
		$http.put("/api/product/" + e + '/' + d, item).then(function(response) {

			$scope.loadall();
			$scope.edit(response.data);

			iziToast.success({
				title: 'OK',
				message: 'Cập nhập thông tin thành công',
			});

		});

	}

	$scope.edit = function(item) {

		$scope.form = angular.copy(item);

		document.getElementById("pills-messages1").click();

		var id = $scope.form.productType.id;
		$('#select-input').val(id).change();

		if ($scope.form.isDeleted == true) {
			var isD = 'true';
		} else {
			var isD = 'false';
		}
		$('#is-delete').val(isD).change();
		$('#create').attr('disabled', 'disabled');
		$('#update').removeAttr('disabled');
	};

	$scope.update = function() {

		var e = document.getElementById('select-input').value;
		var d = document.getElementById('is-delete').value;
		var item = angular.copy($scope.form);
		$http.put("/api/product/${item.id}/" + e + '/' + d, item).then(function(response) {


			$scope.edit(response.data);

			iziToast.success({
				title: 'OK',
				message: 'Cập nhập thông tin thành công',
			});
			$scope.loadall();

		});

	};

	$scope.reset = function() {
		$scope.form = {
			imgUrl: '',
			imgUrl1: '',
			imgUrl2: '',
			imgUrl3: ''
		};
		$scope.load();
		$('#update').attr('disabled', 'disabled');
		$('#create').removeAttr('disabled');
	}

	$scope.remove = function(item) {
		$http.put('/api/product/remove/${item.id}', item).then(function(response) {
			iziToast.success({
				title: 'OK',
				message: 'Cập nhập thông tin thành công',
			});
			$scope.loadall();
		}).catch(error => {
			console.log(error);
		})
	}
	$scope.imageChanged = function(files) {

		var data = new FormData();
		data.append('file', files[0]);
		$http.post('/api/upload/images/product/${files}', data, {
			tranformRequest: angular.identity,
			headers: { 'Content-Type': undefined }
		}).then(respone => {
			alert(respone.data.name);
			$scope.form.imgUrl = respone.data.name;
		}).catch(error => {
			alert("loi hinh anh");
			console.log(error);
		});
	}

	$scope.imageChanged1 = function(files) {

		var data = new FormData();
		data.append('file', files[0]);
		$http.post('/api/upload/images/product/${files}', data, {
			tranformRequest: angular.identity,
			headers: { 'Content-Type': undefined }
		}).then(respone => {
			alert(respone.data.name);
			$scope.form.imgUrl1 = respone.data.name;
		}).catch(error => {
			alert("loi hinh anh");
			console.log(error);
		});
	}

	$scope.imageChanged2 = function(files) {

		var data = new FormData();
		data.append('file', files[0]);
		$http.post('/api/upload/images/product/${files}', data, {
			tranformRequest: angular.identity,
			headers: { 'Content-Type': undefined }
		}).then(respone => {
			alert(respone.data.name);
			$scope.form.imgUrl2 = respone.data.name;
		}).catch(error => {
			alert("loi hinh anh");
			console.log(error);
		});
	}
	$scope.imageChanged3 = function(files) {

		var data = new FormData();
		data.append('file', files[0]);
		$http.post('/api/upload/images/product/${files}', data, {
			tranformRequest: angular.identity,
			headers: { 'Content-Type': undefined }
		}).then(respone => {
			alert(respone.data.name);
			$scope.form.imgUrl3 = respone.data.name;
		}).catch(error => {
			alert("loi hinh anh");
			console.log(error);
		});
	}
});
app.controller("userCtrl", function($scope, $rootScope, $http) {

	$scope.json = function() {
		$http.get('/api/currentuser/all').then(function(respone) {
			$scope.users = respone.data;
			console.log(respone.data);
		}).catch(error => {
			alert(error);
		})
	}
	$scope.json();

	$scope.update = function(item, value) {

		var isdelete = value;

		$http.put('/api/currentuser/' + item.id + '/' + isdelete, item).then(function(data) {
			iziToast.success({
				title: 'OK',
				message: 'Cập nhập tài khoản thành công',
			});
			$scope.json();
		}).catch(error => {
			iziToast.error({
				title: 'Lỗi!!',
				message: 'Illegal operation' + error,
			});
		})
	}

});

app.controller("authCtrl", function($scope, $rootScope, $http) {

	$scope.json = function() {
		$http.get('/api/user/auth').then(function(respone) {
			$scope.auths = respone.data;
		}).catch(error => {
			alert(error);
		})
	}
	$scope.json();

	$scope.update = function(auth, rolesid) {


		$http.put('/api/user/role/' + auth.user.id + '/' + rolesid, auth).then(function(data) {
			iziToast.success({
				title: 'OK',
				message: 'Cập nhập tài khoản thành công',
			});
			$scope.json();
		}).catch(error => {
			iziToast.error({
				title: 'Lỗi!!',
				message: 'Illegal operation' + error,
			});
		})
	}

});