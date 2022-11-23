package service;

import log.Logger;

import java.util.Scanner;

public class ScannerService {

    private static Scanner scanner = new Scanner(System.in);
    private Logger logger;

    public ScannerService() {
        logger = new Logger();
    }

    public String receiveCustomerName() {
        logger.info("Enter customer name: ");
        return scanner.next();
    }

    public String receiveString(String loggerMsg) {
        logger.info(loggerMsg);
        return scanner.next();
    }

    public int receiveInteger() {
        return scanner.nextInt();
    }
    public int receiveInteger(String loggerMsg) {
        logger.info(loggerMsg);
        return receiveInteger();
    }
}