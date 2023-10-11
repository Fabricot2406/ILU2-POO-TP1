package exceptions;

public class VillageSansChefException extends RuntimeException {
	private static final long serialversionUID = 1L;
	
	public VillageSansChefException() {
		
	}
	
    public VillageSansChefException(String message) {
        super(message);
    }
    
    public VillageSansChefException(Throwable cause) {
        super(cause);
    }
    
    public VillageSansChefException(String message, Throwable cause) {
        super(message, cause);
    }
}