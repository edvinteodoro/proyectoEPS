package gt.edu.usac.cunoc.ingenieria.eps.exception;

import javax.ejb.ApplicationException;

/**
 * proyectoEPS-ejb
 * @author jose - 10.08.2020 
 * @Title: HttpClientException
 * @Description: description
 *
 * Changes History
 */
@ApplicationException
public class HttpClientException extends Exception {

    public HttpClientException(String message) {
        super(message);
    }
}