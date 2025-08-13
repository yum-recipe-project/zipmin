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

drop table class_apply;
drop sequence seq_class_apply_id;
drop table class_target;
drop sequence seq_class_target_id;
drop table class_schedule;
drop sequence seq_class_schedule_id;
drop table class_tutor;
drop sequence seq_class_tutor_id;
drop table classes;
drop sequence seq_classes_id;

drop table report;
drop sequence seq_report_id;
drop table likes;
drop sequence seq_likes_id;

drop table fridge;
drop sequence seq_fridge_id;

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
    email varchar2(50),
    avatar varchar2(200),
    point number default 0,
    revenue number default 9,
    role varchar2(20) not null,
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





-- FRIDGE 테이블
-- drop table fridge;
-- drop sequence seq_fridge_id;
create table fridge (
    id number primary key,
    image varchar2(300),
    name varchar2(300) not null,
    amount number,
    unit varchar2(30),
    expdate date,
    category varchar2(50),
    user_id number not null
);
alter table fridge
    add constraint const_fridge_user foreign key(user_id)
    references users(id) on delete cascade;
create sequence seq_fridge_id
    increment by 1
    start with 1
    minvalue 1
    nomaxvalue
    nocycle
    nocache;
COMMIT;





-- RECIPE 테이블
-- drop table recipe;
-- drop sequence seq_recipe_id;
create table recipe (
    id number primary key,
    image varchar2(50) not null,
    title varchar2(200) not null,
    introduce varchar2(300) not null,
    cooklevel varchar2(30) not null,
    cooktime varchar2(30) not null,
    spicy varchar2(30) not null,
    portion varchar2(30) not null,
    tip varchar2(300),
    youtube varchar2(100),
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
    image varchar2(100),
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
commit;





-- MEDGAZINE 테이블
-- drop table megazine;
-- drop sequence seq_megazine_id;
create table megazine (
    id number primary key,
    title varchar2(100) not null,
    postdate date default sysdate,
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
COMMIT;





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
-- drop table classes;
-- drop sequence seq_classes_id;
create table classes (
    id number primary key,
    title varchar2(200) not null,
    image varchar2(200) not null,
    introduce varchar2(1000) not null,
    place varchar2(100) not null,
    eventdate date not null,
    starttime date not null,
    endtime date not null,
    noticedate date not null,
    headcount number not null,
    need varchar2(200) not null,
    approval number default 0,
    user_id number not null
);
alter table classes
    add constraint const_classes_users foreign key(user_id)
    references users(id) on delete cascade;
create sequence seq_classes_id
    increment by 1
    start with 1
    minvalue 1
    nomaxvalue
    nocycle
    nocache;
commit;





-- CLASS_TARGET 테이블
-- drop table class_target;
-- drop sequence seq_class_target_id;
create table class_target (
    id number primary key,
    content varchar2(100) not null,
    class_id number not null
);
alter table class_target
    add constraint const_target_classes foreign key(class_id)
    references classes(id) on delete cascade;
create sequence seq_class_target_id
    increment by 1
    start with 1
    minvalue 1
    nomaxvalue
    nocycle
    nocache;
commit;





-- CLASS_TUTOR 테이블
-- drop table class_tutor;
-- drop sequence seq_class_tutor_id;
create table class_tutor (
    id number primary key,
    image varchar2(300) not null,
    name varchar2(30) not null,
    career varchar2(100) not null,
    class_id number not null
);
alter table class_tutor
    add constraint const_tutor_classes foreign key(class_id)
    references classes(id) on delete cascade;
create sequence seq_class_tutor_id
    increment by 1
    start with 1
    minvalue 1
    nomaxvalue
    nocycle
    nocache;
commit;





-- CLASS_SCHEDULE 테이블
-- drop table class_schedule;
-- drop sequence seq_class_schedule_id;
create table class_schedule (
    id number primary key,
    starttime date not null,
    endtime date not null,
    title varchar2(100),
    class_id number not null
);
alter table class_schedule
    add constraint const_schedule_classes foreign key(class_id)
    references classes(id) on delete cascade;
create sequence seq_class_schedule_id
    increment by 1
    start with 1
    minvalue 1
    nomaxvalue
    nocycle
    nocache;
commit;





-- CLASS_APPLY 테이블
-- drop table class_apply;
-- drop sequence seq_class_apply_id;
create table class_apply (
    id number primary key,
    applydate date default sysdate,
    reason varchar2(1000) not null,
    question varchar2(1000),
    selected number(1) default 0,
    attend number(1) default 0,
    user_id number not null,
    class_id number not null
);
alter table class_apply
    add constraint const_apply_classes foreign key(class_id)
    references classes(id) on delete cascade;
alter table class_apply
    add constraint const_apply_users foreign key(user_id)
    references users(id) on delete cascade;
create sequence seq_class_apply_id
    increment by 1
    start with 1
    minvalue 1
    nomaxvalue
    nocycle
    nocache;
commit;





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
