package com.tes.colors;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
//@Primary
class MySQLStorage implements Storage {
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<Person> getPeople() {
		return entityManager.createNamedQuery("getPeople", Person.class).getResultList();
	}

	@Override
	public Person addRecord(Person p) {
		return entityManager.merge(p);
	}
}
