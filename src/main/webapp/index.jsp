<%@ page import="java.util.List" %>
<%@ page import="model.Post" %>

<!DOCTYPE html>
<html>
<head>
    <title>Post List</title>
</head>
<body>

    <h1>Welcome to My Blog</h1>

    <!-- 投稿フォーム -->
    <h2>Add a New Post</h2>
    <form action="post" method="post">
        <input type="hidden" name="action" value="add">
        <label for="title">Title:</label>
        <input type="text" id="title" name="title" required><br>
        <label for="content">Content:</label>
        <textarea id="content" name="content" required></textarea><br>
        <input type="submit" value="Add Post">
    </form>
    
    <h2>Post List</h2>
    <ul>
        <%
            List<Post> posts = (List<Post>) request.getAttribute("posts");
            if (posts != null) {
                for (Post post : posts) {
        %>
                    <li>
                        <%= post.getTitle() %><br>
                        <%= post.getContent() %>
                        <form action="post" method="get" style="display:inline;">
                            <input type="hidden" name="action" value="edit">
                            <input type="hidden" name="id" value="<%= post.getId() %>">
                            <input type="submit" value="Edit">
                        </form>
                        <form action="post" method="post" style="display:inline;">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="id" value="<%= post.getId() %>">
                            <input type="submit" value="Delete">
                        </form>
                    </li>
        <%
                }
            } else {
        %>
                <p>No posts available.</p>
        <%
            }
        %>
    </ul>
</body>
</html>
