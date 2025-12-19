pipeline {
    agent any
    
    // 환경 변수 지정
    environment {
        IMAGE = "yumrecipe/zipmin:latest"
        DEPLOY_HOST = "43.201.77.32"
        DEPLOY_USER = "ec2-user"
        APP_NAME = "zipmin"
    }

    stages {
		// Git 저장소에서 소스 코드 체크아웃
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
		// WAR 파일 빌드
		stage('Build WAR') {
			steps {
				sh './gradlew clean war -x test'
			}
		}
        
		// 도커 파일을 기반으로 이미지 빌드
		stage('Build Docker Image') {
			steps {
				sh "docker build -t ${IMAGE} ."
			}
		}
        
        // DockerHub에 이미지 Push
		stage('Push to Docker Hub') {
			steps {
				// Jenkins Credentials에 저장된 계정과 토큰을 환경 변수로 주입
				withCredentials([usernamePassword(
					credentialsId: 'docker-hub',
					usernameVariable: 'DOCKERHUB_USERNAME',
					passwordVariable: 'DOCKERHUB_PASS'
				)]) {
					// DockerHub 로그인 후 이미지 push하고 로그아웃
                    sh """
                    	echo "${DOCKERHUB_PASS}" | docker login -u "${DOCKERHUB_USERNAME}" --password-stdin
                    	docker push ${IMAGE}
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
					// 배포 서버 접속 권한을 부여 후 컨테이너 실행
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
    }
}
