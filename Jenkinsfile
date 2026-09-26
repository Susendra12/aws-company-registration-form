pipeline {
    agent any

    stages {

        stage('Build') {
            steps {
                dir('aws-company-registration-form') {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Test') {
            steps {
                dir('aws-company-registration-form') {
                    sh 'mvn test'
                }
            }
        }

        stage('Deploy') {
            steps {
                dir('aws-company-registration-form') {
                    sh '''
                        echo "Stopping old application..."

                        PID=$(pgrep -f "java -jar" || true)

                        if [ -n "$PID" ]; then
                            kill $PID
                            sleep 5
                        fi

                        echo "Starting new application..."

                        nohup java -jar target/*.jar > app.log 2>&1 &

                        sleep 10

                        echo "Application started successfully"

                        ps -ef | grep java
                    '''
                }
            }
        }
    }

    post {
        success {
            echo 'Build, Test and Deployment completed successfully!'
        }

        failure {
            echo 'Pipeline failed. Check the console output.'
        }
    }
}