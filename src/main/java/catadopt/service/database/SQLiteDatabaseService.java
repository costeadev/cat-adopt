package catadopt.service.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import catadopt.model.Cat;
import catadopt.service.DatabaseService;

public class SQLiteDatabaseService implements DatabaseService {

	private static String url = "jdbc:sqlite:mi_base.db";

	public boolean createTable() {

		String sql = """
				CREATE TABLE IF NOT EXISTS adopted_cats(
				id TEXT PRIMARY KEY,
				name TEXT,
				image_url TEXT
				);
				""";

		try (Connection conn = DriverManager.getConnection(url); 
				Statement stms = conn.createStatement()) {

			stms.execute(sql);

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;

	}

	@Override
	public void adoptCat(Cat cat) {

		String sql = """
				INSERT INTO adopted_cats(id, name, image_url)
				VALUES (?,?,?);
				""";

		try (Connection conn = DriverManager.getConnection(url); 
				PreparedStatement pstms = conn.prepareStatement(sql)) {

			pstms.setString(1, cat.getId());
			pstms.setString(2, cat.getName());
			pstms.setString(3, cat.getImageUrl());

			pstms.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<Cat> getAdoptedCats() {

		List<Cat> cats = new ArrayList<>();

		String sql = """
				SELECT * FROM adopted_cats;
				""";

		try (Connection conn = DriverManager.getConnection(url);
				Statement stms = conn.createStatement();
				ResultSet rs = stms.executeQuery(sql)) {

			while (rs.next()) {

				cats.add(new Cat(rs.getString("id"), 
								 rs.getString("name"), 
								 rs.getString("image_url")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return cats;
	}

	@Override
	public void renameCat(Cat cat) {

		String sql = """
				UPDATE adopted_cats 
				SET name = ? 
				WHERE id = ?;
				""";

		try (Connection conn = DriverManager.getConnection(url); 
				PreparedStatement pstms = conn.prepareStatement(sql)) {

			pstms.setString(1, cat.getName());
			pstms.setString(2, cat.getId());

			pstms.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public void start() {
		createTable();
	}
}
