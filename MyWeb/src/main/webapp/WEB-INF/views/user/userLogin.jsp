<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<section>
    <div class="container">
        <div class="row">
            <div class="col-lg-6 col-md-7 col-xs-10 login-form">
                <div class="titlebox">
                    로그인
                </div>
                <form method="post" name="loginForm">
                    <!-- action이 없는이유 = 동일한 url이 post요청으로 날아감-->
                    <div class="form-group">
                        <!--사용자클래스선언-->
                        <label for="id">아이디</label>
                        <input type="text" name="userId" class="form-control" id="id" placeholder="아이디">
                    </div>
                    <div class="form-group">
                        <!--사용자클래스선언-->
                        <label for="id">비밀번호</label>
                        <input type="password" name="userPw" class="form-control" id="pw" placeholder="비밀번호">
                    </div>
                    <div class="form-group">
                        <button type="button" id="loginBtn" class="btn btn-info btn-block">로그인</button>
                        <button type="button" id="joinBtn" class="btn btn-primary btn-block">회원가입</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</section>
<%@ include file="../include/footer.jsp" %>

<script>
    //회원가입후 addflashattribute로 msg데이터가 전달되는지 확인
    const msg = '${msg}';
    if (msg === 'JoinSuccess') {
        alert('회원가입이 정상 처리되었습니다.');
    } else if(msg === 'loginFail'){
        alert('로그인에 실패하였습니다.');
    }

    //id, pw입력란이 공백인지 아닌지 확인후, 공백이 아니라면 submit
    //요청url은 /user/userLogin -> post (비동기x)

    document.getElementById('loginBtn').onclick = () => {
        if(document.getElementById('id').value === ''){
            alert('아이디를 적어주세요.');
            return;
        }
        if(document.getElementById('pw').value === ''){
            alert('비밀번호를 적어주세요.');
            return;
        }
        document.loginForm.submit();
    }

    document.getElementById('joinBtn').onclick = () =>{
        location.href = '${pageContext.request.contextPath}/user/join';
    }

</script>