package BackendCode;
import java.io.*;
import java.util.ArrayList;
public class CustomerRepository {
    private static final String FILE_PATH = "customers.ser";
    public void addCustomer(Customer customer) throws IOException {
        ArrayList<Customer> customers = loadCustomers();
        customer.setID(customers.isEmpty() ? 1 : customers.get(customers.size() - 1).getID() + 1);
        customers.add(customer);
        saveCustomers(customers);
    }
    public void updateCustomer(Customer customer) throws IOException {
        ArrayList<Customer> customers = loadCustomers();
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getID() == customer.getID()) {
                customers.set(i, customer);
                break;
            }
        }
        saveCustomers(customers);
    }
    public void removeCustomer(int customerId) throws IOException {
        ArrayList<Customer> customers = loadCustomers();
        customers.removeIf(c -> c.getID() == customerId);
        saveCustomers(customers);
    }
    public ArrayList<Customer> getAllCustomers() throws IOException {
        return loadCustomers();
    }
    private void saveCustomers(ArrayList<Customer> customers) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            out.writeObject(customers);
        }
    }
    private ArrayList<Customer> loadCustomers() throws IOException {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (ArrayList<Customer>) in.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to find class for object deserialization", e);
        }
    }
}
