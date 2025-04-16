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
drop table seq_comments_id;
drop table guide;
drop sequence seq_guide_id;

drop table recipe_step;
drop sequence seq_recipe_step_id;
drop table recipe_ingredient;
drop table sequence seq_recipe_ingredient_id;
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
    id varchar2(15) primary key,
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
INSERT INTO users VALUES ('harim', '$2a$10$//.68hv55MI4V28Xv87MKe/i3fFMuun6XnDcomuMXDfHC6RPYLSGy', '정하림', '아잠만', 'qazwsx9445@naver.com', null, 100, 200, 'ROLE_USER', 1);
INSERT INTO users VALUES ('dayeoung', '$2a$10$//.68hv55MI4V28Xv87MKe/i3fFMuun6XnDcomuMXDfHC6RPYLSGy', '부다영', '김뿌영', 'dyboo1347@gmail.com', null, 100, 200, 'ROLE_USER', 1);
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
-- drop table sequence seq_recipe_ingredient_id;
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
-- drop table seq_comments_id;
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













