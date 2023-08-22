<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko"></html>
<html>
<head>
<%@ include file="../common/head.jsp"%>
</head>
<body>
  <div id="main-wrapper" data-layout="vertical" data-navbarbg="skin5" data-sidebartype="full"
        data-sidebar-position="absolute" data-header-position="absolute" data-boxed-layout="full">
	<%@ include file="../common/header.jsp"%>
	<main class="container p-3 pb-5">
		<div class="card">
			<div class="card-header p-5">
				<h2>Board View</h2>
			</div>
			<div class="card-body">
				<form method="post">
					<div class="mb-3 mt-3">
						<label for="title" class="form-label">title</label> <input
							type="text" class="form-control" id="title"
							placeholder="Enter title" name="title" readonly
							value="${board.title}">
					</div>
					<div class="mb-3">
						<label for="content" class="form-label">content</label>
						<textarea class="form-control" id="content"
							placeholder="Enter content" name="content" rows="10" readonly>${board.content}</textarea>
					</div>
					<div class="mb-3">
						<label for="writer" class="form-label">writer</label> <input
							type="text" class="form-control" id="writer"
							placeholder="Enter writer" name="writer" value="${board.writer}"
							readonly>
					</div>
					<div class="mb-3">
						<p class="form-label">files</p>
						<c:forEach items="${board.attachs}" var="attach">
						<p><a href="${pageContext.request.contextPath}/download?${attach.queryString}">${attach.origin}</a></p>
						<p></p>
						</c:forEach>
						
					</div>
					<div class="text-center">
						<a href="modify?bno=${board.bno}&${cri.fullQueryString}"
							class="btn btn-warning">수정</a> <a
							href="remove?bno=${board.bno}&${cri.fullQueryString}"
							class="btn btn-danger btn-remove">삭제</a> <a
							href="list?${cri.fullQueryString}"
							class="btn btn-outline-primary">목록으로</a>
					</div>
					<div class="mb -3" id="replyArea">
						<p class="form-label mb-4 border-bottom pb-3">
							<i class="far fa-comment-dots"></i> replies
						</p>
						<div class="px-5 row mb-5">
							<textarea class="form-control mb-2" id="commentArea"
								placeholder="Enter comments" rows="3" ></textarea>
							<button type="button" class="btn btn-primary opacity-75">댓글
								작성</button>
						</div>
						<ul class="container replies">
							<li class="list-unstyled px-4">
								<div class="mb-4 samll text-center ">
									<p>댓글이 없습니다</p>
								</div>
							</li>
						</ul>
					</div>
				</form>
			</div>

		</div>
	</main>
	</div>
	<%@ include file="../common/footer.jsp"%>
	<script>
		$(".btn-remove").click(function() {
			return confirm("정말 삭제 하시겠습니까?");
		});
		let contextPath = '${pageContext.request.contextPath}/';
		let replyPath = contextPath + "/reply"
		let bno = '${board.bno}';
		let writer ='${member.id}';
		
		showList();
		function showList(){
			
			$.ajax({
				url : replyPath,
				data : {bno : bno},
				success : list
			})
		}
		//댓글 목록
	function list(replies) {
			
			
		//let jsonStr = '[{"rno":1,"content":"댓글댓글","regDate":"3월 13, 2023","writer":"id1","bno":271},{"rno":2,"content":"댓글댓글","regDate":"3월 13, 2023","writer":"id1","bno":271},{"rno":3,"content":"댓글댓글","regDate":"3월 13, 2023","writer":"id1","bno":271},{"rno":4,"content":"댓글댓글","regDate":"3월 13, 2023","writer":"id1","bno":271}]'
		//let replies = JSON.parse(jsonStr);
		console.log(replies);
		let str ="";
		if(!replies.length){
			str = `<li class="list-unstyled px-4">
				<div class="mb-4 samll text-center ">
				<p>댓글이 없습니다</p>
			</div>
		</li>`;
			$(".replies").html(str);
			return;
		}
		for(let i in replies){
			let r = replies[i];
			let isMine = writer === r.writer;
			//let isMine = true;
			str += `
				<li class="list-unstyled px-4" data-rno="\${r.rno}">
					<div class="row">
						<a class="text-muted small mb-3 col text-decoration-none" href="">
							<span class="fs-6 fw-bold">\${r.writer}</span>
							<span class="mx-5">\${r.regDate}</span>
						</a>
						<div class="col text-end">`;
			str +=			isMine ? '<a href="" class="text-danger" ><i class="fas fa-eraser"></i></a>':''
			str +=		`</div>
					</div>
					<div class="mb-4 samll">
						<p>\${r.content}</p>
					</div>
				</li>
			`;
		}
		$(".replies").html(str);
	}
	//댓글작성
	$("#commentArea").next().click(function(){
		//console.log($("#commentArea").val());
		let content = $("#commentArea").val();
		if(!writer){
			alert("로그인 후 작성하세요")
			location.href = contextPath + "/member/login?href=" + encodeURIComponent(location.pathname + location.search  + '#replyArea');
			return;
		}
		else if(!content){
			alert("댓글 내용을 작성하세요")
			return;
		}
		$.ajax({
			url : replyPath,
			data : {bno : bno, content : content, writer:writer},
			method:"POST",
			success : function(data){
				alert("댓글이 성공적으로 작성되었습니다")
				$("#commentArea").val("");
				showList();
				
			}
			
		})
	})
	//댓글삭제
	$(".replies").on("click", "li a.text-danger" , function(){
		event.preventDefault();
		if(!confirm("댓글을 삭제하시겠습니까?")){
			return false;
		}
		console.log($(this).closest("li").data("rno"));
		let rno = $(this).closest("li").data("rno");
		$.ajax({
			url : replyPath + "?rno=" + rno,
			method:"DELETE",
			success : function(data){
				if(data == 1){
				alert("댓글이 성공적으로 삭제되었습니다")
				showList();
				}else{
				alert("댓글 삭제를 실패했습니다")
				showList();
					
				}
				
				
			}
			
		})
		
	})
	
	</script>
</body>
</html>
