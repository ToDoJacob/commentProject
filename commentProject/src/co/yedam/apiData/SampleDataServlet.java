package co.yedam.apiData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SampleDataServlet")
public class SampleDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SampleDataServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
//		numberOfRow: 10,
//		pageNo: 2,
//		startDate: '20211001',
//		endDate: '20211010'
		String nor = request.getParameter("numberOfRow");
		String pn = request.getParameter("pageNo");
		String sd = request.getParameter("startDate");
		String ed = request.getParameter("endDate");
		
		String hostUrl = "http://openapi.data.go.kr/openapi/service/rest"
				+ "/Covid19/getCovid19InfStateJson?serviceKey=PBJEF8NRTX6o"
				+ "HXWPat8PMnQhedDS%2Fk9i4y8vMhtx36BX21ZndgEETUR2WIBjjIhFs8"
				+ "4nQJWJkghvQlpr8ImDzg%3D%3D";
		hostUrl += "&pageNo=" + pn;
		hostUrl += "&numOfRows=" + nor;
		hostUrl += "&startCreateDt=" + sd;
		hostUrl += "&endCreateDt=" + ed; //api데이터 주소
//		hostUrl += "ateDt=20200310&endCreateDt=20200315"; //너무길면 뒤에다가 +=로 붙여줘도 된다.
		URL url = new URL(hostUrl);
		HttpURLConnection urlconn = (HttpURLConnection) url.openConnection();
		urlconn.setRequestMethod("GET");
		//빠른 처리를 위해 버퍼리더의 매개값으로 던져준다.
		BufferedReader br = new BufferedReader(new InputStreamReader(urlconn.getInputStream(), "UTF-8"));
		String result = "", line = "";
		//버퍼에있는 데이터를 루핑돌면서 result에 담는다.
		//한 라인씩 읽어들인다.더이상 읽을 부분이 없으면 readLine이 null을 리턴한다. 그래서 null이 아닐때 까지만 루핑돈다
		while((line = br.readLine()) != null) {
			result += line + "\n";
		}
		
		response.getWriter().println(result);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
