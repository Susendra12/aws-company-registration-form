pipeline {
    agent any

    stages {

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

stage('Deploy') {
    steps {
        sh '''
            echo "Stopping old application..."

            PID=$(pgrep -f "Company-Registation-Form-0.0.1-SNAPSHOT.jar" || true)

            if [ -n "$PID" ]; then
                echo "Found old application with PID: $PID"
                kill $PID
                sleep 5
            else
                echo "No old application is running"
            fi

            echo "Starting new application..."

            nohup java -jar target/Company-Registation-Form-0.0.1-SNAPSHOT.jar \
                > app.log 2>&1 &

            sleep 10

            echo "Checking application..."

            ps -ef | grep "[C]ompany-Registation-Form"

            echo "Application started successfully"
        '''
         }
       }
    }

    post {
        success {
            echo 'Pipeline completed successfully.'
        }
        failure {
            echo 'Pipeline failed. Check the console output.'
        }
    }
}