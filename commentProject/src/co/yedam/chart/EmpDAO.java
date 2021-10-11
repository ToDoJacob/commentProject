package co.yedam.chart;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import co.yedam.common.DAO;

public class EmpDAO extends DAO{
	
	public Map<String, Integer> getDeptByEmp() {
		
        connect();
        Map<String, Integer> map = new HashMap<String, Integer>();
        String sql = "select d.department_name, count(1)\r\n"
        		+ "from employees e\r\n"
        		+ "join departments d\r\n"
        		+ "on e.department_id = d.department_id\r\n"
        		+ "group by d.department_name";
        try {
           psmt = conn.prepareStatement(sql);
           rs = psmt.executeQuery();

           while ( rs.next()) {
        	   map.put(rs.getString(1), rs.getInt(2));
           }

        } catch (SQLException e) {
           e.printStackTrace();
        } finally {
           disconnect();
        }
        return map;
     }
	
}
