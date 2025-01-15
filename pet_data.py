from flask import Blueprint, request, jsonify
from mongodb_connection_manager import MongoConnectionManager
import uuid

# Initialize Blueprint
pets_blueprint = Blueprint('pets', __name__)

# 1. Get All Pets
@pets_blueprint.route('/get_all_pets', methods=['GET'])
def get_all_pets():
    """
    Retrieve all pets from the database
    ---
    responses:
        200:
            description: A list of all pets
        500:
            description: An error occurred while fetching pets
    """
    db = MongoConnectionManager.get_db()
    pets_collection = db['pets']
    
    try:
        pets = list(pets_collection.find())
        for pet in pets:
            pet['_id'] = str(pet['_id'])  # Ensure _id is string
        return jsonify(pets), 200
    except Exception as e:
        return jsonify({"error": str(e)}), 500

# 2. Get Pet by Name
@pets_blueprint.route('/get_pet/<name>', methods=['GET'])
def get_pet_by_name(name):
    """
    Retrieve a pet by its name
    ---
    parameters:
      - name: name
        in: path
        type: string
        required: true
        description: The name of the pet
    responses:
        200:
            description: Pet found and returned
        404:
            description: Pet not found
    """ 
    db = MongoConnectionManager.get_db()
    pets_collection = db['pets']
    try:
        pet = pets_collection.find_one({"name": name})
        if pet:
            pet['_id'] = str(pet['_id'])
            return jsonify(pet), 200
        return jsonify({"error": "Pet not found"}), 404
    except Exception as e:
        return jsonify({"error": str(e)}), 500

# 3. Add a Pet
@pets_blueprint.route('/add_pet', methods=['POST'])
def add_pet():
    """
    Add a new pet to the database
    ---
    parameters:
      - name: body
        in: body
        required: true
        schema:
            type: object
            properties:
                name:
                    type: string
                breed:
                    type: string
                age:
                    type: integer
                last_vaccinated:
                    type: integer
                weight:
                    type: number
                gender:
                    type: string
                microchipped:
                    type: boolean
    responses:
        201:
            description: Pet added successfully
        400:
            description: Invalid input
        500:
            description: An error occurred
    """
    db = MongoConnectionManager.get_db()
    pets_collection = db['pets']
    try:
        data = request.json
        required_fields = ['name', 'breed', 'age', 'last_vaccinated', 'weight', 'gender', 'microchipped']

        # Validate required fields
        if not data or not all(field in data for field in required_fields):
            return jsonify({"error": f"Missing required fields: {required_fields}"}), 400

        pet = {
            "_id": str(uuid.uuid4()),
            **data
        }
        pets_collection.insert_one(pet)
        return jsonify({"message": "Pet added successfully", "_id": pet["_id"]}), 201
    except Exception as e:
        return jsonify({"error": str(e)}), 500

# 4. Update Pet
@pets_blueprint.route('/update_pet/<name>', methods=['PUT'])
def update_pet(name):
    """
    Update an existing pet's details
    ---
    parameters:
      - name: name
        in: path
        required: true
        type: string
        description: The name of the pet to update
      - name: body
        in: body
        required: true
        schema:
            type: object
    responses:
        200:
            description: Pet updated successfully
        404:
            description: Pet not found
        500:
            description: An error occurred
    """
    db = MongoConnectionManager.get_db()
    pets_collection = db['pets']
    try:
        data = request.json
        result = pets_collection.update_one({'name': name}, {'$set': data})
        if result.matched_count > 0:
            return jsonify({"message": "Pet updated successfully"}), 200
        return jsonify({"error": "Pet not found"}), 404
    except Exception as e:
        return jsonify({"error": str(e)}), 500

# 5. Delete a Pet
@pets_blueprint.route('/delete_pet/<name>', methods=['DELETE'])
def delete_pet(name):
    """
    Delete a pet from the database
    ---
    parameters:
      - name: name
        in: path
        required: true
        type: string
        description: The name of the pet to delete
    responses:
        200:
            description: Pet deleted successfully
        404:
            description: Pet not found
        500:
            description: An error occurred
    """
    db = MongoConnectionManager.get_db()
    pets_collection = db['pets']
    try:
        result = pets_collection.delete_one({"name": name})
        if result.deleted_count > 0:
            return jsonify({"message": "Pet deleted successfully"}), 200
        return jsonify({"error": "Pet not found"}), 404
    except Exception as e:
        return jsonify({"error": str(e)}), 500
    

# 6. Get Pets by Breed
@pets_blueprint.route('/pets_by_breed/<breed>', methods=['GET'])
def get_pets_by_breed(breed):
    """
    Retrieve all pets of a specific breed
    ---
    parameters:
      - name: breed
        in: path
        required: true
        type: string
        description: The breed of the pets to retrieve
    responses:
        200:
            description: A list of pets of the specified breed
        404:
            description: No pets found
        500:
            description: An error occurred
    """
    db = MongoConnectionManager.get_db()
    pets_collection = db['pets']
    try:
        pets = list(pets_collection.find({"breed": breed}))
        for pet in pets:
            pet['_id'] = str(pet['_id'])
        if pets:
            return jsonify(pets), 200
        return jsonify({"error": "No pets found"}), 404
    except Exception as e:
        return jsonify({"error": str(e)}), 500
    

    # 7. Get Pets by Age
@pets_blueprint.route('/pets_by_age/<int:age>', methods=['GET'])
def get_pets_by_age(age):
    """
    Retrieve all pets of a specific age
    ---
    parameters:
      - name: age
        in: path
        required: true
        type: integer
        description: The age of the pets to retrieve
    responses:
        200:
            description: A list of pets of the specified age
        404:
            description: No pets found
        500:
            description: An error occurred
    """
    db = MongoConnectionManager.get_db()
    pets_collection = db['pets']
    try:
        pets = list(pets_collection.find({"age": age}))
        for pet in pets:
            pet['_id'] = str(pet['_id'])
        if pets:
            return jsonify(pets), 200
        return jsonify({"error": "No pets found"}), 404
    except Exception as e:
        return jsonify({"error": str(e)}), 500
    


    # 8. Get Recently Vaccinated Pets
@pets_blueprint.route('/recently_vaccinated/<int:months>', methods=['GET'])
def get_recently_vaccinated(months):
    """
    Retrieve all pets vaccinated within the last specified number of months
    ---
    parameters:
      - name: months
        in: path
        required: true
        type: integer
        description: The number of months to check for recent vaccinations
    responses:
        200:
            description: A list of pets vaccinated recently
        404:
            description: No pets found
        500:
            description: An error occurred
    """
    db = MongoConnectionManager.get_db()
    pets_collection = db['pets']
    try:
        pets = list(pets_collection.find({"last_vaccinated": {"$lte": months}}))
        for pet in pets:
            pet['_id'] = str(pet['_id'])
        if pets:
            return jsonify(pets), 200
        return jsonify({"error": "No pets found"}), 404
    except Exception as e:
        return jsonify({"error": str(e)}), 500
    

    # 9. Get Pets by Weight Range
@pets_blueprint.route('/pets_by_weight', methods=['GET'])
def get_pets_by_weight():
    """
    Retrieve pets within a specified weight range
    ---
    parameters:
      - name: min_weight
        in: query
        required: true
        type: number
        description: The minimum weight of the pets to retrieve
      - name: max_weight
        in: query
        required: true
        type: number
        description: The maximum weight of the pets to retrieve
    responses:
        200:
            description: A list of pets within the weight range
        404:
            description: No pets found
        500:
            description: An error occurred
    """
    db = MongoConnectionManager.get_db()
    pets_collection = db['pets']
    try:
        min_weight = float(request.args.get('min_weight', 0))
        max_weight = float(request.args.get('max_weight', float('inf')))
        pets = list(pets_collection.find({"weight": {"$gte": min_weight, "$lte": max_weight}}))
        for pet in pets:
            pet['_id'] = str(pet['_id'])
        if pets:
            return jsonify(pets), 200
        return jsonify({"error": "No pets found"}), 404
    except Exception as e:
        return jsonify({"error": str(e)}), 500