// pipeline {
//     agent any
//
//     environment {
//         DOCKER_REGISTRY = 'docker.io/juhir10'
//         DOCKER_CREDENTIALS_ID = 'docker-credentials'
//         PROJECT_NAME = 'latest-spe'
//         K8S_DIR = 'k8s'
//         SECRETS_FILE = 'generate_secret.yaml'
//         ANSIBLE_VAULT_PASSWORD_ID = 'ansible-vault-password' // Jenkins credential ID
//     }
//
//     stages {
//         stage('Clone Repository') {
//             steps {
//                 echo 'Clone'
//             }
//         }
//
//         stage('Docker Compose Build & Tag') {
//             steps {
//                 echo 'Build and tag'
//             }
//         }
//
//         stage('Push Images to Registry') {
//             steps {
//                 echo 'Push to registry'
//             }
//         }
//
//         stage('Ansible Vault Decrypt') {
//             steps {
//                 echo 'vault'
//             }
//         }
//
//         stage('Generate & Apply Kubernetes Secrets') {
//             steps {
//                 echo 'k8s'
//             }
//         }
//
//         stage('Kubernetes Deployment') {
//             steps {
//                 echo 'deployment'
//             }
//         }
//     }
//
//     post {
//         success {
//             echo 'CI/CD pipeline completed successfully!'
//         }
//         failure {
//             echo 'Something went wrong in the pipeline.'
//         }
//     }
// }


pipeline {
    agent any

    environment {
        DOCKER_REGISTRY = 'docker.io/juhir10'
        NAMESPACE = 'spe-final'
//         KUBE_CONFIG_CREDENTIALS = 'kubeconfig-id'
        DOCKER_CREDENTIALS = 'docker-credentials'

        // Define image base names for each service
        QR_CODE_ATTENDANCE_IMAGE = "${DOCKER_REGISTRY}/qr-code-attendance"
        ATTENDANCE_SERVICE_IMAGE = "${DOCKER_REGISTRY}/attendance-service"
        FACE_RECOGNITION_IMAGE = "${DOCKER_REGISTRY}/face-recognition-service"
        EUREKA_SERVER_IMAGE = "${DOCKER_REGISTRY}/server-registry"
        FRONTEND_IMAGE = "${DOCKER_REGISTRY}/frontend"
        // Tag to use for this build
        IMAGE_TAG = "build-${env.BUILD_NUMBER}"
    }

    stages {

        stages {
            stage('Git Clone') {
                steps {
                    // Clones your repo to the Jenkins workspace
                    git branch: 'test-all', url: 'https://github.com/Juhi1010/SPE_Attendance_Tracker_System.git'
                }
        }

        stage('Build Docker Images') {
            steps {
                script {
                    docker.build("${QR_CODE_ATTENDANCE_IMAGE}:${IMAGE_TAG}", "./user-service")
                    docker.build("${ATTENDANCE_SERVICE_IMAGE}:${IMAGE_TAG}", "./attendance-service")
                    docker.build("${FACE_RECOGNITION_IMAGE}:${IMAGE_TAG}", "./face-recognition-service")
                    docker.build("${EUREKA_SERVER_IMAGE}:${IMAGE_TAG}", "./eureka-server")
                    docker.build("${FRONTEND_IMAGE}:${IMAGE_TAG}", "./qr-frontend")
                }
            }
        }

        stage('Push Docker Images') {
            steps {
                script {
                    docker.withRegistry("https://${DOCKER_REGISTRY}", DOCKER_CREDENTIALS) {
                        docker.image("${QR_CODE_ATTENDANCE_IMAGE}:${IMAGE_TAG}").push()
                        docker.image("${ATTENDANCE_SERVICE_IMAGE}:${IMAGE_TAG}").push()
                        docker.image("${FACE_RECOGNITION_IMAGE}:${IMAGE_TAG}").push()
                        docker.image("${EUREKA_SERVER_IMAGE}:${IMAGE_TAG}").push()
                        docker.image("${FRONTEND_IMAGE}:${IMAGE_TAG}").push()
                    }
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
                        [name: 'frontend', image: FRONTEND_IMAGE],
//                         [name: 'postgres-deployment']
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
                   // Set KUBECONFIG env var explicitly to local kubeconfig path
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
                // Export kubeconfig path pointing to your local minikube config
                sh '''
                  export KUBECONFIG=$HOME/.kube/config
                  kubectl get pods --namespace ${NAMESPACE}
                  kubectl describe pods --namespace ${NAMESPACE}
                  # Optionally, fetch logs for failed pods here
                '''
            }
        }
    }
        success {
            echo "Pipeline success"
        }
}

