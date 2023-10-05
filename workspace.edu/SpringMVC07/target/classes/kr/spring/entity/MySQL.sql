CREATE TABLE TBLBOARD(
   IDX INT NOT NULL,
   MEMID VARCHAR(20) NOT NULL,
   TITLE VARCHAR(100) NOT NULL,
   CONTENT VARCHAR(2000) NOT NULL,
   WRITER VARCHAR(30) NOT NULL,
   INDATE DATETIME DEFAULT NOW(),
   COUNT INT DEFAULT 0,
   -- 댓글기능 추가 --
   BOARDGROUP INT, -- 부모 댓글 하나의 그룹 --
   BOARDSEQUENCE INT, -- 같은 그룹게시글안안에서 댓글의 순서 --
    BOARDLEVEL INT, -- 1단계 댓글인지 2단계 댓글인지에 대한 정보  원 댓글에 달린 대댓글인지, 다른 댓글에 달린건지 --   
   BOARDAVAILABLE INT, -- 삭제된 글인지 여부 --
   PRIMARY KEY(IDX)
);
INSERT INTO TBLBOARD
SELECT IFNULL(MAX(IDX+1), 1),
'aischool','공지사항 입니다.', '다음주 월요일 휴일', '교육 운영부', 
NOW(), 0, IFNULL(MAX(BOARDGROUP+1),1),0,0,1
FROM TBLBOARD;

INSERT INTO TBLBOARD
SELECT IFNULL(MAX(IDX+1), 1),
'aischool','여름여름.', '여름여름', '교육 운영부', 
NOW(), 0, IFNULL(MAX(BOARDGROUP+1),1),0,0,1
FROM TBLBOARD;

INSERT INTO TBLBOARD
SELECT IFNULL(MAX(IDX+1), 1),
'aischool','도르미르.', '도르미르', '교육 운영부', 
NOW(), 0, IFNULL(MAX(BOARDGROUP+1),1),0,0,1
FROM TBLBOARD;

SELECT * FROM TBLBOARD;

CREATE TABLE TBLMEMBER(
   MEMID VARCHAR(50) NOT NULL,
   MEMPWD VARCHAR(50) NOT NULL,
   MEMNAME VARCHAR(50) NOT NULL,
   MEMPHONE VARCHAR(50) NOT NULL,
   MEMADDR VARCHAR(100),
   LATITUDE DECIMAL(13, 10), -- 현재위치위도 --
   LONGITUDE DECIMAL(13, 10), -- 현재위치경도 --
   PRIMARY KEY(MEMID)
);

INSERT INTO TBLMEMBER(MEMID, MEMPWD,MEMNAME, MEMPHONE)
VALUES('aischool','1234','교육운영부','010-1111-2222');

INSERT INTO TBLMEMBER(MEMID, MEMPWD,MEMNAME, MEMPHONE)
VALUES('aischool1','12345','교육운영부1','010-1111-2222');

INSERT INTO TBLMEMBER(MEMID, MEMPWD,MEMNAME, MEMPHONE)
VALUES('aischool2','123456','교육운영부2','010-1111-2222');

SELECT* FROM TBLMEMBER;
SELECT * FROM TBLBOARD;