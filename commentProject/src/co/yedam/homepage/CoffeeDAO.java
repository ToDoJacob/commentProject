package co.yedam.homepage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import co.yedam.common.DAO;

public class CoffeeDAO extends DAO{

		public List<Coffee> getProdList() {    ///DB에서 리스트 가지고오는 메소드
			connect();
			List<Coffee> list = new ArrayList<>();
			String sql = "select * from coffee order by 1";
			
			try {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				while(rs.next()) {
					Coffee coffee = new Coffee();
					coffee.setLikeIt((rs.getDouble("like_it")));
					coffee.setOffPrice(rs.getInt("off_price"));
					coffee.setOriginPrice(rs.getInt("origin_price"));
					coffee.setProdDescription(rs.getString("prod_description"));
					coffee.setProdId(rs.getInt("prod_id"));
					coffee.setProdImage(rs.getString("prod_image"));
					coffee.setProdName(rs.getString("prod_name"));
					
					list.add(coffee);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				disconnect();
			}
			return list;
		}
}
