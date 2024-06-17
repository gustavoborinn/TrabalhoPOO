package com.bluevelvetmusicstore.controller;

import com.bluevelvetmusicstore.dao.ProductDAO;
import com.bluevelvetmusicstore.model.Product;
import com.bluevelvetmusicstore.util.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ProductController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField brandField;
    @FXML
    private TextField categoryField;
    @FXML
    private TextField listPriceField;
    @FXML
    private TextField costField;
    @FXML
    private TextField searchField;
    @FXML
    private TableView<Product> productTable;
    @FXML
    private Pagination pagination;

    private ProductDAO productDAO;
    private Connection connection;

    private static final int ROWS_PER_PAGE = 10;

    public void initialize() {
        try {
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false); // Desativa o autocommit
            productDAO = new ProductDAO(connection);
            initializeTableColumns();
            loadProducts();
            addTableSelectionListener();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initializeTableColumns() {
        TableColumn<Product, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Product, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Product, String> descriptionColumn = new TableColumn<>("Short Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("shortDescription"));

        TableColumn<Product, String> brandColumn = new TableColumn<>("Brand");
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));

        TableColumn<Product, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<Product, Double> listPriceColumn = new TableColumn<>("List Price");
        listPriceColumn.setCellValueFactory(new PropertyValueFactory<>("listPrice"));

        TableColumn<Product, Double> costColumn = new TableColumn<>("Cost");
        costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));

        productTable.getColumns().addAll(idColumn, nameColumn, descriptionColumn, brandColumn, categoryColumn, listPriceColumn, costColumn);
    }

    @FXML
    public void saveProduct() {
        Product product = new Product();
        product.setName(nameField.getText());
        product.setShortDescription(descriptionField.getText());
        product.setBrand(brandField.getText());
        product.setCategory(categoryField.getText());
        product.setListPrice(Double.parseDouble(listPriceField.getText()));
        product.setCost(Double.parseDouble(costField.getText()));

        try {
            int generatedId = productDAO.createProduct(product);
            connection.commit(); // Commit manual
            product.setId(generatedId);
            loadProducts(); // Recarrega a tabela após salvar o produto
            clearForm();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback(); // Rollback em caso de erro
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            showError("Failed to save product: " + e.getMessage());
        }
    }

    @FXML
    public void updateProduct() {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            selectedProduct.setName(nameField.getText());
            selectedProduct.setShortDescription(descriptionField.getText());
            selectedProduct.setBrand(brandField.getText());
            selectedProduct.setCategory(categoryField.getText());
            selectedProduct.setListPrice(Double.parseDouble(listPriceField.getText()));
            selectedProduct.setCost(Double.parseDouble(costField.getText()));

            try {
                productDAO.updateProduct(selectedProduct);
                connection.commit(); // Commit manual
                loadProducts(); // Recarrega a tabela após atualizar o produto
                clearForm();
            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    connection.rollback(); // Rollback em caso de erro
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
                showError("Failed to update product: " + e.getMessage());
            }
        } else {
            showError("No product selected for update.");
        }
    }

    @FXML
    public void deleteProduct() {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            try {
                productDAO.deleteProduct(selectedProduct.getId());
                connection.commit(); // Commit manual
                loadProducts(); // Recarrega a tabela após deletar o produto
                clearForm();
            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    connection.rollback(); // Rollback em caso de erro
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
                showError("Failed to delete product: " + e.getMessage());
            }
        } else {
            showError("No product selected for deletion.");
        }
    }

    @FXML
    public void searchProduct() {
        String searchQuery = searchField.getText();
        try {
            List<Product> products = productDAO.searchProducts(searchQuery);
            int pageCount = (int) Math.ceil((double) products.size() / ROWS_PER_PAGE);
            pagination.setPageCount(pageCount);
            pagination.setPageFactory(pageIndex -> createPageFromSearch(pageIndex, products));
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Failed to search products: " + e.getMessage());
        }
    }

    private VBox createPageFromSearch(int pageIndex, List<Product> products) {
        int start = pageIndex * ROWS_PER_PAGE;
        int end = Math.min(start + ROWS_PER_PAGE, products.size());
        productTable.getItems().setAll(products.subList(start, end));
        return new VBox(productTable);
    }

    private void loadProducts() {
        try {
            int totalProducts = productDAO.getTotalProductCount();
            int pageCount = (int) Math.ceil((double) totalProducts / ROWS_PER_PAGE);
            pagination.setPageCount(pageCount);
            pagination.setPageFactory(this::createPage);
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Failed to load products: " + e.getMessage());
        }
    }

    private VBox createPage(int pageIndex) {
        int start = pageIndex * ROWS_PER_PAGE;
        try {
            List<Product> products = productDAO.getProductsInRange(start, start + ROWS_PER_PAGE);
            productTable.getItems().setAll(products);
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Failed to create page: " + e.getMessage());
        }
        return new VBox(productTable);
    }

    private void clearForm() {
        nameField.clear();
        descriptionField.clear();
        brandField.clear();
        categoryField.clear();
        listPriceField.clear();
        costField.clear();
    }

    private void addTableSelectionListener() {
        productTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateForm(newSelection);
            }
        });
    }

    private void populateForm(Product product) {
        nameField.setText(product.getName());
        descriptionField.setText(product.getShortDescription());
        brandField.setText(product.getBrand());
        categoryField.setText(product.getCategory());
        listPriceField.setText(String.valueOf(product.getListPrice()));
        costField.setText(String.valueOf(product.getCost()));
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
