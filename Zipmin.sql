-- 계정 생성
-- alter session set "_ORACLE_SCRIPT"=true;
-- create user zipmin identified by 1234;
-- grant connect, resource, unlimited tablespace to zipmin;



-- 테이블과 시퀀스 일괄 삭제
drop table event;
drop sequence seq_event_id;
drop table megazine;
drop sequence seq_megazine_id;
drop table vote_record;
drop sequence seq_vote_record_id;
drop table vote_choice;
drop sequence seq_vote_choice_id;
drop table vote;
drop sequence seq_vote_id;
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
drop table recipe_stock;
drop sequence seq_recipe_stock_id;
drop table recipe_category;
drop sequence seq_recipe_category_id;
drop table recipe;
drop sequence seq_recipe_id;

drop table user_account;
drop sequence seq_user_account_id;
drop table users;
drop sequence seq_user_id;



-- USERS 테이블
-- drop table users;
-- drop sequence seq_user_id;
create table users (
    id number primary key,
    username varchar2(50) unique not null,
    password varchar2(200),
    name varchar2(30) not null,
    nickname varchar2(100) not null,
    tel varchar2(15),
    email varchar2(50) not null,
    avatar varchar2(200),
    point number default 0,
    revenue number default 9,
    role varchar2(15) not null,
    enable number(1) default 1,
    provider varchar2(100),
    provider_id varchar2(100),
    refresh varchar2(1000),
    expiration varchar2(100)
);
create sequence seq_user_id
    increment by 1
    start with 1
    minvalue 1
    nomaxvalue
    nocycle
    nocache;
insert into users values (seq_user_id.NEXTVAL, 'admin', '$2a$10$B.Qdm3ECeo/PYL2P.0Sx/uuAznM0IkEBrQH6IX9vEn6cXaa4E/NOe', '관리자', '집밥의민족', '010-0000-0000', 'admin@gmail.com', null, 0, 0, 'ROLE_ADMIN', 1, null, null, null, null);
insert into users values (seq_user_id.NEXTVAL, 'harim', '$2a$10$B.Qdm3ECeo/PYL2P.0Sx/uuAznM0IkEBrQH6IX9vEn6cXaa4E/NOe', '정하림', '아잠만', '010-0000-0000', 'qazwsx9445@naver.com', null, 100, 200, 'ROLE_USER', 1, null, null, null, null);
insert into users values (seq_user_id.NEXTVAL, 'dayeoung', '$2a$10$B.Qdm3ECeo/PYL2P.0Sx/uuAznM0IkEBrQH6IX9vEn6cXaa4E/NOe', '부다영', '김뿌영', '010-0000-0000', 'dyboo1347@gmail.com', null, 100, 200, 'ROLE_USER', 1, null, null, null, null);
insert into users values (seq_user_id.NEXTVAL, 'user1', '$2a$10$B.Qdm3ECeo/PYL2P.0Sx/uuAznM0IkEBrQH6IX9vEn6cXaa4E/NOe', '사용자1', '사용자1', '010-0000-0000', 'user1@gmail.com', null, 100, 200, 'ROLE_USER', 1, null, null, null, null);
insert into users values (seq_user_id.NEXTVAL, 'user2', '$2a$10$B.Qdm3ECeo/PYL2P.0Sx/uuAznM0IkEBrQH6IX9vEn6cXaa4E/NOe', '사용자2', '사용자2', '010-0000-0000', 'user2@gmail.com', null, 100, 200, 'ROLE_USER', 1, null, null, null, null);
insert into users values (seq_user_id.NEXTVAL, 'user3', '$2a$10$B.Qdm3ECeo/PYL2P.0Sx/uuAznM0IkEBrQH6IX9vEn6cXaa4E/NOe', '사용자2', '사용자2', '010-0000-0000', 'user2@gmail.com', null, 100, 200, 'ROLE_USER', 1, null, null, null, null);
commit;



-- USER_ACCOUNT 테이블
-- drop table user_account;
-- drop sequence seq_user_account_id;
create table user_account (
    id number primary key,
    bank varchar2(100) not null,
    accountnum varchar2(30) not null,
    name varchar2(30) not null,
    user_id number not null
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
    cooklevel varchar2(30) not null,
    cooktime varchar2(30) not null,
    spicy varchar2(30) not null,
    portion varchar2(30) not null,
    tip varchar2(300),
    youtube_url varchar2(100),
    user_id number not null
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
    add constraint const_category_recipe foreign key(recipe_id)
    references recipe(id) on delete cascade;
create sequence seq_recipe_category_id
    increment by 1
    start with 1
    minvalue 1
    nomaxvalue
    nocycle
    nocache;
commit;



-- RECIPE_STOCK 테이블
-- drop table recipe_stock;
-- drop sequence seq_irecipe_stock_id;
create table recipe_stock (
    id number primary key,
    name varchar2(50) not null,
    amount number not null,
    unit varchar2(30) not null,
    note varchar2(300),
    recipe_id number not null
);
alter table recipe_stock
    add constraint const_stock_reicpe foreign key(recipe_id)
    references recipe(id) on delete cascade;
create sequence seq_recipe_stock_id
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
    add constraint const_step_recipe foreign key(recipe_id)
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
    comm_id number,
    postdate date default sysdate not null,
    content varchar2(2000) not null,
    tablename varchar2(100) not null,
    recodenum number not null,
    user_id number not null
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
insert into comments values (seq_comments_id.NEXTVAL, NULL, sysdate, '녹차 아이스크림은 배스킨라빈스가 최고입니다', 'megazine', 1, 2);
insert into comments values (seq_comments_id.NEXTVAL, 1, sysdate, '나뚜르가 최곤데 뭘 모르시네요', 'megazine', 1, 3);
insert into comments values (seq_comments_id.NEXTVAL, 1, sysdate, '그렇다는 증거 있나요?', 'megazine', 1, 2);
insert into comments values (seq_comments_id.NEXTVAL, NULL, sysdate, '녹차 아이스크림 불매합니다', 'megazine', 1, 2);
insert into comments values (seq_comments_id.NEXTVAL, NULL, sysdate, '요즘 더워서 아이스크림이 더 생각나네요~', 'megazine', 1, 3);
insert into comments values (seq_comments_id.NEXTVAL, NULL, sysdate, '전 녹차보다 딸기가 더 좋아요', 'megazine', 1, 4);
insert into comments values (seq_comments_id.NEXTVAL, NULL, sysdate, '다 좋아요~', 'megazine', 1, 5);
insert into comments values (seq_comments_id.NEXTVAL, NULL, sysdate, '하하', 'megazine', 1, 6);
insert into comments values (seq_comments_id.NEXTVAL, NULL, sysdate, '댓글 작성 테스트를 해볼게요', 'megazine', 1, 2);
insert into comments values (seq_comments_id.NEXTVAL, NULL, sysdate, '안녕하세요~', 'megazine', 1, 3);
insert into comments values (seq_comments_id.NEXTVAL, NULL, sysdate, '댓글은 처음 남겨보네요', 'megazine', 1, 2);
insert into comments values (seq_comments_id.NEXTVAL, NULL, sysdate, '댓글요', 'megazine', 1, 4);
insert into comments values (seq_comments_id.NEXTVAL, NULL, sysdate, '내공 냠냠', 'megazine', 1, 4);
insert into comments values (seq_comments_id.NEXTVAL, NULL, sysdate, '저도 이벤트 참여할래요!', 'event', 1, 4);
insert into comments values (seq_comments_id.NEXTVAL, NULL, sysdate, '이벤트 참여합니다', 'event', 1, 3);
insert into comments values (seq_comments_id.NEXTVAL, NULL, sysdate, '역시 배스킨라빈스네요', 'vote', 1, 2);
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
    user_id number not null
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
    user_id number,
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
insert into likes values (seq_likes_id.NEXTVAL, 2, 'comments', 1);
insert into likes values (seq_likes_id.NEXTVAL, 3, 'comments', 1);
insert into likes values (seq_likes_id.NEXTVAL, 2, 'comments', 4);
insert into likes values (seq_likes_id.NEXTVAL, 3, 'comments', 4);
insert into likes values (seq_likes_id.NEXTVAL, 4, 'comments', 4);
insert into likes values (seq_likes_id.NEXTVAL, 5, 'comments', 4);
insert into likes values (seq_likes_id.NEXTVAL, 6, 'comments', 4);
commit;



-- REPORT 테이블
-- drop table report;
-- drop sequence seq_report_id;
create table report (
    id number primary key,
    user_id number,
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
    category varchar2(30) not null
);
create sequence seq_chomp_id
    increment by 1
    start with 1
    minvalue 1
    nomaxvalue
    nocycle
    nocache;
insert into chomp values (seq_chomp_id.NEXTVAL, 'vote');
insert into chomp values (seq_chomp_id.NEXTVAL, 'megazine');
insert into chomp values (seq_chomp_id.NEXTVAL, 'event');
insert into chomp values (seq_chomp_id.NEXTVAL, 'vote');
insert into chomp values (seq_chomp_id.NEXTVAL, 'megazine');
insert into chomp values (seq_chomp_id.NEXTVAL, 'megazine');
insert into chomp values (seq_chomp_id.NEXTVAL, 'megazine');
insert into chomp values (seq_chomp_id.NEXTVAL, 'megazine');
insert into chomp values (seq_chomp_id.NEXTVAL, 'megazine');
insert into chomp values (seq_chomp_id.NEXTVAL, 'megazine');
insert into chomp values (seq_chomp_id.NEXTVAL, 'megazine');
insert into chomp values (seq_chomp_id.NEXTVAL, 'megazine');
insert into chomp values (seq_chomp_id.NEXTVAL, 'vote');
commit;



-- VOTE 테이블
-- drop table vote;
-- drop sequence seq_vote_id;
create table vote (
    id number primary key,
    title varchar2(100) not null,
    opendate date not null,
    closedate date not null,
    chomp_id number not null
);
alter table vote
    add constraint const_vote_chomp foreign key(chomp_id)
    references chomp(id) on delete cascade;
create sequence seq_vote_id
    increment by 1
    start with 1
    minvalue 1
    nomaxvalue
    nocycle
    nocache;
insert into vote values (seq_vote_id.NEXTVAL, '당신의 녹차 아이스크림에 투표하세요', TO_DATE('25/03/31', 'RR/MM/DD'), TO_DATE('25/07/31', 'RR/MM/DD'), 1);
insert into vote values (seq_vote_id.NEXTVAL, '가장 맛있는 딸기 아이스크림', TO_DATE('25/01/01', 'RR/MM/DD'), TO_DATE('25/02/03', 'RR/MM/DD'), 4);
insert into vote values (seq_vote_id.NEXTVAL, '여름에 가장 맛있는 과일', TO_DATE('25/01/01', 'RR/MM/DD'), TO_DATE('25/07/31', 'RR/MM/DD'), 13);
commit;



-- VOTE_CHOICE 테이블
-- drop table vote_choice;
-- drop sequence seq_vote_choice_id;
create table vote_choice (
    id number primary key,
    choice varchar2(100) not null,
    vote_id number not null
);
alter table vote_choice
    add constraint const_vote_choice_vote foreign key(vote_id)
    references vote(id) on delete cascade;
create sequence seq_vote_choice_id
    increment by 1
    start with 1
    minvalue 1
    nomaxvalue
    nocycle
    nocache;
insert into vote_choice values (seq_vote_choice_id.NEXTVAL, '배스킨라빈스 녹차 아이스크림', 1);
insert into vote_choice values (seq_vote_choice_id.NEXTVAL, '하겐다즈 녹차 아이스크림', 1);
insert into vote_choice values (seq_vote_choice_id.NEXTVAL, '나뚜르 녹차 아이스크림', 1);
insert into vote_choice values (seq_vote_choice_id.NEXTVAL, '배스킨라빈스 딸기 아이스크림', 2);
insert into vote_choice values (seq_vote_choice_id.NEXTVAL, '하겐다즈 딸기 아이스크림', 2);
insert into vote_choice values (seq_vote_choice_id.NEXTVAL, '나뚜르 딸기 아이스크림', 2);
insert into vote_choice values (seq_vote_choice_id.NEXTVAL, '수박', 3);
insert into vote_choice values (seq_vote_choice_id.NEXTVAL, '참외', 3);
insert into vote_choice values (seq_vote_choice_id.NEXTVAL, '복숭아', 3);
commit;



-- VOTE_RECORD 테이블
-- drop table vote_record;
-- drop sequence seq_vote_record_id;
create table vote_record (
    id number primary key,
    vote_id number not null,
    user_id number,
    choice_id number not null
);
alter table vote_record
    add constraint const_vote_record_vote foreign key(vote_id)
    references vote(id) on delete cascade;
alter table vote_record
    add constraint cosnt_vote_record_users foreign key(user_id)
    references users(id) on delete set null;
alter table vote_record
    add constraint const_vote_record_choice foreign key(choice_id)
    references vote_choice(id) on delete cascade;
create sequence seq_vote_record_id
    increment by 1
    start with 1
    minvalue 1
    nomaxvalue
    nocycle
    nocache;
-- insert into vote_record values (seq_vote_record_id.NEXTVAL, 1, 1, 1);
insert into vote_record values (seq_vote_record_id.NEXTVAL, 1, 2, 1);
insert into vote_record values (seq_vote_record_id.NEXTVAL, 1, 3, 2);
insert into vote_record values (seq_vote_record_id.NEXTVAL, 1, 4, 3);
insert into vote_record values (seq_vote_record_id.NEXTVAL, 1, 5, 3);
commit;



-- MEDGAZINE 테이블
-- drop table megazine;
-- drop sequence seq_megazine_id;
create table megazine (
    id number primary key,
    title varchar2(100) not null,
    postdate date default sysdate not null,
    content varchar2(1000) not null,
    chomp_id number not null
);
alter table megazine
    add constraint const_megazine_chomp foreign key(chomp_id)
    references chomp(id) on delete cascade;
create sequence seq_megazine_id
    increment by 1
    start with 1
    minvalue 1
    nomaxvalue
    nocycle
    nocache;
insert into megazine values (seq_megazine_id.NEXTVAL, '녹차아이스크림 최고!', sysdate, '녹차 아이스크림 4종 비교', 2);
insert into megazine values (seq_megazine_id.NEXTVAL, '여름을 강타한 녹차 아이스크림 트렌드', sysdate, '이번 여름, 진한 녹차 아이스크림이 다시 인기몰이 중입니다.
성분과 맛 비교를 통해 당신의 입맛에 맞는 제품을 찾아보세요.', 5);
insert into megazine values (seq_megazine_id.NEXTVAL, '녹차 아이스크림 4종 비교 분석', sysdate, '녹차의 깊은 풍미를 살린 브랜드 A의 아이스크림, 깔끔한 마무리가 인상적인 브랜드 B 등 4가지 제품을 비교해봤습니다.', 6);
insert into megazine values (seq_megazine_id.NEXTVAL, '디저트로 즐기는 녹차의 매력', sysdate, '더운 날씨에 잘 어울리는 매거진 특집! 녹차 디저트 레시피와 함께하는 아이스크림 추천도 놓치지 마세요.', 7);
insert into megazine values (seq_megazine_id.NEXTVAL, '비건 녹차 아이스크림의 부상', sysdate, '비건 아이스크림 시장 확대 속에서 녹차 맛도 새로운 스타일로 출시되고 있습니다. 소비자 반응은?', 8);
insert into megazine values (seq_megazine_id.NEXTVAL, '당신의 최애 아이스크림은?', sysdate, '당신의 냉동고에 들어갈 최고의 아이스크림은? 독자 투표와 함께하는 매거진 기획.', 9);
insert into megazine values (seq_megazine_id.NEXTVAL, '편의점 녹차 아이스크림 맛집 리스트', sysdate, '편의점에서 쉽게 만날 수 있는 녹차 아이스크림 3종, 가격과 맛 비교 분석!', 10);
insert into megazine values (seq_megazine_id.NEXTVAL, '에디터가 뽑은 신상 아이스크림 TOP3', sysdate, 'SNS에서 화제인 신상 아이스크림을 매거진 에디터들이 직접 먹어보고 평가했습니다.', 11);
insert into megazine values (seq_megazine_id.NEXTVAL, '프리미엄 녹차 아이스크림 열풍', sysdate, '냉동 스낵 시장의 다크호스, 프리미엄 녹차 아이스크림이 인기인 이유는?', 12);
commit;



-- EVENT 테이블
-- drop table event;
-- drop sequence seq_event_id;
create table event (
    id number primary key,
    title varchar2(100) not null,
    opendate date not null,
    closedate date not null,
    content varchar2(1000) not null,
    chomp_id number not null
);
alter table event
    add constraint const_event_chomp foreign key(chomp_id)
    references chomp(id) on delete cascade;
create sequence seq_event_id
    increment by 1
    start with 1
    minvalue 1
    nomaxvalue
    nocycle
    nocache;
insert into event values (seq_event_id.NEXTVAL, '3월 한정! 후원 포인트 이체 수수료 무료', TO_DATE('25/01/01', 'RR/MM/DD'), TO_DATE('25/08/01', 'RR/MM/DD'), '3월 기간 한정입니다.
많이 참여해주세요!!', 3);
commit;