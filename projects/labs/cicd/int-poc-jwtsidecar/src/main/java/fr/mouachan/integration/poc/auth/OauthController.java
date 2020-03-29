package fr.mouachan.integration.poc.auth;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.server.ServletServerHttpRequest;

@RestController
//@RequestMapping("/*")
public class OauthController {
	
	private static final String SERVER="localhost";
	
	@Value("${target.port}")
	private int target_port;
	
	@RequestMapping("/**")
	@ResponseBody
	public ResponseEntity mirrorRest(@RequestBody(required=false) String body, HttpServletRequest request) throws URISyntaxException
	{
		ServletServerHttpRequest servletrequest = new ServletServerHttpRequest(request);
		HttpMethod method = servletrequest.getMethod();
		HttpHeaders headers = servletrequest.getHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(body, headers);
		
		URI uri = servletrequest.getURI();
		int port = uri.getPort();
	    URI forward = new URI("http", null, SERVER, target_port, uri.getPath(), uri.getQuery(), null);
	    
	    RestTemplate restTemplate = new RestTemplate();
	    ResponseEntity<String> responseEntity =
	        restTemplate.exchange(forward, method, entity, String.class);

	    HttpHeaders h = responseEntity.getHeaders();
	    return responseEntity;
	}
	
}
