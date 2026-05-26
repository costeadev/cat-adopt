package catadopt.service.api;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

import catadopt.model.Cat;
import catadopt.service.CatApiService;

public class TheCatApiService implements CatApiService {

	@Override
	public List<Cat> fetchCats() {

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://api.thecatapi.com/v1/images/search?limit=10")).build();

		return client.sendAsync(request, BodyHandlers.ofString()).thenApply(response -> {

			if (response.statusCode() != 200) {
				throw new RuntimeException("HTTP error: " + response.statusCode() + " body: " + response.body());
			}

			String body = response.body();

			Gson gson = new Gson();
			Cat[] cats = gson.fromJson(body, Cat[].class);
			return new ArrayList<>(Arrays.asList(cats));
		}).join();
	}

}