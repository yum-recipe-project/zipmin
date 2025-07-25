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
INSERT INTO fridge VALUES (seq_fridge_id.NEXTVAL, '/images/fridge/chicken.png', '닭가슴살', 100, 'g', TO_DATE('25/07/20', 'RR/MM/DD'), '육류', 2);
INSERT INTO fridge VALUES (seq_fridge_id.NEXTVAL, '/images/fridge/cow.png', '소고기', 300, 'g', TO_DATE('25/07/22', 'RR/MM/DD'), '육류', 2);
INSERT INTO fridge VALUES (seq_fridge_id.NEXTVAL, '/images/fridge/pig.png', '돼지고기', 200, 'g', TO_DATE('25/07/18', 'RR/MM/DD'), '육류', 2);
INSERT INTO fridge VALUES (seq_fridge_id.NEXTVAL, '/images/fridge/grass.png', '상추', 1, '팩', TO_DATE('25/07/16', 'RR/MM/DD'), '채소류', 2);
INSERT INTO fridge VALUES (seq_fridge_id.NEXTVAL, '/images/fridge/carrot.png', '당근', 2, '개', TO_DATE('25/07/19', 'RR/MM/DD'), '채소류', 2);
INSERT INTO fridge VALUES (seq_fridge_id.NEXTVAL, '/images/fridge/onion.png', '양파', 1, '망', TO_DATE('25/07/21', 'RR/MM/DD'), '채소류', 2);
INSERT INTO fridge VALUES (seq_fridge_id.NEXTVAL, '/images/fridge/eggplant.png', '가지', 1, '개', TO_DATE('25/07/23', 'RR/MM/DD'), '채소류', 2);
INSERT INTO fridge VALUES (seq_fridge_id.NEXTVAL, '/images/fridge/ketchup.png', '케찹', 1, '병', TO_DATE('25/12/01', 'RR/MM/DD'), '소스류', 2);
INSERT INTO fridge VALUES (seq_fridge_id.NEXTVAL, '/images/fridge/soysauce.png', '간장', 1, '병', TO_DATE('25/10/10', 'RR/MM/DD'), '소스류', 2);
INSERT INTO fridge VALUES (seq_fridge_id.NEXTVAL, '/images/fridge/mayonnaise.png', '마요네즈', 1, '병', TO_DATE('25/11/15', 'RR/MM/DD'), '소스류', 2);


-- 소불고기와 일부 일치 (소고기, 간장 O / 양파 X)
INSERT INTO fridge VALUES (seq_fridge_id.NEXTVAL, '/images/fridge/cow.png', '소고기', 300, 'g', TO_DATE('25/07/20', 'RR/MM/DD'), '육류', 2);
INSERT INTO fridge VALUES (seq_fridge_id.NEXTVAL, '/images/fridge/soysauce.png', '간장', 100, 'ml', TO_DATE('25/10/10', 'RR/MM/DD'), '소스류', 2);

-- 쌈밥과 완전히 일치 (돼지고기, 상추, 밥 O)
INSERT INTO fridge VALUES (seq_fridge_id.NEXTVAL, '/images/fridge/pig.png', '돼지고기', 200, 'g', TO_DATE('25/07/22', 'RR/MM/DD'), '육류', 2);
INSERT INTO fridge VALUES (seq_fridge_id.NEXTVAL, '/images/fridge/grass.png', '상추', 1, '팩', TO_DATE('25/07/16', 'RR/MM/DD'), '채소류', 2);
INSERT INTO fridge VALUES (seq_fridge_id.NEXTVAL, null, '밥', 1, '공기', TO_DATE('25/07/15', 'RR/MM/DD'), '기타', 2); -- 이미지 없음

-- 마요네즈 비빔면과 일부 일치 (마요네즈 O / 불닭소스 X / 면 O)
INSERT INTO fridge VALUES (seq_fridge_id.NEXTVAL, '/images/fridge/mayonnaise.png', '마요네즈', 1, '병', TO_DATE('25/11/15', 'RR/MM/DD'), '소스류', 2);
INSERT INTO fridge VALUES (seq_fridge_id.NEXTVAL, null, '면', 1, '개', TO_DATE('25/07/17', 'RR/MM/DD'), '기타', 2); -- 이미지 없음

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
INSERT INTO recipe VALUES (seq_recipe_id.NEXTVAL, '/images/recipe/bulgogi.jpg', '소불고기', '달콤짭짤한 불고기 요리', '하', '30분 이내', '매움', '2인분', '고기 재울 때 키위나 배를 갈아 넣으면 부드러워져요!', null, 2);
INSERT INTO recipe VALUES (seq_recipe_id.NEXTVAL, '/images/recipe/wrap.jpg', '쌈밥', '야채와 함께 먹는 건강한 쌈밥', '중', '20분 이내', '보통', '1인분', '고기를 굽기 전 밑간하면 더 맛있어요.', null, 2);
INSERT INTO recipe VALUES (seq_recipe_id.NEXTVAL, '/images/recipe/mayomyeon.jpg', '마요네즈 비빔면 두줄까지는 워드랩 가능한지 테스트를 해볼게요', '매콤 고소한 초간단 비빔면', '아주 매움', '10분 이내', '상', '1인분', '불닭소스를 조절해서 맵기 조절 가능해요.', null, 2);
INSERT INTO recipe VALUES (seq_recipe_id.NEXTVAL, '/images/recipe/kimchi_fried_rice.jpg', '김치볶음밥', '집에 있는 재료로 간단하게!', '상', '15분 이내', '아주 매움', '1인분', '김치는 꼭 볶아서 써야 깊은 맛이 나요!', null, 2);
INSERT INTO recipe VALUES (seq_recipe_id.NEXTVAL, '/images/recipe/jjajang.jpg', '짜장면', '달콤 짭짤한 중식의 기본', '중', '30분 이내', '보통', '2인분', '춘장을 충분히 볶아야 맛이 살아나요.', null, 2);
INSERT INTO recipe VALUES (seq_recipe_id.NEXTVAL, '/images/recipe/dakgalbi.jpg', '닭갈비', '매콤한 맛의 대표 주자', '중', '40분 이내', '아주 매움', '3인분', '고구마를 넣으면 단맛이 더해져요.', null, 2);
INSERT INTO recipe VALUES (seq_recipe_id.NEXTVAL, '/images/recipe/eggtamago.jpg', '계란말이', '도시락 단골 반찬', '하', '10분 이내', '매움', '1인분', '육수나 우유를 조금 넣으면 부드러워져요.', null, 2);
INSERT INTO recipe VALUES (seq_recipe_id.NEXTVAL, '/images/recipe/tteokbokki.jpg', '떡볶이', '매콤달콤 국민 간식', '하', '20분 이내', '보통', '2인분', '고추장에 설탕 대신 올리고당을 써보세요.', null, 2);

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
-- 소불고기
INSERT INTO recipe_category VALUES (seq_recipe_category_id.NEXTVAL, '종류별', '메인요리', 1);
INSERT INTO recipe_category VALUES (seq_recipe_category_id.NEXTVAL, '재료별', '소고기', 1);

-- 쌈밥
INSERT INTO recipe_category VALUES (seq_recipe_category_id.NEXTVAL, '상황별', '도시락', 2);
INSERT INTO recipe_category VALUES (seq_recipe_category_id.NEXTVAL, '재료별', '돼지고기', 2);

-- 마요네즈 비빔면
INSERT INTO recipe_category VALUES (seq_recipe_category_id.NEXTVAL, '방법별', '비빔', 3);
INSERT INTO recipe_category VALUES (seq_recipe_category_id.NEXTVAL, '재료별', '소스', 3);
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
-- 소불고기
INSERT INTO recipe_stock VALUES (seq_recipe_stock_id.NEXTVAL, '소고기', 300, 'g', '불고기용', 1);
INSERT INTO recipe_stock VALUES (seq_recipe_stock_id.NEXTVAL, '간장', 50, 'ml', '양념용', 1);
INSERT INTO recipe_stock VALUES (seq_recipe_stock_id.NEXTVAL, '양파', 1, '개', null, 1);

-- 쌈밥
INSERT INTO recipe_stock VALUES (seq_recipe_stock_id.NEXTVAL, '돼지고기', 200, 'g', null, 2);
INSERT INTO recipe_stock VALUES (seq_recipe_stock_id.NEXTVAL, '상추', 5, '장', null, 2);
INSERT INTO recipe_stock VALUES (seq_recipe_stock_id.NEXTVAL, '밥', 1, '공기', null, 2);

-- 마요네즈 비빔면
INSERT INTO recipe_stock VALUES (seq_recipe_stock_id.NEXTVAL, '마요네즈', 2, '큰술', null, 3);
INSERT INTO recipe_stock VALUES (seq_recipe_stock_id.NEXTVAL, '불닭소스', 1, '큰술', null, 3);
INSERT INTO recipe_stock VALUES (seq_recipe_stock_id.NEXTVAL, '면', 1, '개', '라면사리', 3);
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
-- 소불고기
INSERT INTO recipe_step VALUES (seq_recipe_step_id.NEXTVAL, null, '소고기를 양념에 재운다.', 1);
INSERT INTO recipe_step VALUES (seq_recipe_step_id.NEXTVAL, null, '양파와 함께 볶는다.', 1);

-- 쌈밥
INSERT INTO recipe_step VALUES (seq_recipe_step_id.NEXTVAL, null, '돼지고기를 구워준다.', 2);
INSERT INTO recipe_step VALUES (seq_recipe_step_id.NEXTVAL, null, '상추에 밥과 고기를 싸서 먹는다.', 2);

-- 마요네즈 비빔면
INSERT INTO recipe_step VALUES (seq_recipe_step_id.NEXTVAL, null, '면을 삶아 찬물에 헹군다.', 3);
INSERT INTO recipe_step VALUES (seq_recipe_step_id.NEXTVAL, null, '소스 재료와 비빈다.', 3);
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
INSERT INTO guide (id, title, subtitle, category, postdate, content) VALUES
(seq_guide_id.nextval, '뿔 소라 손질법', '제주 바다향을 담은', 'preparation', sysdate, 
'뿔소라는 양식이 불가능해 오직 자연산으로만 채취 가능합니다. 제주 해녀들이 직접 잡은 자연산 뿔소라! 귀한 만큼 손질법을 제대로 알고 먹어야 더 맛있게 즐길 수 있습니다.');

INSERT INTO guide (id, title, subtitle, category, postdate, content) VALUES
(seq_guide_id.nextval, '해산물 신선하게 보관하는 법', '싱싱함을 오래 유지하는 비법', 'storage', sysdate, 
'구입한 해산물은 가능한 빨리 냉장 보관하며, 랩으로 밀폐하거나 진공포장 후 얼음과 함께 보관하는 것이 신선도를 오래 유지하는 비법입니다.');

INSERT INTO guide (id, title, subtitle, category, postdate, content) VALUES
(seq_guide_id.nextval, '해물탕 맛있게 끓이는 비법', '시원한 국물의 비결', 'cooking', sysdate, 
'해물탕을 끓일 때는 신선한 해산물과 함께 멸치 육수를 사용하고, 조개와 새우를 먼저 넣어 시원한 맛을 내는 것이 중요합니다.');

INSERT INTO guide (id, title, subtitle, category, postdate, content) VALUES
(seq_guide_id.nextval, '해산물 요리 시 주의사항', '안전하고 맛있게 즐기는 방법', 'etc', sysdate, 
'해산물은 알레르기 유발 가능성이 있으므로 주의가 필요하며, 조리 전 깨끗이 세척하고 신선도를 꼭 확인하는 것이 중요합니다.');

INSERT INTO guide (id, title, subtitle, category, postdate, content) VALUES
(seq_guide_id.nextval, '소고기 요리 시 주의사항', '안전하고 맛있게 즐기는 방법', 'etc', sysdate, 
'소고기는 알레르기 유발 가능성이 있으므로 주의가 필요하며, 조리 전 깨끗이 세척하고 신선도를 꼭 확인하는 것이 중요합니다.');

INSERT INTO guide (id, title, subtitle, category, postdate, content) VALUES
(seq_guide_id.nextval, '복어 요리 시 주의사항', '안전하고 맛있게 즐기는 방법', 'etc', sysdate, 
'복어는 알레르기 유발 가능성이 있으므로 주의가 필요하며, 조리 전 깨끗이 세척하고 신선도를 꼭 확인하는 것이 중요합니다.');

INSERT INTO guide (id, title, subtitle, category, postdate, content) VALUES
(seq_guide_id.nextval, '전복 손질법', '깨끗하게 다듬는 법', 'preparation', sysdate,
'전복은 껍데기에서 살을 조심히 분리하고, 솔로 깨끗이 문질러 이물질을 제거한 뒤 내장을 제거하고 조리합니다.');

INSERT INTO guide (id, title, subtitle, category, postdate, content) VALUES
(seq_guide_id.nextval, '굴 손질과 세척법', '비린내 없이 준비하기', 'preparation', sysdate,
'굴은 소금물에 2~3번 흔들어 씻고, 식초를 살짝 넣어 헹구면 비린내를 줄이고 깔끔하게 손질할 수 있습니다.');

INSERT INTO guide (id, title, subtitle, category, postdate, content) VALUES
(seq_guide_id.nextval, '생선 냉동 보관법', '신선도 유지하는 팁', 'storage', sysdate,
'생선은 흐르는 물에 씻은 후 물기를 제거하고, 한 마리씩 랩으로 싸서 냉동하면 선도를 오래 유지할 수 있습니다.');

INSERT INTO guide (id, title, subtitle, category, postdate, content) VALUES
(seq_guide_id.nextval, '조개류 보관법', '살아있는 조개 오래 보관하기', 'storage', sysdate,
'조개는 신문지에 싸서 냉장고에 보관하거나, 소금물에 담가 놓으면 하루 정도 더 살아있게 할 수 있습니다.');

INSERT INTO guide (id, title, subtitle, category, postdate, content) VALUES
(seq_guide_id.nextval, '문어 삶는 법', '부드럽게 익히는 노하우', 'cooking', sysdate,
'문어는 끓는 물에 식초와 소금을 약간 넣고 삶으며, 3분마다 꺼내어 식히기를 반복하면 부드러운 식감이 살아납니다.');

INSERT INTO guide (id, title, subtitle, category, postdate, content) VALUES
(seq_guide_id.nextval, '홍합탕 맛있게 끓이기', '국물 맛을 살리는 비법', 'cooking', sysdate,
'홍합은 깨끗이 손질한 뒤, 마늘과 파를 넣고 끓이면 시원한 국물 맛이 살아나며, 끓이기 전에 해감이 필수입니다.');

INSERT INTO guide (id, title, subtitle, category, postdate, content) VALUES
(seq_guide_id.nextval, '생선 조림 실패하지 않는 법', '양념이 잘 배게 만드는 비법', 'cooking', sysdate,
'생선 조림은 물을 적게 넣고, 중불에서 졸이며 양념을 끼얹는 방식으로 조리해야 간이 골고루 배입니다.');

INSERT INTO guide (id, title, subtitle, category, postdate, content) VALUES
(seq_guide_id.nextval, '알레르기 유발 해산물 리스트', '민감한 사람은 꼭 확인하세요', 'etc', sysdate,
'조개류, 새우, 오징어, 문어 등은 알레르기를 유발할 수 있으므로 주의가 필요하며, 섭취 전 반드시 식품 성분 확인이 필요합니다.');

INSERT INTO guide (id, title, subtitle, category, postdate, content) VALUES
(seq_guide_id.nextval, '냉동 해산물 해동법', '맛과 식감 살리는 해동 팁', 'storage', sysdate,
'냉동 해산물은 냉장 해동이 가장 좋으며, 찬물에 담가 천천히 해동하는 것도 효과적입니다. 전자레인지 해동은 권장하지 않습니다.');

INSERT INTO guide (id, title, subtitle, category, postdate, content) VALUES
(seq_guide_id.nextval, '갑각류 손질 시 주의사항', '손 다치지 않는 꿀팁', 'preparation', sysdate,
'꽃게나 새우 손질 시에는 등껍질을 제거할 때 칼 대신 주방가위를 사용하면 안전하게 작업할 수 있습니다.');

INSERT INTO guide (id, title, subtitle, category, postdate, content) VALUES
(seq_guide_id.nextval, '생선 손질법', '비린내 없이 손질하기', 'preparation', sysdate,
'생선을 손질할 때 소금으로 문지른 후 식초물에 헹구면 비린내를 줄일 수 있습니다.');

INSERT INTO guide (id, title, subtitle, category, postdate, content) VALUES
(seq_guide_id.nextval, '채소 보관 꿀팁', '오래 신선하게!', 'storage', sysdate,
'잎채소는 키친타월로 감싸 밀폐용기에 보관하면 수분 조절이 되어 신선함을 오래 유지할 수 있습니다.');

INSERT INTO guide (id, title, subtitle, category, postdate, content) VALUES
(seq_guide_id.nextval, '계란 보관법', '냉장고 온도에 주의', 'storage', sysdate,
'계란은 뾰족한 쪽이 아래로 가도록 보관해야 노른자가 중심을 유지하고 신선하게 보관할 수 있습니다.');

INSERT INTO guide (id, title, subtitle, category, postdate, content) VALUES
(seq_guide_id.nextval, '고기 해동 요령', '전자레인지보다는 냉장해동!', 'preparation', sysdate,
'고기는 냉장실에서 천천히 해동하는 것이 육즙 손실을 줄이고 식감도 유지할 수 있습니다.');

INSERT INTO guide (id, title, subtitle, category, postdate, content) VALUES
(seq_guide_id.nextval, '오일 종류별 특성', '조리용 오일 고르기', 'info', sysdate,
'올리브유는 중불 이하 요리에 적합하고, 해바라기유나 카놀라유는 고온 조리에 더 알맞습니다.');

INSERT INTO guide (id, title, subtitle, category, postdate, content) VALUES
(seq_guide_id.nextval, '스테인리스 팬 사용법', '달궈야 눌어붙지 않아요!', 'cooking', sysdate,
'스테인리스 팬은 충분히 예열한 후 기름을 둘러야 음식이 눌어붙지 않습니다.');

INSERT INTO guide (id, title, subtitle, category, postdate, content) VALUES
(seq_guide_id.nextval, '신선한 해산물 고르는 법', '눈으로 판단하세요', 'info', sysdate,
'해산물은 투명하고 윤기 있는 외관, 선명한 눈동자를 가진 것이 신선한 상태입니다.');

INSERT INTO guide (id, title, subtitle, category, postdate, content) VALUES
(seq_guide_id.nextval, '된장 보관법', '곰팡이 걱정 없이!', 'storage', sysdate,
'된장은 표면에 키친타월을 덮고 냉장 보관하면 곰팡이 발생을 줄일 수 있습니다.');

INSERT INTO guide (id, title, subtitle, category, postdate, content) VALUES
(seq_guide_id.nextval, '튀김 기름 온도 맞추기', '젓가락으로 확인!', 'cooking', sysdate,
'기름에 나무젓가락을 넣었을 때 기포가 빠르게 올라오면 적정 온도(약 170~180도)입니다.');

INSERT INTO guide (id, title, subtitle, category, postdate, content) VALUES
(seq_guide_id.nextval, '양파 눈물 안 나는 법', '냉장고에 잠깐 넣기', 'preparation', sysdate,
'양파를 자르기 전에 냉장고에 30분 정도 넣어두면 휘발성 물질이 줄어 눈물이 덜 납니다.');
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
-- 레시피 1번 리뷰
INSERT INTO review VALUES (1, SYSDATE, 5, '정말 맛있어요! 또 해먹을래요.', 1, 2);
INSERT INTO review VALUES (2, SYSDATE, 4, '조리도 쉽고 맛도 굿!', 1, 3);

-- 레시피 2번 리뷰
INSERT INTO review VALUES (3, SYSDATE, 3, '쏘쏘했어요. 무난한 맛.', 2, 4);

-- 레시피 3번 리뷰
INSERT INTO review VALUES (4, SYSDATE, 2, '제 입맛에는 좀 별로였어요.', 3, 3);
INSERT INTO review VALUES (5, SYSDATE, 4, '간단해서 좋고 맛도 괜찮아요.', 3, 5);

-- 레시피 5번 리뷰
INSERT INTO review VALUES (6, SYSDATE, 5, '가족들이 정말 좋아했어요!', 5, 2);
INSERT INTO review VALUES (7, SYSDATE, 4, '맛있고 재료도 간단해요.', 5, 3);
INSERT INTO review VALUES (8, SYSDATE, 5, '소스 조합이 예술입니다.', 5, 4);

-- 레시피 7번 리뷰
INSERT INTO review VALUES (9, SYSDATE, 3, '무난했어요. 한 번쯤 해볼만.', 7, 5);

-- 레시피 8번 리뷰
INSERT INTO review VALUES (10, SYSDATE, 4, '떡이 쫀득쫀득해서 좋았어요.', 8, 2);

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

INSERT INTO likes VALUES (seq_likes_id.NEXTVAL, 2, 'recipe', 1);
INSERT INTO likes VALUES (seq_likes_id.NEXTVAL, 3, 'recipe', 1);
INSERT INTO likes VALUES (seq_likes_id.NEXTVAL, 4, 'recipe', 1);
INSERT INTO likes VALUES (seq_likes_id.NEXTVAL, 2, 'recipe', 2);
INSERT INTO likes VALUES (seq_likes_id.NEXTVAL, 3, 'recipe', 3);
INSERT INTO likes VALUES (seq_likes_id.NEXTVAL, 4, 'recipe', 3);
INSERT INTO likes VALUES (seq_likes_id.NEXTVAL, 2, 'recipe', 5);
INSERT INTO likes VALUES (seq_likes_id.NEXTVAL, 3, 'recipe', 5);
INSERT INTO likes VALUES (seq_likes_id.NEXTVAL, 4, 'recipe', 5);
INSERT INTO likes VALUES (seq_likes_id.NEXTVAL, 5, 'recipe', 5);
INSERT INTO likes VALUES (seq_likes_id.NEXTVAL, 6, 'recipe', 6);
INSERT INTO likes VALUES (seq_likes_id.NEXTVAL, 3, 'recipe', 7);
INSERT INTO likes VALUES (seq_likes_id.NEXTVAL, 4, 'recipe', 7);
INSERT INTO likes VALUES (seq_likes_id.NEXTVAL, 2, 'recipe', 8);

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