/**
 * 주변 마트 지도 표시 
 */
document.addEventListener('DOMContentLoaded', function() {

    const prevBtn = document.querySelector('.mart_header .btn_wrap .left').parentElement;
    const nextBtn = document.querySelector('.mart_header .btn_wrap .right').parentElement;

    const mapItemsPerPage = 5;
    let mapCurrentPage = 0;
    let mapMartItems = [];
    let mapTotalPages = 0;

    const mapContainer = document.getElementById('map');

    // 사용자 위치 가져오기
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
            initMap(position.coords.latitude, position.coords.longitude);
        }, function() {
            // 위치 가져오기 실패 시 기본 위치
            initMap(37.61369203924935, 127.0296065739101);
        });
    } else {
        initMap(37.61369203924935, 127.0296065739101);
    }

	function initMap(lat, lng) {
	    const map = new kakao.maps.Map(mapContainer, {
	        center: new kakao.maps.LatLng(lat, lng),
	        level: 3
	    });

	    const allOverlays = [];

		// 사용자 위치
	    const userMarker = new kakao.maps.Marker({
	        map: map,
	        position: new kakao.maps.LatLng(lat, lng)
	    });

	    const userOverlay = new kakao.maps.CustomOverlay({
	        content: `<div class="custom-overlay user-overlay">
	                    <div class="place_info">
	                        <span class="place_name">내 위치</span>
	                    </div>
	                  </div>`,
	        map: map,
	        position: userMarker.getPosition(),
	        yAnchor: 2.3
	    });

	    userOverlay.setMap(null);
	    allOverlays.push(userOverlay);

		// 마커 클릭시 기존 켜져있던 오버레이 끄기
	    kakao.maps.event.addListener(userMarker, 'click', function() {
	        allOverlays.forEach(ov => ov.setMap(null)); 
	        userOverlay.setMap(map); 
	    });

	    userMarker.overlay = userOverlay;

	    const ps = new kakao.maps.services.Places(map);
	    ps.categorySearch('MT1', placesSearchCB, { location: map.getCenter() });

	    function placesSearchCB(data, status) {
	        if (status === kakao.maps.services.Status.OK) {
	            data.forEach(place => displayMarker(place, map));
	            renderMartBox(data);
	        }
	    }

	    function displayMarker(place, map) {
	        const marker = new kakao.maps.Marker({
	            map: map,
	            position: new kakao.maps.LatLng(place.y, place.x)
	        });

	        const overlay = new kakao.maps.CustomOverlay({
	            content: `<div class="custom-overlay">
	                        <div class="place_info">
	                            <span class="place_name">${place.place_name}</span>
	                            <span class="place_distance">거리: ${(Number(place.distance)/1000).toFixed(1)}km</span>
	                        </div>
	                      </div>`,
	            map: map,
	            position: marker.getPosition(),
	            yAnchor: 1.9
	        });

	        overlay.setMap(null);
	        allOverlays.push(overlay);

	        kakao.maps.event.addListener(marker, 'click', function() {
	            allOverlays.forEach(ov => ov.setMap(null)); 
	            overlay.setMap(map); 
	        });

	        marker.overlay = overlay;
	    }
	}


	// 마트 리스트 렌더링 
    function renderMartBox(martList) {
        const container = document.querySelector('.mart_list');
        container.innerHTML = '';

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

        mapMartItems = Array.from(container.querySelectorAll('li'));
        mapTotalPages = Math.ceil(mapMartItems.length / mapItemsPerPage);
        mapCurrentPage = 0;
        renderPage(mapCurrentPage);
    }

    function renderPage(page) {
        mapMartItems.forEach(item => item.style.display = 'none');
        const start = page * mapItemsPerPage;
        const end = start + mapItemsPerPage;
        mapMartItems.slice(start, end).forEach(item => item.style.display = 'block');

        prevBtn.classList.toggle('dimmed', page === 0);
        nextBtn.classList.toggle('dimmed', page === mapTotalPages - 1);
    }

    prevBtn.onclick = () => { if(mapCurrentPage>0){ mapCurrentPage--; renderPage(mapCurrentPage); } };
    nextBtn.onclick = () => { if(mapCurrentPage<mapTotalPages-1){ mapCurrentPage++; renderPage(mapCurrentPage); } };

});
