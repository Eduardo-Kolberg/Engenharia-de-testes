package org.example;

public class CustomerService {
    public boolean registerCustomer(Customer customer) {
        if (customer.getAge() < 18 || customer.getAge() > 99) {
            return false;
        }

        return customer.getEmail().matches("^[\\w-.]+@[a-zA-Z]+\\.[a-zA-Z]{2,}$");
    }

    public boolean updateCustomer(Customer customer, String newName, String newEmail, int newAge) {
        if (!customer.isActive()) {
            return false;
        }
        customer.setName(newName);
        customer.setEmail(newEmail);
        customer.setAge(newAge);
        return true;
    }

    public boolean deleteCustomer(Customer customer) {
        return customer.isActive();
    }
}
