package kr.co.poetrypainting.jsp.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.poetrypainting.jsp.domain.Criteria;
import kr.co.poetrypainting.jsp.service.BoardService;
import kr.co.poetrypainting.jsp.service.BoardServiceImpl;
import kr.co.poetrypainting.jsp.util.paramSolver;

@WebServlet("/board/remove")
public class BoardRemoveController extends HttpServlet{
	private BoardService boardService = new BoardServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Criteria cri = paramSolver.getParams(req, Criteria.class);
		
		boardService.remove(Long.valueOf(req.getParameter("bno")));
		resp.sendRedirect("list" + "?" + cri.getFullQueryString());
	}
	
}
