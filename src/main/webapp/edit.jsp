<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="data.DataBaseUtil" %>
<%@ page import="model.Post" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Post</title>
</head>
<body>
    <h1>Edit Post</h1>
    <%
        int id = Integer.parseInt(request.getParameter("id"));
        Post post = null;

        // データベースから投稿を取得
        try {
            Connection conn = DataBaseUtil.getConnection();
            String query = "SELECT * FROM posts WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                post = new Post(rs.getInt("id"), rs.getString("title"), rs.getString("content"));
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    %>

    <form action="post" method="post">
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="id" value="<%= post.getId() %>">
        <label for="title">Title:</label>
        <input type="text" id="title" name="title" value="<%= post.getTitle() %>" required><br>
        <label for="content">Content:</label>
        <textarea id="content" name="content" required><%= post.getContent() %></textarea><br>
        <input type="submit" value="Update Post">
    </form>

    <a href="index.jsp">Back to List</a>
</body>
</html>