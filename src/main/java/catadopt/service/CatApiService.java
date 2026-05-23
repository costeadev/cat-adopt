package catadopt.service;

import java.util.List;

import catadopt.model.Cat;

public interface CatApiService {
	List<Cat> fetchCats();
}
