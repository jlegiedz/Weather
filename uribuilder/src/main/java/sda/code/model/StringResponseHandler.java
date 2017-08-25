package sda.code.model;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class StringResponseHandler implements ResponseHandler<String> {
    @Override
    public String handleResponse(
            final HttpResponse response) throws IOException {
        int status = response.getStatusLine().getStatusCode();  // code:cyferki 200 ok , 300 wskazuja na przekierowanie
        if (status >= 200 && status < 300) {                    // te sa poprawne
            HttpEntity entity = response.getEntity();           // pod spodem jest kod i wyciaga entitiy i zwraca do stringa
            return entity != null ? EntityUtils.toString(entity) : null;
        } else {
            throw new ClientProtocolException("Unexpected response status: " + status);  // ja nie to blad
        }
    }

}
