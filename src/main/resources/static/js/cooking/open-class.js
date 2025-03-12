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