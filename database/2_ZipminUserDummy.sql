-- ===================
-- USERS 테이블 더미데이터
-- ===================
INSERT INTO users VALUES (seq_user_id.NEXTVAL, 'admin', '$2a$10$B.Qdm3ECeo/PYL2P.0Sx/uuAznM0IkEBrQH6IX9vEn6cXaa4E/NOe', '총관리자', '집밥의민족', null, null, '/images/user/admin.png', null, null, 0, 0, 'ROLE_SUPER_ADMIN', 1, null, null, null, null);
INSERT INTO users VALUES (seq_user_id.NEXTVAL, 'admin1', '$2a$10$B.Qdm3ECeo/PYL2P.0Sx/uuAznM0IkEBrQH6IX9vEn6cXaa4E/NOe', '관리자1', '집밥의민족', null, null, '/images/user/admin.png', null, null, 0, 0, 'ROLE_ADMIN', 1, null, null, null, null);
INSERT INTO users VALUES (seq_user_id.NEXTVAL, 'admin2', '$2a$10$B.Qdm3ECeo/PYL2P.0Sx/uuAznM0IkEBrQH6IX9vEn6cXaa4E/NOe', '관리자2', '집밥의민족', null, null, '/images/user/admin.png', null, null, 0, 0, 'ROLE_ADMIN', 1, null, null, null, null);
INSERT INTO users VALUES (seq_user_id.NEXTVAL, 'harim', '$2a$10$B.Qdm3ECeo/PYL2P.0Sx/uuAznM0IkEBrQH6IX9vEn6cXaa4E/NOe', '정하림', '아잠만', '010-0000-0000', 'jhr.chicken@gmail.com', '/images/user/user6.png', '안녕하세요 아잠만입니다 레시피 열심히 작성할게요 !!', null, 2000, 200, 'ROLE_USER', 1, null, null, null, null);
INSERT INTO users VALUES (seq_user_id.NEXTVAL, 'dayeong', '$2a$10$B.Qdm3ECeo/PYL2P.0Sx/uuAznM0IkEBrQH6IX9vEn6cXaa4E/NOe', '부다영', '김뿌영', '010-0000-0000', 'dyboo1347@gmail.com', '/images/user/user9.png', '안녕하세요~', null, 2000, 200, 'ROLE_USER', 1, null, null, null, null);
INSERT INTO users VALUES (seq_user_id.NEXTVAL, 'user1', '$2a$10$B.Qdm3ECeo/PYL2P.0Sx/uuAznM0IkEBrQH6IX9vEn6cXaa4E/NOe', '사용자1',  '꼬물이',  '010-0000-0000', 'user1@gmail.com',  '/images/user/user2.png', null, null, 100, 200, 'ROLE_USER', 1, null, null, null, null);
INSERT INTO users VALUES (seq_user_id.NEXTVAL, 'user2', '$2a$10$B.Qdm3ECeo/PYL2P.0Sx/uuAznM0IkEBrQH6IX9vEn6cXaa4E/NOe', '사용자2',  '정뚱땡',  '010-0000-0000', 'user2@gmail.com',  '/images/user/user3.png', null, null, 100, 200, 'ROLE_USER', 1, null, null, null, null);
INSERT INTO users VALUES (seq_user_id.NEXTVAL, 'user3', '$2a$10$B.Qdm3ECeo/PYL2P.0Sx/uuAznM0IkEBrQH6IX9vEn6cXaa4E/NOe', '사용자3',  '눈물수박',  '010-0000-0000', 'user3@gmail.com',  '/images/user/user4.png', null, null, 100, 200, 'ROLE_USER', 1, null, null, null, null);
INSERT INTO users VALUES (seq_user_id.NEXTVAL, 'user4', '$2a$10$B.Qdm3ECeo/PYL2P.0Sx/uuAznM0IkEBrQH6IX9vEn6cXaa4E/NOe', '사용자4',  '거친파도',  '010-0000-0000', 'user4@gmail.com',  '/images/user/user5.png', null, null, 100, 200, 'ROLE_USER', 1, null, null, null, null);
INSERT INTO users VALUES (seq_user_id.NEXTVAL, 'user5', '$2a$10$B.Qdm3ECeo/PYL2P.0Sx/uuAznM0IkEBrQH6IX9vEn6cXaa4E/NOe', '사용자5',  '졸림핑',  '010-0000-0000', 'user5@gmail.com',  '/images/user/user6.png', null, null, 100, 200, 'ROLE_USER', 1, null, null, null, null);
INSERT INTO users VALUES (seq_user_id.NEXTVAL, 'user6', '$2a$10$B.Qdm3ECeo/PYL2P.0Sx/uuAznM0IkEBrQH6IX9vEn6cXaa4E/NOe', '사용자6',  '해파리',  '010-0000-0000', 'user6@gmail.com',  '/images/user/user7.png', null, null, 100, 200, 'ROLE_USER', 1, null, null, null, null);
INSERT INTO users VALUES (seq_user_id.NEXTVAL, 'user7', '$2a$10$B.Qdm3ECeo/PYL2P.0Sx/uuAznM0IkEBrQH6IX9vEn6cXaa4E/NOe', '사용자7',  '초코하루',  '010-0000-0000', 'user7@gmail.com',  '/images/user/user8.png', null, null, 100, 200, 'ROLE_USER', 1, null, null, null, null);
INSERT INTO users VALUES (seq_user_id.NEXTVAL, 'user8', '$2a$10$B.Qdm3ECeo/PYL2P.0Sx/uuAznM0IkEBrQH6IX9vEn6cXaa4E/NOe', '사용자8',  '요리왕',  '010-0000-0000', 'user8@gmail.com',  '/images/user/user9.png', null, null, 100, 200, 'ROLE_USER', 1, null, null, null, null);
INSERT INTO users VALUES (seq_user_id.NEXTVAL, 'user9', '$2a$10$B.Qdm3ECeo/PYL2P.0Sx/uuAznM0IkEBrQH6IX9vEn6cXaa4E/NOe', '사용자9',  '백곰',  '010-0000-0000', 'user8@gmail.com',  '/images/user/user9.png', null, null, 100, 200, 'ROLE_USER', 1, null, null, null, null);
commit;





-- ==========================
-- USER ACCOUNT 테이블 더미데이터
-- ==========================
















