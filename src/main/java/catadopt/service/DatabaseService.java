package catadopt.service;

import java.util.List;

import catadopt.model.Cat;

public interface DatabaseService {
	void start();
	
	void adoptCat(Cat cat);

	List<Cat> getAdoptedCats();

	void renameCat(Cat cat);
	
	void removeCat(Cat cat);
}
