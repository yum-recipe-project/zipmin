/**
 * 입력폼의 필수 내용을 입력했는지 검증하는 함수
 * 
 * @param {Form} form - 검증할 HTML폼 객체
 * @returns {boolean} - 검증 결과
 */
function validateOpenClassForm(form) {
	if (form.title.value.trim() === "") {
		form.title.focus();
		return false;
	}
	if (form.place.value.trim() === "") {
		form.place.focus();
		return false;
	}
	if (form.eventdate.value.trim() === "") {
		form.eventdate.focus();
		return false;
	}
	if (form.starttime.value.trim() === "") {
		form.starttime.focus();
		return false;
	}
	if (form.endtime.value.trim() === "") {
		form.endtime.focus();
		return false;
	}
	if (form.headcount.value.trim() === "") {
		form.headcount.focus();
		return false;
	}
	if (form.headcount.value.trim() === "") {
		form.headcount.focus();
		return false;
	}
	if (form.need.value.trim() === "") {
		form.need.focus();
		return false;
	}
	if (form.image_url.value.trim() === "") {
		form.image_url.focus();
		return false;
	}
	if (form.target1.value.trim() === "") {
		form.target1.focus();
		return false;
	}
	if (form.introduce.value.trim() === "") {
		form.introduce.focus();
		return false;
	}
	if (form.starttime1.value.trim() === "") {
		form.starttime1.focus();
		return false;
	}
	if (form.endtime1.value.trim() === "") {
		form.endtime1.focus();
		return false;
	}
	if (form.title1.value.trim() === "") {
		form.title1.focus();
		return false;
	}
	if (form.name.value.trim() === "") {
		form.name.focus();
		return false;
	}
	if (form.career1.value.trim() === "") {
		form.career1.focus();
		return false;
	}
	if (form.teacher_img.value.trim() === "") {
		form.teacher_img.focus();
		return false;
	}
}



/**
 * 
 */
document.addEventListener('DOMContentLoaded', function() {
	document.getElementById("addSchedule").addEventListener("click", function(event) {
		event.preventDefault();
		const rowCount = document.querySelectorAll("#classSchedule table tbody tr").length / 2 + 1;
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
			</tbody>
		`;
		
		document.querySelector("#classSchedule table").insertAdjacentHTML("beforeend", newRow);
		initializeTimepicker(`#starttime_${rowCount}`);
		initializeTimepicker(`#endtime_${rowCount}`);
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
    $("#eventdate").datepicker({
      format: "yyyy-mm-dd",
      autoclose: true,
      todayHighlight: true,
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

        try {
            const createRequestDto = {
                title: form.title.value.trim(),
                introduce: document.getElementById('introduce').value.trim(),
                place: form.place.value.trim(),
                category: form.category.value.trim(),
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

            // 2추천 대상 리스트
            ['target1', 'target2', 'target3'].forEach(name => {
                const val = form[name]?.value.trim();
                if (val) createRequestDto.target_list.push(val);
            });

            // 스케줄 리스트 (커리큘럼)
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

            // 4강사 리스트
            const tutorName = form.name.value.trim();
            const careers = [form.career1.value, form.career2.value, form.career3.value]
                .map(c => c.trim())
                .filter(c => c);

            if (tutorName) {
                createRequestDto.tutor_list.push({
                    name: tutorName,
                    career: careers.join(', ')
                });
            }

            const formData = new FormData();
            formData.append(
                'createRequestDto',
                new Blob([JSON.stringify(createRequestDto)], { type: "application/json" })
            );

            // 클래스 이미지
            const classImage = document.getElementById('imageInput').files[0];
            if (classImage) formData.append('classImage', classImage);

            // 강사 이미지
            const teacherImgFile = document.getElementById('teacherImgInput').files[0];
            if (teacherImgFile) formData.append('tutorImages', teacherImgFile);

            console.log("📦 전송 직전 DTO:", createRequestDto);

            const response = await instance.post('/classes', formData, {
                headers: {
                    ...getAuthHeaders(),
                    'Content-Type': undefined
                }
            });

            if (response.data.code === 'CLASS_CREATE_SUCCESS') {
                alertPrimary('쿠킹클래스 개설신청이 완료되었습니다.');
                form.reset();
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


