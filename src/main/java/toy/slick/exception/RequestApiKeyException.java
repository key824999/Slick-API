package toy.slick.exception;

import org.springframework.web.client.RestClientException;

public class RequestApiKeyException extends RestClientException {

    public RequestApiKeyException(String msg) {
        super(msg);
    }
}
