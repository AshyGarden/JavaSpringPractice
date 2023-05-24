<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<%@ include file="../include/header.jsp" %>
<section>
    <div class="container">
        <div class="row">
            <div class="col-xs-12 col-md-9 write-wrap">
                <div class="titlebox">
                    <p>상세보기</p>
                </div>

                <form action="<c:url value='/freeboard/modify'/>" method="post">
                    <div>
                        <label>DATE</label>

                        <c:if test="${article.updateDate == null}">
                            <p>
                                <fmt:parseDate value="${article.regDate}" pattern="yyyy-MM-dd'T'HH:mm:ss"
                                    var="parsedDateTime" type="both" />
                                <fmt:formatDate value="${parsedDateTime}" pattern="yyyy년 MM월 dd일 HH시 mm분" />
                            </p>
                        </c:if>

                        <c:if test="${article.updateDate != null}">
                            <p>
                                <fmt:parseDate value="${vo.updateDate}" pattern="yyyy-MM-dd'T'HH:mm:ss"
                                    var="parsedUpdateTime" type="both" />
                                <fmt:formatDate value="${parsedUpdateTime}" pattern="yyyy년 MM월 dd일 HH시 mm분" />
                            </p>
                        </c:if>
                    </div>

                    <div class="form-group">
                        <label>번호</label>
                        <input class="form-control" name="bno" value="${article.bno}" readonly>
                    </div>
                    <div class="form-group">
                        <label>작성자</label>
                        <input class="form-control" name="writer" value="${article.writer}" readonly>
                    </div>
                    <div class="form-group">
                        <label>제목</label>
                        <input class="form-control" name="title" value="${article.title}" readonly>
                    </div>

                    <div class="form-group">
                        <label>내용</label>
                        <textarea class="form-control" rows="10" name="content" readonly>${article.content}</textarea>
                    </div>

                    <button type="submit" class="btn btn-primary" onclick="return confirm('변경 페이지로 이동합니다.')">변경</button>
                    <button type="button" class="btn btn-dark"
                        onclick="location.href='${pageContext.request.contextPath}/freeboard/freeList?pageNum=${p.pageNum}&cpp=${p.cpp}&keyword=${p.keyword}&condition=${p.condition}'">목록</button>
                </form>
            </div>
        </div>
    </div>
</section>
<!-- 댓글 영역 시작부분  -->
<section style="margin-top: 80px;">
    <div class="container">
        <div class="row">
            <div class="col-xs-12 col-md-9 write-wrap">
                <form class="reply-wrap">
                    <div class="reply-image">
                        <img src="${pageContext.request.contextPath}/img/profile.png">
                    </div>
                    <!--form-control은 부트스트랩의 클래스입니다-->
                    <div class="reply-content">
                        <textarea class="form-control" rows="3" id="reply"></textarea>
                        <div class="reply-group">
                            <div class="reply-input">
                                <input type="text" class="form-control" id="replyId" placeholder="이름">
                                <input type="password" class="form-control" id="replyPw" placeholder="비밀번호">
                            </div>

                            <button type="button" id="replyRegist" class="right btn btn-info">등록하기</button>
                        </div>

                    </div>
                </form>

                <!--여기에접근 반복-->
                <div id="replyList">
                    <!--자바스크립트 단에서 반복문을 이용해서 댓글의 개수만큼 반복 표현.
                      -->
                </div>
                <button type="button" class="form-control" id="moreList" style="display: none;">더보기(페이징)</button>
            </div>
        </div>
    </div>
</section>

<!-- 모달 -->
<div class="modal fade" id="replyModal" role="dialog">
    <div class="modal-dialog modal-md">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="btn btn-default pull-right" data-dismiss="modal">닫기</button>
                <h4 class="modal-title">댓글수정</h4>
            </div>
            <div class="modal-body">
                <!-- 수정폼 id값을 확인하세요-->
                <div class="reply-content">
                    <textarea class="form-control" rows="4" id="modalReply" placeholder="내용입력"></textarea>
                    <div class="reply-group">
                        <div class="reply-input">
                            <input type="hidden" id="modalRno">
                            <input type="password" class="form-control" placeholder="비밀번호" id="modalPw">
                        </div>
                        <button class="right btn btn-info" id="modalModBtn">수정하기</button>
                        <button class="right btn btn-info" id="modalDelBtn">삭제하기</button>
                    </div>
                </div>
                <!-- 수정폼끝 -->
            </div>
        </div>
    </div>
</div>
<%@ include file="../include/footer.jsp" %>

<script>
    window.onload = function () {

        document.getElementById('replyRegist').onclick = () => {

            console.log('');

            const bno = '${article.bno}';
            const reply = document.getElementById('reply').value;
            const replyId = document.getElementById('replyId').value;
            const replyPw = document.getElementById('replyPw').value;

            if (reply === '' || replyId === '' || replyPw === '') {
                alert('이름, 비밀번호, 내용을 입력하세요!');
                return;
            }

            //요청 관련 정보 객체 - javaScript->json으로 변환에서 보내주기
            const reqObj = {
                method: 'post',
                headers: {
                    'Content-type': 'application/json' //json 날아간다고 콜
                },
                body: JSON.stringify({
                    'bno': bno,
                    'reply': reply,
                    'replyId': replyId,
                    'replyPw': replyPw
                })

            };

            fetch('${pageContext.request.contextPath}/reply/regist', reqObj)
                .then(res => res.text())
                .then(data => {
                    console.log('통신 성공!: ' + data);
                    document.getElementById('reply').value = '';
                    document.getElementById('replyId').value = '';
                    document.getElementById('replyPw').value = '';
                    //등론완료후 댓글목록함수 호출- 비동기식으로 목록표현
                    getList(1, true); //마지막 댓글-1번째에 보여주기, 리셋해서 다시 보여주기!
                });

        } //댓글 등록 이벤트 끝

        //더보기 버튼 처리(클릭시 전역변수page에 +1한 값을 요청)
        document.getElementById('moreList').onclick = () => {
            /*
            false인이유 - 댓글을 누적해서 보여줘야 하기 때문
            */
            getList(++page, false);
        }

        let page = 1; //전역의미로 사용할 페이지번호
        let strAdd = ''; //화면에 그려넣을 태그를 문자열 형태로 추가할 변수
        const $replyList = document.getElementById('replyList');
        //게시글 상세보기 화면에 처음 진입시 댓글목록 바로보여줌.
        getList(1, true);

        //댓글 목록을 가져오는 함수
        //getList의 매개값으로 무엇을 줄것인가?
        //요청 페이지 번호와 화면을 리셋할것인지 여부를 bool타입의 reset으로 반환
        //페이지가 그대로 머물면서 댓글이 밑에 계속 쌓이기 때문에, 상황에따라 
        //페이지를 리셋해서 새롭게 그려낼것인지, 누적해서 쌓을것인지 여부를 판단.

        function getList(pageNum, reset) {
            strAdd = '';
            const bno = '${article.bno}'; //게시글 번호

            //get방식으로 댓글 목록을 요청(비동기)
            fetch('${pageContext.request.contextPath}/reply/getList/' + bno + '/' + pageNum)
                .then(res => res.json())
                .then(data => {
                    console.log(data);

                    let total = data.total; //총 댓글수
                    let replyList = data.list; //댓글리스트

                    //const $replyList = document.getElementById('replyList');

                    //insert, update, delete 작업 후에는
                    //댓글 내용 태그를 누적하고 있는 strAdd 변수를 초기화해서
                    //마치 화면이 리셋된 것처럼 보여줘야 함
                    //이 작업이 if (replyList.length <= 0) return보다 빨라야함!
                    if (reset) {

                        while ($replyList.firstChild) {
                            $replyList.firstChild.remove();
                        }
                        page = 1;
                    }

                    //응답 데이터의 길이가 0과 같거나 더 작으면 함수를 종료
                    if (replyList.length <= 0) return;

                    //페이지 번호 * 이번 요청으로 받은 댓글수보다 개수가 적다면 더보기 버튼 숨기기
                    console.log('현재 페이지: ' + page);
                    if (total <= page * 5) {
                        document.getElementById('moreList').style.display = 'none';
                    } else {
                        document.getElementById('moreList').style.display = 'block';
                    }

                    //replyList의 개수만큼 태그를 문자열 형태로 직접그림
                    //중간에 글쓴이, 댓글등은 list에서 꺼내서 표현
                    for (let i = 0; i < replyList.length; i++) {
                        strAdd +=
                            `  <div class='reply-wrap'>
                                     <div class='reply-image'>
                                         <img src='${pageContext.request.contextPath}/img/profile.png'>
                                             </div>
                                             <div class='reply-content'>
                                                    <div class='reply-group'>
                                                        <strong class='left'>` + replyList[i].replyId + `</strong>
                                                        <small class='left'>` + replyList[i].updateDate != null? parsedTime(replyList[i].updateDate)+'(수정됨)' : parsedTime(replyList[i].replyDate) + `</small>
                                                        <a href='` + replyList[i].rno + `' class='right'><span class='glyphicon glyphicon-pencil'></span>수정</a>
                                                        <a href='` + replyList[i].rno + `' class='right'><span class='glyphicon glyphicon-remove'></span>삭제</a>
                                        </div>
                                        <p class='clearfix'>` + replyList[i].reply + `</p>
                                    </div>
                                </div>`
                    }

                    //id가 relpyList라는 div영역에 문자열 형식으로 모든 댓글을 추가
                    if (!reset) {
                        document.getElementById('replyList').insertAdjacentHTML('beforeend', strAdd);
                    } else {
                        document.getElementById('replyList').insertAdjacentHTML('afterbegin', strAdd);
                    }

                });
        } //end getList();

        //수정 삭제
        // document.querySelector('.replyModify').onclick = (e) =>{
        //     e.preventDefault();
        //     console.log('modify activate!');
        // } //동작 안함!
        /*
        replymodify - 실재요소가 아닌 비동기 통신을 통해 생성되는 요소
        비동기 통신을 통해 생성되는 요소
        그러다 보니 이벤트가 등록되는 시점보다 
        fetch함수의 실행이 먼저 끝날것이라는 보장이 없기 때문에 해당방식은 이벤트 등록불가
        이때는, 이미 실제로 존재하는 #replyList에 등록, 이벤트를 자식에게 위임하여
        사용하는 addeventlistener를 통해 처리해야한다.
        */

        document.getElementById('replyList').addEventListener('click', e => {
            e.preventDefault();

            //1.이벤트 발생 target이 a태그가 아니라면 이벤트 종료
            if (!e.target.matches('a')) {
                return;
            }

            //2. a태그가 2개(수정,삭제)이므로 어떤 링크인지 확인 필요
            //댓글이 여러개->수정, 삭제가 발생하는 댓글이 몇번인지도 확인.
            const rno = e.target.getAttribute('href');
            console.log('댓글 번호: ' + rno);

            const content = e.target.parentNode.nextElementSibling.textContent;
            console.log('댓글 내용: ' + content);

            //3. 모달 창하나를 이용해서 상황에 따라 수정/삭제를 모달을 구분하기위래 조건문 작성
            //모달 1개로 수정 삭제를 처리, 디자인 조정
            if (e.target.classList.contains('replyModify')) {
                //수정 버튼을 눌렀으므로 수정 모달형식을 꾸며줌
                document.querySelector('.modal-title').textContent = '댓글 수정';
                document.getElementById('modalReply').style.display = 'inline';
                document.getElementById('modalReply').value = content;
                document.getElementById('modalBtn').style.display = 'inline';
                document.getElementById('modalDelBtn').style.display = 'none';


                //JQUERY를 이용하여 bootstrap modal여는방법
                $('#replyModal').modal('show');
            } else {
                document.querySelector('.modal-title').textContent = '댓글 삭제';
                document.getElementById('modalReply').style.display = 'none';
                //document.getElementById('modalReply').value = content;
                document.getElementById('modalBtn').style.display = 'none';
                document.getElementById('modalDelBtn').style.display = 'inline';

                $('#replyModal').modal('show');
            }

        }); //수정or삭제 클릭 이벤트 end

        //수정 처리함수(수정 modal을 열어서 수정내용을 작성후 수정 버튼 클릭시)
        document.getElementById('modalModBtn').onclick = () => {

            const reply = document.getElementById('modalReply').value;
            const rno = document.getElementById('modalRno').value;
            const replyPw = document.getElementById('modalPw').value;

            if (reply === '' || replyPw === '') {
                alert('내용 비밀번호를 확인하세요!');
                return;
            }

            const reqObj = {
                method: 'put',
                headers: {
                    'Content-type': 'application/json'
                },
                body: JSON.stringify({
                    'reply': reply,
                    'replyPw': replyPw
                })
            };

            fetch('${pageContext.request.contextPath}/reply/' + rno, reqObj)
                .then(res => res.text())
                    .then(data =>{
                        if(data ==='pwFail'){
                            alert('비밀번호를 확인하세요.');
                            document.getElementById('modalPw').value='';
                            document.getElementById('modalPw').focus();
                        } else { //updateSuccess
                            alert('정상수정 되었습니다.');
                            document.getElementById('modalReply').value='';
                            document.getElementById('modalPw').value='';
                            //jquery문법으로 bootstrap modal닫아주기
                            $('#replyModal').modal('hide');
                            getList(1,true);
                        }
                    });
        }

        document.getElementById('modalDelBtn').onclick = ()=>{
            /*
            1. modal창에 rno값, replyPw값을 얻기
            2.fetch 함수를 이용해서 DELETE방식으로 reply/{rno} 요청

            3.서버에서 요청을 받아서 pw확인후 pw가 맞으면 삭제진행

            4. pw틀렸으면 문자열 반환후 '비밀번호가 틀렸습니다.' 경고창 띄우기
            5. 삭제 완료시 modal닫고 목록 요청을 다시보내기
            */

            const rno = document.getElementById('modelRno').value;
            const replyPw = document.getElementById('modelPw').value;

            if(replyPw === ''){
                return;
            }
            fetch('${pageContext.request.contextPath}/reply/'+rno , {             
                method: 'delete',
                headers: {
                    'Content-type': 'application/json'
                },
                body: JSON.stringify({
                    'rno': rno,
                    'replyPw': replyPw
                })
            }).then(res =>res.text())
                .then(data =>{
                    if(data ==='delSuccess'){
                        alert('댓글이 삭제되었습니다.');
                        document.getElementById('modalPw').value='';
                        $('#replyModal').modal('hide');
                        getList(1,true);
                    } else {
                        alert('비밀번호가 틀렸습니다.');
                        document.getElementById('modalPw').value='';
                        document.getElementById('modalPw').focus();
                    }

                });

        } //end del event

        //댓글 날짜 변환 함수
        function parsedTime(regDateTime){
            let year, month, day, hour, minute, second;

            if(regDateTime.length ===5){
               [year, month, day, hour, minute] = regDateTime;
               second =0;
            } else{
               [year, month, day, hour, minute, second] = regDateTime;
            }

            const regTime = new Date(year, month-1, day, hour, minute, second);
            const date = new Date();
            const gap = date.getTime() - regTime.getTime();

            let time;
            if(gap < 60 * 60 * 24 * 1000){
                if(gap < 60* 60* 1000){
                    time = '방금 전';
                } else{
                    time = parseInt(gap /(1000* 60 * 60)) + '시간 전';
                }
            } else if(gap < 60 * 60 * 24 * 30 * 1000){ //1달전
                time = parseInt(gap /(1000* 60 * 60 * 24)) + '일 전';
            } else{
                time = `${regTime.getFullYear()}년 ${regTime.getMonth()-1}월 ${regTime.getDate()}일`
            } 
            
            return time;
        }




    } //window.onload
</script>