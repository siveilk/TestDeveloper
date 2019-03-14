package com.tes.colors;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class PeopleService {
	List<Person> people = null;
	@Autowired
	private Storage storage;

	@PostConstruct
	void init() {
		people = storage.getPeople();
	}

	int npeople() { // used by PeopleController
		return people.size();
	}

	List<Person> findAll() {
		return people;
	}

	Person findOne(int id) {
		return people.get(id);
	}

	List<Person> findByColor(int color) {
		return people.stream().filter(p -> p.getColor().ordinal() == color).collect(Collectors.toList());
	}

	@Transactional
	Person save(Person p) {
		p = storage.addRecord(p); // person p gets id from the DB
		people.add(p);
		return p;
	}
}
