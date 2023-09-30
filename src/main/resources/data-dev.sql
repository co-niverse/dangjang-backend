INSERT INTO users (oauth_id, oauth_provider, nickname, gender, birthday, activity_amount, height, recommended_calorie, status, role, profile_image_path,
                   diabetic, diabetes_year, medicine, injection, created_at, updated_at, accessed_at, health_connect)
VALUES ('11111111', 'KAKAO', 'TEO', 'M', '1997-05-23', 'MEDIUM', 180, 2000, 'ACTIVE', 'ADMIN', 'user/image/teo', false, 0, false, false, now(), now(),
        now(), 'NEVER_CONNECTED');

INSERT INTO users (oauth_id, oauth_provider, nickname, gender, birthday, activity_amount, height, recommended_calorie, status, role, profile_image_path,
                   diabetic, diabetes_year, medicine, injection, created_at, updated_at, accessed_at, health_connect)
VALUES ('22222222', 'NAVER', 'EVE', 'F', '2001-12-24', 'LOW', 160, 1500, 'ACTIVE', 'USER', 'user/image/eve', true, 3, true, true, now(), now(), now(),
        'NEVER_CONNECTED');
INSERT INTO point_product (product, point, type)
VALUES ("접속", 100, 'EARN'),
       ("등록", 500, 'EARN'),
       ("기기연동", 500, 'EARN'),
       ("스타벅스 오천원 금액권", 5000, 'USE'),
       ("CU 오천원 금액권", 5000, 'USE'),
       ("배민 일만원 금액권", 10000, 'USE'),
       ("네이버페이 오천원 금액권", 5000, 'USE');