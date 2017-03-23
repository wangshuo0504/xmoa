var isIE8 = false,
	isIE9 = false,
	inner = $(".main-wrapper > .inner"),
	supportTransition = true, closedbar = $(".closedbar"),
	isMobile = false, isIEMobile = false,
	$body = $("body"),
	$windowWidth, $windowHeight,
	subViews = $(".subviews"),
	sideLeft = $('#pageslide-left'),
	mainNavigation = $('#menu'),
	sidebarWidth = sideLeft.outerWidth(true),
	topBar = $(".topbar"),
	mainContent = $(".main-content"),
	footer = $(".main-wrapper > footer");
var activeAnimation = false, hoverSideBar = false;


// Debounce Function
(function($, sr) {"use strict";
	// debouncing function from John Hann
	// http://unscriptable.com/index.php/2009/03/20/debouncing-javascript-methods/
	var debounce = function(func, threshold, execAsap) {
		var timeout;
		return function debounced() {
			var obj = this, args = arguments;

			function delayed() {
				if(!execAsap)
					func.apply(obj, args);
				timeout = null;
			}

			if(timeout)
				clearTimeout(timeout);
			else if(execAsap)
				func.apply(obj, args);

			timeout = setTimeout(delayed, threshold || 100);
		};
	};
	// smartresize
	jQuery.fn[sr] = function(fn) {
		return fn ? this.on('resize', debounce(fn)) : this.trigger(sr);
	};

})(jQuery, 'espressoResize');

//Main Function
var Main = function() {"use strict";
	//function to init app
	var runInit = function() {
		// Detection for IE Version
		if(/MSIE (\d+\.\d+);/.test(navigator.userAgent)) {
			var ieversion = Number(RegExp.$1);
			if(ieversion == 8) {
				isIE8 = true;
				$body.addClass('isIE8');
			} else if(ieversion == 9) {
				isIE9 = true;
				$body.addClass('isIE9');
			}
		}
		// Detection for Mobile Device
		if(/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)) {
			isMobile = true;
			$body.addClass('isMobile');
		}
		// Detection for CSS Transitions Support
		var thisBody = document.body || document.documentElement, thisStyle = thisBody.style;
		supportTransition = thisStyle.transition !== undefined || thisStyle.WebkitTransition !== undefined || thisStyle.MozTransition !== undefined || thisStyle.MsTransition !== undefined || thisStyle.OTransition !== undefined;
		// active perfectScrollbar only in desktop devices
		if($body.hasClass("isMobile") == false && mainNavigation.length) {
			mainNavigation.perfectScrollbar();
			mainNavigation.click(function(){
				mainNavigation.perfectScrollbar('update');
			});
		}

		// set blockUI options
		if($.blockUI) {
			$.blockUI.defaults.css.border = 'none';
			$.blockUI.defaults.css.padding = '20px 5px';
			$.blockUI.defaults.css.width = '20%';
			$.blockUI.defaults.css.left = '40%';
			$.blockUI.defaults.overlayCSS.backgroundColor = '#DDDDDD';
		}

		// Add Fade Animation to Dropdown
		$('.dropdown').on('show.bs.dropdown', function(e) {
			$(this).find('.dropdown-menu').first().stop(true, true).fadeIn(300);
		});
		$('.dropdown').on('hide.bs.dropdown', function(e) {
			$(this).find('.dropdown-menu').first().stop(true, true).fadeOut(300);
		});

		// change closebar height when footer appear
		if($.fn.appear) {
			if(isMobile == false) {
				footer.appear();
				footer.on("appear", function(event, $all_appeared_elements) {

					closedbar.css({
						bottom: (footer.outerHeight(true) + 1) + "px"
					});
				});
				footer.on("disappear", function(event, $all_disappeared_elements) {

					closedbar.css({
						bottom: 1 + "px"
					});
				});
			}
		}

	};
	//function to get viewport/window size (width and height)
	var viewport = function() {
		var e = window, a = 'inner';
		if(!('innerWidth' in window )) {
			a = 'client';
			e = document.documentElement || document.body;
		}
		return {
			width: e[a + 'Width'],
			height: e[a + 'Height']
		};
	};

	// function to adjust the template elements based on the window size
	var runElementsPosition = function() {
		$windowWidth = viewport().width;
		$windowHeight = viewport().height;

	};

	//function to adapt the Main Content height to the Main Navigation height
	var runContainerHeight = function() {

	};

	// Window Resize Function
	var runWIndowResize = function(func, threshold, execAsap) {
		//wait until the user is done resizing the window, then execute
		$(window).espressoResize(function() {
			runElementsPosition();
		});
	};
	//function to Right and Left PageSlide
	var runToggleSideBars = function() {
		var configAnimation, extendOptions, globalOptions = {
			duration: 150,
			easing: "ease",
			mobileHA: true,
			progress: function() {
				activeAnimation = true;
			}
		};
		$("#pageslide-left, #pageslide-right").on("mouseover", function(e) {
			hoverSideBar = true;
		}).on("mouseleave", function(e) {
			hoverSideBar = false;
		});
		$("#pageslide-left .btn-menu-toggle").click(function(){
			var $menu = $("#pageslide-left #menu");
			if($("#pageslide-left #menu").hasClass('slider-close')){
				$(".main-wrapper").removeClass('slider-close');
				$menu.sliderMenu('openMenu');
			}else{
				$(".main-wrapper").addClass('slider-close');
				$menu.sliderMenu('closeMenu');
			}
		});
		
		$(".sb-toggle-left, .closedbar").on("click", function(e) {
			if(activeAnimation == false) {
				if($windowWidth > 991) {
					$body.removeClass("sidebar-mobile-open");
					if($body.hasClass("sidebar-close")) {
						if($body.hasClass("layout-boxed") || $body.hasClass("isMobile")) {
							$body.removeClass("sidebar-close");
							closedbar.removeClass("open");
							$(window).trigger('resize');
						} else {
							closedbar.removeClass("open").hide();
							closedbar.css({
								//translateZ: 0,
								left: -closedbar.width()
							});

							extendOptions = {
								complete: function() {
									$body.removeClass("sidebar-close");
									$(".main-container, #pageslide-left, #footer .footer-inner, #horizontal-menu .container, .closedbar").attr('style', '');
									$(window).trigger('resize');
									activeAnimation = false;
								}
							};
							configAnimation = $.extend({}, extendOptions, globalOptions);
							$(".main-container, footer .footer-inner, #horizontal-menu .container").velocity({
								//translateZ: 0,
								marginLeft: sidebarWidth
							}, configAnimation);

						}

					} else {
						if($body.hasClass("layout-boxed") || $body.hasClass("isMobile")) {
							$body.addClass("sidebar-close");
							closedbar.addClass("open");
							$(window).trigger('resize');
						} else {
							sideLeft.css({
								zIndex: 0

							});
							extendOptions = {
								complete: function() {
									closedbar.show().velocity({
										//translateZ: 0,
										left: 0
									}, 100, 'ease', function() {
										activeAnimation = false;
										closedbar.addClass("open");
										$body.addClass("sidebar-close");
										$(".main-container, footer .footer-inner, #horizontal-menu .container, .closedbar").attr('style', '');
										$(window).trigger('resize');
									});
								}
							};
							configAnimation = $.extend({}, extendOptions, globalOptions);
							$(".main-container, footer .footer-inner, #horizontal-menu .container").velocity({
								//translateZ: 0,
								marginLeft: 0
							}, configAnimation);
						}
					}

				} else {
					if($body.hasClass("sidebar-mobile-open")) {
						if(supportTransition) {
							extendOptions = {
								complete: function() {
									inner.attr('style', '').removeClass("inner-transform");
									// remove inner-transform (hardware acceleration) for restore z-index
									$body.removeClass("sidebar-mobile-open");
									activeAnimation = false;
								}
							};
							configAnimation = $.extend({}, extendOptions, globalOptions);

							inner.velocity({
								translateZ: 0,
								translateX: [-sidebarWidth, 0]
							}, configAnimation);
						} else {
							$body.removeClass("sidebar-mobile-open");
						}
					} else {
						if(supportTransition) {
							inner.addClass("inner-transform");
							// add inner-transform for hardware acceleration
							extendOptions = {
								complete: function() {
									inner.attr('style', '');
									$body.addClass("sidebar-mobile-open");
									activeAnimation = false;
								}
							};
							configAnimation = $.extend({}, extendOptions, globalOptions);
							inner.velocity({
								translateZ: 0,
								translateX: [sidebarWidth, 0]
							}, configAnimation);
						} else {
							$body.addClass("sidebar-mobile-open");
						}

					}
				}
			}
			e.preventDefault();
		});
	};

	return {
		//main function to initiate template pages
		init: function() {
			runWIndowResize();
			runInit();
			runToggleSideBars();
			runElementsPosition();
		}
	};
}();

Main.init();
