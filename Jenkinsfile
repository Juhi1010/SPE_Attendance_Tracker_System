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

        stage('Docker Login') {
            steps {
                withCredentials([usernamePassword(credentialsId: "${DOCKER_CREDENTIALS}", usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    script {
                        sh '''
                            echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin
                        '''
                    }
                }
            }
        }

        stage('Build Docker Images') {
            steps {
                sh "docker build -t ${QR_CODE_ATTENDANCE_IMAGE}:latest ./QR_code_attendance"
                sh "docker build -t ${ATTENDANCE_SERVICE_IMAGE}:latest ./Attendance_service"
                sh "docker build -t ${FACE_RECOGNITION_IMAGE}:latest ./face-recognition-service"
                sh "docker build -t ${EUREKA_SERVER_IMAGE}:latest ./Server_registry"
                sh "docker build -t ${FRONTEND_IMAGE}:latest ./frontend"
            }
        }

        stage('Push Docker Images') {
            steps {
                withCredentials([usernamePassword(credentialsId: "${DOCKER_CREDENTIALS}", usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    script {
                        try {
                            sh """
                                echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin
                                docker push ${QR_CODE_ATTENDANCE_IMAGE}:latest
                                docker push ${ATTENDANCE_SERVICE_IMAGE}:latest
                                docker push ${FACE_RECOGNITION_IMAGE}:latest
                                docker push ${EUREKA_SERVER_IMAGE}:latest
                                docker push ${FRONTEND_IMAGE}:latest
                            """
                        } catch (err) {
                            echo "Docker push failed: ${err}"
                            currentBuild.result = 'FAILURE'
                            error("Docker push stage failed")
                        }
                    }
                }
            }
        }


    }

    post {
        failure {
            echo "Deployment failed! Fetching pod info for debugging..."
            script {
                sh '''
                    export KUBECONFIG=$HOME/.kube/config
                    kubectl get pods --namespace ${NAMESPACE}
                    kubectl describe pods --namespace ${NAMESPACE}
                '''
            }
        }
        success {
            echo "Pipeline success"
        }
    }


}

