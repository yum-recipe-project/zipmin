-- =======
-- 계정 생성
-- =======
-- ALTER SESSION SET "_ORACLE_SCRIPT"=true;
-- CREATE USER zipmin IDENTIFIED BY 1234;
-- GRANT CONNECT, RESOURCE, UNLIMITED TABLESPACE TO zipmin;





-- ===================
-- 테이블과 시퀀스 일괄 삭제
-- ===================
DROP TABLE withdraw;
DROP SEQUENCE seq_withdraw_id;
DROP TABLE fund;
DROP SEQUENCE seq_fund_id;
DROP TABLE vote_record;
DROP SEQUENCE seq_vote_record_id;
DROP TABLE vote_choice;
DROP SEQUENCE seq_vote_choice_id;
DROP TABLE chomp;
DROP SEQUENCE seq_chomp_id;
DROP TABLE class_apply;
DROP SEQUENCE seq_class_apply_id;
DROP TABLE class_target;
DROP SEQUENCE seq_class_target_id;
DROP TABLE class_schedule;
DROP SEQUENCE seq_class_schedule_id;
DROP TABLE class_tutor;
DROP SEQUENCE seq_class_tutor_id;
DROP TABLE classes;
DROP SEQUENCE seq_classes_id;
DROP TABLE report;
DROP SEQUENCE seq_report_id;
DROP TABLE likes;
DROP SEQUENCE seq_likes_id;
DROP TABLE fridge_memo;
DROP SEQUENCE seq_fridge_memo_id;
DROP TABLE user_fridge;
DROP SEQUENCE seq_user_fridge_id;
DROP TABLE fridge;
DROP SEQUENCE seq_fridge_id;
DROP TABLE review;
DROP SEQUENCE seq_review_id;
DROP TABLE comments;
DROP SEQUENCE seq_comments_id;
DROP TABLE guide;
DROP SEQUENCE seq_guide_id;
DROP TABLE recipe_step;
DROP SEQUENCE seq_recipe_step_id;
DROP TABLE recipe_stock;
DROP SEQUENCE seq_recipe_stock_id;
DROP TABLE recipe_category;
DROP SEQUENCE seq_recipe_category_id;
DROP TABLE recipe;
DROP SEQUENCE seq_recipe_id;
DROP TABLE password_token;
DROP SEQUENCE seq_password_token_id;
DROP TABLE user_account;
DROP SEQUENCE seq_user_account_id;
DROP TABLE users;
DROP SEQUENCE seq_user_id;





-- ===========
-- USERS 테이블
-- ===========
CREATE TABLE users (
    id NUMBER primary key,
    username VARCHAR2(50) unique NOT NULL,
    password VARCHAR2(200),
    name VARCHAR2(30) NOT NULL,
    nickname VARCHAR2(100) NOT NULL,
    tel VARCHAR2(15),
    email VARCHAR2(50),
    avatar VARCHAR2(200) DEFAULT '/images/user/user1.svg',
    introduce VARCHAR2(2000),
    link VARCHAR2(300),
    point NUMBER DEFAULT 0,
    revenue NUMBER DEFAULT 9,
    role VARCHAR2(20) NOT NULL,
    enable NUMBER(1) DEFAULT 1,
    provider VARCHAR2(100),
    provider_id VARCHAR2(100),
    refresh VARCHAR2(1000),
    expiration VARCHAR2(100)
);
CREATE SEQUENCE seq_user_id
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    NOMAXVALUE
    NOCYCLE
    NOCACHE;
COMMIT;





-- ==================
-- USER_ACCOUNT 테이블
-- ==================
CREATE TABLE user_account (
    id NUMBER primary key,
    bank VARCHAR2(100) NOT NULL,
    accountnum VARCHAR2(30) NOT NULL,
    name VARCHAR2(30) NOT NULL,
    user_id NUMBER NOT NULL
);
ALTER TABLE user_account
    ADD CONSTRAINT const_user_account_user FOREIGN KEY(user_id)
    REFERENCES users(id) ON DELETE CASCADE;
CREATE SEQUENCE seq_user_account_id
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    NOMAXVALUE
    NOCYCLE
    NOCACHE;
COMMIT;





-- ====================
-- PASSWORD_TOKEN 테이블
-- ====================
CREATE TABLE password_token (
    id NUMBER primary key,
    token VARCHAR2(64) NOT NULL,
    expires_at DATE NOT NULL,
    user_id NUMBER NOT NULL
);
ALTER TABLE password_token
    ADD CONSTRAINT const_password_token_user FOREIGN KEY(user_id)
    REFERENCES users(id) ON DELETE CASCADE;
CREATE SEQUENCE seq_password_token_id
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    NOMAXVALUE
    NOCYCLE
    NOCACHE;
COMMIT;





-- ============
-- FRIDGE 테이블
-- ============
CREATE TABLE fridge (
    id NUMBER primary key,
    image VARCHAR2(300),
    name VARCHAR2(300) NOT NULL,
    category VARCHAR2(50),
    zone VARCHAR2(300) NOT NULL,
    user_id NUMBER NOT NULL
);
ALTER TABLE fridge
    ADD CONSTRAINT const_fridge_user FOREIGN KEY(user_id)
    REFERENCES users(id) ON DELETE CASCADE;
CREATE SEQUENCE seq_fridge_id
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    NOMAXVALUE
    NOCYCLE
    NOCACHE;
COMMIT;





-- =================
-- USER_FRIDGE 테이블
-- =================
CREATE TABLE user_fridge (
    id NUMBER primary key,
    amount NUMBER,
    unit VARCHAR2(30),
    expdate date,
    fridge_id NUMBER NOT NULL,
    user_id NUMBER NOT NULL
);
ALTER TABLE user_fridge
    ADD CONSTRAINT const_user_fridge_fridge FOREIGN KEY(fridge_id)
    REFERENCES fridge(id) ON DELETE CASCADE;
ALTER TABLE user_fridge
    ADD CONSTRAINT const_user_fridge_users FOREIGN KEY(user_id)
    REFERENCES users(id) ON DELETE CASCADE;
CREATE SEQUENCE seq_user_fridge_id
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    NOMAXVALUE
    NOCYCLE
    NOCACHE;
COMMIT;





-- =================
-- FRIDGE_MEMO 테이블
-- =================
CREATE TABLE fridge_memo (
    id NUMBER primary key,
    name VARCHAR2(50) NOT NULL, 
    amount NUMBER,              
    unit VARCHAR2(30),          
    note VARCHAR2(300),         
    user_id NUMBER NOT NULL     
);
ALTER TABLE fridge_memo
    ADD CONSTRAINT const_fridge_memo_user FOREIGN KEY(user_id)
    REFERENCES users(id) ON DELETE CASCADE;
CREATE SEQUENCE seq_fridge_memo_id
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    NOCYCLE
    NOCACHE;
COMMIT;





-- ============
-- RECIPE 테이블
-- ============
CREATE TABLE recipe (
    id NUMBER primary key,
    image VARCHAR2(200) NOT NULL,
    title VARCHAR2(500) NOT NULL,
    introduce VARCHAR2(2000) NOT NULL,
    postdate DATE DEFAULT sysdate NOT NULL,
    cooklevel VARCHAR2(30) NOT NULL,
    cooktime VARCHAR2(30) NOT NULL,
    spicy VARCHAR2(30) NOT NULL,
    portion VARCHAR2(30) NOT NULL,
    tip VARCHAR2(300),
    user_id NUMBER NOT NULL
);
ALTER TABLE recipe
    ADD CONSTRAINT const_recipe_users FOREIGN KEY(user_id)
    REFERENCES users(id) ON DELETE CASCADE;
CREATE SEQUENCE seq_recipe_id
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    NOMAXVALUE
    NOCYCLE
    NOCACHE;
COMMIT;





-- =====================
-- RECIPE_CATEGORY 테이블
-- =====================
CREATE TABLE recipe_category (
    id NUMBER primary key,
    type VARCHAR2(15) NOT NULL,
    tag VARCHAR2(50) NOT NULL,
    recipe_id NUMBER NOT NULL
);
ALTER TABLE recipe_category
    ADD CONSTRAINT const_category_recipe FOREIGN KEY(recipe_id)
    REFERENCES recipe(id) ON DELETE CASCADE;
CREATE SEQUENCE seq_recipe_category_id
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    NOMAXVALUE
    NOCYCLE
    NOCACHE;
COMMIT;




-- ==================
-- RECIPE_STOCK 테이블
-- ==================
CREATE TABLE recipe_stock (
    id NUMBER primary key,
    name VARCHAR2(50) NOT NULL,
    amount NUMBER NOT NULL,
    unit VARCHAR2(30) NOT NULL,
    note VARCHAR2(300),
    recipe_id NUMBER NOT NULL
);
ALTER TABLE recipe_stock
    ADD CONSTRAINT const_stock_reicpe FOREIGN KEY(recipe_id)
    REFERENCES recipe(id) ON DELETE CASCADE;
CREATE SEQUENCE seq_recipe_stock_id
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    NOMAXVALUE
    NOCYCLE
    NOCACHE;
COMMIT;





-- =================
-- RECIPE_STEP 테이블
-- =================
CREATE TABLE recipe_step (
    id NUMBER primary key,
    image VARCHAR2(100),
    content VARCHAR2(2000) NOT NULL,
    recipe_id NUMBER
);
ALTER TABLE recipe_step
    ADD CONSTRAINT const_step_recipe FOREIGN KEY(recipe_id)
    REFERENCES recipe(id) ON DELETE CASCADE;
CREATE SEQUENCE seq_recipe_step_id
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    NOMAXVALUE
    NOCYCLE
    NOCACHE;
COMMIT;





-- ===========
-- GUIDE 테이블
-- ===========
CREATE TABLE guide (
    id NUMBER primary key,
    title VARCHAR2(300) NOT NULL,
    subtitle VARCHAR2(300) NOT NULL,
    category VARCHAR2(100) NOT NULL, -- preparation, storage, cooking, etc
    postdate DATE DEFAULT sysdate NOT NULL,
    content VARCHAR2(3000) NOT NULL,
    user_id NUMBER NOT NULL
);
ALTER TABLE guide
    ADD CONSTRAINT const_guide_users FOREIGN KEY(user_id)
    REFERENCES users(id) ON DELETE CASCADE;
CREATE SEQUENCE seq_guide_id
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    NOMAXVALUE
    NOCYCLE
    NOCACHE;
COMMIT;





-- CHOMP 테이블
CREATE TABLE chomp (
    id NUMBER primary key,
    title VARCHAR2(100) NOT NULL,
    opendate date,
    closedate DATE NOT NULL,
    content CLOB,
    image VARCHAR2(500),
    category VARCHAR2(30) NOT NULL,
    user_id NUMBER NOT NULL
);
ALTER TABLE chomp
    ADD CONSTRAINT const_chomp_user FOREIGN KEY(user_id)
    REFERENCES users(id) ON DELETE CASCADE;
CREATE SEQUENCE seq_chomp_id
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    NOMAXVALUE
    NOCYCLE
    NOCACHE;
COMMIT;





-- =================
-- VOTE_CHOICE 테이블
-- =================
CREATE TABLE vote_choice (
    id NUMBER primary key,
    choice VARCHAR2(100) NOT NULL,
    chomp_id NUMBER NOT NULL
);
ALTER TABLE vote_choice
    ADD CONSTRAINT const_vote_choice_chomp FOREIGN KEY(chomp_id)
    REFERENCES chomp(id) ON DELETE CASCADE;
CREATE SEQUENCE seq_vote_choice_id
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    NOMAXVALUE
    NOCYCLE
    NOCACHE;
COMMIT;





-- =================
-- VOTE_RECORD 테이블
-- =================
CREATE TABLE vote_record (
    id NUMBER primary key,
    chomp_id NUMBER NOT NULL,
    user_id NUMBER,
    choice_id NUMBER NOT NULL
);
ALTER TABLE vote_record
    ADD CONSTRAINT const_vote_record_chomp FOREIGN KEY(chomp_id)
    REFERENCES chomp(id) ON DELETE CASCADE;
ALTER TABLE vote_record
    ADD CONSTRAINT cosnt_vote_record_users FOREIGN KEY(user_id)
    REFERENCES users(id) on delete set null;
ALTER TABLE vote_record
    ADD CONSTRAINT const_vote_record_choice FOREIGN KEY(choice_id)
    REFERENCES vote_choice(id) ON DELETE CASCADE;
CREATE SEQUENCE seq_vote_record_id
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    NOMAXVALUE
    NOCYCLE
    NOCACHE;
COMMIT;





-- =============
-- COMMENT 테이블
-- =============
CREATE TABLE comments (
    id NUMBER primary key,
    comm_id NUMBER,
    postdate DATE DEFAULT sysdate NOT NULL,
    content VARCHAR2(2000) NOT NULL,
    tablename VARCHAR2(100) NOT NULL,
    recodenum NUMBER NOT NULL,
    user_id NUMBER NOT NULL
);
ALTER TABLE comments
    ADD CONSTRAINT const_comments_comments FOREIGN KEY(comm_id)
    REFERENCES comments(id) ON DELETE CASCADE;
ALTER TABLE comments
    ADD CONSTRAINT const_comments_user FOREIGN KEY(user_id)
    REFERENCES users(id) ON DELETE CASCADE;
CREATE SEQUENCE seq_comments_id
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    NOMAXVALUE
    NOCYCLE
    NOCACHE;
COMMIT;





-- ============
-- REVIEW 테이블
-- ============
CREATE TABLE review (
    id NUMBER primary key,
    postdate DATE DEFAULT sysdate NOT NULL,
    score NUMBER NOT NULL,
    content VARCHAR2(2000) NOT NULL,
    recipe_id NUMBER NOT NULL,
    user_id NUMBER NOT NULL
);
ALTER TABLE review
    ADD CONSTRAINT const_review_recipe FOREIGN KEY(recipe_id)
    REFERENCES recipe(id) ON DELETE CASCADE;
ALTER TABLE review
    ADD CONSTRAINT const_review_users FOREIGN KEY(user_id)
    REFERENCES users(id) ON DELETE CASCADE;
CREATE SEQUENCE seq_review_id
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    NOMAXVALUE
    NOCYCLE
    NOCACHE;
COMMIT;





-- ===========
-- LIKES 테이블
-- ===========
CREATE TABLE likes (
    id NUMBER primary key,
    user_id NUMBER,
    tablename VARCHAR2(15) NOT NULL,
    recodenum NUMBER NOT NULL
);
ALTER TABLE likes
    ADD CONSTRAINT const_likes_users FOREIGN KEY(user_id)
    REFERENCES users(id) on delete set null;
CREATE SEQUENCE seq_likes_id
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    NOMAXVALUE
    NOCYCLE
    NOCACHE;
COMMIT;





-- ============
-- REPORT 테이블
-- ============
CREATE TABLE report (
    id NUMBER primary key,
    user_id NUMBER,
    tablename VARCHAR2(15) NOT NULL,
    recodenum NUMBER NOT NULL,
    reason VARCHAR2(50) NOT NULL
);
ALTER TABLE report
    ADD CONSTRAINT const_report_users FOREIGN KEY(user_id)
    REFERENCES users(id) on delete set null;
CREATE SEQUENCE seq_report_id
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    NOMAXVALUE
    NOCYCLE
    NOCACHE;
COMMIT;





-- -- ==========
-- CLASSES 테이블
-- =============
CREATE TABLE classes (
    id NUMBER primary key,
    title VARCHAR2(200) NOT NULL,
    category VARCHAR2(50) NOT NULL,
    image VARCHAR2(200) NOT NULL,
    introduce VARCHAR2(1000) NOT NULL,
    place VARCHAR2(100) NOT NULL,
    postdate DATE DEFAULT sysdate NOT NULL,
    eventdate DATE NOT NULL,
    starttime DATE NOT NULL,
    endtime DATE NOT NULL,
    noticedate DATE NOT NULL,
    headcount NUMBER NOT NULL,
    need VARCHAR2(200) NOT NULL,
    approval NUMBER DEFAULT 2 NOT NULL,
    user_id NUMBER NOT NULL
);
ALTER TABLE classes
    ADD CONSTRAINT const_classes_users FOREIGN KEY(user_id)
    REFERENCES users(id) ON DELETE CASCADE;
CREATE SEQUENCE seq_classes_id
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    NOMAXVALUE
    NOCYCLE
    NOCACHE;
COMMIT;





-- ==================
-- CLASS_TARGET 테이블
-- ==================
CREATE TABLE class_target (
    id NUMBER primary key,
    content VARCHAR2(100) NOT NULL,
    class_id NUMBER NOT NULL
);
ALTER TABLE class_target
    ADD CONSTRAINT const_target_classes FOREIGN KEY(class_id)
    REFERENCES classes(id) ON DELETE CASCADE;
CREATE SEQUENCE seq_class_target_id
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    NOMAXVALUE
    NOCYCLE
    NOCACHE;
COMMIT;





-- =================
-- CLASS_TUTOR 테이블
-- =================
CREATE TABLE class_tutor (
    id NUMBER primary key,
    image VARCHAR2(300) NOT NULL,
    name VARCHAR2(30) NOT NULL,
    career VARCHAR2(1000) NOT NULL,
    class_id NUMBER NOT NULL
);
ALTER TABLE class_tutor
    ADD CONSTRAINT const_tutor_classes FOREIGN KEY(class_id)
    REFERENCES classes(id) ON DELETE CASCADE;
CREATE SEQUENCE seq_class_tutor_id
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    NOMAXVALUE
    NOCYCLE
    NOCACHE;
COMMIT;





-- ====================
-- CLASS_SCHEDULE 테이블
-- ====================
CREATE TABLE class_schedule (
    id NUMBER primary key,
    starttime DATE NOT NULL,
    endtime DATE NOT NULL,
    title VARCHAR2(100),
    class_id NUMBER NOT NULL
);
ALTER TABLE class_schedule
    ADD CONSTRAINT const_schedule_classes FOREIGN KEY(class_id)
    REFERENCES classes(id) ON DELETE CASCADE;
CREATE SEQUENCE seq_class_schedule_id
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    NOMAXVALUE
    NOCYCLE
    NOCACHE;
COMMIT;





-- =================
-- CLASS_APPLY 테이블
-- =================
CREATE TABLE class_apply (
    id NUMBER primary key,
    applydate DATE DEFAULT sysdate,
    reason VARCHAR2(1000) NOT NULL,
    question VARCHAR2(1000),
    selected NUMBER(1) DEFAULT 2 NOT NULL,
    attend NUMBER(1) DEFAULT 2 NOT NULL,
    user_id NUMBER NOT NULL,
    class_id NUMBER NOT NULL
);
ALTER TABLE class_apply
    ADD CONSTRAINT const_apply_classes FOREIGN KEY(class_id)
    REFERENCES classes(id) ON DELETE CASCADE;
ALTER TABLE class_apply
    ADD CONSTRAINT const_apply_users FOREIGN KEY(user_id)
    REFERENCES users(id) ON DELETE CASCADE;
CREATE SEQUENCE seq_class_apply_id
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    NOMAXVALUE
    NOCYCLE
    NOCACHE;
COMMIT;





-- ==========
-- FUND 테이블
-- ==========
CREATE TABLE fund (
    id NUMBER primary key,
    funder_id NUMBER,
    fundee_id NUMBER,
    recipe_id NUMBER,
    point NUMBER NOT NULL,
    funddate DATE DEFAULT sysdate NOT NULL
);
ALTER TABLE fund
    ADD CONSTRAINT const_fund_funder FOREIGN KEY(funder_id)
    REFERENCES users(id) on delete set null;
ALTER TABLE fund
    ADD CONSTRAINT const_fund_fundee FOREIGN KEY(fundee_id)
    REFERENCES users(id) on delete set null;
ALTER TABLE fund
    ADD CONSTRAINT const_fund_recipe FOREIGN KEY(recipe_id)
    REFERENCES recipe(id) on delete set null;
CREATE SEQUENCE seq_fund_id
	INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    NOCYCLE
    NOCACHE;
COMMIT;





-- ==============
-- WITHDRAW 테이블
-- ==============
CREATE TABLE withdraw (
    id NUMBER primary key,       
    user_id NUMBER NOT NULL,
    account_id NUMBER NOT NULL,
    point NUMBER NOT NULL,
    claimdate DATE DEFAULT SYSDATE NOT NULL,
    status NUMBER DEFAULT 0 NOT NULL,
    settledate DATE,
    admin_id NUMBER
);
ALTER TABLE withdraw 
    ADD CONSTRAINT const_withdraw_user FOREIGN KEY (user_id)
    REFERENCES users(id) ON DELETE CASCADE;
ALTER TABLE withdraw 
    ADD CONSTRAINT const_withdraw_account FOREIGN KEY (account_id)
    REFERENCES user_account(id) ON DELETE CASCADE;
ALTER TABLE withdraw
    ADD CONSTRAINT const_withdraw_admin FOREIGN KEY (admin_id)
    REFERENCES users(id) on delete set null;
CREATE SEQUENCE seq_withdraw_id
	INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    NOCYCLE
    NOCACHE;
COMMIT;
