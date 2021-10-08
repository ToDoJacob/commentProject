package co.yedam.comment;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebServlet("/CommentServlet")
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CommentServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8"); // 요청할때 한글 먹히게 하는것
		response.setCharacterEncoding("UTF-8"); // 데이터를 응답할때 한글먹히게 하는것
		response.setContentType("text/html; charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().create(); //gson.jar 라이브러리속 클래스
		
		String cmd = request.getParameter("cmd"); // 사용자가 넘겨주는 값 parameter
		CommentDAO dao = CommentDAO.getInstance();
		
		if(cmd == null) { //사용자가 넘겨주는 값이 null이면 
			out.println("<h1>빈페이지입니다</h1>");
			
		} else if (cmd.equals("list")) {
			System.out.println("<h1>리스트페이지입니다</h1>");
			List<Comment> list = dao.getCommentList();
			
			out.println(gson.toJson(list));
			
		} else if (cmd.equals("add")) {
			System.out.println("<h1>추가페이지입니다</h1>");
			String name = request.getParameter("name");
			String content = request.getParameter("content"); //이름과 content가 넘어오면그데이터로
			
			Comment comment = new Comment();
			comment.setName(name);
			comment.setContent(content);
			
			dao.insertComment(comment); //인서트작업하고 id값 1증가해서 다시 돌려주는 역할
			out.println(gson.toJson(comment)); //가져온 데이터를 제이슨타입으로변경
			
		} else if (cmd.equals("mod")) {
			System.out.println("<h1>수정페이지입니다</h1>");
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			String content = request.getParameter("content");
			
			Comment comment = new Comment();
			comment.setId(id);
			comment.setName(name);
			comment.setContent(content);
			
			dao.updateComment(comment);
			out.println(gson.toJson(comment));
			
		} else if (cmd.equals("del")) {
			System.out.println("<h1>삭제페이지입니다</h1>");
			String id = request.getParameter("id");
			
			if (dao.deleteComment(id) == null) {
				// {"retCode":"fail"}
				out.println("{\"retCode\":\"fail\"}");
				return;
			}
			out.println("{\"retCode\":\"success\"}");
		} else {
			out.println("<h1>" + cmd + "</h1>");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
