pipeline {
    agent any

    environment {
        DOCKER_REGISTRY = 'docker.io/juhir10'
        NAMESPACE = 'spe-final'
        DOCKER_CREDENTIALS = 'docker-spe-final'

        QR_CODE_ATTENDANCE_IMAGE = "${DOCKER_REGISTRY}/qr-code-attendance"
        ATTENDANCE_SERVICE_IMAGE = "${DOCKER_REGISTRY}/attendance-service"
        FACE_RECOGNITION_IMAGE = "${DOCKER_REGISTRY}/face-recognition-service"
        EUREKA_SERVER_IMAGE = "${DOCKER_REGISTRY}/server-registry"
        FRONTEND_IMAGE = "${DOCKER_REGISTRY}/frontend"
    }

    stages {
        stage('Git Clone') {
            steps {
                git branch: 'test-all', url: 'https://github.com/Juhi1010/SPE_Attendance_Tracker_System.git'
            }
        }

        stage('Docker Login & Network Fix') {
                    steps {
                        withCredentials([usernamePassword(credentialsId: "${DOCKER_CREDENTIALS}", usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                            script {
                                sh '''

                                    # Login to Docker Hub
                                    echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin

                                    # Pre-pull base images to avoid timeout during build
                                    echo "Pre-pulling base images..."
                                    docker pull python:3.9-slim || docker pull python:3.9 || echo "Using fallback"
                                    docker pull node:16-alpine || echo "Node image not needed"
                                    docker pull openjdk:11-jre-slim || echo "Java image not needed"
                                '''
                            }
                        }
                    }
                }



        stage('Build Eureka Server') {
            steps {
                script {
                    echo "Building Eureka Server..."
                    sh """
                        docker build -t ${EUREKA_SERVER_IMAGE}:${BUILD_NUMBER} \
                                     -t ${EUREKA_SERVER_IMAGE}:latest \
                                     ./Server_registry
                    """
                }
            }
        }


        stage('Build QR Code Service') {
            steps {
                script {
                    echo "Building QR Code Attendance Service..."
                    sh """
                        docker build -t ${QR_CODE_ATTENDANCE_IMAGE}:${BUILD_NUMBER} \
                                     -t ${QR_CODE_ATTENDANCE_IMAGE}:latest \
                                     ./QR_code_attendance
                    """
                }
            }
        }


        stage('Build Attendance Service') {
            steps {
                script {
                    echo "Building Attendance Service..."
                    sh """
                        docker build -t ${ATTENDANCE_SERVICE_IMAGE}:${BUILD_NUMBER} \
                                     -t ${ATTENDANCE_SERVICE_IMAGE}:latest \
                                     ./Attendance_service
                    """
                }
            }
        }



        stage('Test Images') {
            steps {
                script {
                    echo "Verifying built images..."
                    sh """
                        docker images | grep ${DOCKER_REGISTRY}/

                        # Basic image inspection
                         docker inspect ${EUREKA_SERVER_IMAGE}:latest > /dev/null
                         docker inspect ${QR_CODE_ATTENDANCE_IMAGE}:latest > /dev/null
                         docker inspect ${ATTENDANCE_SERVICE_IMAGE}:latest > /dev/null

                        echo "images built successfully"
                    """
                }
            }
        }
    }


}

