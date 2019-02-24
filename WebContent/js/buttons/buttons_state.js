/**
 * 
 */
	function newImage(arg) {
		if (document.images) {
			rslt = new Image();
			rslt.src = arg;
			return rslt;
		}
	}
	
	function changeImages() {
		if (document.images && (preloadFlag == true)) {
			for (var i=0; i<changeImages.arguments.length; i+=2) {
				document[changeImages.arguments[i]].src = changeImages.arguments[i+1];
			}
		}
	}
	
	var preloadFlag = false;
	function preloadImages() {
		if (document.images) {
			button_Layer_1_over = newImage(contexPath+"/images/icons_over/user_24x24.png");
			button_Layer_1_down = newImage(contexPath+"/images/icons_press/user_24x24.png");
			preloadFlag = true;
		}
	}
	
	function preloadImagesSimulador() {
		if (document.images) {
			activasimulador_over = newImage(contexPath+"/images/icons_over/simular_over_i.png");
			activasimulador_down = newImage(contexPath+"/images/icons_press/simular_down_i.png");
			preloadFlag = true;
		}
	}