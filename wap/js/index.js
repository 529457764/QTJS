$(function () {
	// logo 位置
	function resize() {
		var $logo = $("#logo");
		//var logoWidth = $logo.width();
		//限制宽度
		var logoWidth = 800;
		var logoHeight = $logo.height();
		var winWidth = $(window).width();
		var lposleft = (winWidth - logoWidth) / 2;
		$logo.css({'left': lposleft + 'px'});

		//title 位置
		// var $title = $(".header-right");
		// //var titleWidth = $title.width();
		// var titleWidth = 500;
		// var tposleft = (winWidth - titleWidth) / 2;
		// $title.css({'left': tposleft + 'px'});
	}
	$(document).ready(function(){
		resize();
		window.onresize = function(){
			resize();
		}
	});
});


