create table users
(id varchar2(30) primary key,
pwd varchar2(20) not null,
email varchar2(30),
regdate date,
profile varchar2(100));

create table board_cafe(
num number primary key,
writer varchar2(100) not null,	-- 글 작성자의 id
title varchar2(100) not null,	
content clob,
viewCount number,	--조회수
regdate date
);

create sequence board_cafe_seq;

--특정 페이지 가져오는 방법--
select * 							--3.page를 select한다.
from
	(select result1.*, ROWNUM as rnum	--2.번호부여하고
	from
		(select num,writer,title	--1.줄세우고
		from board_cafe
		where writer LIKE '%gura%'	--키워드 검색. =>동적쿼리로 처리. (생길 수도 있고 없을 수도 있고.)
		order by num desc) result1)
where rnum between ? and ?;			--몇번 페이지인지

create table board_file(
num number primary key,
writer varchar2(100),
title varchar2(100) not null,
orgFileName varchar2(100) not null,	-- 원본 파일명
saveFileName varchar2(100) not null, -- 파일 시스템에 저장된 파일명
fileSize number, -- 파일의 크기(byte)
downCount number default 0, -- 다운로드 횟수
regdate date
);

create sequence board_file_seq;

alter table users add(profile varchar2(50));	--프로필 이미지 경로를 넣을 예정


create table board_cafe_comment(
num number primary key,
writer varchar2(50) not null,
content clob,
regdate date,
ip varchar2(50),
writeNum number
);

create sequence board_cafe_comment_seq;

-- 댓글 정보를 저장할 테이블
CREATE TABLE board_cafe_comment(
	num NUMBER PRIMARY KEY, -- 댓글의 글번호
	writer VARCHAR2(100), -- 댓글 작성자
	content VARCHAR2(500), -- 댓글 내용
	target_id VARCHAR2(100), -- 댓글의대상이되는아이디(글작성자)
	ref_group NUMBER, -- 댓글 그룹번호
	comment_group NUMBER, -- 원글에 달린 댓글 내에서의 그룹번호
	deleted CHAR(3) DEFAULT 'no', -- 댓글이 삭제 되었는지 여부 
	regdate DATE -- 댓글 등록일 
);

CREATE SEQUENCE board_cafe_comment_seq;


-------------------------------------------------------------------------------------------------------------
CREATE TABLE shop(
	num NUMBER PRIMARY KEY, --상품번호
	name VARCHAR2(30), --상품이름
	price NUMBER, --상품가격
	remainCount NUMBER CHECK(remainCount >= 0) --재고갯수 
);

-- 고객 계좌 테이블
CREATE TABLE client_account(
	id VARCHAR2(30) PRIMARY KEY, -- 고객의 아이디
	money NUMBER CHECK(money >= 0), -- 고객의 잔고 
	point NUMBER
);

-- 주문 테이블
CREATE TABLE client_order(
	num NUMBER PRIMARY KEY, -- 주문번호
	id VARCHAR2(30), -- 주문 고객의 아이디
	code NUMBER, -- 주문한 상품의 번호 
	addr VARCHAR2(50) -- 배송 주소
);

-- 주문 테이블에 사용할 시퀀스 
CREATE SEQUENCE client_order_seq;


-- sample 데이터
INSERT INTO shop (num,name,price,remainCount)
VALUES(1, '사과', '1000', 5);

INSERT INTO shop (num,name,price,remainCount)
VALUES(2, '바나나', '2000', 5);

INSERT INTO shop (num,name,price,remainCount)
VALUES(3, '귤', '3000', 5);

INSERT INTO client_account (id, money, point)
VALUES('superman', 10000, 0);

INSERT INTO client_account (id, money, point)
VALUES('batman', 10000, 0);