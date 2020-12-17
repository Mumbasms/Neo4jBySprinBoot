/* WebServiceController.java
* Created on 15-Dec-2020
*/

package com.spring.prashant.DarkSpringBoot;

import java.io.IOException;
import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.google.gson.Gson;
import com.spring.prashant.DarkSpringBoot.domain.Person;
import com.spring.prashant.DarkSpringBoot.repository.PersonRepository;

/**
 * Add one sentence class summary here. Add class description here.
 *
 * @author prash
 * @version 1.0, 15-Dec-2020
 */
@Controller
public class WebServiceController implements WebMvcConfigurer {

    @Autowired
    PersonRepository personRepository;

    
    @GetMapping(value = "/download/{person}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public HttpEntity<byte[]> downloadScript(@PathVariable("person") String person)
	    throws IOException, HttpClientErrorException {
	byte[] documentBody = null;
	String fileExtension = "json";
	Person personOb = personRepository.findByName(person);
	Gson gson = new Gson();
	System.out.println("I was here"+ personOb.getName());
	String here= gson.toJson(personOb);
	documentBody = here.getBytes(Charset.forName("UTF-8"));
	if (documentBody != null) {
	    HttpHeaders header = new HttpHeaders();
	    header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + person + "." + fileExtension);
	    header.setContentLength(documentBody.length);
	    return new HttpEntity<byte[]>(documentBody, header);
	} else {
	    return null;
	}

    }
}
