package com.tes.colors;
import org.junit.runners.MethodSorters;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ColorsApplicationTests {
	MockHttpServletRequestBuilder builder;
	String r1 = "{\"id\":1,\"name\":\"Name1\",\"lastname\":\"LastName1\",\"zipcode\":\"1111\",\"city\":\"City1\",\"color\":\"white\"}";
	String r2 = "{\"id\":2,\"name\":\"Name2\",\"lastname\":\"LastName2\",\"zipcode\":\"2222\",\"city\":\"City2\",\"color\":\"blue\"}";
	String r3 = "{\"id\":3,\"name\":\"Name3\",\"lastname\":\"LastName3\",\"zipcode\":\"3333\",\"city\":\"City3\",\"color\":\"white\"}";

	String p1 = "{" + r1.substring(8);
	String p2 = "{" + r2.substring(8);
	String p3 = "{" + r3.substring(8);

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void emptyDB() throws Exception { // empty file or database
		builder = MockMvcRequestBuilders.get("/persons");
		ResultMatcher contentMatcher = MockMvcResultMatchers.content().string("[]");
		this.mockMvc.perform(builder).andExpect(contentMatcher).andExpect(MockMvcResultMatchers.status().isOk());

	}

	@Test
	public void firstRecord() throws Exception { // adds first record
		builder = MockMvcRequestBuilders.post("/persons").contentType(MediaType.APPLICATION_JSON_VALUE).content(p1);
		ResultMatcher contentMatcher = MockMvcResultMatchers.content().string(r1);
		this.mockMvc.perform(builder).andExpect(contentMatcher).andExpect(MockMvcResultMatchers.status().isCreated());
	}

	@Test
	public void getOne() throws Exception { // one record
		builder = MockMvcRequestBuilders.get("/persons");
		ResultMatcher contentMatcher = MockMvcResultMatchers.content().string("[" + r1 + "]");
		this.mockMvc.perform(builder).andExpect(contentMatcher).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void hh() throws Exception { // adds second record
		builder = MockMvcRequestBuilders.post("/persons").contentType(MediaType.APPLICATION_JSON_VALUE).content(p2);
		ResultMatcher contentMatcher = MockMvcResultMatchers.content().string(r2);
		this.mockMvc.perform(builder).andExpect(contentMatcher).andExpect(MockMvcResultMatchers.status().isCreated());
	}

	@Test
	public void ii() throws Exception { // adds third record
		builder = MockMvcRequestBuilders.post("/persons").contentType(MediaType.APPLICATION_JSON_VALUE).content(p3);
		ResultMatcher contentMatcher = MockMvcResultMatchers.content().string(r3);
		this.mockMvc.perform(builder).andExpect(contentMatcher).andExpect(MockMvcResultMatchers.status().isCreated());
	}

	@Test
	public void jj() throws Exception { // get 3 persons
		builder = MockMvcRequestBuilders.get("/persons");
		ResultMatcher contentMatcher = MockMvcResultMatchers.content().string("[" + r1 + "," + r2 + "," + r3 + "]");
		this.mockMvc.perform(builder).andExpect(contentMatcher).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void kk() throws Exception { // get blue
		builder = MockMvcRequestBuilders.get("/persons/color/1");
		ResultMatcher contentMatcher = MockMvcResultMatchers.content().string("[" + r2 + "]");
		this.mockMvc.perform(builder).andExpect(contentMatcher).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void ll() throws Exception { // get white
		builder = MockMvcRequestBuilders.get("/persons/color/7");
		ResultMatcher contentMatcher = MockMvcResultMatchers.content().string("[" + r1 + "," + r3 + "]");
		this.mockMvc.perform(builder).andExpect(contentMatcher).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void mm() throws Exception { // get red
		builder = MockMvcRequestBuilders.get("/persons/color/4");
		ResultMatcher contentMatcher = MockMvcResultMatchers.content().string("[]");
		this.mockMvc.perform(builder).andExpect(contentMatcher).andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	public void oo() throws Exception { // get id7
		builder = MockMvcRequestBuilders.get("/persons/7");
		ResultMatcher contentMatcher = MockMvcResultMatchers.content().string("");
		this.mockMvc.perform(builder).andExpect(contentMatcher).andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	public void pp() throws Exception { // get id2
		builder = MockMvcRequestBuilders.get("/persons/2");
		ResultMatcher contentMatcher = MockMvcResultMatchers.content().string(r3);
		this.mockMvc.perform(builder).andExpect(contentMatcher).andExpect(MockMvcResultMatchers.status().isOk());
	}
}
