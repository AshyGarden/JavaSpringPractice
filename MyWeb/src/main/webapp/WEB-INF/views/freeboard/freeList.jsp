<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

    <%@ include file="../include/header.jsp" %>
    <section>
        <div class="container-fluid">
            <div class="row">
                <!--lg에서 9그리드, xs에서 전체그리드-->   
                <div class="col-lg-9 col-xs-12 board-table">
                    <div class="titlebox">
                        <p>자유게시판</p>
                    </div>
                    <hr>
                    
                    <!--form select를 가져온다 -->
                    <form action="<c:url value='/freeboard/freeList' />">
			  			<div class="search-wrap">
	                       <button type="submit" class="btn btn-info search-btn">검색</button>
	                       <input type="text" name="keyword" class="form-control search-input" value="${pc.paging.keyword}">
	                       <select name="condition" class="form-control search-select">
	                            <option value="title" ${pc.paging.condition == 'title' ? 'selected' : ''}>제목</option>
	                            <option value="content" ${pc.paging.condition == 'content' ? 'selected' : ''}>내용</option>
	                            <option value="writer" ${pc.paging.condition == 'writer' ? 'selected' : ''}>작성자</option>
	                            <option value="titleContent"${pc.paging.condition == 'titleContent' ? 'selected' : ''}>제목+내용</option>
	                      	</select>
	                   	</div>
		   			</form>
                   
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>번호</th>
                                <th class="board-title">제목</th>
                                <th>작성자</th>
                                <th>등록일</th>
                                <th>수정일</th>
                            </tr>
                        </thead>
                        <tbody>
                           <c:forEach var="vo" items="${boardList}">
                          <tr>
	                           <td>${vo.bno}</td>
	                           <td>
	                           		<!-- <a href="${pageContext.request.contextPath}/freeboard/content/${vo.bno}">${vo.title}</a> -->
	                           		<a href="${pageContext.request.contextPath}/freeboard/content/${vo.bno}?pageNum=${pc.paging.pageNum}&cpp=${pc.paging.cpp}&keyword=${pc.paging.keyword}&condition=${pc.paging.condition}">${vo.title}</a>
                                    &nbsp;
                                    <strong>[${vo.replyCnt}]</strong>
	                           </td>
	                           <td>${vo.writer}</td>
	                           <td>
	                           		<fmt:parseDate value="${vo.regDate}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="parsedDateTime" type="both"/>
	                           		<fmt:formatDate value="${parsedDateTime}" pattern="yyyy년 MM월 dd일 HH시 mm분"/>
	                           </td>
                         	   <td>
                         	   		<fmt:parseDate value="${vo.updateDate}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="parsedUpdateTime" type="both"/>
	                           		<fmt:formatDate value="${parsedUpdateTime}" pattern="yyyy년 MM월 dd일 HH시 mm분"/>
	                          </td>
                          	</tr>                           
                           </c:forEach>
                        </tbody>
                        
                    </table>


                    <!--페이지 네이션을 가져옴-->
		    <form action="${pageContext.request.contextPath}/freeboard/freeList" name="pageForm">
                    <div class="text-center">
                    <hr>
                    <ul id="pagination" class="pagination pagination-sm">
                    	<c:if test="${pc.prev}">
                        	<li><a href="#" data-pagenum="${page.beginPage-1}">이전</a></li>
                        </c:if>
                        
                        <c:forEach var="num" begin="${pc.beginPage}" end="${pc.endPage}">
                        	<li  class="${pc.paging.pageNum == num? 'active': ''}"><!-- 사용자가 보고있는 페이지가  NUM과 같은 번호라면 활성화 나머지 비활성화 -->
                       			 <a href="#"data-pagenum="${num}">${num}</a>
                       		 </li>
                        </c:forEach>
                        
                        <c:if test="${pc.next}">
                        	<li><a href="#" data-pagenum="${page.endPage+1}">다음</a></li>
                        </c:if>
                    </ul>
                    <button type="button" class="btn btn-info" onclick="location.href='${pageContext.request.contextPath}/freeboard/regist'">글쓰기</button>
                    </div>

                    <input type="hidden" name="pageNum" value="${pc.paging.pageNum}">
                    <input type="hidden" name="cpp" value="${pc.paging.cpp}">
                    <input type="hidden" name="keyword" value="${pc.paging.keyword}">
                    <input type="hidden" name="condition" value="${pc.paging.condition}">
		    </form>

                </div>
            </div>
        </div>
	</section>
<%@ include file="../include/footer.jsp" %>

<script>
    //브라우저 창이 로딩이 완료된후에 실행할것을 보장하는 Event
    window.onload = function(){
       /* 사용자가 페이지 관련 버튼을 클릭했을때,
        기존에는 각각의 a태그의 href에다가 각각다른 url을 작성해서 보내줬다면,
        이번에는 클릭한 그버튼이 무엇인지 확인해서 그 버튼에 맞는 페이지정보를
        js로 끌고와서 요청을 보내기로함.*/
        document.getElementById('pagination').addEventListener('click', e => {
           
            if(!e.target.matches('a')){
                return;
            }

            e.preventDefault(); //a태그 고유기능 중지.

            //현재 이벤트가 발생한 요소(버튼)의
            //data-pagenum의 값을 얻어서 변수에 저장

            const value = e.target.dataset.pagenum;

			//페이지 버튼들을 감싸고 있는 form태그를 지목하여, 
            //그안에 숨겨져있는 pageNum이란 input태그의 value에
            //위에서 얻은 data-pagenum값을 삽입한 후 submit
            document.pageForm.pageNum.value = value;
            document.pageForm.submit();


        });


    }

</script>
