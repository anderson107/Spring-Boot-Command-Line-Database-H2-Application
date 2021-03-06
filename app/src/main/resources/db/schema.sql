CREATE TABLE CUSTOMER (
       CUSTOMER_ID INT NOT NULL AUTO_INCREMENT
     , FIRST_NAME VARCHAR(60) NOT NULL
     , LAST_NAME VARCHAR(40) NOT NULL
     , ADDRESS VARCHAR(50) NOT NULL
     , EMAIL VARCHAR(40) NOT NULL
     , UNIQUE UQ_CUSTOMER_1 (FIRST_NAME, LAST_NAME, ADDRESS)
     , PRIMARY KEY (CUSTOMER_ID)
);

CREATE TABLE CARD(
	CARD_ID INT NOT NULL AUTO_INCREMENT,
	CUSTOMER_ID INT NOT NULL,
	CARD_NUMBER VARCHAR(100),
	CARD_CODE VARCHAR(3),
	EXPIRE_DATE DATE,
	PRIMARY KEY(CARD_ID),
	CONSTRAINT FK_CARD FOREIGN KEY (CUSTOMER_ID)
		REFERENCES CUSTOMER(CUSTOMER_ID)
);

