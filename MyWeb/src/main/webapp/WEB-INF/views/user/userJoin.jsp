<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<section>
    <div class="container">
        <div class="row">
            <div class="col-lg-6 col-md-9 col-sm-12 join-form">
                <div class="titlebox">
                    회원가입
                </div>
                <form action="${pageContext.request.contextPath}/user/join" method="post" name="joinForm">
                    <div class="form-group">
                        <!--사용자클래스선언-->
                        <label for="id">아이디</label>
                        <div class="input-group">
                            <!--input2탭의 input-addon을 가져온다 -->
                            <input type="text" name="userId" class="form-control" id="userId"
                                placeholder="아이디를 (영문포함 4~12자 이상)">
                            <div class="input-group-addon">
                                <button type="button" class="btn btn-primary" id="idCheckBtn">아이디중복체크</button>
                            </div>
                        </div>
                        <span id="msgId"></span>
                        <!--자바스크립트에서 추가-->
                    </div>
                    <div class="form-group">
                        <!--기본 폼그룹을 가져온다-->
                        <label for="password">비밀번호</label>
                        <input type="password" name="userPw" class="form-control" id="userPw"
                            placeholder="비밀번호 (영 대/소문자, 숫자 조합 8~16자 이상)">
                        <span id="msgPw"></span>
                        <!--자바스크립트에서 추가-->
                    </div>
                    <div class="form-group">
                        <label for="password-confrim">비밀번호 확인</label>
                        <input type="password" class="form-control" id="pwConfirm" placeholder="비밀번호를 확인해주세요.">
                        <span id="msgPw-c"></span>
                        <!--자바스크립트에서 추가-->
                    </div>
                    <div class="form-group">
                        <label for="name">이름</label>
                        <input type="text" name="userName" class="form-control" id="userName" placeholder="이름을 입력하세요.">
                    </div>
                    <!--input2탭의 input-addon을 가져온다 -->
                    <div class="form-group">
                        <label for="hp">휴대폰번호</label>
                        <div class="input-group">
                            <select name="userPhone1" class="form-control phone1" id="userPhone1">
                                <option>010</option>
                                <option>011</option>
                                <option>017</option>
                                <option>018</option>
                            </select>
                            <input type="text" name="userPhone2" class="form-control phone2" id="userPhone2"
                                placeholder="휴대폰번호를 입력하세요.">
                        </div>
                    </div>
                    <div class="form-group email-form">
                        <label for="email">이메일</label><br>
                        <div class="input-group">
                            <input type="text" name="userEmail1" class="form-control" id="userEmail1" placeholder="이메일">
                            <select name="userEmail2" class="form-control" id="userEmail2">
                                <option>@naver.com</option>
                                <option>@daum.net</option>
                                <option>@gmail.com</option>
                                <option>@hanmail.com</option>
                                <option>@yahoo.co.kr</option>
                            </select>
                            <div class="input-group-addon">
                                <button type="button" id="mail-check-btn" class="btn btn-primary">이메일 인증</button>
                            </div>
                        </div>
                    </div>
                    <div class="mail-check-box">
                        <input type="text" class="form-control mail-check-input" placeholder="인증번호 6자리를 입력하세요."
                            maxlength="6" disabled="disabled">
                        <span id="mail-check-warn"></span>
                    </div>


                    <!--readonly 속성 추가시 자동으로 블락-->
                    <div class="form-group">
                        <label for="addr-num">주소</label>
                        <div class="input-group">
                            <input type="text" name="addrZipNum" class=" form-control" id="addrZipNum" placeholder="우편번호"
                                readonly>
                            <div class="input-group-addon">
                                <button type="button" class="btn btn-primary" onclick="searchAddress()">주소찾기</button>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <input type="text" name="addrBasic" class="form-control" id="addrBasic" placeholder="기본주소">
                    </div>
                    <div class="form-group">
                        <input type="text" name="addrDetail" class="form-control" id="addrDetail" placeholder="상세주소">
                    </div>

                    <!--button탭에 들어가서 버튼종류를 확인한다-->
                    <div class="form-group">
                        <button type="button" id="joinBtn" class="btn btn-lg btn-success btn-block">회원가입</button>
                    </div>

                    <div class="form-group">
                        <button type="button" id="=loginBtn" class="btn btn-lg btn-info btn-block">로그인</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</section>
<%@ include file="../include/footer.jsp" %>

<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
    let code = ''; //이메일 전송 인증번호 저장을 위한 변수
    let idFlag, pwFlag; //정규표현식 유효성 검사 여부 판단

    //아이디 중복체크
    document.getElementById('idCheckBtn').onclick = function () {
        const userId = document.getElementById('userId').value;
        if (userId == '') {
            alert('아이디는 필수값입니다.');
            return;
        }
        if(!idFlag){
            alert('아이디를 다시 적어주세요');
        }
        //아이디 중복확인 비동기 요청준비
        const xhr = new XMLHttpRequest();

        /*
            //서버요청정보설정 - 구버전
            xhr.open('POST','${pageContext.request.contextPath}/user/idCheck');
    
            xhr.setRequestHeader('content-type','text/plain');
            xhr.send(userId);

            xhr.onload = ()=>{
                console.log(xhr.status);
                console.log(xhr.response);
            }
            */

        /*
        # fetch API: JAVA script에서 제공하는 비동기 통신 함수.
        - Promise 객체를 자동으로 리턴하여 순쉽게 통신의 응답데이터를 사용하게해줌
        (Promise: 비동기 통신의 순서를 보장하는 문법)

        -fetch함수가 리턴하는 Promise객체는 단순 응답Json데이터가 아닌
        전체적이고 포괄적인 정보를 담고있음.

        따라서, 서버가 응답한 여러정보중 
        json데이터만 사용하려면 JSON()매서드 
        문자열 데이터라면 test() 사용해야함!
        */

        //fetch('url',{요청 관련 객체})
        // fetch('${pageContext.request.contextPath}/user/idCheck',{
        //     method: 'post',
        //     headers: {
        //         'Content-type' : 'application/json'
        //     },
        //     body: userId    
        // //promise객체의 상태가 요청성곡일시 데이터 후속처리 진행  
        // }).then(res =>{



        //     /*
        //     fetch 함수를 통해 비동기 통신이 실행되고,
        //     요청이 완료된후 then()의 매개값으로 응답에 관련된
        //     함수를 콜백방식으로 전달
        //     함수의 매개변수를 선언하면 해당매개변수로 응답에 관련된 
        //     전반적인 정보를 가진 응답정보가 전달됨.
        //     */
        //     console.log(res);
        //     console.log(res.text());
        //     res.text();
        // }).then(data=>{
        //     console.log(data);
        // })

        const reqObj = {
            method: 'post',
            headers: {
                'Content-type': 'text/plain'
            },
            body: userId
        };

        fetch('${pageContext.request.contextPath}/user/idCheck', reqObj)
            .then(res => res.text()) //요청 완료후 응답정보에서 텍스트만 추출
            .then(data => {
                if (data === 'ok') {
                    //더이상 아이디를 작성할수 없도록 막기
                    document.getElementById('userId').setAttribute('readonly', true);

                    //더이상 버튼을 누를수 없도록 버튼 비활성화
                    document.getElementById('idCheckBtn').setAttribute('disabled', true);
                    //메세지 남기기
                    document.getElementById('msgId').textContent = '사용 가능한 아이디 입니다.';
                } else {
                    document.getElementById('msgId').textContent = '중복된 아이디 입니다.';
                }
            }); //promise 객체로부터 추출한 텍스트를 data로 전달
    } //아이디 중복확인 끝

    //인증번호 확인 이메일 전송
    document.getElementById('mail-check-btn').onclick = function () {
        const email = document.getElementById('userEmail1').value +
            document.getElementById('userEmail2').value;

        console.log('완성된 email: ' + email);
        fetch('${pageContext.request.contextPath}/user/mailCheck?email=' + email)
            .then(res => res.text())
            .then(data => {
                console.log('인증번호: ' + data)
                //비활성된 인증 창 활성화
                document.querySelector('.mail-check-input').disabled = false;
                code = data; //인증번호 전역변수에 저장
                alert('인증번호가 전송되었습니다. 확인후 입력란에 정확히 입력해주세요');
            }); //비동기 끝     

        //인증번호 검증
        //blur -> focus가 벗어나는 경우 발생.
        document.querySelector('.mail-check-input').onblur = function (e) {
            console.log('Blur Activate!');
            const inputCode = e.target.value; //인증한 인증번호
            const $resultMsg = document.getElementById('mail-check-warn'); //span

            console.log('사용자 입력값: ' + inputCode);

            if (inputCode === code) {
                $resultMsg.textContent = '인증번호가 일치합니다.';
                $resultMsg.style.color = 'green';
                document.getElementById('mail-check-btn').disabled = true;
                document.getElementById('userEmail1').setAttribute('readonly', true);
                document.getElementById('userEmail2').setAttribute('readonly', true);
                e.target.style.display = 'none'; //인증번호 입력창 숨기기

                //초기값을 사용자가 선택한 값으로 무조건 설정하는 방법(select에서 readonly로 변경)
                //항상 2개를 같이 사용해야함.
                const email2 = document.getElementById('userEmail2');
                email2.setAttribute('onFocus', 'this.initialSelect = this.selectedIndex');
                email2.setAttribute('onChange', 'this.selectedIndex = this.initialSelect');
            } else {
                $resultMsg.textContent = '인증번호를 다시 확인해주십시오.';
                $resultMsg.style.color = 'red';
                e.target.focus();
            }
        };
    } //인증번호 검증끝

    //다음주소API사용해보기(TRL9이상
    function searchAddress() {
        new daum.Postcode({
            oncomplete: function (data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                if (data.userSelectedType === 'R') {
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if (data.buildingName !== '' && data.apartment === 'Y') {
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if (extraAddr !== '') {
                        extraAddr = ' (' + extraAddr + ')';
                    }
                    // 조합된 참고항목을 해당 필드에 넣는다.
                    document.getElementById("sample6_extraAddress").value = extraAddr;

                } else {
                    document.getElementById("sample6_extraAddress").value = '';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('sample6_postcode').value = data.zonecode;
                document.getElementById("sample6_address").value = addr;
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("sample6_detailAddress").focus();
            }
        }).open();
    } //주소찾기 api끝

    //폼 데이터 검증(회원 가입 버튼을 눌렀을시)
    document.getElementById('joinBtn').onclick = function () {


        if (idFlag && pwFlag) {
            if (document.getElementById('userId').value === '' ||
                !document.getElementById('userId').getAttribute('readonly')) {
                alert('아이디 중복체크는 필수입니다.');
                return;
            }

            if (document.getElementById('userPw').value !== 
                document.getElementById('pwConfirm').value) {
                alert('비밀번호를 다시 확인해주세요.');
                return;
            }

            if(document.getElementById('userName').value === '' ){
                alert('이름은 필수값입니다.');
                return;
            }

            if(!document.getElementById('mail-check-btn').disabled) {
                alert('이메일 인증을 완료해 주세요');
                return;
            }

            if(confirm('회원가입을 진행합니다.')) {
                document.joinForm.submit();
            } else return;


        } else {
            alert('입력값을 다시한번 확인하세요!.');
        }



    };



    /*아이디 형식 검사 스크립트*/
    var id = document.getElementById("userId");
    id.onkeyup = function () {
        /*
        	자바스크립트의 정규표현식 입니다
        	정규표현식: 문자열 내의 특정 문자 조합을 찾기위한 패턴
        	특정 규칙이 있는 문자열 집합을 규칙을 직접 지정하여 탐색하세 해주는 메타문자
        */
        /*test메서드를 통해 비교하며, 매칭되면 true, 아니면 false반*/
        var regex = /^[A-Za-z0-9+]{4,12}$/; //찾고자 하는 문자의 규칙(정규표현식)
        if (regex.test(document.getElementById("userId").value)) {
            document.getElementById("userId").style.borderColor = "green";
            document.getElementById("msgId").innerHTML = "아이디중복체크는 필수 입니다";
            idFlag = true;
        } else {
            document.getElementById("userId").style.borderColor = "red";
            document.getElementById("msgId").innerHTML = "부적합한 아이디입니다.";
            idFlag = false;
        }
    }
    /*비밀번호 형식 검사 스크립트*/
    var pw = document.getElementById("userPw");
    pw.onkeyup = function () {
        var regex = /^[A-Za-z0-9+]{8,16}$/;
        if (regex.test(document.getElementById("userPw").value)) {
            document.getElementById("userPw").style.borderColor = "green";
            document.getElementById("msgPw").innerHTML = "사용가능합니다";
            pwFlag = true;
        } else {
            document.getElementById("userPw").style.borderColor = "red";
            document.getElementById("msgPw").innerHTML = "부적합한 비밀번호입니다.";
            pwFlag = false;
        }
    }
    /*비밀번호 확인검사*/
    var pwConfirm = document.getElementById("pwConfirm");
    pwConfirm.onkeyup = function () {
        var regex = /^[A-Za-z0-9+]{8,16}$/;
        if (document.getElementById("pwConfirm").value == document.getElementById("userPw").value) {
            document.getElementById("pwConfirm").style.borderColor = "green";
            document.getElementById("msgPw-c").innerHTML = "비밀번호가 일치합니다";
        } else {
            document.getElementById("pwConfirm").style.borderColor = "red";
            document.getElementById("msgPw-c").innerHTML = "비밀번호 확인란을 확인하세요";
        }
    }
</script>