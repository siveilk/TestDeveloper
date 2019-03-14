package com.tes.colors;

import java.util.List;

interface Storage {
	List<Person> getPeople();

	Person addRecord(Person p);
}
