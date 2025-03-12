<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<form id="changeAccountForm" onsubmit="">
	<div class="modal" id="changeAccountModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5>출금 계좌 관리</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
				<div class="modal-body">
					<!-- 출금 계좌가 있을 경우에만 출력 -->
					<h5>출금 계좌</h5>
					<div class="form-account">
						<p>국민은행 919*******0</p>
						<p>예금주 정하림</p>
					</div>
					<!-- 출금 계좌 변경 -->
					<h5>출금 계좌 변경</h5>
					<div class="form-group">
						<label>은행</label>
		                <select class="form-select" id="accountBankInput">
		                    <option value="1">산업은행</option>
		                    <option value="2">기업은행</option>
		                    <option value="3">국민은행</option>
		                    <option value="3">수협</option>
		                    <option value="3">농협은행</option>
		                    <option value="3">지역농축협</option>
		                    <option value="3">우리은행</option>
		                    <option value="3">SC은행</option>
		                    <option value="3">시티은행</option>
		                    <option value="3">대구은행</option>
		                    <option value="3">부산은행</option>
		                    <option value="3">광주은행</option>
		                    <option value="3">제주은행</option>
		                    <option value="3">전북은행</option>
		                    <option value="3">경남은행</option>
		                    <option value="3">새마을금고</option>
		                    <option value="3">신협</option>
		                    <option value="3">우체국</option>
		                    <option value="3">하나은행</option>
		                    <option value="3">신한은행</option>
		                    <option value="3">케이뱅크</option>
		                    <option value="3">카카오뱅크</option>
		                    <option value="3">토스뱅크</option>
		                    <option value="3">KB증권</option>
		                    <option value="3">미래에셋증권</option>
		                    <option value="3">삼성증권</option>
		                    <option value="3">한국투자</option>
		                    <option value="3">NH투자증권</option>
		                    <option value="3">하나증권</option>
		                    <option value="3">신한투자증권</option>
		                    <option value="3">메리츠증권</option>
		                </select>
					</div>
					<div class="form-group">
						<label>계좌번호</label>
						<input class="form-control" id="accountNumberInput" name="" value="" type="number" step="1">
						<p>'-' 없이 숫자만 입력해 주세요. 선불전자지급수단은 사용할 수 없어요.</p>
					</div>
					<div class="form-group">
						<label>예금주명</label>
						<input class="form-control" id="accountNameInput" name="name" value="">
						<p>예금주명은 회원의 실명과 일치해야 해요.</p>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-danger" data-bs-dismiss="modal">닫기</button>
					<button type="submit" class="btn btn-primary disabled">변경하기</button>
				</div>
			</div>
		</div>
	</div>
</form>