package co.yedam.fileboard;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import co.yedam.common.DAO;

public class FileDAO extends DAO{
	
	public List<FileVO> getFileList() {
		connect();
		List<FileVO> list = new ArrayList<>();
		String sql = "select * from fileboard order by 1";
		
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			while(rs.next()) {
				FileVO vo = new FileVO();
				vo.setAuthor(rs.getString("author"));
				vo.setDay(rs.getString("day"));
				vo.setFileName(rs.getString("fileName"));
				vo.setNum(rs.getInt("num"));
				vo.setTitle(rs.getString("title"));
				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}
	
	// 파일 업로드 처리
	// 서블릿에서 파일업로드처리, db에 저장
	public FileVO uploadFile(String author, String title, String file) {
		connect();
		String sql = "insert into fileboard values(?, ?, ?, ?, sysdate)";
		try {
			int nextNum = -1;
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select nvl(max(num), 0) + 1 from fileboard");
			if(rs.next()) {
				nextNum = rs.getInt(1);
			}
			
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, nextNum);
			psmt.setString(2, author);
			psmt.setString(3, title);
			psmt.setString(4, file);
			
			int r = psmt.executeUpdate();
			System.out.println(r + "건 입력.");
			
			FileVO vo = new FileVO();
			vo.setNum(nextNum);
			vo.setAuthor(author);
			vo.setDay(null);
			vo.setFileName(file);
			vo.setTitle(title);
			return vo;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			disconnect();
		}
	}
}
