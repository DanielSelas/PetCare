from pet_data import pets_blueprint

def initial_routes(app):
    app.register_blueprint(pets_blueprint)