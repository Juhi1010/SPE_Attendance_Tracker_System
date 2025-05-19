pipeline {
    agent any

    environment {
        DOCKER_REGISTRY = 'docker.io/juhir10'
        DOCKER_CREDENTIALS_ID = 'docker-credentials'
        PROJECT_NAME = 'latest-spe'
        K8S_DIR = 'k8s'
        SECRETS_FILE = 'generate_secret.yaml'
        ANSIBLE_VAULT_PASSWORD_ID = 'ansible-vault-password' // Jenkins credential ID
    }

    stages {
        stage('Clone Repository') {
            steps {
                echo 'Clone'
            }
        }

        stage('Docker Compose Build & Tag') {
            steps {
                echo 'Build and tag'
            }
        }

        stage('Push Images to Registry') {
            steps {
                echo 'Push to registry'
            }
        }

        stage('Ansible Vault Decrypt') {
            steps {
                echo 'vault'
            }
        }

        stage('Generate & Apply Kubernetes Secrets') {
            steps {
                echo 'k8s'
            }
        }

        stage('Kubernetes Deployment') {
            steps {
                echo 'deployment'
            }
        }
    }

    post {
        success {
            echo 'CI/CD pipeline completed successfully!'
        }
        failure {
            echo 'Something went wrong in the pipeline.'
        }
    }
}
