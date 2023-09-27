package edu.lehigh.cse216.rmd225.backend;

public class StructuredResponse {
     /**
     * The status is a string that the application can use to quickly determine
     * if the response indicates an error.  Values will probably just be "ok" or
     * "error", but that may evolve over time.
     */
    public String mStatus;

    /**
     * The message is only useful when this is an error, or when data is null.
     */
    public String mMessage;

    /**
     * Any JSON-friendly object can be referenced here, so that we can have a
     * rich reply to the client
     */
    public Object mData;

    /**
     * Construct a StructuredResponse by providing a status, message, and data.
     * If the status is not provided, set it to "invalid".
     * 
     * @param status The status of the response, typically "ok" or "error"
     * @param message The message to go along with an error status
     * @param data An object with additional data to send to the client
     */
    public StructuredResponse(String status, String message, Object data) {
        mStatus = (status != null) ? status : "invalid";
        mMessage = message;
        mData = data;
    }
    
}
