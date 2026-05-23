package catadopt.model;

import java.util.Objects;

// Alberto
public class Cat {
	private String id;
	private String name;
	private String imageUrl;
	
	public Cat(String id, String name, String imageUrl) {
		this.id = id;
		this.name = name;
		this.imageUrl = imageUrl;
	}
	
	public Cat(String id, String imageUrl) {
		this.id = id;
		this.name = "Sin nombre";
		this.imageUrl = imageUrl;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cat other = (Cat) obj;
		return Objects.equals(id, other.id);
	}
	
	
	@Override
	public String toString() {
		return "Cat [id=" + id + ", name=" + name + ", imageUrl=" + imageUrl + "]";
	}
}
