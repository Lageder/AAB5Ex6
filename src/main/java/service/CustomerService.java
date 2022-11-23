package service;

import data.Customer;
import data.Rental;
import log.Logger;

import java.util.List;

public class CustomerService {
    private final Logger logger;
    
    private final ScannerService scannerService;
    
    public CustomerService(Logger logger, ScannerService scannerService) {
        this.logger = logger;
        this.scannerService = scannerService;
    }
    
    public void listCustomers(List<Customer> customers) {
        logger.info("List of customers");
        for (Customer customer : customers) {
            logger.info("Name: " + customer.getName() + "\tRentals: " + customer.getRentals().size());
            for (Rental rental : customer.getRentals()) {
                logger.info("\tTitle: " + rental.getVideo().getTitle() + "\tPrice Code: " + rental.getVideo().getPriceCode());
            }
        }
        logger.info("End of list");
    }

    public void getCustomerReport(List<Customer> customers) {
        String customerName = scannerService.receiveString("Enter customer name: ");

        Customer foundCustomer = findCustomer(customers, customerName);

        if (foundCustomer == null) {
            logger.info("No customer found");
        } else {
            String result = foundCustomer.getReport();
            logger.info(result);
        }
    }

    public void registerCustomer(List<Customer> customers) {
        String name = scannerService.receiveString("Enter customer name: ");
        Customer customer = new Customer(name);
        customers.add(customer);
    }

    private Customer findCustomer(List<Customer> customers, String customerName) {
        Customer foundCustomer = null;
        for (Customer customer : customers) {
            if (customer.getName().equals(customerName)) {
                foundCustomer = customer;
                break;
            }
        }
        return foundCustomer;
    }
}