# from flask import Flask, request, jsonify
# import face_recognition
# import os
# import requests
# from flask_cors import cross_origin
# from flask_cors import CORS
# import socket
#
# app = Flask(__name__)
# CORS(app)
#
# # Eureka Server URL
# EUREKA_SERVER = 'http://localhost:8761/eureka/apps/'
#
# # Configuration
# UPLOAD_FOLDER = "uploads"
# KNOWN_FOLDER = "../images"
# ALLOWED_EXTENSIONS = {'png', 'jpg', 'jpeg'}
# IMAGE_FOLDER = '../images'
# os.makedirs(UPLOAD_FOLDER, exist_ok=True)
#
# def allowed_file(filename):
#     return '.' in filename and filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS
#
# # Eureka registration
# def register_with_eureka():
#     app_name = 'face-recognition-service'
#     ip = socket.gethostbyname(socket.gethostname())
#     port = 5001
#     headers = {'Content-Type': 'application/json'}
#
#     payload = {
#         "instance": {
#             "hostName": ip,
#             "app": app_name,
#             "ipAddr": ip,
#             "port": {
#                 "$": port,
#                 "@enabled": "true"
#             },
#             "vipAddress": app_name,
#             "secureVipAddress": app_name,
#             "status": "UP",
#             "healthCheckUrl": f"http://{ip}:{port}/health",
#             "homePageUrl": f"http://{ip}:{port}",
#             "dataCenterInfo": {
#                 "@class": "com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo",
#                 "name": "MyOwn"
#             }
#         }
#     }
#
#     try:
#         response = requests.post(EUREKA_SERVER + app_name, json=payload, headers=headers)
#         if response.status_code == 204:
#             print(f"{app_name} registered with Eureka successfully!")
#         else:
#             print(f"Failed to register {app_name} with Eureka. Status Code: {response.status_code}")
#     except Exception as e:
#         print(f"Error connecting to Eureka: {e}")
#
# @app.route('/verify-face', methods=['POST'])
# @cross_origin()
# def verify_face():
#     missing_fields = []
#     if 'image' not in request.files:
#         missing_fields.append('image')
#     if 'studentId' not in request.form:
#         missing_fields.append('studentId')
#
#         user = None
#     if 'studentId' in request.form:
#         user = request.form['studentId']
#     elif 'studentId' in request.args:
#         user = request.args.get('studentId')
#     else:
#         return jsonify({'error': 'Missing studentId parameter'}), 400
#
#     # file = request.files['image']
#
# # if missing_fields:
# #     return jsonify({'error': f"Missing fields: {', '.join(missing_fields)}"}), 400
# #     if 'image' not in request.files or 'studentId' not in request.form:
# #         return jsonify({'error': 'Missing image or studentId'}), 400
#
#     file = request.files['image']
#     # user = request.form['studentId']
#     user_id = str(user)
#
#     try:
#         uploaded_image = face_recognition.load_image_file(file)
#         uploaded_encodings = face_recognition.face_encodings(uploaded_image)
#         if not uploaded_encodings:
#             return jsonify({'error': 'No face found in uploaded image'}), 400
#         uploaded_encoding = uploaded_encodings[0]
#     except Exception as e:
#         return jsonify({'error': 'Error processing uploaded image', 'details': str(e)}), 500
#
#     stored_image_path = os.path.join(IMAGE_FOLDER, f"{user_id}.jpg")
#     if not os.path.exists(stored_image_path):
#         return jsonify({'error': f'Image for studentId {user_id} not found'}), 404
#
#     try:
#         stored_image = face_recognition.load_image_file(stored_image_path)
#         stored_encodings = face_recognition.face_encodings(stored_image)
#         if not stored_encodings:
#             return jsonify({'error': 'No face found in stored image'}), 400
#         stored_encoding = stored_encodings[0]
#     except Exception as e:
#         return jsonify({'error': 'Error processing stored image', 'details': str(e)}), 500
#
#     # Compare the faces with tolerance
#     results = face_recognition.compare_faces([stored_encoding], uploaded_encoding, tolerance=0.4)
#     match = results[0]
#
#     return jsonify(bool(match))
#
# @app.route('/health', methods=['GET'])
# def health_check():
#     return jsonify({'status': 'UP'}), 200
#
# if __name__ == '__main__':
#     register_with_eureka()
#     app.run(debug=True, host='0.0.0.0', port=5001)


from flask import Flask, request, jsonify
import face_recognition
import os
import requests
from flask_cors import cross_origin
import socket

app = Flask(__name__)

# Eureka Server URL - UPDATED to use container name instead of localhost
EUREKA_SERVER = 'http://server-registry:8761/eureka/apps/'

# Configuration
UPLOAD_FOLDER = "uploads"
KNOWN_FOLDER = "images"
ALLOWED_EXTENSIONS = {'png', 'jpg', 'jpeg'}
# IMAGE_FOLDER = 'images'
IMAGE_FOLDER = "/app/images"
print("Image folder path inside container:", IMAGE_FOLDER)
os.makedirs(UPLOAD_FOLDER, exist_ok=True)

def allowed_file(filename):
    return '.' in filename and filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS

# Eureka registration
def register_with_eureka():
    app_name = 'face-recognition-service'
    ip = socket.gethostbyname(socket.gethostname())
    # UPDATED port to match the Docker Compose file (5001 instead of 5000)
    port = 5001
    headers = {'Content-Type': 'application/json'}

    payload = {
        "instance": {
            "hostName": ip,
            "app": app_name,
            "ipAddr": ip,
            "port": {
                "$": port,
                "@enabled": "true"
            },
            "vipAddress": app_name,
            "secureVipAddress": app_name,
            "status": "UP",
            "healthCheckUrl": f"http://{ip}:{port}/health",
            "homePageUrl": f"http://{ip}:{port}",
            "dataCenterInfo": {
                "@class": "com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo",
                "name": "MyOwn"
            }
        }
    }

    try:
        response = requests.post(EUREKA_SERVER + app_name, json=payload, headers=headers)
        if response.status_code == 204:
            print(f"{app_name} registered with Eureka successfully!")
        else:
            print(f"Failed to register {app_name} with Eureka. Status Code: {response.status_code}")
    except Exception as e:
        print(f"Error connecting to Eureka: {e}")

@app.route('/verify-face', methods=['POST'])
@cross_origin()
def verify_face():
    if 'image' not in request.files:
        return jsonify({'error': 'Missing image'}), 400

        # Try to get studentId from form first, then from query params
    student_id = request.form.get('studentId') or request.args.get('studentId')

    if not student_id:
        return jsonify({'error': 'Missing studentId'}), 400

    file = request.files['image']
    user_id = str(student_id)

    try:
        uploaded_image = face_recognition.load_image_file(file)
        uploaded_encodings = face_recognition.face_encodings(uploaded_image)
        if not uploaded_encodings:
            return jsonify({'error': 'No face found in uploaded image'}), 400
        uploaded_encoding = uploaded_encodings[0]
    except Exception as e:
        return jsonify({'error': 'Error processing uploaded image', 'details': str(e)}), 500

    stored_image_path = os.path.join(IMAGE_FOLDER, f"{user_id}.jpg")
    print("Looking for image at:", stored_image_path)
    if not os.path.exists(stored_image_path):
        return jsonify({'error': f'Image for studentId {user_id} not found'}), 404

    try:
        stored_image = face_recognition.load_image_file(stored_image_path)
        stored_encodings = face_recognition.face_encodings(stored_image)
        if not stored_encodings:
            return jsonify({'error': 'No face found in stored image'}), 400
        stored_encoding = stored_encodings[0]
    except Exception as e:
        return jsonify({'error': 'Error processing stored image', 'details': str(e)}), 500

    # Compare the faces with tolerance
    results = face_recognition.compare_faces([stored_encoding], uploaded_encoding, tolerance=0.4)
    match = results[0]

    return jsonify(bool(match))

@app.route('/health', methods=['GET'])
def health_check():
    return jsonify({'status': 'UP'}), 200

if __name__ == '__main__':
    register_with_eureka()
    # UPDATED port to match Docker Compose file
    app.run(debug=True, host='0.0.0.0', port=5001)
