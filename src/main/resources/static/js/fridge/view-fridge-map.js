/*
지도에 현재 사용자의 위치를 마커로 표시하는 함수 - sample
*/
//document.addEventListener('DOMContentLoaded', async function() {
//	var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
//	    mapOption = { 
//		center: new kakao.maps.LatLng(37.61369203924935, 127.0296065739101),
//	        level: 10 // 지도의 확대 레벨 
//	    }; 
//
//	var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
//
//	// HTML5의 geolocation으로 사용할 수 있는지 확인합니다 
//	if (navigator.geolocation) {
//	    
//	    // GeoLocation을 이용해서 접속 위치를 얻어옵니다
//	    navigator.geolocation.getCurrentPosition(function(position) {
//	        
//	        var lat = position.coords.latitude, // 위도
//	            lon = position.coords.longitude; // 경도
//	        
//	        var locPosition = new kakao.maps.LatLng(lat, lon), // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다
//	            message = '<div style="padding:5px;">여기에 계신가요?!</div>'; // 인포윈도우에 표시될 내용입니다
//	        
//	        // 마커와 인포윈도우를 표시합니다
//	        displayMarker(locPosition, message);
//	            
//	      });
//	    
//	} else { // HTML5의 GeoLocation을 사용할 수 없을때 마커 표시 위치와 인포윈도우 내용을 설정합니다
//	    
//	    var locPosition = new kakao.maps.LatLng(33.450701, 126.570667),    
//	        message = 'geolocation을 사용할수 없어요..'
//	        
//	    displayMarker(locPosition, message);
//	}
//
//	// 지도에 마커와 인포윈도우를 표시하는 함수입니다
//	function displayMarker(locPosition, message) {
//
//	    // 마커를 생성합니다
//	    var marker = new kakao.maps.Marker({  
//	        map: map, 
//	        position: locPosition
//	    }); 
//	    
//	    var iwContent = message, // 인포윈도우에 표시할 내용
//	        iwRemoveable = true;
//
//	    // 인포윈도우를 생성합니다
//	    var infowindow = new kakao.maps.InfoWindow({
//	        content : iwContent,
//	        removable : iwRemoveable
//	    });
//	    
//	    // 인포윈도우를 마커위에 표시합니다 
//	    infowindow.open(map, marker);
//	    
//	    // 지도 중심좌표를 접속위치로 변경합니다
//	    map.setCenter(locPosition);      
//	}    
//
//    
//});




/*
카테고리로 장소검색하기
*/
document.addEventListener('DOMContentLoaded', async function() {

	// 마커 클릭시 장소명 출력 infowindow
	var infowindow = new kakao.maps.InfoWindow({ zIndex: 1 }); 

	// 지도 설정
	var mapContainer = document.getElementById('map'),
	    mapOption = {
			// TODO: 사용자 위치로 변경 필요 - 테스트를 위해 하드코딩
	        center: new kakao.maps.LatLng(37.61369203924935, 127.0296065739101), 
	        level: 3
	    };

	var map = new kakao.maps.Map(mapContainer, mapOption);
	var ps = new kakao.maps.services.Places(map);
	
	// 마트 목록 표시할 요소
	var martListEl = document.querySelector('.mart_list');

	// 편의점 검색
	// TODO: 대형마트(MT1)으로 변경필요 - 테스트를 위해 편의점(CS2)로 진행
	ps.categorySearch('CS2', placesSearchCB, { location: map.getCenter() });

	function placesSearchCB(data, status, pagination) {
	    if (status === kakao.maps.services.Status.OK) {
			// distance(center와의 거리) 가 작은 순으로 정렬 = center와의 거리가 가까운 순
	        data.sort((a, b) => Number(a.distance) - Number(b.distance));

	        martListEl.innerHTML = '';

			// marker 표시 및 html 생성
	        data.forEach((place, index) => {
	            displayMarker(place);

	            // mart_box 생성
	            var li = document.createElement('li');
	            li.innerHTML = `
	                <div class="mart_box">
	                    <div class="mart_txt">
	                        <span>${place.place_name}</span>
	                        <div class="ping">
	                            <img src="/images/fridge/location.png">
	                            <p>${(Number(place.distance)/1000).toFixed(1)}km</p>
	                        </div>
	                    </div>
	                </div>`;
	            martListEl.appendChild(li);
	        });
	    } else {
	        console.warn("검색 실패:", status);
	    }
	}
	
	function displayMarker(place) {
	        var marker = new kakao.maps.Marker({
	            map: map,
	            position: new kakao.maps.LatLng(place.y, place.x)
	        });

	        kakao.maps.event.addListener(marker, 'click', function() {
	            var content = `
	                <div style="padding:5px;font-size:12px;">
	                    <strong>${place.place_name}</strong><br>
	                    거리: ${(Number(place.distance)/1000).toFixed(1)}km
	                </div>`;
	            infowindow.setContent(content);
	            infowindow.open(map, marker);
	        });
	    }
});

