


The PetCare project is an Android-based application with an integrated API service that helps users manage pet-related data, such as pet details, vaccination status, breed, age, and more. The project consists of two main parts:
	1.	The PetCare Android Application: A mobile app developed to interact with the PetCare API.
	2.	The PetCare API: A backend service built with Flask and MongoDB to store and manage pet data.

This repository also includes a PetCareLib Android library that wraps the API and provides developers with methods to interact with the backend easily.


Architecture
The project follows a client-server architecture with a mobile app (client) and a Flask API (server) connected to a MongoDB database.

Components:
	1.	PetCare Android App:
	    •	A native Android app written in Java that interacts with the backend API to fetch, update, or delete pet data.
	    •	The app uses Retrofit for API requests.
	2.	PetCareLib:
	    •	An Android library module that simplifies the interaction with the PetCare API. It wraps API calls and provides Java developers with easy-to-use methods to fetch or manage pet data.
	3.	PetCare API:
	    •	Built with Flask(Python), it exposes various endpoints to handle operations such as adding a pet, fetching pet data, updating pet details, and deleting pet data.
	    •	MongoDB stores the pet information, including details such as name, breed, age, last vaccination status, etc.


Features
    1. PetCare API
	    •	GET /get_all_pets: Fetch all pets from the database.
	    •	GET /get_pet_by_name/<name>: Fetch pet details by name.
	    •	POST /add_pet: Add a new pet to the database.
	    •	PUT /update_pet/<name>: Update details for an existing pet.
	    •	DELETE /delete_pet/<name>: Delete a pet from the database.

    2. PetCare Android App
	    •	View Pets: Users can view a list of pets stored in the system.
	    •	Add Pets: Users can add new pets, including their breed, age, vaccination status, etc.
	    •	Update Pets: Modify existing pet details.
	    •	Delete Pets: Delete pet records from the database.



Usage
PetCare API
	1.	Start the Flask API:
    	•	Navigate to the project directory containing the Flask app.
    	•	Run python3 app.py in macOS or py app.py in Windows to start the Flask API.
    	•	The API will be available at http://127.0.0.1:8088.
	2.	API Endpoints:
    	•	Use tools like Postman or curl to interact with the API:
    	•	GET all pets: http://127.0.0.1:8088/get_all_pets
    	•	POST add pet: http://127.0.0.1:8088/add_pet

PetCare Android Application
	1.	Clone the repository:
	    •	Clone this repository to your local machine.
    	•	Open the project in Android Studio.
	2.	Run the App:
    	•	In Android Studio, click on Run to launch the app.
    	•	The app should be connected to the locally running PetCare API.
	3.	Using PetCareLib:
    	•	PetCareLib is included as a module. You can use it to make API calls in your app:


Installation
	1.	Clone the repository:
        git clone https://github.com/DanielSelas/PetCare.git
    2.	Navigate to the project folder and install the necessary dependencies.
	3.	Run the Flask API on your local machine.
	4.	Open the Android project in Android Studio.
	5.	Run the app using Android Studio on a physical or virtual device.