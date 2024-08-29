import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

//Run This as Main
public class AddressScreen extends JFrame {

    private JTable addressTable;
    private JButton addButton;
    private JButton modifyButton;
    private JButton deleteButton;
    private JButton viewAddressButton;
    private JPanel AddressPanel;

    public AddressScreen() {
        setTitle("Customer Address List");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(600, 400));
        setContentPane(AddressPanel);
        setLocationRelativeTo(null);

        addressTable = new JTable();
        addButton = new JButton("Add");
        viewAddressButton = new JButton("View Address");
        modifyButton = new JButton("Modify");
        deleteButton = new JButton("Delete");

        AddressPanel.setLayout(new BorderLayout());
        AddressPanel.add(new JScrollPane(addressTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewAddressButton); // Add the new button
        AddressPanel.add(buttonPanel, BorderLayout.SOUTH);

        loadAddressData();

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AddCustomerForm(AddressScreen.this);
                loadAddressData(); //reload after adding new customer data
            }
        });

        modifyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modifyAddress();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteAddress();
            }
        });

        viewAddressButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewFullAddress();
            }
        });

        setVisible(true);
    }

    //display Customer Data with Address into JTable
    private void loadAddressData() {

        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Full Name", "Address 1", "Address 2", "Address 3", "City", "Postal Code"}, 0);

        final String db_url = "jdbc:mysql://localhost:3306/customers";
        final String db_user = "root";
        final String db_password = "admin";

        try {
            Connection conn = DriverManager.getConnection(db_url, db_user, db_password);

            Statement stmt = conn.createStatement();
            String sql = "SELECT customer_id, full_name, address_1, address_2, address_3, city, postal_code FROM customers";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("customer_id");
                String fullName = rs.getString("full_name");
                String address1 = rs.getString("address_1");
                String address2 = rs.getString("address_2");
                String address3 = rs.getString("address_3");
                String city = rs.getString("city");
                String postalCode = rs.getString("postal_code");
                model.addRow(new Object[]{id, fullName, address1, address2, address3, city, postalCode});
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error in displaying customer data");
        }

        addressTable.setModel(model);
    }

    //Display full address by combining 3 addresses and city and postalcode
    private void viewFullAddress() {
        int selectedRow = addressTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select customer to view Customer's full address.", "View Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String fullName = (String) addressTable.getValueAt(selectedRow, 1);
        String address1 = (String) addressTable.getValueAt(selectedRow, 2);
        String address2 = (String) addressTable.getValueAt(selectedRow, 3);
        String address3 = (String) addressTable.getValueAt(selectedRow, 4);
        String city = (String) addressTable.getValueAt(selectedRow, 5);
        String postalCode = (String) addressTable.getValueAt(selectedRow, 6);

        String fullAddress = "" +address1 + ", " + address2 + ", " + address3 + ", " + postalCode + ", " + city;
        JOptionPane.showMessageDialog(this, "Customer Name: " + fullName + "\n" + "Address: " + fullAddress, "Full Address", JOptionPane.INFORMATION_MESSAGE);
    }

    //update or modify address into db
    private void modifyAddress() {
        int selectedRow = addressTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select row to modify", "Modify Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int id = (int) addressTable.getValueAt(selectedRow, 0);
        String newAddress1 = JOptionPane.showInputDialog(this, "Enter new Address 1:", addressTable.getValueAt(selectedRow, 2));
        String newAddress2 = JOptionPane.showInputDialog(this, "Enter new Address 2:", addressTable.getValueAt(selectedRow, 3));
        String newAddress3 = JOptionPane.showInputDialog(this, "Enter new Address 3:", addressTable.getValueAt(selectedRow, 4));
        String newCity = JOptionPane.showInputDialog(this, "Enter new City:", addressTable.getValueAt(selectedRow, 5));
        String newPostalCode = JOptionPane.showInputDialog(this, "Enter new Postal Code:", addressTable.getValueAt(selectedRow, 6));

        // Validate postal code
        if (!newPostalCode.matches("\\d{1,10}")) {
            JOptionPane.showMessageDialog(this, "Postal code must be numeric value.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        final String db_url = "jdbc:mysql://localhost:3306/customers";
        final String db_user = "root";
        final String db_password = "admin";

        try {
            Connection conn = DriverManager.getConnection(db_url, db_user, db_password);

            String sql = "UPDATE customers SET address_1 = ?, address_2 = ?, address_3 = ?, city = ?, postal_code = ? WHERE customer_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, newAddress1);
            ps.setString(2, newAddress2);
            ps.setString(3, newAddress3);
            ps.setString(4, newCity);
            ps.setString(5, newPostalCode);
            ps.setInt(6, id);

            ps.executeUpdate();

            ps.close();
            conn.close();

            loadAddressData();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error updating customer address");
        }
    }

    //delete customer data along with addresses
    private void deleteAddress() {
        int selectedRow = addressTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a row to delete.", "Delete Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int id = (int) addressTable.getValueAt(selectedRow, 0);

        int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure to delete this customer?", "Confirm Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirmation == JOptionPane.YES_OPTION) {
            final String db_url = "jdbc:mysql://localhost:3306/customers";
            final String db_user = "root";
            final String db_password = "admin";

            try {
                Connection conn = DriverManager.getConnection(db_url, db_user, db_password);

                String sql = "DELETE FROM customers WHERE customer_id = ?";
                PreparedStatement ps = conn.prepareStatement(sql);

                ps.setInt(1, id);

                ps.executeUpdate();

                ps.close();
                conn.close();

                loadAddressData();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error deleting customer address");
            }
        }
    }


    //main function
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AddressScreen();
            }
        });
    }
}
