package com.navercorp.fixturemonkey.tests.springtests.test;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.navercorp.fixturemonkey.spring.interceptor.MethodInterceptorContext;
import com.navercorp.fixturemonkey.spring.interceptor.FixtureMonkeyInterceptorConfiguration;
import com.navercorp.fixturemonkey.tests.springtests.SpringTestApplication;
import com.navercorp.fixturemonkey.tests.springtests.config.TestApiClient;

@SpringBootTest(
	classes = SpringTestApplication.class,
	webEnvironment = WebEnvironment.NONE
)
@Import(FixtureMonkeyInterceptorConfiguration.class)
@ActiveProfiles("test")
class SpringTestsApplicationTests {

	@Autowired

	private TestApiClient testApiClient;

	@AfterEach
	void tearDown() {
		MethodInterceptorContext.clear();
	}

	@Test
	void getMonoString() {
		String expected = "test";

		String actual = testApiClient.getMonoString()
			.blockOptional().orElseThrow();

		then(actual).isEqualTo(expected);
	}

	@Test
	void getRawString() {
		String expected = "test";

		String actual = testApiClient.getRawString();

		then(actual).isEqualTo(expected);
	}

	@Test
	void getStaticString() {
		String actual = TestApiClient.getStaticString();

		then(actual).isEqualTo("test");
	}

	@Test
	void setRawString() {
		String expected = "hahaho";
		MethodInterceptorContext.type(TestApiClient.class, String.class)
			.set("$", expected);

		String actual = testApiClient.getRawString();

		then(actual).isEqualTo(expected);
	}

	@Test
	void setByType() {
		String expected = "hahaho";
		MethodInterceptorContext.type(TestApiClient.class, String.class)
			.set("$", expected);

		String actual = testApiClient.getMonoString()
			.blockOptional().orElseThrow();

		then(actual).isEqualTo(expected);
	}

	@Test
	void setByName() {
		String expected = "hahaho";
		MethodInterceptorContext.name(TestApiClient.class, "getMonoString")
			.set("$", expected);

		String actual = testApiClient.getMonoString()
			.blockOptional().orElseThrow();

		then(actual).isEqualTo(expected);
	}

	@Test
	void setWhenEmpty() {
		String expected = "hahaho";
		MethodInterceptorContext.type(TestApiClient.class, String.class)
			.set("$", expected);

		String actual = testApiClient.getMonoEmpty()
			.blockOptional().orElseThrow();

		then(actual).isEqualTo(expected);
	}

	@Test
	void setWhenNull() {
		String expected = "hahaho";
		MethodInterceptorContext.type(TestApiClient.class, String.class)
			.set("$", expected);

		String actual = testApiClient.getNull();

		then(actual).isEqualTo(expected);
	}

	@Test
	void fetchError() {
		String actual = testApiClient.fetch()
			.blockOptional().orElseThrow();

		then(actual).isNotNull();
	}

	@Test
	void getComplexType() {
		String actual = testApiClient.getComplexType().value();

		then(actual).isEqualTo("test");
	}

	@Test
	void setComplexType() {
		String expected = "hahaho";
		MethodInterceptorContext.type(TestApiClient.class, ComplexType.class)
			.set("value", expected);
		String actual = testApiClient.getComplexType().value();

		then(actual).isEqualTo(expected);
	}
}
