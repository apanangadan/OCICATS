'use strict';

angular.module('myapp', ['ngMaterial'])
.controller('soi', ["$scope", "$http", "gmaps", function ($scope, $http, gmaps) {
	$scope.searchComplete = true;

	gmaps.initiate();
	$scope.topics = ["High speed rail", "Metro Expresslanes", "Bicycle lanes", "All"];
	//tweets section
	$scope.group="Retweets";
	$scope.rankedby = "";
	$scope.tweets;
	$scope.influentials;
	$scope.selectedUser = null;
	$scope.findInfluentialPeople = function () {
		$scope.searchComplete = false;
		$scope.influentials = [];
		$scope.tweets = [];
		$scope.selectedUser = null;
		var URL = null;
		if ($scope.group == "Retweets") {
			URL = "rest/influential/retweet/" + $scope.selectedTopic;
			$scope.rankedby="retweets";
		}
		else if ($scope.group == "Mentions") {
			URL = "rest/influential/mention/" + $scope.selectedTopic;
			$scope.rankedby="mentions";
		}
		$http({
			method: "GET",
			url: URL,
			dataType: "application/json"
		}).then(function successCallback(data) {
			$scope.searchComplete = true;
			$scope.influentials = data.data;
			console.log(data.data);
		}, function errorCallback(error) {
			console.log(error);
			alert(error);
		});
	}
	gmaps.addMarker(36.7783, -119.4179);
	$scope.getTweets = function (user) {
		gmaps.clearAllMarkers();
		$scope.selectedUser = user.name;
		var URL;
		if($scope.rankedby == "retweets"){
			URL = "rest/tweets/user/" + user.id_str
		}else if($scope.rankedby == "mentions"){
			URL = "rest/tweets/mention/" + user.id_str
		}
		$http({
			method: "GET",
			url: URL,
			dataType: "application/json"
		}).then(function successCallback(response) {
			$scope.tweets = response.data;
			console.log($scope.tweets);

			angular.forEach($scope.tweets, function (tweet) {
				var lat = null, lng = null;	
				if($scope.rankedby=="retweets"){
					if (tweet.coordinates != null) {
						lat = tweet.coordinates.coordinates[0];
						lng = tweet.coordinates.coordinates[1];
					}
					console.log(lat, lng);
					if (lat != null && lng != null) {
						gmaps.addMarker(lat, lng, true);
					}
				}else if($scope.rankedby == "mentions"){
					if (tweet.coordinates != null) {
						lat = tweet.coordinates.coordinates[0];
						lng = tweet.coordinates.coordinates[1];
					}
					console.log(lat, lng);
					if (lat == null && lng == null) {
						$http({
							method: "GET",
							url: "http://maps.googleapis.com/maps/api/geocode/json?sensor=false&address=" + tweet.user.location,
							dataType: "application/json"
						}).then(function successCallback(response) {
							console.log(response.data);
							var loc = response.data;
							var lat = loc.results[0].geometry.location.lat;
							var lng = loc.results[0].geometry.location.lng;
							gmaps.addMarker(lat, lng, true) ;
						}, function errorCallback(error) {
							console.log(error);
							// alert(error);
						});
						// gmaps.addMarker(lat, lng, true);
					}
				}
			});
			$http({
				method: "GET",
				url: "http://maps.googleapis.com/maps/api/geocode/json?sensor=false&address=" + $scope.tweets[0].user.location,
				dataType: "application/json"
			}).then(function successCallback(response) {
				console.log(response.data);
				var loc = response.data;
				var lat = loc.results[0].geometry.location.lat;
				var lng = loc.results[0].geometry.location.lng;
				gmaps.addMarker(lat, lng, false) ;
			}, function errorCallback(error) {
				console.log(error);
				// alert(error);
			});

		}, function errorCallback(error) {
			console.log(error);
			alert(error);
		});
	};
}])
//	.config(function ($mdThemingProvider) {
//		$mdThemingProvider
//			.theme('default')
//			.primaryPalette('indigo')
//			.accentPalette('pink')
//			.warnPalette('red');
////			.backgroundPalette('blue-grey');
//	})
.service("gmaps", [function () {
	this.map;
	this.california = { lat: 36.7783, lng: -119.4179 };
	this.markers = [];
	this.initiate = function (callback) {
		var mapDiv = document.getElementById('maps');
		this.map = new google.maps.Map(mapDiv, {
			center: this.california,
			zoom: 6
		});

		this.ctaLayer = new google.maps.KmlLayer({
			url: 'images/CaliforniaCounties.kml',
			map: this.map
		});

		// Create the DIV to hold the control and call the CenterControl()
		// constructor passing in this DIV.
//		var centerControlDiv = document.createElement('div');
//		var centerControl = new this.CenterControl(centerControlDiv, this.map);
//
//		centerControlDiv.index = 1;
//		this.map.controls[google.maps.ControlPosition.TOP_CENTER].push(centerControlDiv);
		//		this.image = { url:'images/TwitterLogo.png',
		//	    		size: new google.maps.Size(30, 30),
		//	    		// The origin for this image is (0, 0).
		//	    	    origin: new google.maps.Point(0, 0),
		//	    	    // The anchor for this image is the base of the flagpole at (0, 32).
		//	    	    anchor: new google.maps.Point(0, 32)
		//	    }
	};
	this.image = 'https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png';

	this.addMarker = function (mylat, mylng, green) {
		var image = {
			url: 'images/Twitter.png',
			// url:'https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png',
			scaledSize: new google.maps.Size(50, 50),
			// The origin for this image is (0, 0).
			origin: new google.maps.Point(0, 0),
			// The anchor for this image is the base of the flagpole at (0, 32).
			anchor: new google.maps.Point(0, 20)
		};
		
		this.marker = new google.maps.Marker({
			position: { lat: mylat, lng: mylng },
			animation: google.maps.Animation.DROP,
			map: this.map
			//icon: image
		});
		this.markers.push(this.marker);
		if(green){
			this.marker.setIcon('http://maps.google.com/mapfiles/ms/icons/green-dot.png')
		}else{
			this.map.panTo(this.marker.getPosition());
		}
		this.map.setZoom(5);
	};

	this.setMapOnAll = function (map) {
		for (var i = 0; i < this.markers.length; i++) {
			this.markers[i].setMap(map);
        }
	};

	this.clearAllMarkers = function () {
		this.setMapOnAll(null);
	};

//	this.CenterControl = function (controlDiv, map) {
//
//		// Set CSS for the control border.
//		var controlUI = document.createElement('div');
//		controlUI.style.backgroundColor = '#fff';
//		controlUI.style.border = '2px solid #fff';
//		controlUI.style.borderRadius = '3px';
//		controlUI.style.boxShadow = '0 2px 6px rgba(0,0,0,.3)';
//		controlUI.style.cursor = 'pointer';
//		controlUI.style.marginBottom = '22px';
//		controlUI.style.textAlign = 'center';
//		controlUI.title = 'Click to recenter the map';
//		controlDiv.appendChild(controlUI);
//
//		// Set CSS for the control interior.
//		var controlText = document.createElement('div');
//		controlText.style.color = 'rgb(25,25,25)';
//		controlText.style.fontFamily = 'Roboto,Arial,sans-serif';
//		controlText.style.fontSize = '16px';
//		controlText.style.lineHeight = '38px';
//		controlText.style.paddingLeft = '5px';
//		controlText.style.paddingRight = '5px';
//		controlText.innerHTML = 'Center Map';
//		controlUI.appendChild(controlText);
//
//		// Setup the click event listeners: simply set the map to Chicago.
//		controlUI.addEventListener('click', [this.map, function (map) {
//			map.setCenter(this.california);
//		}]);
//	}
}]);