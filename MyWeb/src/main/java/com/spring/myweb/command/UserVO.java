package com.spring.myweb.command;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*
 CREATE TABLE users (
    user_id VARCHAR(50) PRIMARY KEY,
    user_pw VARCHAR(50) NOT NULL,
    user_name VARCHAR(50) NOT NULL,
    user_phone1 VARCHAR(50),
    user_phone2 VARCHAR(50),
    user_email1 VARCHAR(50),
    user_email2 VARCHAR(50),
    addr_basic VARCHAR(300),
    addr_detail VARCHAR(50),
    addr_zip_num VARCHAR(50),
    reg_date DATETIME DEFAULT CURRENT_TIMESTAMP
);
 */

@Getter @Setter @ToString
public class UserVO {
	private String userId;
	private String userPw;
	private String userName;
	private String userPhone1;
	private String userPhone2;
	private String userEmail1;
	private String userEmail2;
	private String addrBasic;
	private String addrDetail;
	private String addrZipNum;
	private LocalDateTime regDate;
	
	/*
	 1명의 회원은 글을 여러개 작성가능
	 마이페이지는 특정회원이 작성한 글 목록을 나타내야함.
	 회원정보와 글 목록은 서로다른 테이블로 이루어짐
	 마이페이지에서는 해당정보를 1번의DB연동으로 가져올수 있도록 하기위해
	 JOIN문법으로 테이블을 합친뒤 원하는 컬럼을 선택해 가져옴. 
	 */
	
	
	//1이 UserVO이기 때문에 UserVO안에 N값을 뜻하는 FreeBoardVO객체의 모음을 저장할수 있는 리스트를 선언.
	//1:N관계의 테이블은 List형태로 선언.
	private List<FreeBoardVO> userBoardList;
	
}
