package catadopt.service.api;

import java.util.List;

import catadopt.model.Cat;
import catadopt.service.CatApiService;

public class MockCatApiService implements CatApiService {

	@Override
	public List<Cat> fetchCats() {
		return List.of(
				new Cat("MTUxOTg2Mg", "https://cdn2.thecatapi.com/images/MTUxOTg2Mg.jpg"),
				new Cat("a52", "https://cdn2.thecatapi.com/images/a52.jpg"),
				new Cat ("clm", "https://cdn2.thecatapi.com/images/clm.jpg")
		);
	}
	
}
