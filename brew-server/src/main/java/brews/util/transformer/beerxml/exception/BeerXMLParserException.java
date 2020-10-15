package brews.util.transformer.beerxml.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BeerXMLParserException extends RuntimeException {

    public BeerXMLParserException() {
        super();
    }

    public BeerXMLParserException(String msg) {
        super(msg);
    }

    public BeerXMLParserException(Exception e) {
        super(e);
    }

    public BeerXMLParserException(String msg, Exception e) {
        super(msg,e);
    }
}
