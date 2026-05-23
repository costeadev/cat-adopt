package catadopt.service;

import java.util.List;

import catadopt.model.Cat;

public interface DatabaseService {
	void adoptCat(Cat cat);
	List<Cat> getAdoptedCats();
}
