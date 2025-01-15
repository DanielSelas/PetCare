import os
from flask import Flask
from flasgger import Swagger
from mongodb_connection_manager import MongoConnectionManager
from routes import initial_routes

app = Flask(__name__)
Swagger(app)

# Initialize Database Connection
MongoConnectionManager.initialize_db()


# Import the routes
initial_routes(app)

if __name__ == '__main__':  # Correct the typo
    port = int(os.environ.get('PORT', 8088))
    app.run(debug=True, port=port)



    