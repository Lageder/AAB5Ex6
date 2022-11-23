package presentation;

import data.Customer;
import data.Video;
import service.VideoRentalService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// This is Presentation layer..kind of??
public class VRUI {
	private static Scanner scanner = new Scanner(System.in) ;
	private final VideoRentalService videoRentalService = new VideoRentalService(this);

	private List<Customer> customers = new ArrayList<Customer>() ;

	private List<Video> videos = new ArrayList<Video>() ;

	public static void main(String[] args) {
		VRUI ui = new VRUI() ;

		boolean quit = false ;
		while ( ! quit ) {
			int command = ui.showCommand() ;
			switch ( command ) {
				case 0: quit = true ; break ;
				case 1: ui.listCustomers() ; break ;
				case 2: ui.listVideos() ; break ;
				case 3: ui.register("customer") ; break ;
				case 4: ui.register("video") ; break ;
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
		videoRentalService.listCustomers(customers);
	}

	public void getCustomerReport() {
		videoRentalService.getCustomerReport(customers);
	}

	public void rentVideo() {
		videoRentalService.rentVideo(customers, videos);
	}

	public void register(String object) {
		videoRentalService.register(object, customers, videos);
	}

	public int showCommand() {
		return videoRentalService.showCommand();
	}
}
