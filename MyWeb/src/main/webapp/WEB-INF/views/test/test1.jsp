<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

    이름: <input type="text" name="name"> <br>
    나이: <input type="text" name="age"> 
    
    <hr>
    
    취미: <br>
    <input type="checkbox" name="hobby" value="soccer">축구    <br>
    <input type="checkbox" name="hobby" value="music">음악감상  <br>
    <input type="checkbox" name="hobby" value="game">게임 <br>

    <button id="send"> 요청 보내기! </button>

    <script>
        const $sendBtn = document.getElementById('send');
        
        $sendBtn.onclick = function(e){

            const name = document.querySelector('input[name=name]').value;
            const age = document.querySelector('input[name=age]').value;
            const hobby = document.querySelectorAll('input[name=hobby]');

            const arr=[];
            
            [...hobby].forEach($check => {
                if($check.checked){
                    arr.push($check.value);
                }
            });
           

            console.log(name);
            console.log(age);
            console.log(arr);

            //http요청을 서버에게보내는 방법
            //1.xmlHttpRequest생성
            const xhr = new XMLHttpRequest();

            /*
            2. http 요청을 설정(요청 방식, 요청 url)
            - 요청 방식
            a. GET - 조회
            b. POST - 등록
            c. PUT - 수정
            d. DELETE - 삭제
            */
            xhr.open('POST', '${pageContext.request.contextPath}/rest/getObject');

            //서버로 전송할 데이터를 제작
            //데이터 형식은 JSON이어야한다.

            //Person의 객체형식과 같게
            const data={
                'name' : name,
                'age' : age,
                'hobby' : arr
            };//이 객체를 JSON으로 변환해줘야함!

            //js->JSON : JSON.stringify(arg);
            const sendData = JSON.stringify(data);
            //전송할 데이터가 무슨 형태인지를 요청 헤더에 지정.

            xhr.setRequestHeader('content-type','application/json');

            //4.서버에 요청전송
            xhr.send(sendData);

            //5.응답 정보 확인
            xhr.onload = function(){
                //응답코드
                console.log(xhr.status);
                
                //응답데이터확인
                console.log(xhr.response);
            }


        }


    </script>

</body>
</html>