import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AddCustomerForm extends JDialog {
    private JTextField shortNameTextField;
    private JTextField fullNameTextField;
    private JTextField addressField1;
    private JTextField addressField2;
    private JTextField addressField3;
    private JTextField cityField;
    private JTextField postalCodeField;
    private JButton addButton;
    private JPanel AddCustomerPanel;
    private JButton resetButton;

    public AddCustomerForm(JFrame parent) {
        super(parent);
        setTitle("Add Customer");
        setContentPane(AddCustomerPanel);
        setMinimumSize(new Dimension(400, 300));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCustomer();
            }
        });
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }

    private void addCustomer() {
        String shortName = shortNameTextField.getText();
        String fullName = fullNameTextField.getText();
        String address1 = addressField1.getText();
        String address2 = addressField2.getText();
        String address3 = addressField3.getText();
        String city = cityField.getText();
        String postalCode = postalCodeField.getText();

        if(shortName.isEmpty() || fullName.isEmpty() || city.isEmpty() || postalCode.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all the fields","Try Again",JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!postalCode.matches("\\d{1,10}")) {
            JOptionPane.showMessageDialog(null, "Postal code must be numeric value");
            return;
        }

        cust = addCustomerDb(shortName,fullName,address1,address2,address3,city,postalCode);
        if (cust == null) {
            System.out.println("Add customer failed");
            JOptionPane.showMessageDialog(this, "Error adding customer to the database", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
        } else {
            System.out.println("Add customer success");
            JOptionPane.showMessageDialog(this, "Customer successfully added to the database!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
    }

    //function to add into db
    public Customer cust;
    private Customer addCustomerDb(String shortName, String fullName, String address1, String address2, String address3, String city, String postalCode) {
        Customer cust = null;

        final String db_url = "jdbc:mysql://localhost:3306/customers";
        final String db_user = "root";
        final String db_password = "admin";

        try {
            Connection conn = DriverManager.getConnection(db_url,db_user,db_password);

            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO customers (short_name, full_name, address_1, address_2, address_3, city, postal_code) VALUES (?,?,?,?,?,?,?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, shortName);
            ps.setString(2, fullName);
            ps.setString(3, address1);
            ps.setString(4, address2);
            ps.setString(5, address3);
            ps.setString(6, city);
            ps.setString(7, postalCode);

            int add = ps.executeUpdate();
            if (add > 0) {
                cust = new Customer();
                cust.shortName = shortName;
                cust.fullName = fullName;
                cust.address1 = address1;
                cust.address2 = address2;
                cust.address3 = address3;
                cust.city = city;
                cust.postalCode = postalCode;
            }

            stmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cust;
    }

    //main func
    public static void main(String[] args) {
        AddCustomerForm addCust = new AddCustomerForm(null);
    }
}
