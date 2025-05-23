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
                                    # Clear Docker cache
                                    docker system prune -f || true
                                    docker buildx prune -f || true

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

                stage('Build Docker Images') {
                        stages {
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

                        stage('Build Face Recognition Service') {
                            steps {
                                script {
                                    echo "Building Face Recognition Service..."
                                    sh """
                                        docker build -t ${FACE_RECOGNITION_IMAGE}:${BUILD_NUMBER} \
                                                     -t ${FACE_RECOGNITION_IMAGE}:latest \
                                                     ./face-recognition-service
                                    """
                                }
                            }
                        }
//                         stage('Build Frontend') {
//                             steps {
//                                 script {
//                                     echo "Building Frontend..."
//                                     sh """
//                                         docker build -t ${FRONTEND_IMAGE}:${BUILD_NUMBER} \
//                                                      -t ${FRONTEND_IMAGE}:latest \
//                                                      ./frontend
//                                     """
//                                 }
//                             }
//                         }
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
                                 docker inspect ${FACE_RECOGNITION_IMAGE}:latest > /dev/null

                                echo "images built successfully"
                            """
                        }
                    }
                }


//         stage('Push Docker Images') {
//             steps {
//                 withCredentials([usernamePassword(credentialsId: "${DOCKER_CREDENTIALS}", usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
//                     script {
//                         try {
//                             sh """
//                                 echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin
//                                 docker push ${QR_CODE_ATTENDANCE_IMAGE}:latest
//                                 docker push ${ATTENDANCE_SERVICE_IMAGE}:latest
//                                 docker push ${FACE_RECOGNITION_IMAGE}:latest
//                                 docker push ${EUREKA_SERVER_IMAGE}:latest
//                                 docker push ${FRONTEND_IMAGE}:latest
//                             """
//                         } catch (err) {
//                             echo "Docker push failed: ${err}"
//                             currentBuild.result = 'FAILURE'
//                             error("Docker push stage failed")
//                         }
//                     }
//                 }
//             }
//         }




    }

//     post {
//         failure {
//             echo "Deployment failed! Fetching pod info for debugging..."
//             script {
//                 sh '''
//                     export KUBECONFIG=$HOME/.kube/config
//                     kubectl get pods --namespace ${NAMESPACE}
//                     kubectl describe pods --namespace ${NAMESPACE}
//                 '''
//             }
//         }
//         success {
//             echo "Pipeline success"
//         }
//     }


}

