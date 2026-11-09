package Controller;

import model.MenuItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.*;

public final class controller {

    private List<MenuItem> menuList;
    private List<Object[]> orderRecords;

    private static final String FILE_NAME = "coffee_records.dat";

    public controller() {

        menuList = new ArrayList<>();
        orderRecords = new ArrayList<>();

        // Default menu items
        menuList.add(new MenuItem("Cappuccino", 120.0));
        menuList.add(new MenuItem("Espresso", 150.0));
        menuList.add(new MenuItem("Americano", 145.0));
        menuList.add(new MenuItem("Latte", 130.0));
        menuList.add(new MenuItem("Mocha", 120.0));
        menuList.add(new MenuItem("Cold Brew", 110.0));
        menuList.add(new MenuItem("Croissant", 110.0));
        menuList.add(new MenuItem("Muffin", 105.0));

        loadData();
    }

    public List<MenuItem> getMenuList() {
        return menuList;
    }

    public List<Object[]> getOrderRecords() {
        return orderRecords;
    }

    // Record purchase
    public void recordPurchase(String itemName, int quantity) {

        for (MenuItem item : menuList) {

            if (item.getName().equalsIgnoreCase(itemName)) {

                item.addQuantitySold(quantity);
                break;
            }
        }

        saveData();
    }

    // Add order record
    public void addOrderRecord(Object[] record) {

        orderRecords.add(record.clone());
        saveData();
    }

    // Update order status
    public void updateOrderStatus(int row, String status) {

        if (row >= 0 && row < orderRecords.size()) {

            orderRecords.get(row)[5] = status;
            saveData();
        }
    }

    // Delete order record
    public void deleteOrderRecord(int row) {

        if (row >= 0 && row < orderRecords.size()) {

            orderRecords.remove(row);
            saveData();
        }
    }

  // Update best seller table using Quick Sort
public void updateBestSellerTable(
        javax.swing.table.DefaultTableModel bestSellerModel) {

    bestSellerModel.setRowCount(0);

    // Sort menu items using Quick Sort
    quickSort(menuList, 0, menuList.size() - 1);

    for (MenuItem item : menuList) {

        if (item.getTotalQuantitySold() > 0) {

            bestSellerModel.addRow(new Object[]{
                item.getName(),
                item.getTotalQuantitySold()
            });
        }
    }
}

    // Save records
    public void saveData() {

        try (ObjectOutputStream output =
                new ObjectOutputStream(
                        new FileOutputStream(FILE_NAME))) {

            output.writeObject(menuList);
            output.writeObject(orderRecords);

        } catch (IOException e) {

            System.out.println("Error saving records: " + e.getMessage());
        }
    }

    // Load records
    @SuppressWarnings("unchecked")
    public void loadData() {

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return;
        }

        try (ObjectInputStream input =
                new ObjectInputStream(
                        new FileInputStream(file))) {

            menuList = (List<MenuItem>) input.readObject();
            orderRecords = (List<Object[]>) input.readObject();

        } catch (IOException | ClassNotFoundException e) {

            System.out.println("Error loading records: " + e.getMessage());
        }
    }
    // Quick Sort algorithm
    private void quickSort(List<MenuItem> list, int low, int high) {

    if (low < high) {

        int pivotIndex = partition(list, low, high);

        quickSort(list, low, pivotIndex - 1);
        quickSort(list, pivotIndex + 1, high);
    }
}

// Partition method
    private int partition(List<MenuItem> list, int low, int high) {

    int pivot = list.get(high).getTotalQuantitySold();

    int i = low - 1;

    for (int j = low; j < high; j++) {

        if (list.get(j).getTotalQuantitySold() >= pivot) {

            i++;

            Collections.swap(list, i, j);
        }
    }

    Collections.swap(list, i + 1, high);

    return i + 1;
}
}