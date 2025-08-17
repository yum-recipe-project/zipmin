<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="modal" id="listReportModal">
	<div class="modal-dialog modal-lg modal-dialog-scrollable">
		<div class="modal-content">
			<div class="modal-header d-flex align-items-center">
				<h5>신고 목록</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
			</div>
			<div class="modal-body">
				<table class="table text-nowrap mb-0 align-middle">
					<thead class="text-dark fs-4">
						<tr>
				            <th id="reportCount" class="text-start"></th>
				            <th></th>
				            <th></th>
				        </tr>
				        <tr class="table_th">
				            <th>
				                <h6 class="fs-4 fw-semibold mb-0">No</h6>
				            </th>
				            <th>
				                <h6 class="fs-4 fw-semibold mb-0">신고 사유</h6>
				            </th>
				            <th>
				                <h6 class="fs-4 fw-semibold mb-0">신고자</h6>
				            </th>
				        </tr>
					</thead>
					<tbody class="report_list"></tbody>
				</table>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-light px-4" data-bs-dismiss="modal">닫기</button>
			</div>
		</div>
	</div>
	
</div>




