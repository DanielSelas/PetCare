package com.example.petcarelib;

public class Pet {
    private String _id;
    private String name;
    private String breed;
    private int age;
    private int last_vaccinated;
    private double weight;
    private String gender;
    private boolean microchipped;

    // Default constructor
    public Pet() {
    }

    // Parameterized constructor
    public Pet(String name, String breed, int age, int last_vaccinated, double weight, String gender, boolean microchipped) {
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.last_vaccinated = last_vaccinated;
        this.weight = weight;
        this.gender = gender;
        this.microchipped = microchipped;
    }

    // Getters and Setters
    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getLastVaccinated() {
        return last_vaccinated;
    }

    public void setLastVaccinated(int last_vaccinated) {
        this.last_vaccinated = last_vaccinated;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isMicrochipped() {
        return microchipped;
    }

    public void setMicrochipped(boolean microchipped) {
        this.microchipped = microchipped;
    }

    @Override
    public String toString() {
        return "Name: " + name + "\n" +
                "Breed: " + breed + "\n" +
                "Age: " + age + "\n" +
                "Last Vaccinated: " + last_vaccinated + " months ago\n" +
                "Weight: " + weight + " kg\n" +
                "Gender: " + gender + "\n" +
                "Microchipped: " + (microchipped ? "Yes" : "No");
    }
}
