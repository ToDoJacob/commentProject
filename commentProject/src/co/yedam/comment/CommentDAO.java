package co.yedam.comment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import co.yedam.common.DAO;

public class CommentDAO extends DAO {
	
	private static CommentDAO singleton = new CommentDAO();
	
	private CommentDAO() {
		
	}
	
	public static CommentDAO getInstance() {
		return singleton;
	}
	// 글목록 가지고와서 리턴해주기 위한 메소드
	public List<Comment> getCommentList() {
		connect();
		List<Comment> list = new ArrayList<>();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from comments order by 1");
			while(rs.next()) {
				Comment cmt = new Comment();
				cmt.setId(rs.getString("id"));
				cmt.setContent(rs.getString("content"));
				cmt.setName(rs.getString("name"));
				list.add(cmt); //데이터 컬렉션을 다가지고와서 list에 담아준다.
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}	
	
	// 글입력.. 1:현재id 2:id+1값으로 글등록 3:현재id값 변경
	public Comment insertComment(Comment comment) { //매개값 받아와야 한다 이름과 content 받아오기위해
		connect();
		int currId = 0;
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			stmt = conn.createStatement();
			 //현재 id값가지고오는 쿼리
			rs = stmt.executeQuery("select value from id_repository where name='COMMENT'");
			if(rs.next()) {
				currId = rs.getInt("value"); //현재의 id값을 가지고온걸 currId변수에 담음
			}
			currId++; //현재의 id값에 1씩증가 시키기
			psmt = conn.prepareStatement("insert into comments values(?,?,?)");
			psmt.setInt(1, currId); // 1증가시킨 id값으로 넣어줌
			psmt.setString(2, comment.getName()); //서블릿에서 받아온 이름 으로 바꿔줌
			psmt.setString(3, comment.getContent());
			int r = psmt.executeUpdate();
			System.out.println(r +"건 입력.");
			
			psmt = conn.prepareStatement("update id_repository set value=? where name = 'COMMENT'");
			psmt.setInt(1, currId);
			r = psmt.executeUpdate();
			System.out.println(r + "건 변경.");
			
			conn.commit();
			comment.setId(String.valueOf(currId)); //int타입을 string타입으로 변경
			return comment; //매개값으로 받았던 comment에 id값 추가해서 다시 리턴 
				//정상적으로 처리가되면 commnet리턴
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();		//오류가 생기면 rollback 키는 것 (이것도 try catch해줘야함)
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return null; //정상적으로 처리가안되면 null리턴
		} finally {
			disconnect();
		}
	}
	
	// 글수정 
	public Comment updateComment(Comment comment) {
		connect();
		String sql = "update comments set name=?, content=? where id=?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, comment.getName());
			psmt.setString(2, comment.getContent());
			psmt.setString(3, comment.getId());
			int r = psmt.executeUpdate();
			System.out.println(r + "건 변경.");
			return comment;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
			
		} finally {
			disconnect();
		}
	}	
	
	// 글삭제
	public String deleteComment(String id) {
		connect();
		try {
			psmt = conn.prepareStatement("delete from comments where id=?");
			psmt.setString(1, id);
			int r = psmt.executeUpdate();
			System.out.println(r + "건 삭제.");
			return id;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			disconnect();
		}
	}	
}
