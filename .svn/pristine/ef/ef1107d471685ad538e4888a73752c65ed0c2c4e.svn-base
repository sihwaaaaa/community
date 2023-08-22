package kr.co.poetrypainting.jsp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import kr.co.poetrypainting.jsp.domain.Reply;
import kr.co.poetrypainting.jsp.service.ReplyService;
import kr.co.poetrypainting.jsp.service.ReplyServiceImpl;
import kr.co.poetrypainting.jsp.util.paramSolver;

@WebServlet("/reply")
public class ReplyController extends HttpServlet{
	private ReplyService service = new ReplyServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long bno =  Long.valueOf(req.getParameter("bno"));
		List<Reply> replies = service.list(bno);
		Gson gson = new Gson();
		String json = gson.toJson(replies);
		resp.setContentType("application/json; charset=utf-8");
		resp.getWriter().print(json);
	}
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//로그인여부
		//그래서 그게 내꺼냐?
		Long rno = Long.valueOf(req.getParameter("rno"));
		PrintWriter out = resp.getWriter();
		Reply reply = service.get(rno);
		if(reply != null && paramSolver.isMine(req, reply.getWriter())) {
			out.print(service.remove(rno));
		}
		else {
			out.print(0);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Reply reply = paramSolver.getParams(req, Reply.class);
		service.register(reply);
	}
	//http메서드
	//get, post, put(patch), delete
	
}
