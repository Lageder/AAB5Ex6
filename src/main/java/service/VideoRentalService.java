package service;

import data.Customer;
import data.Rental;
import data.Video;
import log.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class VideoRentalService {

    private static Scanner scanner = new Scanner(System.in);
    private final Logger logger = new Logger();

    public VideoRentalService() {
    }

    public void clearRentals(List<Customer> customers) {
        logger.info("Enter customer name: ");
        String customerName = scanner.next();

        Customer foundCustomer = findCustomer(customers, customerName);

        if (foundCustomer == null) {
            logger.info("No customer found");
        } else {
            logger.info("Name: " + foundCustomer.getName() +
                    "\tRentals: " + foundCustomer.getRentals().size());
            for (Rental rental : foundCustomer.getRentals()) {
                System.out.print("\tTitle: " + rental.getVideo().getTitle() + " ");
                System.out.print("\tPrice Code: " + rental.getVideo().getPriceCode());
            }

            List<Rental> rentals = new ArrayList<Rental>();
            foundCustomer.setRentals(rentals);
        }
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

    public void returnVideo(List<Customer> customers) {
        logger.info("Enter customer name: ");
        String customerName = scanner.next();

        Customer foundCustomer = findCustomer(customers, customerName);
        if (foundCustomer == null) return;

        logger.info("Enter video title to return: ");
        String videoTitle = scanner.next();

        List<Rental> customerRentals = foundCustomer.getRentals();
        for (Rental rental : customerRentals) {
            if (rental.getVideo().getTitle().equals(videoTitle) && rental.getVideo().isRented()) {
                rental.returnVideo();
                rental.getVideo().setRented(false);
                break;
            }
        }
    }

    public void init(List<Customer> customers, List<Video> videos) {
        Customer james = new Customer("James");
        Customer brown = new Customer("Brown");
        customers.add(james);
        customers.add(brown);

        Video v1 = new Video("v1", Video.CD, Video.REGULAR, new Date());
        Video v2 = new Video("v2", Video.DVD, Video.NEW_RELEASE, new Date());
        videos.add(v1);
        videos.add(v2);

        Rental r1 = new Rental(v1);
        Rental r2 = new Rental(v2);

        james.addRental(r1);
        james.addRental(r2);
    }

    public void listVideos(List<Video> videos) {
        logger.info("List of videos");

        for (Video video : videos) {
            logger.info("Price code: " + video.getPriceCode() + "\tTitle: " + video.getTitle());
        }
        logger.info("End of list");
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
        logger.info("Enter customer name: ");
        String customerName = scanner.next();

        Customer foundCustomer = findCustomer(customers, customerName);

        if (foundCustomer == null) {
            logger.info("No customer found");
        } else {
            String result = foundCustomer.getReport();
            logger.info(result);
        }
    }

    public void rentVideo(List<Customer> customers, List<Video> videos) {
        logger.info("Enter customer name: ");
        String customerName = scanner.next();

        Customer foundCustomer = findCustomer(customers, customerName);

        if (foundCustomer == null) return;

        logger.info("Enter video title to rent: ");
        String videoTitle = scanner.next();

        Video foundVideo = null;
        for (Video video : videos) {
            if (video.getTitle().equals(videoTitle) && video.isRented() == false) {
                foundVideo = video;
                break;
            }
        }

        if (foundVideo == null) return;

        Rental rental = new Rental(foundVideo);
        foundVideo.setRented(true);

        List<Rental> customerRentals = foundCustomer.getRentals();
        customerRentals.add(rental);
        foundCustomer.setRentals(customerRentals);
    }

    public void register(String object, List<Customer> customers, List<Video> videos) {
        if (object.equals("customer")) {
            registerCustomer(customers);
        } else {
            registerVideo(videos);
        }
    }

    private void registerCustomer(List<Customer> customers) {
        logger.info("Enter customer name: ");
        String name = scanner.next();
        Customer customer = new Customer(name);
        customers.add(customer);
    }

    private void registerVideo(List<Video> videos) {
        logger.info("Enter video title to register: ");
        String title = scanner.next();

        logger.info("Enter video type( 1 for VHD, 2 for CD, 3 for DVD ):");
        int videoType = scanner.nextInt();

        logger.info("Enter price code( 1 for Regular, 2 for New Release ):");
        int priceCode = scanner.nextInt();

        Date registeredDate = new Date();
        Video video = new Video(title, videoType, priceCode, registeredDate);
        videos.add(video);
    }

    public int showCommand() {
        logger.info("\nSelect a command !");
        logger.info("\t 0. Quit");
        logger.info("\t 1. List customers");
        logger.info("\t 2. List videos");
        logger.info("\t 3. Register customer");
        logger.info("\t 4. Register video");
        logger.info("\t 5. Rent video");
        logger.info("\t 6. Return video");
        logger.info("\t 7. Show customer report");
        logger.info("\t 8. Show customer and clear rentals");

        int command = scanner.nextInt();

        return command;

    }
}