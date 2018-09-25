package blah;
import javax.jws.WebService;

import blah.Library;

@WebService(endpointInterface="blah.Library")
public class LibraryImpl implements Library{

	@Override
	public String hello(String helloMessage) {
		return "Service message: " + helloMessage;
	}

}
