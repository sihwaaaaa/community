<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- U01TX0FVVEgyMDIzMDMyMzE1NTcwMjExMzYxOTg= -->
<!DOCTYPE html>
<html lang="ko"></html>
<html>
<head>
<%@ include file="../common/head.jsp" %>   
</head>
<body>
    <main class="container p-3 pb-5 ">
    	<div class="col-lg-4 col-md-6 mx-auto">
	    	<div class="mt-5">
	    		<h3 class="text-center my-5"><a href="${pageContext.request.contextPath}/" >더조은 아카데미</a></h3>
	    	</div>
      <div class="card">
        <div class="card-header p-5">
          <h2>My Page</h2>
        </div>
        <div class="card-body">
          <form method="post" autocomplete="off">
            <div class="mb-3 mt-3">
              <div class="d-flex justify-content-between">
	             <h4 class="d-inline-block">${member.id}</h4> <a href="#" class="btn btn-success btn-sm ms-3">비밀번호 변경</a>
              </div>
              <div class="d-flex justify-content-between mt-3">
              	<p>작성한 게시글</p> <span class="text-warning">0</span>
			  </div>
              <div class="d-flex justify-content-between">
              	<p>작성한 댓글</p> <span class="text-warning">0</span>
			  </div>
            </div>
           
            <div class="mb-3">
              <label for="name" class="form-label">Name</label>
              <input type="text" class="form-control" id="name" placeholder="Enter your name" name="name" value="${member.name}">
            </div>
            <div class="mb-3">
              <label for="email" class="form-label">email</label>
              <input type="text" class="form-control" id="email" placeholder="Enter your email" name="email" value="${member.email}">
            </div>
            <div class="mb-3">
              <label for="addr" class="form-label">address</label>
              <div class="input-group">
             	<input type="text" class="form-control" id="addr" placeholder="Enter your address" name="addr" readonly value="${member.addr}">
				<button class="btn btn-primary" type="button" data-bs-toggle="modal" data-bs-target="#myModal" id="btnAddr">주소검색</button>              
              </div>
              <input type="text" class="form-control mt-3" id="addrDetail" placeholder="상세주소" name="addrDetail" value="${member.addrDetail}" readonly>
            </div>
            <div class="mb-3">
            	<p class="text-danger samll">${param.msg}</p>
            </div>
            <button type="submit" class="btn btn-primary">Modify</button>
            <input type="hidden" value="${member.id}" name="id">
          </form>
        </div>
      </div>
      </div>
    </main>
    <!-- The Modal -->
<div class="modal" id="myModal">
  <div class="modal-dialog">
    <div class="modal-content">

      <!-- Modal Header -->
      <div class="modal-header">
        <h4 class="modal-title">주소 검색</h4>
        <br>
        <form id="jusoSearchFrm">
      		<input type="hidden" name="currentPage" value="1" />
        	<input type="hidden" name="countPerPage" value="10" />
        	<input type="hidden" name="resultType" value="json" />
          	<input type="hidden" name="confmKey" value="U01TX0FVVEgyMDIzMDMyMzE1NTcwMjExMzYxOTg=" />
          	<div class="input-group">
          		<input type="text" name="keyword" value="" class="form-control w-75" />
				<button class="btn btn-primary">검색</button>          	
          	</div>
    	</form>
      </div>

      <!-- Modal body -->
      <div class="modal-body">
    	<div class="result-search card">
    		
    	</div>
    		<ul class="pagination justify-content-center mt-3">
			  <li class="page-item"><a class="page-link" href="#">Previous</a></li>
			  <li class="page-item"><a class="page-link" href="#">1</a></li>
			  <li class="page-item"><a class="page-link" href="#">2</a></li>
			  <li class="page-item"><a class="page-link" href="#">3</a></li>
			  <li class="page-item"><a class="page-link" href="#">4</a></li>
			  <li class="page-item"><a class="page-link" href="#">5</a></li>
			  <li class="page-item"><a class="page-link" href="#">Next</a></li>
			</ul>
      </div>

      <!-- Modal footer -->
      <div class="modal-footer">
        <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Close</button>
      </div>

    </div>
  </div>
</div>
<jsp:include page="../common/footer.jsp"></jsp:include>
<script>
$(function () {	
	$("#btnAddr").click(function () {
		$("#myModal").modal("show").find(":text").focus();
		$("#addr").val("");
		$("#addrDetail").prop("readonly",false).val("");
		
	})
	$(".result-search").on("click","a",function(){
		event.preventDefault();
		let str = $(this).text();
		$("#myModal").modal("hide")
		$("#addr").val(str);
		$("#addrDetail").prop("readonly",false).val("");
	})
	$("#jusoSearchFrm").submit(function () {
		console.log("event");
		let data = $(this).serialize();
		
		$.post({
			url : "https://business.juso.go.kr/addrlink/addrLinkApiJsonp.do",
			data: data,
			dataType : "jsonp",
			crossDomain : true,
			success : function (data) {
				if(! (data.results.common.totalCount/1)){
					$(".result-search").html("검색결과가 없습니다");
				//alert("검색결과 없음")
				return;
				}
				let result = ""; 
					
	    		let arr = data.results.juso;
	    		for(var i in arr){
	    			result += ` 
	    				
	    			<div class="card-body border p-3 \${i%2 ? 'bg-secondary' : ''}">
		    			<p class="card-text text-truncate">
		    				<span class="badge bg-primary d-inline-block me-2" style="width:45px;">지번</span>
		    				<a href="" class="\${i%2 ? 'text-white' : 'text-secondary'}">\${arr[i].jibunAddr}</a>
		    			</p>
		    			<p class="card-text text-truncate">
		    				<span class="badge bg-primary me-2" >도로명</span>
		    				<a href="" class="\${i%2 ? 'text-white' : 'text-secondary'}">\${arr[i].roadAddr}</a>
		    			</p>
		    		</div>
		    		`;
	    			console.log(i,arr[i].jibunAddr,arr[i].roadAddr);
	    			
	    		}
	    		console.log(result);
	    		$(".result-search").html(result);
			}
		})
		return false;
	})
})
</script>
</body>
</html>
