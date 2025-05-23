-- 계정 생성
-- alter session set "_ORACLE_SCRIPT"=true;
-- create user zipmin identified by 1234;
-- grant connect, resource, unlimited tablespace to zipmin;



-- 테이블과 시퀀스 일괄 삭제
drop table chomp_event;
drop sequence seq_chomp_event_id;
drop table chomp_megazine;
drop sequence seq_chomp_megazine_id;
drop table chomp_vote_record;
drop sequence seq_chomp_vote_record_id;
drop table chomp_vote_choice;
drop sequence seq_chomp_vote_choice_id;
drop table chomp_vote;
drop sequence seq_chomp_vote_id;
drop table chomp;
drop sequence seq_chomp_id;

drop table report;
drop sequence seq_report_id;
drop table likes;
drop sequence seq_likes_id;

drop table review;
drop sequence seq_review_id;
drop table comments;
drop sequence seq_comments_id;
drop table guide;
drop sequence seq_guide_id;

drop table recipe_step;
drop sequence seq_recipe_step_id;
drop table recipe_ingredient;
drop sequence seq_recipe_ingredient_id;
drop table recipe_category;
drop sequence seq_recipe_category_id;
drop table recipe;
drop sequence seq_recipe_id;

drop table user_account;
drop sequence seq_user_account_id;
drop table users;



-- USERS 테이블
-- drop table users;
create table users (
    id varchar2(100) primary key,
    password varchar2(200) not null,
    name varchar2(30) not null,
    nickname varchar2(100) not null,
    email varchar2(50) not null,
    avatar varchar2(200),
    point number default 0,
    revenue number default 9,
    auth varchar2(15) not null,
    enable number(1) default 1
);
insert into users values ('harim', '$2a$10$//.68hv55MI4V28Xv87MKe/i3fFMuun6XnDcomuMXDfHC6RPYLSGy', '정하림', '아잠만', 'qazwsx9445@naver.com', null, 100, 200, 'ROLE_USER', 1);
insert into users values ('dayeoung', '$2a$10$//.68hv55MI4V28Xv87MKe/i3fFMuun6XnDcomuMXDfHC6RPYLSGy', '부다영', '김뿌영', 'dyboo1347@gmail.com', null, 100, 200, 'ROLE_USER', 1);
insert into users values ('user1', '$2a$10$//.68hv55MI4V28Xv87MKe/i3fFMuun6XnDcomuMXDfHC6RPYLSGy', '사용자1', '사용자1', 'user1@gmail.com', null, 100, 200, 'ROLE_USER', 1);
insert into users values ('user2', '$2a$10$//.68hv55MI4V28Xv87MKe/i3fFMuun6XnDcomuMXDfHC6RPYLSGy', '사용자2', '사용자2', 'user2@gmail.com', null, 100, 200, 'ROLE_USER', 1);
insert into users values ('user3', '$2a$10$//.68hv55MI4V28Xv87MKe/i3fFMuun6XnDcomuMXDfHC6RPYLSGy', '사용자2', '사용자2', 'user2@gmail.com', null, 100, 200, 'ROLE_USER', 1);
commit;



-- USER_ACCOUNT 테이블
-- drop table user_account;
-- drop sequence seq_user_account_id;
create table user_account (
    id number primary key,
    bank varchar2(100) not null,
    accountnum varchar2(30) not null,
    name varchar2(30) not null,
    user_id varchar2(15) not null
);
alter table user_account
    add constraint const_user_account_user foreign key(user_id)
    references users(id) on delete cascade;
create sequence seq_user_account_id
    increment by 1
    start with 1
    minvalue 1
    nomaxvalue
    nocycle
    nocache;
commit;



-- USER_INGREDIENT 테이블






-- RECIPE 테이블
-- drop table recipe;
-- drop sequence seq_recipe_id;
create table recipe (
    id number primary key,
    image_url varchar2(50) not null,
    title varchar2(50) not null,
    introduce varchar2(300) not null,
    cooklevel varchar2(15) not null,
    cooktime varchar2(15) not null,
    spicy varchar2(15) not null,
    portion varchar2(15) not null,
    tip varchar2(300),
    youtube_url varchar2(100),
    user_id varchar2(15) not null
);
alter table recipe
    add constraint const_recipe_users foreign key(user_id)
    references users(id) on delete cascade;
create sequence seq_recipe_id
    increment by 1
    start with 1
    minvalue 1
    nomaxvalue
    nocycle
    nocache;
commit;



-- RECIPE_CATEGORY 테이블
-- drop table recipe_category;
-- drop sequence seq_recipe_category_id;
create table recipe_category (
    id number primary key,
    type varchar2(15) not null,
    tag varchar2(15) not null,
    recipe_id number not null
);
alter table recipe_category
    add constraint const_recipe_category_recipe foreign key(recipe_id)
    references recipe(id) on delete cascade;
create sequence seq_recipe_category_id
    increment by 1
    start with 1
    minvalue 1
    nomaxvalue
    nocycle
    nocache;
commit;



-- RECIPE_INGREDIENT 테이블
-- drop table recipe_ingredient;
-- drop sequence seq_recipe_ingredient_id;
create table recipe_ingredient (
    id number primary key,
    name varchar2(50) not null,
    amount number not null,
    unit varchar2(30) not null,
    note varchar2(300),
    recipe_id number not null
);
alter table recipe_ingredient
    add constraint const_recipe_ingredient_reicpe foreign key(recipe_id)
    references recipe(id) on delete cascade;
create sequence seq_recipe_ingredient_id
    increment by 1
    start with 1
    minvalue 1
    nomaxvalue
    nocycle
    nocache;
commit;



-- RECIPE_STEP 테이블
-- drop table recipe_step;
-- drop sequence seq_recipe_step_id;
create table recipe_step (
    id number primary key,
    image_url varchar2(100) not null,
    content varchar2(500) not null,
    recipe_id number
);
alter table recipe_step
    add constraint const_recipe_step_recipe foreign key(recipe_id)
    references recipe(id) on delete cascade;
create sequence seq_recipe_step_id
    increment by 1
    start with 1
    minvalue 1
    nomaxvalue
    nocycle
    nocache;
commit;





-- GUIDE 테이블
-- drop table guide;
-- drop sequence seq_guide_id;
create table guide (
    id number primary key,
    title varchar2(300) not null,
    subtitle varchar2(300) not null,
    category varchar2(100) not null,
    postdate date default sysdate not null,
    content varchar2(1000) not null
);
create sequence seq_guide_id
    increment by 1
    start with 1
    minvalue 1
    nomaxvalue
    nocycle
    nocache;
commit;



-- COMMENT 테이블
-- drop table comments;
-- drop sequence seq_comments_id;
create table comments (
    id number primary key,
    comm_id number not null,
    postdate date default sysdate not null,
    content varchar2(2000) not null,
    tablename varchar2(100) not null,
    recodenum number not null,
    user_id varchar2(15) not null
);
alter table comments
    add constraint const_comments_comments foreign key(comm_id)
    references comments(id) on delete cascade;
alter table comments
    add constraint const_comments_user foreign key(user_id)
    references users(id) on delete cascade;
create sequence seq_comments_id
    increment by 1
    start with 1
    minvalue 1
    nomaxvalue
    nocycle
    nocache;
insert into comments values (1, 1, sysdate, '녹차 아이스크림은 배스킨라빈스가 최고입니다', 'chomp_megazine', 1, 'harim');
insert into comments values (2, 1, sysdate, '나뚜르가 최곤데 뭘 모르시네요', 'chomp_megazine', 1, 'dayeoung');
insert into comments values (3, 1, sysdate, '그렇다는 증거 있나요?', 'chomp_megazine', 1, 'harim');
insert into comments values (4, 4, sysdate, '녹차 아이스크림 불매합니다', 'chomp_megazine', 1, 'harim');
insert into comments values (5, 5, sysdate, '녹차아이스크림 맛있겠다..', 'chomp_megazine', 1, 'harim');
insert into comments values (6, 6, sysdate, '녹차아이스크림 맛있겠다..', 'chomp_megazine', 1, 'harim');
insert into comments values (7, 7, sysdate, '녹차아이스크림 맛있겠다..', 'chomp_megazine', 1, 'harim');
insert into comments values (8, 8, sysdate, '녹차아이스크림 맛있겠다..', 'chomp_megazine', 1, 'harim');
insert into comments values (9, 9, sysdate, '녹차아이스크림 맛있겠다..', 'chomp_megazine', 1, 'harim');
insert into comments values (10, 10, sysdate, '녹차아이스크림 맛있겠다..', 'chomp_megazine', 1, 'harim');
insert into comments values (11, 11, sysdate, '녹차아이스크림 맛있겠다..', 'chomp_megazine', 1, 'harim');
insert into comments values (12, 12, sysdate, '녹차아이스크림 맛있겠다..', 'chomp_megazine', 1, 'harim');
insert into comments values (13, 13, sysdate, '녹차아이스크림 맛있겠다..', 'chomp_megazine', 1, 'harim');
insert into comments values (14, 14, sysdate, '저도 이벤트 참여할래요!', 'chomp_event', 1, 'harim');
insert into comments values (15, 15, sysdate, '이벤트 참여합니다', 'chomp_event', 1, 'dayeoung');
insert into comments values (16, 16, sysdate, '역시 배스킨라빈스네요', 'chomp_vote', 1, 'harim');
commit;



-- REVIEW 테이블
-- drop table review;
-- drop sequence seq_review_id;
create table review (
    id number primary key,
    postdate date default sysdate not null,
    score number not null,
    content varchar2(2000) not null,
    recipe_id number not null,
    user_id varchar2(15) not null
);
alter table review
    add constraint const_review_recipe foreign key(recipe_id)
    references recipe(id) on delete cascade;
alter table review
    add constraint const_review_users foreign key(user_id)
    references users(id) on delete cascade;
create sequence seq_review_id
    increment by 1
    start with 1
    minvalue 1
    nomaxvalue
    nocycle
    nocache;
commit;



-- LIKES 테이블
-- drop table likes;
-- drop sequence seq_likes_id;
create table likes (
    id number primary key,
    user_id varchar2(15),
    tablename varchar2(15) not null,
    recodenum number not null
);
alter table likes
    add constraint const_likes_users foreign key(user_id)
    references users(id) on delete set null;
create sequence seq_likes_id
    increment by 1
    start with 1
    minvalue 1
    nomaxvalue
    nocycle
    nocache;
insert into likes values (1, 'harim', 'comments', 1);
insert into likes values (2, 'dayeoung', 'comments', 1);
insert into likes values (3, 'harim', 'comments', 4);
insert into likes values (4, 'dayeoung', 'comments', 4);
insert into likes values (5, 'user1', 'comments', 4);
insert into likes values (6, 'user2', 'comments', 4);
insert into likes values (7, 'user3', 'comments', 4);
commit;



-- REPORT 테이블
-- drop table report;
-- drop sequence seq_report_id;
create table report (
    id number primary key,
    user_id varchar2(15),
    tablename varchar2(15) not null,
    recodenum number not null,
    reason varchar2(50) not null
);
alter table report
    add constraint const_report_users foreign key(user_id)
    references users(id) on delete set null;
create sequence seq_report_id
    increment by 1
    start with 1
    minvalue 1
    nomaxvalue
    nocycle
    nocache;
commit;



-- CLASSES 테이블




-- CLASS_TEACHER 테이블



-- CLASS_SCHEDULE 테이블



-- CLASS_APPLY 테이블




-- FUND 테이블
/**
create table fund (
    id number primary key,
    funder_id varchar2(15),
    fundee_id varchar2(15),
    recipe_id number not null,
    point number not null,
    funddate date default sysdate not null,
    status number default 0 not null
);

*/



-- CHOMP 테이블
-- drop table chomp;
-- drop sequence seq_chomp_id;
create table chomp (
    id number primary key,
    category varchar2(30) not null,
    title varchar2(100) not null
);
create sequence seq_chomp_id
    increment by 1
    start with 1
    minvalue 1
    nomaxvalue
    nocycle
    nocache;
insert into chomp values (1, '투표', '당신의 녹차 아이스크림에 투표하세요');
insert into chomp values (2, '매거진', '녹차 아이스크림 4종 비교');
insert into chomp values (3, '이벤트', '3월 한정! 후원 포인트 이체 수수료 무료');
insert into chomp values (4, '투표', '가장 맛있는 딸기 아이스크림');
insert into chomp values (5, '매거진', '여름을 강타한 녹차 아이스크림 트렌드');
insert into chomp values (6, '매거진', '녹차 아이스크림 4종 비교 분석');
insert into chomp values (7, '매거진', '디저트로 즐기는 녹차의 매력');
insert into chomp values (8, '매거진', '비건 녹차 아이스크림의 부상');
insert into chomp values (9, '매거진', '당신의 최애 아이스크림은?');
insert into chomp values (10, '매거진', '편의점 녹차 아이스크림 맛집 리스트');
insert into chomp values (11, '매거진', '에디터가 뽑은 신상 아이스크림 TOP3');
insert into chomp values (12, '매거진', '프리미엄 녹차 아이스크림 열풍');
commit;



-- CHOMP_VOTE 테이블
-- drop table chomp_vote;
-- drop sequence seq_chomp_vote_id;
create table chomp_vote (
    id number primary key,
    opendate date not null,
    closedate date not null,
    chomp_id number not null
);
alter table chomp_vote
    add constraint const_chomp_vote_chomp foreign key(chomp_id)
    references chomp(id) on delete cascade;
create sequence seq_chomp_vote_id
    increment by 1
    start with 1
    minvalue 1
    nomaxvalue
    nocycle
    nocache;
insert into chomp_vote values (1, '25/03/31', '25/05/05', 1);
insert into chomp_vote values (2, '25/01/01', '25/04/01', 4);
commit;



-- CHOMP_VOTE_CHOICE 테이블
-- drop table chomp_vote_choice;
-- drop sequence seq_chomp_vote_choice_id;
create table chomp_vote_choice (
    id number primary key,
    vote_id number not null,
    choice varchar2(100) not null
);
alter table chomp_vote_choice
    add constraint const_chomp_vote_choice_chomp_vote foreign key(vote_id)
    references chomp_vote(id) on delete cascade;
create sequence seq_chomp_vote_choice_id
    increment by 1
    start with 1
    minvalue 1
    nomaxvalue
    nocycle
    nocache;
commit;



-- CHOMP_VOTE_RECORD 테이블
-- drop table chomp_vote_record;
-- drop sequence seq_chomp_vote_record_id;
create table chomp_vote_record (
    id number primary key,
    vote_id number not null,
    user_id varchar2(15),
    choice_id number not null
);
alter table chomp_vote_record
    add constraint const_chomp_vote_record_chomp_vote foreign key(vote_id)
    references chomp_vote(id) on delete cascade;
alter table chomp_vote_record
    add constraint cosnt_chomp_vote_record_users foreign key(user_id)
    references users(id) on delete set null;
alter table chomp_vote_record
    add constraint const_chomp_vote_record_chomp_vote_choice foreign key(choice_id)
    references chomp_vote_choice(id) on delete cascade;
create sequence seq_chomp_vote_record_id
    increment by 1
    start with 1
    minvalue 1
    nomaxvalue
    nocycle
    nocache;
commit;



-- CHOMP_MEDGAZINE 테이블
-- drop table chomp_megazine;
-- drop sequence seq_chomp_megazine_id;
create table chomp_megazine (
    id number primary key,
    postdate date default sysdate not null,
    content varchar2(1000) not null,
    chomp_id number not null
);
alter table chomp_megazine
    add constraint const_chomp_megazine_chomp foreign key(chomp_id)
    references chomp(id) on delete cascade;
create sequence seq_chomp_megazine_id
    increment by 1
    start with 1
    minvalue 1
    nomaxvalue
    nocycle
    nocache;
insert into chomp_megazine values (1, sysdate, '녹차아이스크림 최고!', 2);
insert into chomp_megazine values (2, sysdate, '이번 여름, 진한 녹차 아이스크림이 다시 인기몰이 중입니다. 성분과 맛 비교를 통해 당신의 입맛에 맞는 제품을 찾아보세요.', 5);
insert into chomp_megazine values (3, sysdate, '녹차의 깊은 풍미를 살린 브랜드 A의 아이스크림, 깔끔한 마무리가 인상적인 브랜드 B 등 4가지 제품을 비교해봤습니다.', 6);
insert into chomp_megazine values (4, sysdate, '더운 날씨에 잘 어울리는 매거진 특집! 녹차 디저트 레시피와 함께하는 아이스크림 추천도 놓치지 마세요.', 7);
insert into chomp_megazine values (5, sysdate, '비건 아이스크림 시장 확대 속에서 녹차 맛도 새로운 스타일로 출시되고 있습니다. 소비자 반응은?', 8);
insert into chomp_megazine values (6, sysdate, '당신의 냉동고에 들어갈 최고의 아이스크림은? 독자 투표와 함께하는 매거진 기획.', 9);
insert into chomp_megazine values (7, sysdate, '편의점에서 쉽게 만날 수 있는 녹차 아이스크림 3종, 가격과 맛 비교 분석!', 10);
insert into chomp_megazine values (8, sysdate, 'SNS에서 화제인 신상 아이스크림을 매거진 에디터들이 직접 먹어보고 평가했습니다.', 11);
insert into chomp_megazine values (9, sysdate, '냉동 스낵 시장의 다크호스, 프리미엄 녹차 아이스크림이 인기인 이유는?', 12);
commit;



-- CHOMP_EVENT 테이블
-- drop table chomp_event;
-- drop sequence seq_chomp_event_id;
create table chomp_event (
    id number primary key,
    opendate date not null,
    closedate date not null,
    content varchar2(1000) not null,
    chomp_id number not null
);
alter table chomp_event
    add constraint const_chomp_event_chomp foreign key(chomp_id)
    references chomp(id) on delete cascade;
create sequence seq_chomp_event_id
    increment by 1
    start with 1
    minvalue 1
    nomaxvalue
    nocycle
    nocache;
insert into chomp_event values (1, '25/01/01', '25/04/01', '3월 기간 한정', 3);
commit;













