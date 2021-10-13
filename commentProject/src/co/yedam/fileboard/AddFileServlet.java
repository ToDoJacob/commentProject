package co.yedam.fileboard;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@WebServlet("/AddFileServlet")
public class AddFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddFileServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = getServletContext();
		
		//요청정보에 한글이 포함돼있으면 요청정보에 인코딩해주기
		request.setCharacterEncoding("UTF-8");
		//응답정보에 한글이 포함돼있으면 응답정보도 인코딩하기
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		String saveDir = context.getRealPath("upload"); //
		int maxSize = 1024 * 1024 * 30; // 1024Byte = 1kilo Byte 1024 KB = 1 MB
		String encoding = "UTF-8";
		MultipartRequest multi = new MultipartRequest(request, saveDir, maxSize, encoding, 
				new DefaultFileRenamePolicy()); 
		// 1.request 요청정보, saveDir 저장위치, maxFileSize 최대파일사이즈, encode UTF-8, renamePolicy똑같은파일명이여도 구분하게끔하는것
		
		String author = multi.getParameter("author");
		String title = multi.getParameter("title");
		String file = multi.getFilesystemName("file");
		
		FileDAO dao = new FileDAO();
		FileVO vo = dao.uploadFile(author, title, file);
		
		Gson gson = new GsonBuilder().create();
		response.getWriter().println(gson.toJson(vo));
		
		System.out.println(saveDir);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
