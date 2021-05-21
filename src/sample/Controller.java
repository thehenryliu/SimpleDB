package sample;

import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

import java.sql.*;

public class Controller {
    // I think you can only define variables in the controller and all logic must be on events?
    String conn_url = "jdbc:mysql://localhost:3306/world?user=root&password=root&serverTimezone=UTC";
    Connection conn = null;
    String sqlQuery = "select * from country ";
    Statement statement;
    ResultSet resultSet;
    ObservableList<ObservableList> data;

    @FXML // fx:id="connectToDatabaseButton";
    private Button connectToDatabaseButton;

    @FXML // fx:id="querySubmitButton";
    private Button querySubmitButton;

    @FXML // fx:id="queryTextField";
    private TextField queryTextField;

    @FXML // fx:id="tableView";
    private TableView tableView;

    public void connectToDB()
    {
        {
            try {

                conn = DriverManager.getConnection(conn_url);
                System.out.println("Connection successful!");

                statement = conn.createStatement();


                connectToDatabaseButton.setVisible(false); // Hiding Connect to Database button
                querySubmitButton.setVisible(true); // only showing Send Query button instead

            } catch (SQLException e) {
                e.printStackTrace();
            } /*finally {
                if (conn != null){
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }*/
        }
    }


    public void submitQuery()
    {
        //select * from country
        sqlQuery = queryTextField.getText();
        int colCount = 0;

        data = FXCollections.observableArrayList();

        try {
            resultSet = statement.executeQuery(sqlQuery);
            colCount = resultSet.getMetaData().getColumnCount();

            String[] colNames = new String[colCount];

        tableView.getColumns().clear();

        for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
            final int j = i;
            TableColumn col = new TableColumn(resultSet.getMetaData().getColumnName(i + 1));
            col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                    return new SimpleStringProperty(param.getValue().get(j).toString());
                }
            });

            System.out.print(tableView);
            tableView.getColumns().addAll(col);
        }

            while (resultSet.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    row.add(resultSet.getString(i));
                }
                data.add(row);
            }

            tableView.setItems(data);
            //System.out.println("here");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

// On button press for Send Query
    // Take text entered in text field and set that equal to sqlQuery // sqlQuery = textField.getText();
    // Statement stmt = conn.createStatement(); // Controller doesn't know what conn is because connection happens in Main
        // Maybe Controller can initiate connection instead?
        // What if Send Query button also checks to see if it's connected all in one so the connection, statement, and resultset can be defined in Controller?
    // resultSet = statement.executeQuery(sqlQuery);


