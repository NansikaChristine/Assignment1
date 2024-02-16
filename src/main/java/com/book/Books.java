package com.book;

import com.book.Main;

import java.sql.*;
import java.util.List;

public class Books {
    private int id;
    private String bookName;
    private String authorName;
    private String language;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public static void TruncateTableBooks() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/books", "root", "");
            if (connection != null) {
                String truncateQuery = "TRUNCATE TABLE books";
                PreparedStatement truncateStatement = connection.prepareStatement(truncateQuery);
                truncateStatement.executeUpdate(truncateQuery);
            }
        } catch (SQLException e) {
            System.err.println("Can not connect to database!");
            e.printStackTrace();
        }
    }
//    public void Database() {
//        Connection connection = null;
//        try {
//            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/books", "root", "");
//            if (connection != null) {
//                String insertQuery = "INSERT INTO books (id, bookName, authorName, language) VALUES (?, ?, ?, ?)";
//                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
//                preparedStatement.setInt(1, id);
//                preparedStatement.setString(2, bookName);
//                preparedStatement.setString(3, authorName);
//                preparedStatement.setString(4, language);
//                preparedStatement.executeUpdate();
//
//            }
//        } catch (SQLException e) {
//            System.err.println("Can not connect to database!");
//            e.printStackTrace();
//        }
//    }
//}

    public void Database() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/books", "root", "");
            if (connection != null) {
                // Check if the book with the same ID already exists in the database
                if (!isBookIdExists(id, connection)) {
                    String insertQuery = "INSERT INTO books (id, bookName, authorName, language) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                        preparedStatement.setInt(1, id);
                        preparedStatement.setString(2, bookName);
                        preparedStatement.setString(3, authorName);
                        preparedStatement.setString(4, language);
                        preparedStatement.executeUpdate();
                    }
                } else {
                    System.out.println("Duplicate book with ID " + id + ". Skipping insertion.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Can not connect to database!");
            e.printStackTrace();
        } finally {
            // Close the connection in the finally block
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Helper method to check if a book with the given ID already exists in the database
    private boolean isBookIdExists(int bookId, Connection connection) throws SQLException {
        String checkQuery = "SELECT COUNT(*) FROM books WHERE id = ?";
        try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
            checkStatement.setInt(1, bookId);
            try (ResultSet resultSet = checkStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }
}