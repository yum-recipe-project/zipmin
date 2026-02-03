-- =====================
-- CLASSES 테이블 더미데이터
-- =====================
INSERT INTO classes VALUES (seq_classes_id.NEXTVAL, '위스키 원데이 클래스',
  '음료', '8c1f5a2d9e7b4a60c3b0e6f49725d138.png',
  '위스키 최신 트렌드를 시작으로'
  || CHR(10) || '위스키에 대한 기본적인 지식은 물론'
  || CHR(10) || '위스키를 마시는 다양한 음용 방법과 함께'
  || CHR(10) || '위스키 칵테일(하이볼) 맛있게 만드는 꿀팁과'
  || CHR(10) || '음식과의 페어링 법칙까지 하루만에 배우실 수 있습니다.',
  '서울 송파구',
   SYSDATE-100, SYSDATE-80, DATE '1970-01-01'+10/24, DATE '1970-01-01'+13/24, SYSDATE-90,
   6, '1인 90,000원', 1, 1); -- 1

INSERT INTO classes VALUES (seq_classes_id.NEXTVAL, '양식 원데이 클래스',
  '양식', 'e4b7f6a2c91d5b3080af2c6d9e13b845.png',
  '파스타, 피자, 스테이크 등 대표적인 양식 메뉴를 직접 만들어보는 클래스입니다.'
  || CHR(10) || '소스 만드는 법부터 플레이팅까지 전 과정을 체험할 수 있습니다.'
  || CHR(10) || '집에서 재현하기 쉬운 레시피 위주로 구성되어 있습니다.'
  || CHR(10) || '연인, 친구와 함께 참여하기에도 좋은 클래스입니다.',
  '대한민국 서울특별시 강남구 도곡동',
  SYSDATE-50, SYSDATE-20, DATE '1970-01-01'+10/24, DATE '1970-01-01'+12/24, SYSDATE-25,
  20, '1인 55,000원', 1, 1); -- 2

INSERT INTO classes VALUES (seq_classes_id.NEXTVAL, '속은 촉촉 겉은 달콤한 마들렌',
  '제과제빵', '4902847e30435d15ad15204bed332bbb.png', 
  '수업은 간단한 이론 수업으로 시작하고'
  || CHR(10) || '계량부터 오븐에 넣어 굽는 것까지 모두 직접 만들어볼 수 있는 수업입니다!'
  || CHR(10) || '수강생 한분 한분 꼼꼼하게 밀착 지도하며 수업도 재미있는 분위기로 진행하니 부담 갖지 말고 오세요~'
  || CHR(10) || '레시피는 제공해드리며 집에서도 만들어 보실 수 있도록 설명해드릴게요!',
  '수원시 팔달구 권광로',
  SYSDATE-1, SYSDATE+15, DATE '1970-01-01'+10/24, DATE '1970-01-01'+12/24, SYSDATE+12,
  2, '1인 40,000원', 1, 4); -- 3

INSERT INTO classes VALUES (seq_classes_id.NEXTVAL, '수제 약과 만들기',
  '제과제빵', '7f6a2c1e9b4d5a38c0e4f21d8a6b3c90.png',
  '약과는 약켓팅이라는 신조어가 생길 정도로 요즘 엄청난 인기를 끌고 있죠'
  || CHR(10) || '이런 핫한 약과! 직접 만들어멱으면 어떨까요?'
  || CHR(10) || '회사 단체 동호회, 명절 선물, 아이들 간식으로도 좋은 약과!'
  || CHR(10) || '한국인 뿐만 아니라 외국인들의 마음까지 사로잡은 갓 만든 달달쫀득한 약과의 매력에 빠져보세요',
  '서울특별시 마포구 신공덕동',
  SYSDATE-1, SYSDATE+10, DATE '1970-01-01'+10/24, DATE '1970-01-01'+12/24, SYSDATE+7,
  10, '1인 150,000원', 1, 1); -- 4
  
INSERT INTO classes VALUES (seq_classes_id.NEXTVAL, '한식 원데이 클래스',
  '한식', '1c7e5f8a9b2d4e60a3f5c9d7b8e0412a.png',
  '집에서도 활용하기 좋은 한식 메뉴들을 함께 만들어보는 원데이 클래스입니다.'
  || CHR(10) || '떡갈비, 찜닭, 잡채 등 일상에서 자주 찾는 메뉴 위주로 구성되어 있습니다.'
  || CHR(10) || '조리 순서와 불 조절, 양념 비율까지 꼼꼼하게 알려드립니다.'
  || CHR(10) || '요리에 자신감이 없으신 분들도 편하게 참여하실 수 있어요.',
  '서울특별시 강남구 도곡동',
  SYSDATE-1, SYSDATE+8, DATE '1970-01-01'+10/24, DATE '1970-01-01'+12/24, SYSDATE+5,
  20, '1인 55,000원', 1, 1); -- 5

INSERT INTO classes VALUES (seq_classes_id.NEXTVAL, '커피는 나만의 스케치북! 라떼아트',
  '음료', '8a3f4c2d9e5b6170a4d1c9e2f6b8053d.png',
  '라떼아트 기초부터 차근차근 배우는 입문자용 클래스입니다.'
  || CHR(10) || '처음 에스프레소 머신을 접하시는 분들도 쉽게 따라오실 수 있어요.'
  || CHR(10) || '짧은 시간 안에 라떼아트의 재미를 느껴보실 수 있습니다.',
  '대한민국 서울특별시 서초구',
  SYSDATE-5, SYSDATE-1, DATE '1970-01-01'+10/24, DATE '1970-01-01'+12/24+30/1440, SYSDATE-3,
  2, '1인 60,000원', 1, 4); -- 6
  
INSERT INTO classes VALUES (seq_classes_id.NEXTVAL, '수제 과일타르트',
  '제과제빵', '3f8a2d9c4e7b5a016c9d2e8f4b1a0576.png',
  '타르트지부터 크림, 토핑까지 전 과정을 직접 만들어보는 클래스입니다.'
  || CHR(10) || '제철 과일을 활용해 보기에도 예쁘고 맛도 좋은 타르트를 완성합니다.'
  || CHR(10) || '기념일이나 파티 디저트로 활용하기 좋은 레시피입니다.'
  || CHR(10) || '완성한 타르트는 포장해 가져가실 수 있습니다.',
  '서울특별시 송파구 오금동',
  SYSDATE-50, SYSDATE-10, DATE '1970-01-01'+10/24, DATE '1970-01-01'+12/24+30/1440, SYSDATE-15,
  4, '1인 90,000원', 1, 4); -- 7

INSERT INTO classes VALUES (seq_classes_id.NEXTVAL, '카페 여름시즌 메뉴',
  '음료', '9d2f4a7c1e6b5c3084a0f3e8b2d91567.png',
  '원데이 음료 클래스를 모집합니다!'
  || CHR(10) || '이번 원데이 클래스는 여름맞이 음료클래스를 진행할 예정인데요'
  || CHR(10) || '봄, 여름 시즌에 활용하기 좋은 음료 레시피들을 소개해 드리려고 합니다'
  || CHR(10) || '매장에 바로 적용할 수 있는 레시피들로 트렌드에 맞는 다양한 시그니처 음료 레시피들로 준비했으니'
  || CHR(10) || '한층 빠르게 다가오는 여름을 함께 준비하실 분들은 놓치지 말고 참여해주세요!'
  || CHR(10) || CHR(10) || '초보자 분들도 쉽게 이해할 수 있는 수업으로'
  || CHR(10) || '집에서 홈카페를 즐기고자 하는 분들에게도 도움이 되는 수업입니다'
  || CHR(10) || '가장 성수기 시즌인 여름시즌을 보다 특별하게 해줄 매출 정복 메뉴!',
  '서울특별시 은평구 역촌동',
  SYSDATE-1, SYSDATE+10, DATE '1970-01-01'+10/24, DATE '1970-01-01'+12/24+30/1440, SYSDATE+7,
  4, '1인 100,000원', 1, 1); -- 8

INSERT INTO classes VALUES (seq_classes_id.NEXTVAL, '커피 드립백 클래스',
  '음료', '4b1a9f2c7e5d6a308c2e0f3d8b915764.png',
  '커피의 종류와 특징을 배우고, 자신이 원하는 원두를 골라'
  || CHR(10) || '세상에 하나뿐인 나만의 드립백을 만들어 볼 수 있어요'
  || CHR(10) || '정성스럽게 포장한 드립백은 소중한 사람에게 선물하기도 좋고'
  || CHR(10) || '바쁜 일상 속에서도 간편하게 내가 직접 만든 커피를 즐길 수 있어'
  || CHR(10) || '더욱 의미있는 클래스입니다',
  '충청남도 논산시 연산면',
  SYSDATE-1, SYSDATE+10, DATE '1970-01-01'+10/24, DATE '1970-01-01'+11/24+30/1440, SYSDATE+7,
  20, '1인 35,000원', 1, 1); -- 9

INSERT INTO classes VALUES (seq_classes_id.NEXTVAL, '강아지 케이크 원데이 클래스',
  '기타', '5c0e9a2d7b1f4836e4d8b3a6c97521f0.png',
  '반려견을 위한 건강한 케이크를 직접 만들어보는 원데이 클래스입니다.'
  || CHR(10) || '반려동물에게 안전한 재료만 사용해 안심하고 참여하실 수 있어요.'
  || CHR(10) || '특별한 날을 위한 맞춤 케이크를 만들어보세요.'
  || CHR(10) || '베이킹 경험이 없어도 충분히 참여 가능합니다.',
  '경기도 고양시 일산서구',
   SYSDATE-100, SYSDATE-50, DATE '1970-01-01'+10/24, DATE '1970-01-01'+11/24+30/1440, SYSDATE-55,
   5, '1인 50,000원', 1, 1); -- 10

INSERT INTO classes VALUES (seq_classes_id.NEXTVAL, '빈티지 크리스마스 레드벨벳 케이크',
  '제과제빵', 'f2b7a9c1d0e46385b4a5e8c397216d0f.png',
  '빈티지 스타일의 레드벨벳 케이크를 만들어보는 클래스입니다.'
  || CHR(10) || '크림 아이싱과 장식 기법을 중심으로 디자인 실습을 진행합니다.'
  || CHR(10) || '완성도 높은 케이크를 직접 만들어보실 수 있습니다.'
  || CHR(10) || '기념일 케이크 제작에 관심 있으신 분들께 추천드립니다.',
  '서울 용산구 보광동',
   SYSDATE-1, SYSDATE+10, DATE '1970-01-01'+10/24, DATE '1970-01-01'+12/24, SYSDATE+7,
   2, '1인 70,000원', 1, 1); -- 11

INSERT INTO classes VALUES (seq_classes_id.NEXTVAL, '막걸리 빚기 원데이 클래스',
  '음료', 'f3a9c2e7b5d84160c0e2f1d4b8a69735.png',
  '짧은 시간 안에 핵심만 배우고 직접 만들어보는 실습 중심 원데이 클래스입니다.'
  || CHR(10) || '기초 이론 설명 후 바로 실습에 들어가며, 처음 참여하시는 분들도 부담 없이 따라오실 수 있도록 구성되어 있습니다.',
  '광주 남구 임암동',
   SYSDATE-1, SYSDATE+50, DATE '1970-01-01'+10/24, DATE '1970-01-01'+11/24+30/1440, SYSDATE+40,
   10, '1인 55,000원', 1, 4); -- 12





-- ==========================
-- CLASS_TARGER 테이블 더미데이터
-- ==========================
INSERT INTO class_target VALUES (seq_class_target_id.NEXTVAL, '위스키에 대해서 알고 싶은 위린이', 1);
INSERT INTO class_target VALUES (seq_class_target_id.NEXTVAL, '특별한 데이트를 원하는 커플', 1);

INSERT INTO class_target VALUES (seq_class_target_id.NEXTVAL, '취미로 요리를 하고 싶으신 분', 2);
INSERT INTO class_target VALUES (seq_class_target_id.NEXTVAL, '요리를 전문적으로 배우고 싶으신 분', 2);

INSERT INTO class_target VALUES (seq_class_target_id.NEXTVAL, '직접 만든 선물을 하고 싶은 분', 3);
INSERT INTO class_target VALUES (seq_class_target_id.NEXTVAL, '밥먹고 카페가는 데이트 말고 다른 이색 데이트를 즐기고 싶은 연인', 3);
INSERT INTO class_target VALUES (seq_class_target_id.NEXTVAL, '새로운 취미생활을 찾아보고 싶은 분', 3);
INSERT INTO class_target VALUES (seq_class_target_id.NEXTVAL, '친구끼리 좋은 추억 남기고 싶으신 분', 3);

INSERT INTO class_target VALUES (seq_class_target_id.NEXTVAL, '정성이 들어간 특별한 선물을 하고 싶은 분', 4);
INSERT INTO class_target VALUES (seq_class_target_id.NEXTVAL, '이색체험을 원하시는 분', 4);
INSERT INTO class_target VALUES (seq_class_target_id.NEXTVAL, '한국의 문화를 경험해보고 싶으신 분', 4);
INSERT INTO class_target VALUES (seq_class_target_id.NEXTVAL, '1인 공방 창업을 하고 싶으신 분', 4);

INSERT INTO class_target VALUES (seq_class_target_id.NEXTVAL, '취미로 요리를 하고 싶으신 분', 5);
INSERT INTO class_target VALUES (seq_class_target_id.NEXTVAL, '요리를 전문적으로 배우고 싶으신 분', 5);

INSERT INTO class_target VALUES (seq_class_target_id.NEXTVAL, '라떼아트를 하고 싶은데 어떻게 해야 할지 모르시는 분', 6);
INSERT INTO class_target VALUES (seq_class_target_id.NEXTVAL, '바리스타 체험을 원하시는 분', 6);
INSERT INTO class_target VALUES (seq_class_target_id.NEXTVAL, '카페 아르바이트를 앞두신 분', 6);

INSERT INTO class_target VALUES (seq_class_target_id.NEXTVAL, '디저트를 내 손으로 만들어 보고 싶으신 분', 7);
INSERT INTO class_target VALUES (seq_class_target_id.NEXTVAL, '베이킹에 관심이 있으신 분', 7);
INSERT INTO class_target VALUES (seq_class_target_id.NEXTVAL, '커플끼리 친구끼리 베이킹으로 추억을 만들고 싶으신 분', 7);
INSERT INTO class_target VALUES (seq_class_target_id.NEXTVAL, '부모님이나 소중한 분께 내 손으로 만들어 선물하고 싶으신 분', 7);

INSERT INTO class_target VALUES (seq_class_target_id.NEXTVAL, '여름 시즌 메뉴가 고민이신 카페 사장님', 8);
INSERT INTO class_target VALUES (seq_class_target_id.NEXTVAL, '베이커리 전문 매장으로 음료 메뉴가 고민이신 사장님', 8);
INSERT INTO class_target VALUES (seq_class_target_id.NEXTVAL, '음료 제조 기술을 배우고 싶으신 예비 사장님', 8);
INSERT INTO class_target VALUES (seq_class_target_id.NEXTVAL, '나만의 홈카페 메뉴를 즐기고 싶으신 분', 8);

INSERT INTO class_target VALUES (seq_class_target_id.NEXTVAL, '색다른 커피 체럼을 즐기고 싶은 분', 9);
INSERT INTO class_target VALUES (seq_class_target_id.NEXTVAL, '핸드메이드 감성을 사랑하는 분', 9);
INSERT INTO class_target VALUES (seq_class_target_id.NEXTVAL, '예쁜 패키지에 감성을 담아 선물하고 싶은 분', 9);

INSERT INTO class_target VALUES (seq_class_target_id.NEXTVAL, '케이크를 만들어보고 싶은데 어려울까 걱정하셨던 분', 10);
INSERT INTO class_target VALUES (seq_class_target_id.NEXTVAL, '이번년도 우리 강아지 생일은 꼭 내가 만들어주고 싶은 분', 10);
INSERT INTO class_target VALUES (seq_class_target_id.NEXTVAL, '강아지랑 즐거운 추억 쌓고 싶은 분', 10);

INSERT INTO class_target VALUES (seq_class_target_id.NEXTVAL, '복잡한 과정 없이 바로 재밌는 단계를 원하시는 분', 11);

INSERT INTO class_target VALUES (seq_class_target_id.NEXTVAL, '술을 즐기시는 분', 12);
INSERT INTO class_target VALUES (seq_class_target_id.NEXTVAL, '밥먹고 카페가는 데이트 말고 다른 이색 데이트를 즐기고 싶은 연인', 12);




-- ==========================
-- CLASS_TUTOR 테이블 더미데이터
-- ==========================
INSERT INTO class_tutor VALUES (seq_class_tutor_id.NEXTVAL, 'a3f9c2e7b5d84160c0e2f1d4b8a69735.png', '집밥의민족',
  '실무 경험을 바탕으로 초보자도 이해하기 쉽게 설명하는 수업을 진행합니다, 즐겁고 편안한 분위기에서 요리와 만드는 즐거움을 느끼실 수 있도록 수업을 운영하고 있습니다', 10);

INSERT INTO class_tutor VALUES (seq_class_tutor_id.NEXTVAL, 'a3f9c2e7b5d84160c0e2f1d4b8a69735.png', '집밥의민족',
  '소스 만들기부터 플레이팅까지 양식 조리 전반을 쉽게 풀어 설명합니다, 요리가 처음이신 분들도 부담 없이 따라오실 수 있습니다', 2);

INSERT INTO class_tutor VALUES (seq_class_tutor_id.NEXTVAL, 'a4f7c2e9b5d84160c0e3f1d8b69725aa.png', '정하림',
  '베이킹 입문자를 위한 소규모 클래스 위주로 수업을 진행하고 있습니다, 계량부터 굽기까지 실패하지 않도록 옆에서 꼼꼼하게 도와드립니다', 3);

INSERT INTO class_tutor VALUES (seq_class_tutor_id.NEXTVAL, 'a3f9c2e7b5d84160c0e2f1d4b8a69735.png', '집밥의민족',
  '전통 디저트를 현대적인 방식으로 쉽게 풀어 설명하는 수업을 진행합니다, 선물용 포장과 보관 방법까지 함께 알려드립니다', 4);

INSERT INTO class_tutor VALUES (seq_class_tutor_id.NEXTVAL, 'a3f9c2e7b5d84160c0e2f1d4b8a69735.png', '집밥의민족',
  '집에서 바로 활용할 수 있는 한식 레시피 위주로 수업을 구성하고 있습니다, 불 조절과 양념 비율까지 이해하기 쉽게 설명해드립니다', 5);

INSERT INTO class_tutor VALUES (seq_class_tutor_id.NEXTVAL, 'a4f7c2e9b5d84160c0e3f1d8b69725aa.png', '정하림',
  '카페 실무 경험을 바탕으로 우유 스티밍과 기본 패턴 위주로 집중 지도합니다, 단시간에 감을 잡을 수 있도록 실습 위주로 진행합니다', 6);

INSERT INTO class_tutor VALUES (seq_class_tutor_id.NEXTVAL, 'a4f7c2e9b5d84160c0e3f1d8b69725aa.png', '정하림',
  '타르트지 성형부터 크림 제조, 과일 데코까지 단계별로 알려드립니다, 완성도 높은 결과물이 나오도록 개별 피드백을 제공합니다', 7);

INSERT INTO class_tutor VALUES (seq_class_tutor_id.NEXTVAL, 'a3f9c2e7b5d84160c0e2f1d4b8a69735.png', '집밥의민족',
  '카페 운영 경험을 바탕으로 매장에 바로 적용 가능한 메뉴 구성과 레시피를 공유합니다, 원가와 작업 동선까지 함께 고려한 수업입니다', 8);

INSERT INTO class_tutor VALUES (seq_class_tutor_id.NEXTVAL, 'a3f9c2e7b5d84160c0e2f1d4b8a69735.png', '집밥의민족',
  '원두 선택과 분쇄도 조절부터 포장까지 전 과정을 직접 체험할 수 있도록 구성된 클래스입니다, 커피 입문자분들께 특히 추천드립니다', 9);

INSERT INTO class_tutor VALUES (seq_class_tutor_id.NEXTVAL, 'a3f9c2e7b5d84160c0e2f1d4b8a69735.png', '집밥의민족',
  '반려동물 전용 재료만 사용하여 안전하게 만드는 방법을 알려드립니다, 반려견 생일이나 기념일을 위한 케이크 제작을 도와드립니다', 10);

INSERT INTO class_tutor VALUES (seq_class_tutor_id.NEXTVAL, 'a3f9c2e7b5d84160c0e2f1d4b8a69735.png', '집밥의민족',
  '케이크 아이싱과 장식 기법을 중심으로 디자인 완성도를 높이는 수업을 진행합니다, 베이킹 경험이 없어도 참여하실 수 있습니다', 11);

INSERT INTO class_tutor VALUES (seq_class_tutor_id.NEXTVAL, 'a4f7c2e9b5d84160c0e3f1d8b69725aa.png', '정하림',
  '지역 양조장 협업 및 전통주 교육 경험을 바탕으로 발효 원리와 제조 과정을 쉽게 설명합니다, 단순히 만드는 법뿐 아니라 맛의 차이가 나는 이유와 실패하지 않는 관리 방법까지 함께 알려드립니다', 12);





-- ============================
-- CLASS_SCHEDULE 테이블 더미데이터
-- ============================
INSERT INTO class_schedule VALUES (seq_class_schedule_id.NEXTVAL, DATE '1970-01-01'+10/24,
  DATE '1970-01-01'+13/24, '위스키에 대한 기본적인 지식', 1);
INSERT INTO class_schedule VALUES (seq_class_schedule_id.NEXTVAL, DATE '1970-01-01'+10/24,
  DATE '1970-01-01'+13/24, '위스키 선택 요령', 1);
INSERT INTO class_schedule VALUES (seq_class_schedule_id.NEXTVAL, DATE '1970-01-01'+10/24,
  DATE '1970-01-01'+13/24, '위스키와 음식의 페어링 법칙', 1);
INSERT INTO class_schedule VALUES (seq_class_schedule_id.NEXTVAL, DATE '1970-01-01'+10/24,
  DATE '1970-01-01'+13/24, '위스키 보관 요령', 1);
INSERT INTO class_schedule VALUES (seq_class_schedule_id.NEXTVAL, DATE '1970-01-01'+10/24,
  DATE '1970-01-01'+13/24, '위스키 시음', 1);
  
INSERT INTO class_schedule VALUES (seq_class_schedule_id.NEXTVAL, DATE '1970-01-01'+10/24,
  DATE '1970-01-01'+12/24, '커리큘럼 소개', 2);
INSERT INTO class_schedule VALUES (seq_class_schedule_id.NEXTVAL, DATE '1970-01-01'+10/24,
  DATE '1970-01-01'+12/24, '이론 설명', 2);
INSERT INTO class_schedule VALUES (seq_class_schedule_id.NEXTVAL, DATE '1970-01-01'+10/24,
  DATE '1970-01-01'+12/24, '실전 (각 요리별로 상이)', 2);
  
INSERT INTO class_schedule VALUES (seq_class_schedule_id.NEXTVAL, DATE '1970-01-01'+10/24,
  DATE '1970-01-01'+12/24, '반죽', 3);
INSERT INTO class_schedule VALUES (seq_class_schedule_id.NEXTVAL, DATE '1970-01-01'+10/24,
  DATE '1970-01-01'+12/24, '오븐에 굽기', 3);
INSERT INTO class_schedule VALUES (seq_class_schedule_id.NEXTVAL, DATE '1970-01-01'+10/24,
  DATE '1970-01-01'+12/24, '글레이즈드와 초콜릿 중탕', 3);
INSERT INTO class_schedule VALUES (seq_class_schedule_id.NEXTVAL, DATE '1970-01-01'+10/24,
  DATE '1970-01-01'+12/24, '마들렌 완성 및 포장', 3);

INSERT INTO class_schedule VALUES (seq_class_schedule_id.NEXTVAL, DATE '1970-01-01'+10/24,
  DATE '1970-01-01'+12/24, '약과틀을 이용해 약과의 모양 만들기', 4);
INSERT INTO class_schedule VALUES (seq_class_schedule_id.NEXTVAL, DATE '1970-01-01'+10/24,
  DATE '1970-01-01'+12/24, '만들어진 약과를 튀기기', 4);
INSERT INTO class_schedule VALUES (seq_class_schedule_id.NEXTVAL, DATE '1970-01-01'+10/24,
  DATE '1970-01-01'+12/24, '약과를 집청에 묻히기', 4);
INSERT INTO class_schedule VALUES (seq_class_schedule_id.NEXTVAL, DATE '1970-01-01'+10/24,
  DATE '1970-01-01'+12/24, '집청이 묻은 약과를 굳히기', 4);

INSERT INTO class_schedule VALUES (seq_class_schedule_id.NEXTVAL, DATE '1970-01-01'+10/24,
  DATE '1970-01-01'+12/24, '커리큘럼 소개', 5);
INSERT INTO class_schedule VALUES (seq_class_schedule_id.NEXTVAL, DATE '1970-01-01'+10/24,
  DATE '1970-01-01'+12/24, '이론 설명', 5);
INSERT INTO class_schedule VALUES (seq_class_schedule_id.NEXTVAL, DATE '1970-01-01'+10/24,
  DATE '1970-01-01'+12/24, '실전 (각 요리별로 상이)', 5);

INSERT INTO class_schedule VALUES (seq_class_schedule_id.NEXTVAL, DATE '1970-01-01'+10/24,
  DATE '1970-01-01'+12/24+30/1440, '스티밍 배우기 (우유 거품 내기)', 6);
INSERT INTO class_schedule VALUES (seq_class_schedule_id.NEXTVAL, DATE '1970-01-01'+10/24,
  DATE '1970-01-01'+12/24+30/1440, '안정화 연습 (커피와 우유 섞기)', 6);
INSERT INTO class_schedule VALUES (seq_class_schedule_id.NEXTVAL, DATE '1970-01-01'+10/24,
  DATE '1970-01-01'+12/24+30/1440, '몽키헤드 띄우기 (꼬리 없는 하트)', 6);
INSERT INTO class_schedule VALUES (seq_class_schedule_id.NEXTVAL, DATE '1970-01-01'+10/24,
  DATE '1970-01-01'+12/24+30/1440, '면하트 띄우기 (무결 하트)', 6);

INSERT INTO class_schedule VALUES (seq_class_schedule_id.NEXTVAL, DATE '1970-01-01'+10/24,
  DATE '1970-01-01'+12/24+30/1440, '타르트를 오븐에 굽기', 7);
INSERT INTO class_schedule VALUES (seq_class_schedule_id.NEXTVAL, DATE '1970-01-01'+10/24,
  DATE '1970-01-01'+12/24+30/1440, '아몬드크림을 반죽하기', 7);
INSERT INTO class_schedule VALUES (seq_class_schedule_id.NEXTVAL, DATE '1970-01-01'+10/24,
  DATE '1970-01-01'+12/24+30/1440, '생크림 휘핑', 7);
INSERT INTO class_schedule VALUES (seq_class_schedule_id.NEXTVAL, DATE '1970-01-01'+10/24,
  DATE '1970-01-01'+12/24+30/1440, '과일 얹기', 7);
INSERT INTO class_schedule VALUES (seq_class_schedule_id.NEXTVAL, DATE '1970-01-01'+10/24,
  DATE '1970-01-01'+12/24+30/1440, '박스 포장', 7);
  
INSERT INTO class_schedule VALUES (seq_class_schedule_id.NEXTVAL, DATE '1970-01-01'+10/24,
  DATE '1970-01-01'+12/24+30/1440, '다양한 과일을 활용한 시그니처 메뉴 레시피', 8);
INSERT INTO class_schedule VALUES (seq_class_schedule_id.NEXTVAL, DATE '1970-01-01'+10/24,
  DATE '1970-01-01'+12/24+30/1440, '티를 활용한 색감 좋은 음료 레시피', 8);
INSERT INTO class_schedule VALUES (seq_class_schedule_id.NEXTVAL, DATE '1970-01-01'+10/24,
  DATE '1970-01-01'+12/24+30/1440, '초보자도 쉽게 만들 수 있는 목테일 음료 레시피', 8);
INSERT INTO class_schedule VALUES (seq_class_schedule_id.NEXTVAL, DATE '1970-01-01'+10/24,
  DATE '1970-01-01'+12/24+30/1440, '다양한 원료에 대한 이해와 활용 방법', 8);

INSERT INTO class_schedule VALUES (seq_class_schedule_id.NEXTVAL, DATE '1970-01-01'+10/24,
  DATE '1970-01-01'+11/24+30/1440, '생두, 로스팅, 드립백까지 커피의 여정', 9);
INSERT INTO class_schedule VALUES (seq_class_schedule_id.NEXTVAL, DATE '1970-01-01'+10/24,
  DATE '1970-01-01'+11/24+30/1440, '나만의 커피 취향 알아보기', 9);
INSERT INTO class_schedule VALUES (seq_class_schedule_id.NEXTVAL, DATE '1970-01-01'+10/24,
  DATE '1970-01-01'+11/24+30/1440, '실습: 내 손으로 만드는 나만의 드립백!', 9);
INSERT INTO class_schedule VALUES (seq_class_schedule_id.NEXTVAL, DATE '1970-01-01'+10/24,
  DATE '1970-01-01'+11/24+30/1440, '손글씨 태그 만들기', 9);
INSERT INTO class_schedule VALUES (seq_class_schedule_id.NEXTVAL, DATE '1970-01-01'+10/24,
  DATE '1970-01-01'+11/24+30/1440, '내가 만든 드립백으로 함께 커피 내려보기', 9);

INSERT INTO class_schedule VALUES (seq_class_schedule_id.NEXTVAL, DATE '1970-01-01'+10/24,
  DATE '1970-01-01'+11/24+30/1440, '아이스브레이킹: 댕댕이 인사 및 소개', 10);
INSERT INTO class_schedule VALUES (seq_class_schedule_id.NEXTVAL, DATE '1970-01-01'+10/24,
  DATE '1970-01-01'+11/24+30/1440, '사용되는 재료와 영양 간단 소개', 10);
INSERT INTO class_schedule VALUES (seq_class_schedule_id.NEXTVAL, DATE '1970-01-01'+10/24,
  DATE '1970-01-01'+11/24+30/1440, '케이크 만들기', 10);
INSERT INTO class_schedule VALUES (seq_class_schedule_id.NEXTVAL, DATE '1970-01-01'+10/24,
  DATE '1970-01-01'+11/24+30/1440, '포장과 사진', 10);

INSERT INTO class_schedule VALUES (seq_class_schedule_id.NEXTVAL, DATE '1970-01-01'+10/24,
  DATE '1970-01-01'+12/24, '휘핑크림 제조', 11);
INSERT INTO class_schedule VALUES (seq_class_schedule_id.NEXTVAL, DATE '1970-01-01'+10/24,
  DATE '1970-01-01'+12/24, '크림 샌딩 및 아이싱', 11);
INSERT INTO class_schedule VALUES (seq_class_schedule_id.NEXTVAL, DATE '1970-01-01'+10/24,
  DATE '1970-01-01'+12/24, '케이크 꾸미기', 11);
INSERT INTO class_schedule VALUES (seq_class_schedule_id.NEXTVAL, DATE '1970-01-01'+10/24,
  DATE '1970-01-01'+12/24, '케이크 포장', 11);

INSERT INTO class_schedule VALUES (seq_class_schedule_id.NEXTVAL, DATE '1970-01-01'+10/24,
  DATE '1970-01-01'+11/24+30/1440, '시음회', 12);
INSERT INTO class_schedule VALUES (seq_class_schedule_id.NEXTVAL, DATE '1970-01-01'+10/24,
  DATE '1970-01-01'+11/24+30/1440, '고두밥 식히기', 12);
INSERT INTO class_schedule VALUES (seq_class_schedule_id.NEXTVAL, DATE '1970-01-01'+10/24,
  DATE '1970-01-01'+11/24+30/1440, '혼화 (치대기)', 12);
INSERT INTO class_schedule VALUES (seq_class_schedule_id.NEXTVAL, DATE '1970-01-01'+10/24,
  DATE '1970-01-01'+11/24+30/1440, '입항', 12);
INSERT INTO class_schedule VALUES (seq_class_schedule_id.NEXTVAL, DATE '1970-01-01'+10/24,
  DATE '1970-01-01'+11/24+30/1440, '기념사진 및 마무리', 12);





-- =========================
-- CLASS_APPLY 테이블 더미데이터
-- =========================
INSERT INTO class_apply VALUES (seq_class_apply_id.NEXTVAL, SYSDATE-93, '위스키 입문이라 꼭 배우고 싶습니다.', '개인 컵이나 도구를 따로 준비해야 하나요?', 1, 0, 4, 1);
INSERT INTO class_apply VALUES (seq_class_apply_id.NEXTVAL, SYSDATE-95, '하이볼 만들어보고 싶어요.', '알코올 도수는 어느 정도인가요?', 0, 2, 6, 1);
INSERT INTO class_apply VALUES (seq_class_apply_id.NEXTVAL, SYSDATE-94, '위스키 페어링이 궁금합니다.', '안주도 제공되나요?', 2, 2, 7, 1);

INSERT INTO class_apply VALUES (seq_class_apply_id.NEXTVAL, SYSDATE-40, '요리 기초부터 배우고 싶어요.', '앞치마는 제공되나요?', 1, 1, 8, 2);
INSERT INTO class_apply VALUES (seq_class_apply_id.NEXTVAL, SYSDATE-39, '연인과 같이 참여하고 싶습니다.', '2명이 같이 조리하나요?', 1, 1, 9, 2);
INSERT INTO class_apply VALUES (seq_class_apply_id.NEXTVAL, SYSDATE-40, '양식 요리에 관심 많아요.', '칼 사용이 많나요? 초보도 가능한가요?', 0, 2, 10, 2);

INSERT INTO class_apply VALUES (seq_class_apply_id.NEXTVAL, SYSDATE, '베이킹 처음인데 참여해보고 싶어요.', '오븐 사용법도 알려주시나요?', 1, 2, 5, 3);
INSERT INTO class_apply VALUES (seq_class_apply_id.NEXTVAL, SYSDATE, '디저트 만들기 좋아합니다.', '포장 박스도 제공되나요?', 2, 2, 6, 3);

INSERT INTO class_apply VALUES (seq_class_apply_id.NEXTVAL, SYSDATE, '약과 너무 좋아해서 신청합니다.', '아이도 함께 참여 가능한가요?', 1, 2, 7, 4);
INSERT INTO class_apply VALUES (seq_class_apply_id.NEXTVAL, SYSDATE, '선물용으로 만들고 싶어요.', '개별 포장까지 가능한가요?', 2, 2, 8, 4);
INSERT INTO class_apply VALUES (seq_class_apply_id.NEXTVAL, SYSDATE, '아이와 같이 참여하고 싶어요.', '연령 제한이 있나요?', 0, 2, 9, 4);

INSERT INTO class_apply VALUES (seq_class_apply_id.NEXTVAL, SYSDATE, '집밥 실력 늘리고 싶습니다.', '재료 손질부터 하나요?', 1, 2, 10, 5);
INSERT INTO class_apply VALUES (seq_class_apply_id.NEXTVAL, SYSDATE, '요리 초보라 도움 받고 싶어요.', '매운 음식이 포함되어 있나요?', 2, 2, 11, 5);

INSERT INTO class_apply VALUES (seq_class_apply_id.NEXTVAL, SYSDATE-4, '라떼아트 배우고 싶어요.', '머신 사용법도 같이 알려주시나요?', 1, 1, 5, 6);
INSERT INTO class_apply VALUES (seq_class_apply_id.NEXTVAL, SYSDATE-4, '카페 취업 준비 중입니다.', '취업 포트폴리오로 활용 가능할까요?', 1, 2, 6, 6);
INSERT INTO class_apply VALUES (seq_class_apply_id.NEXTVAL, SYSDATE-4, '기초부터 배우고 싶습니다.', '완전 초보도 따라갈 수 있나요?', 0, 2, 7, 6);
INSERT INTO class_apply VALUES (seq_class_apply_id.NEXTVAL, SYSDATE-4, '라떼아트 하고 싶어요', '똥손인데 할 수 있을까요?', 1, 0, 10, 6);

INSERT INTO class_apply VALUES (seq_class_apply_id.NEXTVAL, SYSDATE, '기념일 케이크 만들고 싶어요.', '초에 불 붙여 사진 찍어도 되나요?', 1, 1, 8, 7);
INSERT INTO class_apply VALUES (seq_class_apply_id.NEXTVAL, SYSDATE, '베이킹 취미로 배우고 싶습니다.', '과일은 계절 과일로 바뀌나요?', 1, 2, 9, 7);
INSERT INTO class_apply VALUES (seq_class_apply_id.NEXTVAL, SYSDATE, '너무 재미있을 것 같아요!', '', 2, 2, 10, 7);
INSERT INTO class_apply VALUES (seq_class_apply_id.NEXTVAL, SYSDATE, '기대됩니다.', '포장도 해주시나요?', 1, 1, 6, 7);

INSERT INTO class_apply VALUES (seq_class_apply_id.NEXTVAL, SYSDATE, '카페 창업 준비 중입니다.', '원가 계산 방법도 알려주시나요?', 2, 2, 4, 8);
INSERT INTO class_apply VALUES (seq_class_apply_id.NEXTVAL, SYSDATE, '홈카페 관심 많아요.', '집에서도 만들 수 있는 레시피인가요?', 2, 2, 11, 8);
INSERT INTO class_apply VALUES (seq_class_apply_id.NEXTVAL, SYSDATE, '메뉴 개발 배우고 싶어요.', '레시피 자료는 제공되나요?', 0, 2, 12, 8);

INSERT INTO class_apply VALUES (seq_class_apply_id.NEXTVAL, SYSDATE, '커피 좋아해서 신청합니다.', '원두는 여러 종류 중에서 고를 수 있나요?', 0, 2, 5, 9);
INSERT INTO class_apply VALUES (seq_class_apply_id.NEXTVAL, SYSDATE, '선물용으로 만들고 싶어요.', '포장 디자인도 선택 가능한가요?', 2, 2, 14, 9);

INSERT INTO class_apply VALUES (seq_class_apply_id.NEXTVAL, SYSDATE-60, '반려견 생일 케이크 만들고 싶어요.', '알러지 있는 재료는 제외 가능한가요?', 1, 0, 5, 10);
INSERT INTO class_apply VALUES (seq_class_apply_id.NEXTVAL, SYSDATE-70, '강아지 간식 직접 만들고 싶어요.', '남은 케이크 보관 방법도 알려주시나요?', 0, 1, 6, 10);

INSERT INTO class_apply VALUES (seq_class_apply_id.NEXTVAL, SYSDATE, '케이크 디자인 배우고 싶습니다.', '아이싱 난이도는 어느 정도인가요?', 1, 2, 4, 11);
INSERT INTO class_apply VALUES (seq_class_apply_id.NEXTVAL, SYSDATE, '베이킹 심화 과정 원해요.', '색소 사용하나요?', 2, 2, 8, 11);

INSERT INTO class_apply VALUES (seq_class_apply_id.NEXTVAL, SYSDATE, '전통주 직접 만들어보고 싶어요.', '발효 후 집에서 관리 방법도 알려주시나요?', 1, 2, 9, 12);
INSERT INTO class_apply VALUES (seq_class_apply_id.NEXTVAL, SYSDATE, '발효 과정이 궁금합니다.', '집에서도 같은 재료로 만들 수 있나요?', 2, 2, 10, 12);
INSERT INTO class_apply VALUES (seq_class_apply_id.NEXTVAL, SYSDATE, '막걸리 좋아해서 신청합니다.', '완성된 술은 가져갈 수 있나요?', 0, 2, 11, 12);

COMMIT;