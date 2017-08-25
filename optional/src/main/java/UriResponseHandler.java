import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.ResponseHandler;

public class UriResponseHandler implements ResponseHandler {
    @Override
    public OutputStream createReply() {
        return null;
    }

    @Override
    public OutputStream createExceptionReply() {
        return null;
    }
}
