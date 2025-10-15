///*
//지도에 현재 사용자의 위치를 마커로 표시하는 함수 - sample
//*/
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
  주변 마트 검색 + 페이징
*/
document.addEventListener('DOMContentLoaded', function() {

    // 마커 클릭시 장소명 출력 infowindow
    var infowindow = new kakao.maps.InfoWindow({ zIndex: 1 }); 

    // 지도 설정 (테스트용 하드코딩 좌표)
    var mapContainer = document.getElementById('map'),
        mapOption = {
            center: new kakao.maps.LatLng(37.61369203924935, 127.0296065739101),
            level: 3
        };

    var map = new kakao.maps.Map(mapContainer, mapOption);
    var ps = new kakao.maps.services.Places(map);

    // 버튼
    const prevBtn = document.querySelector('.mart_header .btn_wrap .left').parentElement;
    const nextBtn = document.querySelector('.mart_header .btn_wrap .right').parentElement;

    // 페이징 설정
    const itemsPerPage = 5;
    let currentPage = 0;
    let martItems = [];
    let totalPages = 0;

    // 카테고리 검색 (대형마트: MT1 / 테스트용 편의점: CS2)
    ps.categorySearch('CS2', placesSearchCB, { location: map.getCenter() });

    // 검색 콜백
    function placesSearchCB(data, status, pagination) {
        if (status === kakao.maps.services.Status.OK) {
            data.forEach(place => displayMarker(place)); 
			// 화면 렌더링
            renderMartBox(data);                        
        } else {
            console.warn("검색 실패:", status);
        }
    }

    // 마커 표시
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

    // 마트 리스트 렌더링
    function renderMartBox(martList) {
        const container = document.querySelector('.mart_list');
        container.innerHTML = '';

		// 거리가 가까운순으로 출력
        martList.sort((a,b)=> Number(a.distance) - Number(b.distance));

        martList.forEach(place => {
            const li = document.createElement('li');
            const martBox = document.createElement('div');
            martBox.className = 'mart_box';
            const martTxt = document.createElement('div');
            martTxt.className = 'mart_txt';

            const spanName = document.createElement('span');
            spanName.textContent = place.place_name;

            const ping = document.createElement('div');
            ping.className = 'ping';
            const img = document.createElement('img');
            img.src = '/images/fridge/location.png';
            const pDistance = document.createElement('p');
            pDistance.textContent = `${(Number(place.distance)/1000).toFixed(1)}km`;

            ping.append(img, pDistance);
            martTxt.append(spanName, ping);
            martBox.appendChild(martTxt);
            li.appendChild(martBox);
            container.appendChild(li);
        });

        martItems = Array.from(container.querySelectorAll('li'));
        totalPages = Math.ceil(martItems.length / itemsPerPage);
        currentPage = 0;

        renderPage(currentPage);
    }

    // 페이지 렌더링
    function renderPage(page) {
        martItems.forEach(item => item.style.display = 'none');
        const start = page * itemsPerPage;
        const end = start + itemsPerPage;
        martItems.slice(start, end).forEach(item => item.style.display = 'block');

        // dimmed 클래스 처리
        prevBtn.classList.toggle('dimmed', page === 0);
        nextBtn.classList.toggle('dimmed', page === totalPages - 1);
    }

    // 버튼 클릭
    prevBtn.onclick = () => { if(currentPage>0){ currentPage--; renderPage(currentPage); } };
    nextBtn.onclick = () => { if(currentPage<totalPages-1){ currentPage++; renderPage(currentPage); } };
});
