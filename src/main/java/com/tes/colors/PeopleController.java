package com.tes.colors;

import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class PeopleController {
	@Autowired
	private PeopleService peopleService;

	PeopleController(PeopleService peopleService) {
		this.peopleService = peopleService;
	}

	@GetMapping("/persons")
	public List<Person> findAll() {
		return peopleService.findAll();
	}

	@GetMapping("/persons/{id:[0-9]+}") // id must be a number
	public ResponseEntity<Person> findOne(@PathVariable("id") int id) {
		boolean exists = 0 <= id && id < peopleService.npeople(); // I hope this is not too much business logic here
		return exists ? new ResponseEntity<>(peopleService.findOne(id), HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/persons/color/{color:[1-7]}") // color must be a number from 1 to 7
	public ResponseEntity<List<Person>> findByColor(@PathVariable("color") int color) {
		List<Person> lp = peopleService.findByColor(color - 1);// values [0-6] used
		return lp.isEmpty() ? new ResponseEntity<List<Person>>(lp, HttpStatus.NOT_FOUND)
				: new ResponseEntity<List<Person>>(lp, HttpStatus.OK);
	}

	@PostMapping(path = "/persons", consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Person> save(@RequestBody Person p, UriComponentsBuilder ucb) {
		URI locationUri = ucb.path("/persons/").path(String.valueOf(peopleService.npeople())).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(locationUri);
		p = peopleService.save(p); // person p gets id from the DB
		return new ResponseEntity<Person>(p, headers, HttpStatus.CREATED);
	}
}
