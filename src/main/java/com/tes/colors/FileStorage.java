package com.tes.colors;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
class FileStorage implements Storage {
	String uri = "/home/a/people.csv";
	Path path = Paths.get(uri);
	int id;

	@Override
	public List<Person> getPeople() {
		int n = Colors.values().length; // n number of colors
		Colors[] lookup = new Colors[n]; // an array for getting enum's value from it's ordinal
		for (Colors col : Colors.values()) {
			lookup[col.ordinal()] = col;
		}
		id = 1;
		String record = null;
		List<Person> persons = new ArrayList<>();
		try (BufferedReader reader = Files.newBufferedReader(path)) {
			while ((record = reader.readLine()) != null) {

				String[] tokens = record.split(",");
				if (tokens.length == 4) { // a record consists of 4 fields
					for (int i = 0; i < tokens.length; i++) {
						tokens[i] = tokens[i].trim();
					}
					if (tokens[3].length() == 1) { // color's number is one digit
						int i = tokens[3].charAt(0) - '0'; // i is a color's number [1-7]
						if (i > 0 && i <= n) { // n number of colors
							int space = tokens[2].indexOf(' ');
							Person p = new Person(id++, tokens[0], tokens[1], tokens[2].substring(0, space),
									tokens[2].substring(space + 1, tokens[2].length()), lookup[i - 1]);
							persons.add(p);
						}
					}
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// id used as the id of the next person to be added
		return persons;
	}

	void processRecord(String r) {
	}

	@Override
	public Person addRecord(Person p) {
		p.setId(id); // p was without id
		String record = "\n" + p.getLastname() + ", " + p.getName() + ", " + p.getZipcode() + ' ' + p.getCity() + ", "
				+ (p.getColor().ordinal() + 1);
		List<String> li = new ArrayList<>();
		li.add(record); // list contains only one record
		try {
			Files.write(path, li, StandardOpenOption.APPEND, StandardOpenOption.WRITE);
			id++;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return p;
	}

}
