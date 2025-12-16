pipeline {
    agent any

    environment {
        IMAGE = "yumrecipe/zipmin:latest"
        DEPLOY_HOST = "ec2-15-164-104-202.ap-northeast-2.compute.amazonaws.com"
        DEPLOY_USER = "ec2-user"
        APP_NAME = "zipmin"
    }

    stages {
		/*
        stage('Clone Repository') {
            steps {
                git 'https://github.com/yum-recipe-project/zipmin.git'
            }
        }
        */

        stage('Build Docker Image') {
            steps {
                sh "docker build -t ${IMAGE} ."
            }
        }

        stage('Push to Docker Hub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker-hub',
                    usernameVariable: 'DH_USER', passwordVariable: 'DH_PASS')]) {
                    sh """
                      echo "${DH_PASS}" | docker login -u "${DH_USER}" --password-stdin
                      docker push ${IMAGE}
                      docker logout
                    """
                }
            }
        }

        stage('Deploy on Project Server') {
            steps {
                sshagent(credentials: ['zipmin-ssh']) {
                    sh """
                      ssh -o StrictHostKeyChecking=no ${DEPLOY_USER}@${DEPLOY_HOST} '
                        docker pull ${IMAGE} &&
                        docker rm -f ${APP_NAME} || true &&
                        docker run -d --name ${APP_NAME} -p 8586:8080 ${IMAGE}
                      '
                    """
                }
            }
        }
    }
}
