// pipeline {
//     agent any
//
//     environment {
//         DOCKER_REGISTRY = 'docker.io/juhir10'
//         NAMESPACE = 'spe-final'
//         DOCKER_CREDENTIALS = 'docker-spe-final'
//
//         QR_CODE_ATTENDANCE_IMAGE = "${DOCKER_REGISTRY}/qr-code-attendance"
//         ATTENDANCE_SERVICE_IMAGE = "${DOCKER_REGISTRY}/attendance-service"
//         FACE_RECOGNITION_IMAGE = "${DOCKER_REGISTRY}/face-recognition-service"
//         EUREKA_SERVER_IMAGE = "${DOCKER_REGISTRY}/server-registry"
//         FRONTEND_IMAGE = "${DOCKER_REGISTRY}/frontend"
//     }
//
//     stages {
//         stage('Git Clone') {
//             steps {
//                 git branch: 'test-all', url: 'https://github.com/Juhi1010/SPE_Attendance_Tracker_System.git'
//             }
//         }
//
//         stage('Docker Login & Network Fix') {
//                     steps {
//                         withCredentials([usernamePassword(credentialsId: "${DOCKER_CREDENTIALS}", usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
//                             script {
//                                 sh '''
//
//                                     # Login to Docker Hub
//                                     echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin
//
//                                     # Pre-pull base images to avoid timeout during build
//                                     echo "Pre-pulling base images..."
//                                     docker pull python:3.9-slim || docker pull python:3.9 || echo "Using fallback"
//                                     docker pull node:16-alpine || echo "Node image not needed"
//                                     docker pull openjdk:11-jre-slim || echo "Java image not needed"
//                                 '''
//                             }
//                         }
//                     }
//                 }
//
//
//
//         stage('Build Eureka Server') {
//             steps {
//                 script {
//                     echo "Building Eureka Server..."
//                     sh """
//                         docker build -t ${EUREKA_SERVER_IMAGE}:${BUILD_NUMBER} \
//                                      -t ${EUREKA_SERVER_IMAGE}:latest \
//                                      ./Server_registry
//                     """
//                 }
//             }
//         }
//
//
//         stage('Build QR Code Service') {
//             steps {
//                 script {
//                     echo "Building QR Code Attendance Service..."
//                     sh """
//                         docker build -t ${QR_CODE_ATTENDANCE_IMAGE}:${BUILD_NUMBER} \
//                                      -t ${QR_CODE_ATTENDANCE_IMAGE}:latest \
//                                      ./QR_code_attendance
//                     """
//                 }
//             }
//         }
//
//
//         stage('Build Attendance Service') {
//             steps {
//                 script {
//                     echo "Building Attendance Service..."
//                     sh """
//                         docker build -t ${ATTENDANCE_SERVICE_IMAGE}:${BUILD_NUMBER} \
//                                      -t ${ATTENDANCE_SERVICE_IMAGE}:latest \
//                                      ./Attendance_service
//                     """
//                 }
//             }
//         }
//
//         stage('Build Frontend') {
//             steps {
//                 script {
//                     echo "Building Frontend..."
//                     sh """
//                         docker build -t ${FRONTEND_IMAGE}:${BUILD_NUMBER} \
//                                      -t ${FRONTEND_IMAGE}:latest \
//                                      ./frontend
//                     """
//                 }
//             }
//         }
//
//        stage('Push Docker Images') {
//                steps {
//                    withCredentials([usernamePassword(credentialsId: "${DOCKER_CREDENTIALS}", usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
//                        script {
//                            try {
//                                sh """
//                                    echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin
//                                    docker push ${QR_CODE_ATTENDANCE_IMAGE}:latest
//                                    docker push ${ATTENDANCE_SERVICE_IMAGE}:latest
//                                    docker push ${FACE_RECOGNITION_IMAGE}:latest
//                                    docker push ${EUREKA_SERVER_IMAGE}:latest
//                                    docker push ${FRONTEND_IMAGE}:latest
//                                """
//                            } catch (err) {
//                                echo "Docker push failed: ${err}"
//                                currentBuild.result = 'FAILURE'
//                                error("Docker push stage failed")
//                            }
//                        }
//                    }
//                }
//            }
//
//
//     }
//
// }



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
                            echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin

                            echo "Pre-pulling base images..."
                            docker pull python:3.9-slim || docker pull python:3.9 || echo "Using fallback"
                            docker pull node:16-alpine || echo "Node image not needed"
                            docker pull openjdk:11-jre-slim || echo "Java image not needed"
                        '''
                    }
                }
            }
        }

        // Build stages
        stage('Build Eureka Server') {
            steps {
                script {
                    echo "Building Eureka Server..."
                    sh """
                        docker build -t ${EUREKA_SERVER_IMAGE}:${BUILD_NUMBER} -t ${EUREKA_SERVER_IMAGE}:latest ./Server_registry
                    """
                }
            }
        }

        stage('Build QR Code Service') {
            steps {
                script {
                    echo "Building QR Code Attendance Service..."
                    sh """
                        docker build -t ${QR_CODE_ATTENDANCE_IMAGE}:${BUILD_NUMBER} -t ${QR_CODE_ATTENDANCE_IMAGE}:latest ./QR_code_attendance
                    """
                }
            }
        }

        stage('Build Attendance Service') {
            steps {
                script {
                    echo "Building Attendance Service..."
                    sh """
                        docker build -t ${ATTENDANCE_SERVICE_IMAGE}:${BUILD_NUMBER} -t ${ATTENDANCE_SERVICE_IMAGE}:latest ./Attendance_service
                    """
                }
            }
        }

//         stage('Build Face Recognition Service') {
//             steps {
//                 script {
//                     echo "Building Face Recognition Service..."
//                     sh """
//                         docker build -t ${FACE_RECOGNITION_IMAGE}:${BUILD_NUMBER} -t ${FACE_RECOGNITION_IMAGE}:latest ./Face_recognition_service
//                     """
//                 }
//             }
//         }

        stage('Build Frontend') {
            steps {
                script {
                    echo "Building Frontend..."
                    sh """
                        docker build -t ${FRONTEND_IMAGE}:${BUILD_NUMBER} -t ${FRONTEND_IMAGE}:latest ./frontend
                    """
                }
            }
        }


        // Deployment stages
        stage('Deploy PostgreSQL to Kubernetes') {
            steps {
                script {
                    sh """
                        echo "Deploying PostgreSQL..."
                        export KUBECONFIG=\$HOME/.kube/config

                        kubectl apply -f k8s/postgres/deployment.yaml -n ${NAMESPACE}
                        kubectl apply -f k8s/postgres/service.yaml -n ${NAMESPACE}
                        kubectl apply -f k8s/postgres/hpa.yaml -n ${NAMESPACE}

                        kubectl rollout status deployment/postgres-deployment -n ${NAMESPACE} --timeout=120s
                    """
                }
            }
        }

        stage('Deploy Eureka Server to Kubernetes') {
            steps {
                script {
                    sh """
                        echo "Waiting for PostgreSQL to be ready..."
                        export KUBECONFIG=\$HOME/.kube/config

                        kubectl rollout status deployment/postgres-deployment -n ${NAMESPACE} --timeout=120s

                        echo "Deploying Eureka Server..."
                        kubectl apply -f k8s/server-registry/deployment.yaml -n ${NAMESPACE}
                        kubectl apply -f k8s/server-registry/service.yaml -n ${NAMESPACE}
                        kubectl apply -f k8s/server-registry/hpa.yaml -n ${NAMESPACE}

                        kubectl rollout status deployment/eureka-server-deployment -n ${NAMESPACE} --timeout=120s
                    """
                }
            }
        }

        stage('Deploy Face Recognition Service to Kubernetes') {
            steps {
                script {
                    sh """
                        echo "Waiting for PostgreSQL and Eureka Server..."
                        export KUBECONFIG=\$HOME/.kube/config

                        kubectl rollout status deployment/postgres-deployment -n ${NAMESPACE} --timeout=120s
                        kubectl rollout status deployment/eureka-server-deployment -n ${NAMESPACE} --timeout=120s

                        echo "Deploying Face Recognition Service..."
                        kubectl apply -f k8s/face-recognition-service/deployment.yaml -n ${NAMESPACE}
                        kubectl apply -f k8s/face-recognition-service/service.yaml -n ${NAMESPACE}
                        kubectl apply -f k8s/face-recognition-service/hpa.yaml -n ${NAMESPACE}

                        kubectl rollout status deployment/face-recognition-service -n ${NAMESPACE} --timeout=120s
                    """
                }
            }
        }

        stage('Deploy Attendance Service to Kubernetes') {
            steps {
                script {
                    sh """
                        echo "Waiting for PostgreSQL, Eureka, Face Recognition Service..."
                        export KUBECONFIG=\$HOME/.kube/config

                        kubectl rollout status deployment/postgres-deployment -n ${NAMESPACE} --timeout=120s
                        kubectl rollout status deployment/eureka-server-deployment -n ${NAMESPACE} --timeout=120s
                        kubectl rollout status deployment/face-recognition-service -n ${NAMESPACE} --timeout=120s

                        echo "Deploying Attendance Service..."
                        kubectl apply -f k8s/attendance/deployment.yaml -n ${NAMESPACE}
                        kubectl apply -f k8s/attendance/service.yaml -n ${NAMESPACE}
                        kubectl apply -f k8s/attendance/hpa.yaml -n ${NAMESPACE}

                        kubectl rollout status deployment/attendance-service -n ${NAMESPACE} --timeout=120s
                    """
                }
            }
        }

        stage('Deploy QR Code Attendance Service') {
            steps {
                script {
                    sh """
                        echo "Waiting for dependent services to be ready..."
                        export KUBECONFIG=\$HOME/.kube/config

                        kubectl rollout status deployment/eureka-server-deployment -n ${NAMESPACE} --timeout=120s
                        kubectl rollout status deployment/face-recognition-service -n ${NAMESPACE} --timeout=120s
                        kubectl rollout status deployment/postgres-deployment -n ${NAMESPACE} --timeout=120s
                        kubectl rollout status deployment/attendance-service -n ${NAMESPACE} --timeout=120s

                        echo "Deploying QR Code Attendance Service..."
                        kubectl apply -f k8s/qr-code-attendance/deployment.yaml -n ${NAMESPACE}
                        kubectl apply -f k8s/qr-code-attendance/service.yaml -n ${NAMESPACE}
                        kubectl apply -f k8s/qr-code-attendance/hpa.yaml -n ${NAMESPACE}

                        kubectl rollout status deployment/qr-code-attendance -n ${NAMESPACE} --timeout=120s
                    """
                }
            }
        }

        stage('Deploy Frontend Service') {
            steps {
                script {
                    sh """
                        echo "Waiting for all dependent services to be ready..."
                        export KUBECONFIG=\$HOME/.kube/config

                        kubectl rollout status deployment/eureka-server-deployment -n ${NAMESPACE} --timeout=120s
                        kubectl rollout status deployment/face-recognition-service -n ${NAMESPACE} --timeout=120s
                        kubectl rollout status deployment/postgres-deployment -n ${NAMESPACE} --timeout=120s
                        kubectl rollout status deployment/attendance-service -n ${NAMESPACE} --timeout=120s
                        kubectl rollout status deployment/qr-code-attendance -n ${NAMESPACE} --timeout=120s

                        echo "Deploying Frontend Service..."
                        kubectl apply -f k8s/frontend/deployment.yaml -n ${NAMESPACE}
                        kubectl apply -f k8s/frontend/service.yaml -n ${NAMESPACE}
                        kubectl apply -f k8s/frontend/hpa.yaml -n ${NAMESPACE} || echo "No HPA defined for frontend"

                        kubectl rollout status deployment/frontend -n ${NAMESPACE} --timeout=120s
                    """
                }
            }
        }
    }
}

