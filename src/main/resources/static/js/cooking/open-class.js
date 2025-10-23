/**
 * 입력폼의 필수 내용을 입력했는지 검증하는 함수
 * 
 * @param {Form} form - 검증할 HTML폼 객체
 * @returns {boolean} - 검증 결과
 */
function validateOpenClassForm(form) {
	
	// 동의서
	const accept1 = form.querySelector('#acceptNotice1');
	const accept2 = form.querySelector('#acceptNotice2');
	if (!accept1.checked) {
		alertDanger("개인정보 수집 및 이용 동의에 체크해주세요.");
		accept1.focus(); 
		return false;
	}
	if (!accept2.checked) {
		alertDanger("안전 수칙 및 책임 동의서에 체크해주세요.");
		accept2.focus(); 
		return false;
	}
	
	if (form.title.value.trim() === "") {
		alertDanger("제목을 입력해주세요.");
		form.title.focus();
		return false;
	}
	
	const selectedCategory = form.querySelector('input[name="category"]:checked');
	if (!selectedCategory) {
		alertDanger("카테고리를 선택해주세요.");
		form.querySelector('input[name="category"]').focus(); 
		return false;
	}
	
	if (form.place.value.trim() === "") {
		alertDanger("장소를 입력해주세요.");
		form.place.focus();
		return false;
	}
	if (form.eventdate.value.trim() === "") {
		alertDanger("날짜를 선택해주세요.");
		form.eventdate.focus();
		return false;
	}
	if (form.starttime.value.trim() === "") {
		alertDanger("시작 시간을 선택해주세요.");
		form.starttime.focus();
		return false;
	}
	if (form.endtime.value.trim() === "") {
		alertDanger("종료 시간을 선택해주세요.");
		form.endtime.focus();
		return false;
	}
	if (form.headcount.value.trim() === "") {
		alertDanger("모집 인원을 입력해주세요.");
		form.headcount.focus();
		return false;
	}
	if (form.noticedate.value.trim() === "") {
		alertDanger("신청 마감일을 선택해주세요.");
		form.noticedate.focus();
		return false;
	}
	if (form.need.value.trim() === "") {
		alertDanger("준비물을 입력해주세요.");
		form.need.focus();
		return false;
	}
	if (form.image_url.value.trim() === "") {
		alertDanger("대표 이미지를 선택해주세요.");
		form.image_url.focus();
		return false;
	}
	if (form.target1.value.trim() === "") {
		form.target1.focus();
		alertDanger("추천 대상을 입력해주세요.");
		return false;
	}
	if (form.introduce.value.trim() === "") {
		alertDanger("클래스 소개를 작성해주세요.");
		form.introduce.focus();
		return false;
	}
	
	// 커리큘럼
	const scheduleRows = document.querySelectorAll('#classSchedule table tbody');
	for (const [index, tbody] of scheduleRows.entries()) {
		const start = tbody.querySelector('input[name^="starttime"]')?.value.trim();
		const end = tbody.querySelector('input[name^="endtime"]')?.value.trim();
		const title = tbody.querySelector('input[name^="title"]')?.value.trim();

		if (!start) {
			alertDanger(`커리큘럼 ${index + 1}의 시작 시간을 선택해주세요.`);
			tbody.querySelector('input[name^="starttime"]').focus();
			return false;
		}
		if (!end) {
			alertDanger(`커리큘럼 ${index + 1}의 종료 시간을 선택해주세요.`);
			tbody.querySelector('input[name^="endtime"]').focus();
			return false;
		}
		if (!title) {
			alertDanger(`커리큘럼 ${index + 1}의 제목을 입력해주세요.`);
			tbody.querySelector('input[name^="title"]').focus();
			return false;
		}
	}

	// 강사
	const tutorRows = document.querySelectorAll('#classTutor table tbody.tutor_row');
	for (const [index, tbody] of tutorRows.entries()) {
		const name = tbody.querySelector('input[name^="name"]')?.value.trim();
		const career1 = tbody.querySelector('input[name^="career"]')?.value.trim();
		const img = tbody.querySelector('input[type="file"]')?.value.trim();

		if (!name) {
			alertDanger(`강사 ${index + 1}의 이름을 입력해주세요.`);
			tbody.querySelector('input[name^="name"]').focus();
			return false;
		}
		if (!career1) {
			alertDanger(`강사 ${index + 1}의 경력을 입력해주세요.`);
			tbody.querySelector('input[name^="career"]').focus();
			return false;
		}
		if (!img) {
			alertDanger(`강사 ${index + 1}의 사진을 선택해주세요.`);
			tbody.querySelector('input[type="file"]').focus();
			return false;
		}
	}
	
	return true; 
}




/**
 * 강사 정보 입력창을 추가하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
    let tutorCount = 1; // 초기 강사 1명

    document.getElementById("addTutor").addEventListener("click", function(event) {
        event.preventDefault();
		console.log("추가버튼 클릭");
        tutorCount++;
        const classTutorDiv = document.querySelector("#classTutor"); // 기존 HTML 그대로 사용
        const table = classTutorDiv.querySelector("table");

        const newTutor = `
        <tbody class="tutor_row">
            <tr>
                <th scope="col">이름<span class="ess"></span></th>
                <td>
                    <span class="form_text">
                        <input maxlength="50" name="name${tutorCount}" placeholder="이름을 입력해주세요" type="text" value="">
                    </span>
                </td>
            </tr>
            <tr>
                <th scope="col">경력<span class="ess"></span></th>
                <td>
                    <span class="form_text">
                        <input maxlength="50" name="career${tutorCount}_1" placeholder="경력 및 자격증을 입력해주세요" type="text" value="">
                    </span>
                    <span class="form_text">
                        <input maxlength="50" name="career${tutorCount}_2" placeholder="경력 및 자격증을 입력해주세요 (선택)" type="text" value="">
                    </span>
                    <span class="form_text">
                        <input maxlength="50" name="career${tutorCount}_3" placeholder="경력 및 자격증을 입력해주세요 (선택)" type="text" value="">
                    </span>
                </td>
            </tr>
            <tr>
                <th scope="col">강사 사진<span class="ess"></span></th>
                <td>
                    <label class="form_file">
                        <input type="file" name="teacher_img${tutorCount}" class="teacherImgInput">
                        <input type="text" readonly="readonly" placeholder="파일을 선택하세요" class="teacherFileName">
                    </label>
                </td>
            </tr>
        </tbody>
        `;

        // 테이블에 추가
        table.insertAdjacentHTML("beforeend", newTutor);

        // 새로 추가된 파일 input 이벤트 바인딩
        const newImgInput = table.querySelector(`input[name="teacher_img${tutorCount}"]`);
        const newFileName = newImgInput.nextElementSibling;
        newImgInput.addEventListener("change", function () {
            if (this.files.length > 0) {
                newFileName.value = this.files[0].name;
            }
        });
    });
});









/**
 * 
 */
function initializeTimepicker(selector) {
    $(selector).timepicker({
        timeFormat: 'HH:mm',
        interval: 30,
        minTime: '08:00',
        maxTime: '22:00',
        startTime: '08:00',
        dynamic: false,
        dropdown: true,
        scrollbar: true
    });
}




/**
 * 파일 선택 시 파일명을 표시하는 함수
 */
document.addEventListener("DOMContentLoaded", function () {
	const imageInput = document.getElementById("imageInput");
	const fileName = document.getElementById("fileName");
	const teacherImgInput = document.getElementById("teacherImgInput");
	const teacherFileName = document.getElementById("teacherFileName");

    imageInput.addEventListener("change", function () {
        if (this.files.length > 0) {
            fileName.value = this.files[0].name;
        }
    });
	
    teacherImgInput.addEventListener("change", function () {
        if (this.files.length > 0) {
            teacherFileName.value = this.files[0].name;
        }
    });
});



/**
 * 데이트 피커 및 타임 피커
 */
$(document).ready(function () {
	const today = new Date(); 
	
	$("#eventdate").datepicker({
	    format: "yyyy-mm-dd",
	    autoclose: true,
	    todayHighlight: true,
	    minDate: today,   
	    onSelect: function () {
	        const eventDate = $(this).datepicker('getDate');
	        if (eventDate) {
	            const maxNoticeDate = new Date(eventDate);
	            maxNoticeDate.setDate(eventDate.getDate() - 3);

	            $("#noticedate").datepicker("option", "maxDate", maxNoticeDate);
	            $("#noticedate").datepicker("option", "minDate", today);
	        }
	    }
	});
    $("#noticedate").datepicker({
      format: "yyyy-mm-dd",
      autoclose: true,
      todayHighlight: true,
    });
	
	$('#starttime').timepicker({
        timeFormat: 'HH:mm',
        interval: 30,
        minTime: '08',
        maxTime: '22:00',
        startTime: '08:00',
        dynamic: false,
        dropdown: true,
        scrollbar: true
    });
	
	$('#endtime').timepicker({
        timeFormat: 'HH:mm',
        interval: 30,
        minTime: '08',
        maxTime: '22:00',
        startTime: '08:00',
        dynamic: false,
        dropdown: true,
        scrollbar: true
    });
	
	$('#starttime_1').timepicker({
        timeFormat: 'HH:mm',
        interval: 30,
        minTime: '08',
        maxTime: '22:00',
        startTime: '08:00',
        dynamic: false,
        dropdown: true,
        scrollbar: true
    });
	
	$('#endtime_1').timepicker({
        timeFormat: 'HH:mm',
        interval: 30,
        minTime: '08',
        maxTime: '22:00',
        startTime: '08:00',
        dynamic: false,
        dropdown: true,
        scrollbar: true
    });
});













/**
 * 쿠킹클래스 개설 신청을 지원하는 함수
 */
document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('openClassForm');

    form.addEventListener('submit', async function(event) {
        event.preventDefault();

        // 입력값 검증
        if (!validateOpenClassForm(form)) {
            return;
        }

        try {
            const createRequestDto = {
                title: form.title.value.trim(),
                introduce: document.getElementById('introduce').value.trim(),
                place: form.place.value.trim(),
                category: form.querySelector('input[name="category"]:checked').value,
                eventdate: formatDateForServer(form.eventdate.value.trim()),
                noticedate: formatDateForServer(form.noticedate.value.trim()),
                starttime: formatTimeForServer(form.starttime.value.trim()),
                endtime: formatTimeForServer(form.endtime.value.trim()),
                headcount: parseInt(form.headcount.value.trim(), 10),
                need: form.need.value.trim(),
                target_list: [],
                schedule_list: [],
                tutor_list: []
            };

			// 추천 대상 
			['target1', 'target2', 'target3'].forEach(name => {
			    const val = form[name]?.value.trim();
			    if (val) {
			        createRequestDto.target_list.push({
			            content: val 
			        });
			    }
			});
			
			// 커리큘럼
            const scheduleRows = document.querySelectorAll('#classSchedule table tbody');
            scheduleRows.forEach((tbody) => {
                const startInput = tbody.querySelector('input[name^="starttime"]');
                const endInput = tbody.querySelector('input[name^="endtime"]');
                const titleInput = tbody.querySelector('input[name^="title"]');

                const start = startInput?.value.trim();
                const end = endInput?.value.trim();
                const title = titleInput?.value.trim();

                if (start && end && title) {
                    createRequestDto.schedule_list.push({
                        starttime: formatTimeForServer(start),
                        endtime: formatTimeForServer(end),
                        title: title
                    });
                }
            });

			// 강사
            const tutorRows = document.querySelectorAll('#classTutor table tbody.tutor_row');
            tutorRows.forEach((tbody) => {
                const nameInput = tbody.querySelector('input[name^="name"]');
                const careerInputs = Array.from(tbody.querySelectorAll('input[name^="career"]'));

                const name = nameInput?.value.trim();
                const careers = careerInputs.map(c => c.value.trim()).filter(c => c);

                if (name) {
                    createRequestDto.tutor_list.push({
                        name: name,
                        career: careers.join(', ')
                    });
                }
            });

            const formData = new FormData();
            formData.append(
                'createRequestDto',
                new Blob([JSON.stringify(createRequestDto)], { type: "application/json" })
            );

			// 클래스 이미지
            const classImage = document.getElementById('imageInput').files[0];
            if (classImage) formData.append('classImage', classImage);

			// 강사 이미지
			tutorRows.forEach((tbody, index) => {
			    const imgInput = tbody.querySelector('input[type="file"]');
			    if (imgInput?.files[0]) {
			        formData.append(`tutorImages`, imgInput.files[0]); 
			    }
			});
			
            const response = await instance.post('/classes', formData, {
                headers: {
                    ...getAuthHeaders(),
                    'Content-Type': undefined
                }
            });

            if (response.data.code === 'CLASS_CREATE_SUCCESS') {
                alertPrimary('쿠킹클래스 개설신청이 완료되었습니다.');
                window.location.href = '/mypage/class.do';
            }

        } catch (error) {
            const code = error?.response?.data?.code;
            switch (code) {
                case 'CLASS_CREATE_FAIL': alertDanger('쿠킹클래스 개설에 실패했습니다.'); break;
                case 'CLASS_INVALID_INPUT': alertDanger('입력값이 유효하지 않습니다.'); break;
                case 'CLASS_CREATE_DUPLICATE': alertDanger('이미 개설한 클래스입니다.'); break;
                case 'CLASS_UNAUTHORIZED_ACCESS': alertDanger('로그인되지 않은 사용자입니다.'); break;
                default: console.log(error); break;
            }
        }
    });
});



function formatDateForServer(dateStr) {
    const parts = dateStr.split('/');
    if (parts.length === 3) {
        return `${parts[2]}-${parts[0].padStart(2,'0')}-${parts[1].padStart(2,'0')}`;
    }
    return dateStr;
}

function formatTimeForServer(timeStr) {
    if (!timeStr) return null;
    return `1970-01-01T${timeStr}:00`;
}






/**
 * 클래스 개설 신청시 시간 설정 제한 함수
 */
document.addEventListener('DOMContentLoaded', function() {

	/**
	 * timeToMinutes
	 * - "HH:mm" 문자열 또는 Date 객체를 분 단위로 변환하는 함수
	 * - 시간 계산 및 비교에 활용
	 */
	function timeToMinutes(value) {
	    if (!value) return 0;

	    if (typeof value === 'string') {
	        const [h, m] = value.split(':').map(Number);
	        return h*60 + m;
	    } else if (value instanceof Date) {
	        return value.getHours()*60 + value.getMinutes();
	    } else {
	        return 0;
	    }
	}

	/**
	 * minutesToTime
	 * - 분 단위를 "HH:mm" 문자열로 변환하는 함수
	 * - timepicker에서 minTime, maxTime 설정에 활용
	 */
    function minutesToTime(minutes) {
        const h = Math.floor(minutes / 60).toString().padStart(2,'0');
        const m = (minutes % 60).toString().padStart(2,'0');
        return `${h}:${m}`;
    }

	
	/**
	 * parseTime
	 * - "HH:mm" 문자열을 Date 객체로 변환
	 * - 필요시 Date 기반 비교/계산에 활용
	 */
//    function parseTime(str) {
//        const [h,m] = str.split(':').map(Number);
//        const d = new Date();
//        d.setHours(h, m, 0, 0);
//        return d;
//    }

	/**
	 * bindTimepickers
	 * - 클래스 전체 시간 선택 시 종료시간이 시작시간보다 같거나 빠르지 않도록 제한하는 함수
	 * - 전체 시간 변경 시 커리큘럼 시간도 제한 
	 */
    function bindTimepickers(startSelector, endSelector) {
        const $start = $(startSelector);
        const $end = $(endSelector);

        $start.timepicker({
            timeFormat: 'HH:mm',
            interval: 30,
            minTime: '08:00',
            maxTime: '22:00',
            startTime: '08:00',
            dynamic: false,
            dropdown: true,
            scrollbar: true,
            change: function(startTime) {
                const startMinutes = timeToMinutes(startTime);
                const minEnd = startMinutes + 30; 
                $end.timepicker('option', 'minTime', minutesToTime(minEnd));

                const endVal = $end.val();
                if (endVal && timeToMinutes(endVal) <= startMinutes) $end.val('');

                // 전체 시간 변경 시, 모든 커리큘럼 제한 갱신
                updateCurriculumLimits();
            }
        });

        $end.timepicker({
            timeFormat: 'HH:mm',
            interval: 30,
            minTime: '08:30',
            maxTime: '22:00',
            startTime: '08:30',
            dynamic: false,
            dropdown: true,
            scrollbar: true
        });
    }

	/**
	 * bindCurriculumTimepickers
	 * - 각 커리큘럼 시간 선택 시 종료시간이 시작시간보다 같거나 빠르지 않도록 제한하는 함수
	 * - 커리큘럼 시간 선택 시 전체 클래스 시간 안에서만 선택 가능하도록 제한
	 * - 이전 커리큘럼 종료시간 이후부터 시작 가능하도록 제한 (겹치지 않도록)
	 */
	function bindCurriculumTimepickers(startSelector, endSelector) {
	    const $start = $(startSelector);
	    const $end = $(endSelector);

	    function getClassStartEnd() {
	        const classStart = $('#starttime').val() || '08:00';
	        const classEnd = $('#endtime').val() || '22:00';
	        return {classStart, classEnd};
	    }

	    function getPreviousEndTime(currentIndex) {
	        if (currentIndex <= 1) return null; 
	        const prevEnd = $(`#endtime_${currentIndex - 1}`).val();
	        return prevEnd || null;
	    }

	    const currentIndex = parseInt(startSelector.match(/\d+$/)[0]);

	    $start.timepicker({
	        timeFormat: 'HH:mm',
	        interval: 30,
	        dynamic: false,
	        dropdown: true,
	        scrollbar: true,
	        change: function(startTime) {
	            const startMinutes = timeToMinutes(startTime);
	            const minEnd = startMinutes + 30; // 최소 30분
	            $end.timepicker('option', 'minTime', minutesToTime(minEnd));

	            const endVal = $end.val();
	            if (endVal && timeToMinutes(endVal) <= startMinutes) $end.val('');
	        }
	    });

	    $end.timepicker({
	        timeFormat: 'HH:mm',
	        interval: 30,
	        dynamic: false,
	        dropdown: true,
	        scrollbar: true
	    });

	    const {classStart, classEnd} = getClassStartEnd();

	    const prevEndTime = getPreviousEndTime(currentIndex);

	    const minTime = prevEndTime || classStart; 
	    const maxTime = classEnd;

	    $start.timepicker('option', 'minTime', minTime);
	    $start.timepicker('option', 'maxTime', maxTime);
	    $end.timepicker('option', 'minTime', minTime);
	    $end.timepicker('option', 'maxTime', maxTime);

	    // end < start 체크
	    const startVal = $start.val();
	    const endVal = $end.val();
	    if (startVal && endVal && timeToMinutes(endVal) <= timeToMinutes(startVal)) {
	        $end.val('');
	    }
	}

	/**
	 * updateCurriculumLimits
	 * - 클래스 전체 시간 변경 시 커리큘럼 시간 설정 제한 
	 * - 모든 커리큘럼 input의 min/max 제한을 다시 계산
	 * - 이전 커리큘럼 종료시간 이후부터 시작하도록 제한
	 * - 종료시간 < 시작시간이면 초기화
	 */
	function updateCurriculumLimits() {
	    const rows = document.querySelectorAll('#classSchedule table tbody');
	    rows.forEach((tbody, idx) => {
	        const $start = $(`#starttime_${idx+1}`);
	        const $end = $(`#endtime_${idx+1}`);
	        if ($start.length && $end.length) {
	            const classStart = $('#starttime').val() || '08:00';
	            const classEnd = $('#endtime').val() || '22:00';

	            // 이전 커리큘럼 끝시간
	            const prevEnd = idx === 0 ? classStart : $(`#endtime_${idx}`).val() || classStart;

	            $start.timepicker('option', 'minTime', prevEnd);
	            $start.timepicker('option', 'maxTime', classEnd);
	            $end.timepicker('option', 'minTime', prevEnd);
	            $end.timepicker('option', 'maxTime', classEnd);

	            // end < start 체크
	            const startVal = $start.val();
	            const endVal = $end.val();
	            if (startVal && endVal && timeToMinutes(endVal) <= timeToMinutes(startVal)) {
	                $end.val('');
	            }
	        }
	    });
	}

    // 초기 바인딩
    bindTimepickers('#starttime', '#endtime');
    bindCurriculumTimepickers('#starttime_1', '#endtime_1');

	/**
	 * addScheduleBtn 클릭 이벤트
	 * - 새로운 커리큘럼 row 추가
	 * - 자동으로 bindCurriculumTimepickers 호출
	 */
    const addScheduleBtn = document.getElementById('addSchedule');
    addScheduleBtn.addEventListener('click', function(e){
        e.preventDefault();

        const table = document.querySelector('#classSchedule table');
        const rowCount = table.querySelectorAll('tbody').length + 1;

        const newRow = `
        <tbody>
            <tr>
                <th scope="col">시간<span class="ess"></span></th>
                <td>
                    <span class="form_timepicker">
                        <input type="text" class="form-control" id="starttime_${rowCount}" name="starttime${rowCount}" placeholder="시작 시간을 선택하세요">
                    </span>
                    &nbsp;&nbsp;-&nbsp;&nbsp;
                    <span class="form_timepicker">
                        <input type="text" class="form-control" id="endtime_${rowCount}" name="endtime${rowCount}" placeholder="종료 시간을 선택하세요">
                    </span>
                </td>
            </tr>
            <tr>
                <th scope="col">제목<span class="ess"></span></th>
                <td>
                    <span class="form_text">
                        <input maxlength="50" name="title${rowCount}" placeholder="수업 제목을 입력해주세요" type="text" value="">
                    </span>
                </td>
            </tr>
        </tbody>`;

        table.insertAdjacentHTML('beforeend', newRow);
        bindCurriculumTimepickers(`#starttime_${rowCount}`, `#endtime_${rowCount}`);
    });

});

