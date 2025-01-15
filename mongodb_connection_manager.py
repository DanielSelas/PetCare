from dotenv import load_dotenv
from pymongo.mongo_client import MongoClient
from pymongo.server_api import ServerApi
import os

# Load environment variables from .env file
load_dotenv()

# Fetch database connection details from environment variables
DB_CONNECTION_STRING = os.getenv("DB_CONNECTION_STRING")
DB_NAME = os.getenv("DB_NAME")
DB_USERNAME = os.getenv("DB_USERNAME")
DB_PASSWORD = os.getenv("DB_PASSWORD")




# Construct the MongoDB URI
MONGO_URI = f"mongodb+srv://{DB_USERNAME}:{DB_PASSWORD}@{DB_CONNECTION_STRING}/{DB_NAME}?tlsAllowInvalidCertificates=true"

class MongoConnectionManager:
    __db = None

    @staticmethod
    def initialize_db():
        """
        Initialize the database connection
        :return: MongoDB connection
        :rtype: Database
        """
        if MongoConnectionManager.__db is None:
            try:
                # Create a new client and connect to the server
                client = MongoClient(MONGO_URI, server_api=ServerApi('1'))

                # Ping the database to confirm connection
                client.admin.command('ping')
                print("Connected successfully to MongoDB!")



                MongoConnectionManager.__db = client[DB_NAME]
            except Exception as e:
                print(f"Error connecting to MongoDB: {e}")
                MongoConnectionManager.__db = None


        return MongoConnectionManager.__db


    @staticmethod
    def get_db():
        """
        Get the database connection
        :return: MongoDB connection
        :rtype: Database
        """
        if MongoConnectionManager.__db is None:
            
            MongoConnectionManager.initialize_db()

        return MongoConnectionManager.__db
    