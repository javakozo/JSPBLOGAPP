package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import data.DataBaseUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Post;

@WebServlet("/post")
public class PostServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("edit".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            // 投稿の取得と編集ページの表示
            Post post = getPostById(id);
            req.setAttribute("post", post);
            req.getRequestDispatcher("edit.jsp").forward(req, resp);
        } else {
            // 投稿一覧ページの表示
            listPosts(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("add".equals(action)) {
            String title = req.getParameter("title");
            String content = req.getParameter("content");
            try {
                DataBaseUtil.addPost(title, content);
                resp.sendRedirect("post"); // 投稿後に一覧ページにリダイレクト
            } catch (SQLException e) {
                e.printStackTrace();
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "データベースエラーが発生しました。");
            }
        } else if ("update".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            String title = req.getParameter("title");
            String content = req.getParameter("content");
            try {
                DataBaseUtil.updatePost(id, title, content);
                resp.sendRedirect("post"); // 編集後に一覧ページにリダイレクト
            } catch (SQLException e) {
                e.printStackTrace();
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "データベースエラーが発生しました。");
            }
        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            try {
                DataBaseUtil.deletePost(id);
                resp.sendRedirect("post"); // 削除後に一覧ページにリダイレクト
            } catch (SQLException e) {
                e.printStackTrace();
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "データベースエラーが発生しました。");
            }
        }
    }

    // 投稿の取得
    private Post getPostById(int id) {
        // データベースから投稿を取得するロジックを実装
        // 例: DataBaseUtil.getPostById(id);
        return new Post(id, "Sample Title", "Sample Content");
    }

    // 投稿一覧の取得と表示
    private void listPosts(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ArrayList<Post> posts = DataBaseUtil.getAllPosts();
            req.setAttribute("posts", posts);
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "データベースエラーが発生しました。");
        }
    }

}