pipeline {
    agent any

    environment {
        IMAGE = "yumrecipe/zipmin:latest"
        DEPLOY_HOST = "ec2-15-164-104-202.ap-northeast-2.compute.amazonaws.com"
        DEPLOY_USER = "ec2-user"
        APP_NAME = "zipmin"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build WAR') {
            steps {
                sh '''
                  chmod +x ./gradlew
                  ./gradlew clean war -x test
                  ls -al build/libs
                '''
            }
        }

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
		        withCredentials([
		            string(credentialsId: 'DATABASE_USERNAME', variable: 'DATABASE_USERNAME'),
		            string(credentialsId: 'DATABASE_PASSWORD', variable: 'DATABASE_PASSWORD'),
		            string(credentialsId: 'JWT_SECRET', variable: 'JWT_SECRET'),
		            string(credentialsId: 'GOOGLE_CLIENT_ID', variable: 'GOOGLE_CLIENT_ID'),
		            string(credentialsId: 'GOOGLE_CLIENT_SECRET', variable: 'GOOGLE_CLIENT_SECRET'),
		            string(credentialsId: 'NAVER_CLIENT_ID', variable: 'NAVER_CLIENT_ID'),
		            string(credentialsId: 'NAVER_CLIENT_SECRET', variable: 'NAVER_CLIENT_SECRET'),
		            string(credentialsId: 'MAIL_USERNAME', variable: 'MAIL_USERNAME'),
		            string(credentialsId: 'MAIL_PASSWORD', variable: 'MAIL_PASSWORD')
		        ]) {
		            sshagent(credentials: ['zipmin-ssh']) {
		                sh """
		                  ssh -o StrictHostKeyChecking=no ${DEPLOY_USER}@${DEPLOY_HOST} '
		                    set -e
		
		                    umask 077
		                    cat > /home/ec2-user/zipmin.env <<-EOF
								DATABASE_USERNAME=${DATABASE_USERNAME}
								DATABASE_PASSWORD=${DATABASE_PASSWORD}
								JWT_SECRET=${JWT_SECRET}
								GOOGLE_CLIENT_ID=${GOOGLE_CLIENT_ID}
								GOOGLE_CLIENT_SECRET=${GOOGLE_CLIENT_SECRET}
								NAVER_CLIENT_ID=${NAVER_CLIENT_ID}
								NAVER_CLIENT_SECRET=${NAVER_CLIENT_SECRET}
								MAIL_USERNAME=${MAIL_USERNAME}
								MAIL_PASSWORD=${MAIL_PASSWORD}
							EOF
		
		                    docker pull ${IMAGE}
		                    docker rm -f ${APP_NAME} || true
		                    docker run -d --name ${APP_NAME} -p 8586:8080 --env-file /home/ec2-user/zipmin.env ${IMAGE}
		                  '
		                """
		            }
		        }
		    }
		}

/*
        stage('Deploy on Project Server') {
            steps {
                sshagent(credentials: ['zipmin-ssh']) {
                    sh """
                      ssh -o StrictHostKeyChecking=no ${DEPLOY_USER}@${DEPLOY_HOST} '
                        set -e
                        docker pull ${IMAGE}
                        docker rm -f ${APP_NAME} || true
                        docker run -d --name ${APP_NAME} -p 8586:8080 ${IMAGE}
                      '
                    """
                }
            }
        }
        */
    }
}
