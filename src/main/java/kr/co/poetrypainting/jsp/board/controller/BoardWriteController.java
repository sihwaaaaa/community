package kr.co.poetrypainting.jsp.board.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.poetrypainting.jsp.domain.Board;
import kr.co.poetrypainting.jsp.service.BoardService;
import kr.co.poetrypainting.jsp.service.BoardServiceImpl;
import kr.co.poetrypainting.jsp.util.paramSolver;

import static kr.co.poetrypainting.jsp.util.paramSolver.*;
@MultipartConfig(location = paramSolver.UPLOAD_PATH, fileSizeThreshold = 1024)
@WebServlet("/board/write")
public class BoardWriteController extends HttpServlet{
	private BoardService boardService = new BoardServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if(!isLogin(req)) {
			resp.sendRedirect(req.getContextPath() + "/member/login?href=" + URLEncoder.encode(req.getRequestURI() + "?" + req.getQueryString(), "utf-8"));
			return;
		}
		List<String> categories = new ArrayList<>(Arrays.asList("공지사항", "자유게시판", "자료실", "갤러리"));
		req.setAttribute("categories", categories);
		req.getRequestDispatcher("/WEB-INF/jsp/board/write.jsp").forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(!isLogin(req)) {
			resp.sendRedirect(req.getContextPath() + "/member/login" + URLEncoder.encode(req.getRequestURI() + "?" + req.getQueryString(), "utf-8"));
			return;
		}
		Board board = getParams(req, Board.class);
		System.out.println(board);
		boardService.register(board);
		resp.sendRedirect("list?category=" + board.getCategory() );
	}
	
}
