/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tables;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;

/**
 *
 * @author cmeehan
 */
public class PackingListTable {

    public PackingListTable(TableView tableView) {
        packingListTable(tableView);
    }

    public PackingListTable() {
    }

    public ObservableList<Cargo> data = FXCollections.observableArrayList(
            new Cargo(null, null, null, null, null, null, null)
    );

    public static void addRow() {
        new Cargo().setCommodity(null);
    }

    private void packingListTable(TableView tableView) {

        Callback<TableColumn<Cargo, String>, TableCell<Cargo, String>> cellFactory = (TableColumn<Cargo, String> param) -> new EditingCell();

        // Setting the column properties for the packing list table view
        TableColumn<Cargo, String> commodityColumn = new TableColumn("Commodity Name");
        TableColumn quantityColumn = new TableColumn("Quantity");
        TableColumn weightColumn = new TableColumn("Weight");
        TableColumn lengthColumn = new TableColumn("L (cm)");
        TableColumn widthColumn = new TableColumn("W (cm)");
        TableColumn heightColumn = new TableColumn("H (cm)");
        TableColumn cubicMetersColumn = new TableColumn("Cubic Meters");

        // Set the data properties to the columns
        commodityColumn.setCellValueFactory(new PropertyValueFactory<>("commodity"));
        commodityColumn.setPrefWidth(200.0);
        commodityColumn.setCellFactory(cellFactory);
        commodityColumn.setOnEditCommit((TableColumn.CellEditEvent<Cargo, String> t) -> {
            ((Cargo) t.getTableView().getItems().get(t.getTablePosition().getRow())).setCommodity(t.getNewValue());
        });

        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        quantityColumn.setCellFactory(cellFactory);
        quantityColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Cargo, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Cargo, String> t) {
                ((Cargo) t.getTableView().getItems().get(t.getTablePosition().getRow())).setQuantity(t.getNewValue());
            }
        });
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
        weightColumn.setCellFactory(cellFactory);
        weightColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Cargo, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Cargo, String> t) {
                ((Cargo) t.getTableView().getItems().get(t.getTablePosition().getRow())).setWeight(t.getNewValue());
            }
        });
        lengthColumn.setCellValueFactory(new PropertyValueFactory<>("length"));
        lengthColumn.setCellFactory(cellFactory);
        lengthColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Cargo, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Cargo, String> t) {
                ((Cargo) t.getTableView().getItems().get(t.getTablePosition().getRow())).setLength(t.getNewValue());
            }
        });
        widthColumn.setCellValueFactory(new PropertyValueFactory<>("width"));
        widthColumn.setCellFactory(cellFactory);
        widthColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Cargo, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Cargo, String> t) {
                ((Cargo) t.getTableView().getItems().get(t.getTablePosition().getRow())).setWidth(t.getNewValue());
            }
        });
        heightColumn.setCellValueFactory(new PropertyValueFactory<>("height"));
        heightColumn.setCellFactory(cellFactory);
        heightColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Cargo, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Cargo, String> t) {
                ((Cargo) t.getTableView().getItems().get(t.getTablePosition().getRow())).setHeight(t.getNewValue());
            }
        });
        cubicMetersColumn.setCellValueFactory(new PropertyValueFactory<>("cubicMeters"));
        cubicMetersColumn.setCellFactory(cellFactory);
        cubicMetersColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Cargo, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Cargo, String> t) {
                ((Cargo) t.getTableView().getItems().get(t.getTablePosition().getRow())).setCubicMeters(t.getNewValue());
            }
        });

        // Set the items
        tableView.setItems(data);
        // Add the columns
        tableView.getColumns().addAll(commodityColumn, quantityColumn, weightColumn, lengthColumn, widthColumn, heightColumn, cubicMetersColumn);
    }

    public static class Cargo {

        public SimpleStringProperty commodity;
        public SimpleStringProperty quantity;
        public SimpleStringProperty weight;
        public SimpleStringProperty length;
        public SimpleStringProperty width;
        public SimpleStringProperty height;
        public SimpleStringProperty cubicMeters;

        public Cargo() {
        }

        public Cargo(String comm, String qty, String wt, String l, String w, String h, String cbm) {
            this.commodity = new SimpleStringProperty(comm);
            this.quantity = new SimpleStringProperty(qty);
            this.weight = new SimpleStringProperty(wt);
            this.length = new SimpleStringProperty(l);
            this.width = new SimpleStringProperty(w);
            this.height = new SimpleStringProperty(h);
            this.cubicMeters = new SimpleStringProperty(cbm);
        }

        public String getCommodity() {
            return commodity.get();
        }

        public void setCommodity(String comm) {
            commodity.set(comm);
        }

        public String getQuantity() {
            return quantity.get();
        }

        public void setQuantity(String qty) {
            quantity.set(qty);
        }

        public String getWeight() {
            return weight.get();
        }

        public void setWeight(String wt) {
            weight.set(wt);
        }

        public String getLength() {
            return length.get();
        }

        public void setLength(String l) {
            length.set(l);
        }

        public String getWidth() {
            return width.get();
        }

        public void setWidth(String w) {
            width.set(w);
        }

        public String getHeight() {
            return height.get();
        }

        public void setHeight(String h) {
            height.set(h);
        }

        public String getCubicMeters() {
            return cubicMeters.get();
        }

        public void setCubicMeters(String cbm) {
            cubicMeters.set(cbm);
        }
    }

    public class EditingCell extends TableCell<Cargo, String> {

        private TextField textField;

        public EditingCell() {
        }

        @Override
        public void startEdit() {
            super.startEdit();
            if (!isEmpty()) {
                createTextField();
            }
            setText(null);
            setGraphic(textField);
            textField.requestFocus();
            textField.selectAll();
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setText((String) getItem());
            setGraphic(null);
        }

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getString());
                    setGraphic(null);
                }
            }
        }

        private void createTextField() {
            textField = new TextField(getString());
            textField.focusedProperty().addListener((ObservableValue<? extends Boolean> arg, Boolean arg1, Boolean arg2) -> {
                if (!arg2) {
                    commitEdit(textField.getText());
                }
            });
            textField.setOnKeyPressed((KeyEvent event) -> {
                if (event.getCode() == KeyCode.ENTER) {
                    commitEdit(textField.getText());
                } else if (event.getCode() == KeyCode.TAB) {
                    commitEdit(textField.getText());
                    TableColumn nextColumn = getNextColumn(!event.isShiftDown());
                    if(nextColumn != null){
                        getTableView().edit(getTableRow().getIndex(), nextColumn);
                        
                    }
                }
            });
        }

        // Thanks to https://gist.github.com/abhinayagarwal/9383881
        
        private TableColumn<Cargo, ?> getNextColumn(boolean forward) {
            List<TableColumn<Cargo, ?>> columns = new ArrayList<>();
            getTableView().getColumns().forEach((column) -> {
                columns.addAll(getLeaves(column));
            });
            // There is no other column that supports editing.
            if (columns.size() < 2) {
                return null;
            }
            int currentIndex = columns.indexOf(getTableColumn());
            int nextIndex = currentIndex;
            if (forward) {
                nextIndex++;
                if (nextIndex > columns.size() - 1) {
                    nextIndex = 0;
                }
            } else {
                nextIndex--;
                if (nextIndex < 0) {
                    nextIndex = columns.size() - 1;
                }
            }
            return columns.get(nextIndex);
        }

        private List<TableColumn<Cargo, ?>> getLeaves(
                TableColumn<Cargo, ?> root) {
            List<TableColumn<Cargo, ?>> columns = new ArrayList<>();
            if (root.getColumns().isEmpty()) {
                // We only want the leaves that are editable.
                if (root.isEditable()) {
                    columns.add(root);
                }
                return columns;
            } else {
                root.getColumns().forEach((column) -> {
                    columns.addAll(getLeaves(column));
                });
                return columns;
            }
        }

        private String getString() {
            return getItem() == null ? "" : getItem();
        }
    }

}
