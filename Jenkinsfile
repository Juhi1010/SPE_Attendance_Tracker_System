pipeline {
    agent any

    environment {
        DOCKER_REGISTRY = 'docker.io/juhir10'
        NAMESPACE = 'spe-final'
        DOCKER_CREDENTIALS = 'docker-credentials'

        QR_CODE_ATTENDANCE_IMAGE = "${DOCKER_REGISTRY}/qr-code-attendance"
        ATTENDANCE_SERVICE_IMAGE = "${DOCKER_REGISTRY}/attendance-service"
        FACE_RECOGNITION_IMAGE = "${DOCKER_REGISTRY}/face-recognition-service"
        EUREKA_SERVER_IMAGE = "${DOCKER_REGISTRY}/server-registry"
        FRONTEND_IMAGE = "${DOCKER_REGISTRY}/frontend"

        IMAGE_TAG = "build-${env.BUILD_NUMBER}"
    }

    stages {
        stage('Git Clone') {
            steps {
                git branch: 'test-all', url: 'https://github.com/Juhi1010/SPE_Attendance_Tracker_System.git'
            }
        }

        stage('Build Docker Images') {
            steps {
                sh "docker build -t ${QR_CODE_ATTENDANCE_IMAGE}:${IMAGE_TAG} ./QR_code_attendance"
                sh "docker build -t ${ATTENDANCE_SERVICE_IMAGE}:${IMAGE_TAG} ./Attendance-service"
                sh "docker build -t ${FACE_RECOGNITION_IMAGE}:${IMAGE_TAG} ./face-recognition-service"
                sh "docker build -t ${EUREKA_SERVER_IMAGE}:${IMAGE_TAG} ./Server_registry"
                sh "docker build -t ${FRONTEND_IMAGE}:${IMAGE_TAG} ./frontend"
            }
        }

        stage('Push Docker Images') {
            steps {
                withCredentials([usernamePassword(credentialsId: "${DOCKER_CREDENTIALS}", usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh """
                        echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin
                        docker push ${QR_CODE_ATTENDANCE_IMAGE}:${IMAGE_TAG}
                        docker push ${ATTENDANCE_SERVICE_IMAGE}:${IMAGE_TAG}
                        docker push ${FACE_RECOGNITION_IMAGE}:${IMAGE_TAG}
                        docker push ${EUREKA_SERVER_IMAGE}:${IMAGE_TAG}
                        docker push ${FRONTEND_IMAGE}:${IMAGE_TAG}
                    """
                }
            }
        }

        stage('Update Kubernetes Manifests with New Image Tags') {
            steps {
                script {
                    def services = [
                        [name: 'qr-code-attendance', image: QR_CODE_ATTENDANCE_IMAGE],
                        [name: 'attendance-service', image: ATTENDANCE_SERVICE_IMAGE],
                        [name: 'face-recognition-service', image: FACE_RECOGNITION_IMAGE],
                        [name: 'eureka-server-deployment', image: EUREKA_SERVER_IMAGE],
                        [name: 'frontend', image: FRONTEND_IMAGE]
                    ]
                    services.each { svc ->
                        sh """
                            sed -i 's|image: ${svc.image}:.*|image: ${svc.image}:${IMAGE_TAG}|g' k8s/${svc.name}-deployment.yaml
                        """
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                script {
                    sh """
                        export KUBECONFIG=$HOME/.kube/config
                        kubectl apply -f k8s/ --namespace ${NAMESPACE}

                        deployments=(
                            "eureka-server-deployment"
                            "postgres-deployment"
                            "face-recognition-service"
                            "attendance-service"
                            "qr-code-attendance"
                            "frontend"
                        )

                        for deploy in "\${deployments[@]}"; do
                            echo "Waiting for rollout of deployment/\${deploy} in namespace ${NAMESPACE}"
                            kubectl rollout status deployment/\${deploy} --namespace ${NAMESPACE} --timeout=180s
                        done
                    """
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
