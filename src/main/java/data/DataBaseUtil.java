package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Post;

public class DataBaseUtil {

    private static final String URL = "jdbc:mysql://localhost:3306/blogapplv1";
    private static final String USER = "root"; // MySQLのユーザー名
    private static final String PASSWORD = "MySQL@2024"; // MySQLのパスワード

    static {
        try {
            // JDBCドライバの登録
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // データベースへの接続を取得するメソッド
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // 接続をクローズするメソッド
    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 投稿の追加
    public static void addPost(String title, String content) throws SQLException {
        String query = "INSERT INTO posts (title, content) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, title);
            stmt.setString(2, content);
            stmt.executeUpdate();
        }
    }

    // 投稿の編集
    public static void updatePost(int id, String title, String content) throws SQLException {
        String query = "UPDATE posts SET title = ?, content = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, title);
            stmt.setString(2, content);
            stmt.setInt(3, id);
            stmt.executeUpdate();
        }
    }

    // 投稿の削除
    public static void deletePost(int id) throws SQLException {
        String query = "DELETE FROM posts WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }


    public static ArrayList<Post> getAllPosts() throws SQLException {
        ArrayList<Post> posts = new ArrayList<>();
        Connection conn = getConnection();
        String query = "SELECT * FROM posts";
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Post post = new Post();
            post.setId(rs.getInt("id"));
            post.setTitle(rs.getString("title"));
            post.setContent(rs.getString("content"));
            posts.add(post);
        }
        rs.close();
        stmt.close();
        conn.close();
        return posts;
    }

}
