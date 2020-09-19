package ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions;

public class InvalidDonationException extends Exception {

    public InvalidDonationException(String errorMessage) {
        super(errorMessage);
    }
}
