package hello;

import org.springframework.data.annotation.Id;

import javax.annotation.Generated;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class User {
    @Id
    private Long id;

    @NotNull
    @Size(min=2, max=30)
    private String first_name;

    @NotNull
    @Size(min=2, max=45)
    private String last_name;

    @NotNull
    private String gender;

    @NotNull
    @Min(18)
    @Max(34)
    private Integer age;

    @NotNull
    private String country;
    //constructors getters setters

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public User(){}

    public User(String first_name, String lastName, Integer age, String sex, String country) {
        this.first_name = first_name;
        this.last_name = lastName;
        this.age = age;
        this.gender = sex;
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSex() {
		return gender;
	}

	public void setSex(String sex) {
		this.gender = sex;
	}

	@Override
    public String toString() {
        return "User(Id: " + this.id + "Name: " + this.first_name + " " +this.last_name + ", Age: " + this.age + ")";
    }
    public static enum COUNTRIES { Austria,
        Litwa, Belgia, Luksemburg, Bułgaria, Łotwa, Chorwacja, Malta, Cypr,
        Niemcy, Czechy, Polska, Dania, Portugalia, Estonia, Rumunia, Finlandia,
        Słowacja, Francja, Słowenia, Grecja, Szwecja, Hiszpania, Węgry, Holandia,
        Włochy, Irlandia, Zjednoczone_Królestwo }
}
