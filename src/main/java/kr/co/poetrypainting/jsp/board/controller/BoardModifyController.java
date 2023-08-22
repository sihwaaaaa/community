package kr.co.poetrypainting.jsp.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.poetrypainting.jsp.domain.Board;
import kr.co.poetrypainting.jsp.domain.Criteria;
import kr.co.poetrypainting.jsp.service.BoardService;
import kr.co.poetrypainting.jsp.service.BoardServiceImpl;
import kr.co.poetrypainting.jsp.util.paramSolver;

@WebServlet("/board/modify")
public class BoardModifyController extends HttpServlet{
	private BoardService boardService = new BoardServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(req.getSession().getAttribute("member") == null) {
			resp.sendRedirect(req.getContextPath() + "/member/login");
			return;
		}
		req.setAttribute("cri", paramSolver.getParams(req, Criteria.class));
		req.setAttribute("board", boardService.get(Long.valueOf(req.getParameter("bno"))));
		req.getRequestDispatcher("/WEB-INF/jsp/board/modify.jsp").forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Criteria cri = paramSolver.getParams(req, Criteria.class);
		Board board = paramSolver.getParams(req, Board.class);
		boardService.modify(board);
		resp.sendRedirect("view?bno=" + board.getBno() +"&" + cri.getFullQueryString());
	}
	
}
