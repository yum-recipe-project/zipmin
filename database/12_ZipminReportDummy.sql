-- ======================
-- 레시피 신고 테이블 더미데이터
-- ======================
INSERT INTO report VALUES (seq_report_id.NEXTVAL, 4, 'recipe', 1, '유출/사칭/사기');
INSERT INTO report VALUES (seq_report_id.NEXTVAL, 5, 'recipe', 2, '상업적 광고 및 판매');
INSERT INTO report VALUES (seq_report_id.NEXTVAL, 6, 'recipe', 3, '욕설/비하');
INSERT INTO report VALUES (seq_report_id.NEXTVAL, 7, 'recipe', 5, '낚시/놀람/도배');
INSERT INTO report VALUES (seq_report_id.NEXTVAL, 8, 'recipe', 6, '정당/정치인 비하 및 선거운동');
INSERT INTO report VALUES (seq_report_id.NEXTVAL, 4, 'recipe', 7, '상업적 광고 및 판매');
INSERT INTO report VALUES (seq_report_id.NEXTVAL, 5, 'recipe', 8, '유출/사칭/사기');

COMMIT;





-- =====================
-- 댓글 신고 테이블 더미데이터
-- ======================
INSERT INTO report VALUES (seq_report_id.NEXTVAL, 4, 'comments', 1, '욕설/비하');
INSERT INTO report VALUES (seq_report_id.NEXTVAL, 5, 'comments', 1, '유출/사칭/사기');
INSERT INTO report VALUES (seq_report_id.NEXTVAL, 6, 'comments', 2, '낚시/놀람/도배');
INSERT INTO report VALUES (seq_report_id.NEXTVAL, 4, 'comments', 3, '상업적 광고 및 판매');
INSERT INTO report VALUES (seq_report_id.NEXTVAL, 5, 'comments', 4, '정당/정치인 비하 및 선거운동');
INSERT INTO report VALUES (seq_report_id.NEXTVAL, 7, 'comments', 5, '욕설/비하');
INSERT INTO report VALUES (seq_report_id.NEXTVAL, 8, 'comments', 6, '유출/사칭/사기');
INSERT INTO report VALUES (seq_report_id.NEXTVAL, 4, 'comments', 7, '낚시/놀람/도배');
INSERT INTO report VALUES (seq_report_id.NEXTVAL, 5, 'comments', 8, '상업적 광고 및 판매');
INSERT INTO report VALUES (seq_report_id.NEXTVAL, 9, 'comments', 9, '욕설/비하');
INSERT INTO report VALUES (seq_report_id.NEXTVAL, 10, 'comments', 10, '정당/정치인 비하 및 선거운동');
INSERT INTO report VALUES (seq_report_id.NEXTVAL, 4, 'comments', 11, '유출/사칭/사기');
INSERT INTO report VALUES (seq_report_id.NEXTVAL, 5, 'comments', 12, '욕설/비하');
INSERT INTO report VALUES (seq_report_id.NEXTVAL, 11, 'comments', 13, '상업적 광고 및 판매');
INSERT INTO report VALUES (seq_report_id.NEXTVAL, 12, 'comments', 14, '낚시/놀람/도배');
INSERT INTO report VALUES (seq_report_id.NEXTVAL, 4, 'comments', 15, '정당/정치인 비하 및 선거운동');
INSERT INTO report VALUES (seq_report_id.NEXTVAL, 5, 'comments', 16, '유출/사칭/사기');
INSERT INTO report VALUES (seq_report_id.NEXTVAL, 13, 'comments', 17, '욕설/비하');
INSERT INTO report VALUES (seq_report_id.NEXTVAL, 14, 'comments', 18, '상업적 광고 및 판매');
INSERT INTO report VALUES (seq_report_id.NEXTVAL, 4, 'comments', 19, '낚시/놀람/도배');
INSERT INTO report VALUES (seq_report_id.NEXTVAL, 5, 'comments', 20, '정당/정치인 비하 및 선거운동');

COMMIT;