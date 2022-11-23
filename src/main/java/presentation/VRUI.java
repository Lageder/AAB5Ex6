package presentation;

import data.Customer;
import data.Video;
import log.Logger;
import service.CustomerService;
import service.ScannerService;
import service.VideoRentalService;

import java.util.ArrayList;
import java.util.List;

/*
	todo list
	- Can we process customers and videos information in another class?
	- Separate init responsibility
* */
public class VRUI {
	Logger logger = new Logger();
	private final ScannerService scannerService = new ScannerService();
	private final CustomerService customerService = new CustomerService(logger, scannerService);
	private final VideoRentalService videoRentalService = new VideoRentalService(logger, scannerService);
	private List<Customer> customers = new ArrayList<Customer>();
	private List<Video> videos = new ArrayList<Video>();

	public static void main(String[] args) {
		VRUI ui = new VRUI();
		boolean quit = false ;
		while ( ! quit ) {
			int command = ui.showCommand() ;
			switch ( command ) {
				case 0: quit = true ; break ;
				case 1: ui.listCustomers() ; break ;
				case 2: ui.listVideos() ; break ;
				case 3: ui.registerCustomer() ; break ;
				case 4: ui.registerVideo() ; break ;
				case 5: ui.rentVideo() ; break ;
				case 6: ui.returnVideo() ; break ;
				case 7: ui.getCustomerReport() ; break;
				case 8: ui.clearRentals() ; break ;
				case -1: ui.init() ; break ;
				default: break ;
			}
		}
		System.out.println("Bye");
	}

	public void clearRentals() {
		videoRentalService.clearRentals(customers);
	}

	public void returnVideo() {
		videoRentalService.returnVideo(customers);
	}

	private void init() {
		videoRentalService.init(customers, videos);
	}

	public void listVideos() {
		videoRentalService.listVideos(videos);
	}

	public void listCustomers() {
		customerService.listCustomers(customers);
	}

	public void getCustomerReport() {
		customerService.getCustomerReport(customers);
	}

	public void rentVideo() {
		videoRentalService.rentVideo(customers, videos);
	}

	private void registerVideo() {
		videoRentalService.registerVideo(videos);
	}

	private void registerCustomer() {
		customerService.registerCustomer(customers);
	}

	public int showCommand() {
		return videoRentalService.showCommand();
	}
}
