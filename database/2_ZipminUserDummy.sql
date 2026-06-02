-- ======================================================================================================================================================
-- USERS 테이블 더미데이터
-- id, username, password, name, nickname, tel, email, avatar, introduce, link, point, revenue, role, enable, provider, provider_id, refresh, expiration
-- ======================================================================================================================================================
INSERT INTO users VALUES (seq_user_id.NEXTVAL, 'admin', '$2a$10$B.Qdm3ECeo/PYL2P.0Sx/uuAznM0IkEBrQH6IX9vEn6cXaa4E/NOe', '총관리자', '집밥의민족', null, null, '/images/user/admin.png', null, null, 0, 0, 'ROLE_SUPER_ADMIN', 1, null, null, null, null); -- 1
INSERT INTO users VALUES (seq_user_id.NEXTVAL, 'admin1', '$2a$10$B.Qdm3ECeo/PYL2P.0Sx/uuAznM0IkEBrQH6IX9vEn6cXaa4E/NOe', '관리자1', '집밥의민족', null, null, '/images/user/admin.png', null, null, 0, 0, 'ROLE_ADMIN', 1, null, null, null, null); -- 2
INSERT INTO users VALUES (seq_user_id.NEXTVAL, 'admin2', '$2a$10$B.Qdm3ECeo/PYL2P.0Sx/uuAznM0IkEBrQH6IX9vEn6cXaa4E/NOe', '관리자2', '집밥의민족', null, null, '/images/user/admin.png', null, null, 0, 0, 'ROLE_ADMIN', 1, null, null, null, null); -- 3
INSERT INTO users VALUES (seq_user_id.NEXTVAL, 'harim', '$2a$10$B.Qdm3ECeo/PYL2P.0Sx/uuAznM0IkEBrQH6IX9vEn6cXaa4E/NOe', '정하림', '아잠만', '010-0000-0000', 'jhr.chicken@gmail.com', '/images/user/user6.png', '안녕하세요 레시피 열심히 작성할게요!', 'harim.com', 2000, 1500, 'ROLE_USER', 1, null, null, null, null); -- 4
INSERT INTO users VALUES (seq_user_id.NEXTVAL, 'dayeong', '$2a$10$B.Qdm3ECeo/PYL2P.0Sx/uuAznM0IkEBrQH6IX9vEn6cXaa4E/NOe', '부다영', '김뿌영', '010-0000-0000', 'dyboo1347@gmail.com', '/images/user/user9.png', '안녕하세요~', null, 2000, 200, 'ROLE_USER', 1, null, null, null, null); -- 5
INSERT INTO users VALUES (seq_user_id.NEXTVAL, 'user1', '$2a$10$B.Qdm3ECeo/PYL2P.0Sx/uuAznM0IkEBrQH6IX9vEn6cXaa4E/NOe', '사용자1',  '꼬물이', '010-0000-0000', 'user1@gmail.com',  '/images/user/user2.png', null, null, 100, 200, 'ROLE_USER', 1, null, null, null, null); -- 6
INSERT INTO users VALUES (seq_user_id.NEXTVAL, 'user2', '$2a$10$B.Qdm3ECeo/PYL2P.0Sx/uuAznM0IkEBrQH6IX9vEn6cXaa4E/NOe', '사용자2',  '정뚱땡', '010-0000-0000', 'user2@gmail.com',  '/images/user/user3.png', null, null, 100, 200, 'ROLE_USER', 1, null, null, null, null); -- 7
INSERT INTO users VALUES (seq_user_id.NEXTVAL, 'user3', '$2a$10$B.Qdm3ECeo/PYL2P.0Sx/uuAznM0IkEBrQH6IX9vEn6cXaa4E/NOe', '사용자3', '눈물수박',  '010-0000-0000', 'user3@gmail.com',  '/images/user/user4.png', null, null, 100, 200, 'ROLE_USER', 1, null, null, null, null); -- 8
INSERT INTO users VALUES (seq_user_id.NEXTVAL, 'user4', '$2a$10$B.Qdm3ECeo/PYL2P.0Sx/uuAznM0IkEBrQH6IX9vEn6cXaa4E/NOe', '사용자4',  '거친파도', '010-0000-0000', 'user4@gmail.com',  '/images/user/user5.png', null, null, 100, 200, 'ROLE_USER', 1, null, null, null, null); -- 9
INSERT INTO users VALUES (seq_user_id.NEXTVAL, 'user5', '$2a$10$B.Qdm3ECeo/PYL2P.0Sx/uuAznM0IkEBrQH6IX9vEn6cXaa4E/NOe', '사용자5', '졸림핑', '010-0000-0000', 'user5@gmail.com',  '/images/user/user6.png', null, null, 100, 200, 'ROLE_USER', 1, null, null, null, null); -- 10
INSERT INTO users VALUES (seq_user_id.NEXTVAL, 'user6', '$2a$10$B.Qdm3ECeo/PYL2P.0Sx/uuAznM0IkEBrQH6IX9vEn6cXaa4E/NOe', '사용자6', '해파리', '010-0000-0000', 'user6@gmail.com',  '/images/user/user7.png', null, null, 100, 200, 'ROLE_USER', 1, null, null, null, null); -- 11
INSERT INTO users VALUES (seq_user_id.NEXTVAL, 'user7', '$2a$10$B.Qdm3ECeo/PYL2P.0Sx/uuAznM0IkEBrQH6IX9vEn6cXaa4E/NOe', '사용자7', '초코하루', '010-0000-0000', 'user7@gmail.com',  '/images/user/user8.png', null, null, 100, 200, 'ROLE_USER', 1, null, null, null, null); -- 12
INSERT INTO users VALUES (seq_user_id.NEXTVAL, 'user8', '$2a$10$B.Qdm3ECeo/PYL2P.0Sx/uuAznM0IkEBrQH6IX9vEn6cXaa4E/NOe', '사용자8', '요리왕', '010-0000-0000', 'user8@gmail.com',  '/images/user/user9.png', null, null, 100, 200, 'ROLE_USER', 1, null, null, null, null); -- 13
INSERT INTO users VALUES (seq_user_id.NEXTVAL, 'user9', '$2a$10$B.Qdm3ECeo/PYL2P.0Sx/uuAznM0IkEBrQH6IX9vEn6cXaa4E/NOe', '사용자9', '백곰', '010-0000-0000', 'user8@gmail.com',  '/images/user/user9.png', null, null, 100, 200, 'ROLE_USER', 1, null, null, null, null); -- 14

COMMIT;





-- ====================================
-- USER ACCOUNT 테이블 더미데이터
-- id, bank, accountnum, name, user_id
-- ====================================
INSERT INTO user_account VALUES (seq_user_account_id.NEXTVAL, '국민은행', '91930101000000', '정하림', 4);
INSERT INTO user_account VALUES (seq_user_account_id.NEXTVAL, '신한은행', '11000000000000', '부다영', 5);
INSERT INTO user_account VALUES (seq_user_account_id.NEXTVAL, '우리은행', '10020000000000', '사용자1', 6);
INSERT INTO user_account VALUES (seq_user_account_id.NEXTVAL, '하나은행', '08100000000000', '사용자2', 7);
INSERT INTO user_account VALUES (seq_user_account_id.NEXTVAL, '농협은행', '30100000000000', '사용자3', 8);
INSERT INTO user_account VALUES (seq_user_account_id.NEXTVAL, '기업은행', '00300000000000', '사용자4', 9);
INSERT INTO user_account VALUES (seq_user_account_id.NEXTVAL, 'SC제일은행', '02000000000000', '사용자5', 10);
INSERT INTO user_account VALUES (seq_user_account_id.NEXTVAL, '카카오뱅크', '33330000000000', '사용자6', 11);
INSERT INTO user_account VALUES (seq_user_account_id.NEXTVAL, '토스뱅크', '10010000000000', '사용자7', 12);
INSERT INTO user_account VALUES (seq_user_account_id.NEXTVAL, '부산은행', '10100000000000', '사용자8', 13);
INSERT INTO user_account VALUES (seq_user_account_id.NEXTVAL, '대구은행', '50400000000000', '사용자9', 14);

COMMIT;