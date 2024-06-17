package com.bluevelvetmusicstore.dao;

import com.bluevelvetmusicstore.model.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private Connection connection;

    public ProductDAO(Connection connection) {
        this.connection = connection;
    }

    public int createProduct(Product product) throws SQLException {
        String sql = "INSERT INTO Product (name, short_description, brand, category, list_price, cost) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getShortDescription());
            stmt.setString(3, product.getBrand());
            stmt.setString(4, product.getCategory());
            stmt.setDouble(5, product.getListPrice());
            stmt.setDouble(6, product.getCost());
            stmt.executeUpdate();

            // Commit the transaction
            connection.commit();

            // Retrieve the generated ID
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating product failed, no ID obtained.");
                }
            }
        }
    }

    public List<Product> listProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM Product";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                products.add(mapRowToProduct(rs));
            }
        }
        return products;
    }

    public List<Product> searchProducts(String query) throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM Product WHERE name LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + query + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    products.add(mapRowToProduct(rs));
                }
            }
        }
        return products;
    }

    public List<Product> getProductsInRange(int start, int end) throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM Product LIMIT ? OFFSET ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, end - start);
            stmt.setInt(2, start);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getInt("id"));
                    product.setName(rs.getString("name"));
                    product.setShortDescription(rs.getString("short_description"));
                    product.setBrand(rs.getString("brand"));
                    product.setCategory(rs.getString("category"));
                    product.setListPrice(rs.getDouble("list_price"));
                    product.setCost(rs.getDouble("cost"));
                    products.add(product);
                }
            }
        }
        return products;
    }


    public void updateProduct(Product product) throws SQLException {
        String sql = "UPDATE Product SET name=?, short_description=?, brand=?, category=?, list_price=?, cost=? WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getShortDescription());
            stmt.setString(3, product.getBrand());
            stmt.setString(4, product.getCategory());
            stmt.setDouble(5, product.getListPrice());
            stmt.setDouble(6, product.getCost());
            stmt.setInt(7, product.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteProduct(int id) throws SQLException {
        String sql = "DELETE FROM Product WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private Product mapRowToProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getInt("id"));
        product.setName(rs.getString("name"));
        product.setShortDescription(rs.getString("short_description"));
        product.setBrand(rs.getString("brand"));
        product.setCategory(rs.getString("category"));
        product.setListPrice(rs.getDouble("list_price"));
        product.setCost(rs.getDouble("cost"));
        return product;
    }

    public int getTotalProductCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM Product";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }


}