<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<form id="writeGuideForm">
    <div class="modal" id="writeGuideModal">
        <div class="modal-dialog modal-lg modal-dialog-scrollable">
            <div class="modal-content">
                
                <!-- 헤더 -->
                <div class="modal-header d-flex align-items-center">
                    <h5>키친가이드 작성하기</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                
                <!-- 바디 -->
                <div class="modal-body">
                    
                    <!-- 소제목 -->
                    <div class="form-group">
                        <label for="subtitle">소제목</label>
                        <input type="text" id="subtitle" name="subtitle" placeholder="신선도를 유지하는 법" class="form-control">
<!--                         <p id="subtitleHint" class="danger">소제목을 입력해주세요.</p> -->
                    </div>
                    
                    <!-- 제목 -->
                    <div class="form-group">
                        <label for="title">제목</label>
                        <input type="text" id="title" name="title" placeholder="생선 냉동 보관법" class="form-control">
<!--                         <p id="titleHint" class="danger">제목을 입력해주세요.</p> -->
                    </div>
                    
                    <!-- 카테고리 -->
                    <div class="form-group">
                        <label for="category">카테고리</label>
                        <select id="category" name="category" class="form-control">
                            <option value="">-- 선택하세요 --</option>
                            <option value="preparation">손질법</option>
                            <option value="storage">보관법</option>
                            <option value="info">요리 정보</option>
                            <option value="etc">기타 정보</option>
                        </select>
<!--                         <p id="categoryHint" class="danger">카테고리를 선택해주세요.</p> -->
                    </div>
                    
                    <!-- 내용 -->
                    <div class="form-group">
                        <label for="content">내용</label>
                        <textarea id="content" name="content" rows="10" class="form-control" placeholder="본문 내용을 입력하세요"></textarea>
<!--                         <p id="contentHint" class="danger">내용을 입력해주세요.</p> -->
                    </div>
                    
                </div>
                
                <!-- 푸터 -->
                <div class="modal-footer">
                    <button type="button" class="btn btn-light px-4" data-bs-dismiss="modal">닫기</button>
                    <button type="submit" class="btn btn-info px-4 ms-6">작성하기</button>
                </div>
                
            </div>
        </div>
    </div>
</form>
