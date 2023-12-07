package brews.services.exceptions;

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
