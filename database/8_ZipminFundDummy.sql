-- ======================================================================
-- FUND 테이블 더미데이터
-- VALUES (id, funder_id, fundee_id, recipe_id, point, funddate, status);
-- ======================================================================
INSERT INTO fund VALUES (seq_fund_id.NEXTVAL, 5, 4, 26, 1000, SYSDATE, 1);
INSERT INTO fund VALUES (seq_fund_id.NEXTVAL, 8, 4, 32, 500, SYSDATE, 1);
INSERT INTO fund VALUES (seq_fund_id.NEXTVAL, 14, 4, 32, 700, SYSDATE, 1);
INSERT INTO fund VALUES (seq_fund_id.NEXTVAL, 14, 4, 101, 1500, SYSDATE, 1);
COMMIT;





-- =================================================================================
-- WITHDRAW 테이블
-- VALUES (id, user_id, account_id, point, claimdate, status, settledate, admin_id);
-- =================================================================================
INSERT INTO withdraw VALUES (seq_withdraw_id.NEXTVAL, 4, 1, 1000, SYSDATE - 50, 1, SYSDATE - 30, 1);
INSERT INTO withdraw VALUES (seq_withdraw_id.NEXTVAL, 5, 2, 5000, SYSDATE - 44, 1, SYSDATE - 42, 1);
INSERT INTO withdraw VALUES (seq_withdraw_id.NEXTVAL, 8, 5, 2500, SYSDATE - 43, 1, SYSDATE - 42, 1);
INSERT INTO withdraw VALUES (seq_withdraw_id.NEXTVAL, 14, 11, 1000, SYSDATE - 39, 1, SYSDATE - 37, 1);
INSERT INTO withdraw VALUES (seq_withdraw_id.NEXTVAL, 6, 3, 3500, SYSDATE - 32, 1, SYSDATE - 30, 1);
INSERT INTO withdraw VALUES (seq_withdraw_id.NEXTVAL, 7, 4, 1200, SYSDATE - 24, 1, SYSDATE - 22, 1);
INSERT INTO withdraw VALUES (seq_withdraw_id.NEXTVAL, 4, 1, 5000, SYSDATE - 20, 1, SYSDATE - 18, 1);
INSERT INTO withdraw VALUES (seq_withdraw_id.NEXTVAL, 7, 4, 1400, SYSDATE - 10, 1, SYSDATE - 8, 1);
INSERT INTO withdraw VALUES (seq_withdraw_id.NEXTVAL, 8, 5, 1700, SYSDATE - 8, 0, NULL, NULL);
INSERT INTO withdraw VALUES (seq_withdraw_id.NEXTVAL, 14, 11, 2300, SYSDATE - 4, 0, NULL, NULL);
INSERT INTO withdraw VALUES (seq_withdraw_id.NEXTVAL, 4, 1, 3000, SYSDATE - 1, 0, NULL, NULL);

COMMIT;
