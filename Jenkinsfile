pipeline {
    agent any
    
	environment {
		APP_NAME = "zipmin"
		DOCKER_IMAGE = "yumrecipe/zipmin:latest"
		DEPLOY_HOST = "13.209.50.3"
		DEPLOY_USER = "ec2-user"
    }

    stages {
		// 소스 코드 체크아웃
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
		// WAR 파일 빌드
		stage('Build WAR') {
			steps {
				sh './gradlew clean bootWar -x test'
			}
		}
        
		// 이미지 빌드
		stage('Build Docker Image') {
			steps {
				sh "docker build -t ${DOCKER_IMAGE} ."
			}
		}
        
        // 도커 허브에 이미지 푸시
		stage('Push to Docker Hub') {
			steps {
				withCredentials([usernamePassword(
					credentialsId: 'DOCKERHUB',
					usernameVariable: 'DH_USERNAME',
					passwordVariable: 'DH_PASSWORD'
				)]) {
                    sh """
                    	echo "${DH_PASSWORD}" | docker login -u "${DH_USERNAME}" --password-stdin
                    	docker push ${DOCKER_IMAGE}
                    	docker logout
                    """
				}
			}
		}
        
        // 배포 서버에서 최신 이미지로 컨테이너 재배포
        stage('Deploy on Project Server') {
		    steps {
				// 배포에 필요한 민감 정보를 환경 변수로 주입
				withCredentials([
					string(credentialsId: 'APP_BASE_URL', variable: 'APP_BASE_URL'),
					string(credentialsId: 'DB_HOST', variable: 'DB_HOST'),
					string(credentialsId: 'DB_PORT', variable: 'DB_PORT'),
					string(credentialsId: 'DB_NAME', variable: 'DB_NAME'),
					string(credentialsId: 'DB_USERNAME', variable: 'DB_USERNAME'),
					string(credentialsId: 'DB_PASSWORD', variable: 'DB_PASSWORD'),
					string(credentialsId: 'JWT_SECRET', variable: 'JWT_SECRET'),
					string(credentialsId: 'GOOGLE_CLIENT_ID', variable: 'GOOGLE_CLIENT_ID'),
					string(credentialsId: 'GOOGLE_CLIENT_SECRET', variable: 'GOOGLE_CLIENT_SECRET'),
					string(credentialsId: 'NAVER_CLIENT_ID', variable: 'NAVER_CLIENT_ID'),
					string(credentialsId: 'NAVER_CLIENT_SECRET', variable: 'NAVER_CLIENT_SECRET'),
					string(credentialsId: 'MAIL_USERNAME', variable: 'MAIL_USERNAME'),
					string(credentialsId: 'MAIL_PASSWORD', variable: 'MAIL_PASSWORD')
				]) {
					// 배포 서버 접속 권한을 부여 후 컨테이너 실행
					sshagent(credentials: ['ZIPMIN_SSH']) {
						sh """
							ssh -o StrictHostKeyChecking=no ${DEPLOY_USER}@${DEPLOY_HOST} '
								set -e
								
			                    umask 077
								cat > /home/ec2-user/zipmin.env <<-EOF
									APP_BASE_URL=${APP_BASE_URL}
									DB_HOST=${DB_HOST}
									DB_PORT=${DB_PORT}
									DB_NAME=${DB_NAME}
									DB_USERNAME=${DB_USERNAME}
									DB_PASSWORD=${DB_PASSWORD}
									JWT_SECRET=${JWT_SECRET}
									GOOGLE_CLIENT_ID=${GOOGLE_CLIENT_ID}
									GOOGLE_CLIENT_SECRET=${GOOGLE_CLIENT_SECRET}
									NAVER_CLIENT_ID=${NAVER_CLIENT_ID}
									NAVER_CLIENT_SECRET=${NAVER_CLIENT_SECRET}
									MAIL_USERNAME=${MAIL_USERNAME}
									MAIL_PASSWORD=${MAIL_PASSWORD}
								EOF
								
								docker pull ${DOCKER_IMAGE}
								docker rm -f ${APP_NAME} || true
								docker run -d \
									--name ${APP_NAME} \
									-p 8586:8080 \
									-v /home/ec2-user/uploads:/uploads \
									--env-file /home/ec2-user/zipmin.env \
									${DOCKER_IMAGE}
							'
		                """
		            }
		        }
		    }
		}
    }
}
