package service;

import data.Customer;
import data.Rental;
import data.Video;
import log.Logger;

import java.util.Date;
import java.util.List;

public class VideoRentalService {
    
    private final Logger logger;
    private final ScannerService scannerService;

    public VideoRentalService(Logger logger, ScannerService scannerService) {
        this.logger = logger;
        this.scannerService = scannerService;
    }

    public void clearRentals(List<Customer> customers) {
        String customerName = scannerService.receiveString("Enter customer name: ");

        Customer foundCustomer = findCustomer(customers, customerName);

        if (foundCustomer == null) {
            logger.info("No customer found");
        } else {
            logger.info("Name: " + foundCustomer.getName() + "\tRentals: " + foundCustomer.getRentals().size());
            for (Rental rental : foundCustomer.getRentals()) {
                logger.info("\tTitle: " + rental.getVideo().getTitle() + " " + "\tPrice Code: " + rental.getVideo().getPriceCode());
            }
            foundCustomer.resetRental();
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
        String customerName = scannerService.receiveString("Enter customer name: ");

        Customer foundCustomer = findCustomer(customers, customerName);
        if (foundCustomer == null) return;

        String videoTitle = scannerService.receiveString("Enter video title to return: ");

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

    public void rentVideo(List<Customer> customers, List<Video> videos) {
        String customerName = scannerService.receiveString("Enter customer name: ");

        Customer foundCustomer = findCustomer(customers, customerName);

        if (foundCustomer == null) return;

        String videoTitle = scannerService.receiveString("Enter video title to rent: ");

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
        foundCustomer.addRental(rental);
    }

    public void registerVideo(List<Video> videos) {
        String title = scannerService.receiveString("Enter video title to register: ");
        int videoType = scannerService.receiveInteger("Enter video type( 1 for VHD, 2 for CD, 3 for DVD ):");
        int priceCode = scannerService.receiveInteger("Enter price code( 1 for Regular, 2 for New Release ):");
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
        return scannerService.receiveInteger();
    }
}