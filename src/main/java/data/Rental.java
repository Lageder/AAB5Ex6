package data;

import java.util.Date;

public class Rental {
	private Video video ;
	private int status ; // 0 for Rented, 1 for Returned
	private Date rentDate ;
	private Date returnDate ;

	public Rental(Video video) {
		this.video = video ;
		status = 0 ;
		rentDate = new Date() ;
	}

	public Video getVideo() {
		return video;
	}

	public void returnVideo() {
		if ( status == 1 ) {
			this.status = 1;
			returnDate = new Date() ;
		}
	}

	public int getDaysRentedLimit() {
		if (getDaysRented() <= 2) return 0;
		int limit = 0;
		switch ( video.getVideoType() ) {
			case Video.VHS: limit = 5 ; break ;
			case Video.CD: limit = 3 ; break ;
			case Video.DVD: limit = 2 ; break ;
		}
		return limit ;
	}

	public int getDaysRented() {
		long diff;
		if (status == 1) { // returned data.Video
			diff = returnDate.getTime() - rentDate.getTime();
		} else { // not yet returned
			Date currentDate = new Date();
			diff = currentDate.getTime() - rentDate.getTime();
		}
		return calculateDaysRented(diff);
	}

	private int calculateDaysRented(long diff) {
		return (int) ((diff / (1000 * 60 * 60 * 24)) + 1);
	}

	int calculatePoint() {
		int point = 0 ;
		point++;

		if ((getVideo().getPriceCode() == Video.NEW_RELEASE) )
			point++;

		if ( getDaysRented() > getDaysRentedLimit() )
			point -= Math.min(point, getVideo().getLateReturnPointPenalty()) ;
		return point;
	}

	double calculateCharge() {
		int daysRented = getDaysRented();
		double charge = 0;

		if (getVideo().getPriceCode() == Video.REGULAR) {
			charge += 2;
			if (daysRented > 2) {
				charge += (daysRented - 2) * 1.5;
			}
		} else if (getVideo().getPriceCode() == Video.NEW_RELEASE) {
			charge = daysRented * 3;
		}
		return charge;
	}
}
