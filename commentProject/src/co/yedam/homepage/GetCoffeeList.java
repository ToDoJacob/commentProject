package co.yedam.homepage;

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

@WebServlet("/GetCoffeeList")
public class GetCoffeeList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetCoffeeList() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8"); // 한글쓰기위해 그리고 메소드의 최상단에 위치
		
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().create(); // 제이슨형태 쓰기위해
		
		
		CoffeeDAO dao = new CoffeeDAO();
		List<Coffee> list = dao.getProdList();
		
		out.println(gson.toJson(list));
		
		}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
