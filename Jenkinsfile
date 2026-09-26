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
                    echo "Deploying application..."

                    sudo mkdir -p /opt/company-registration

                    sudo cp target/Company-Registation-Form-0.0.1-SNAPSHOT.jar \
                        /opt/company-registration/Company-Registation-Form.jar

                    sudo systemctl restart company-registration

                    sudo systemctl status company-registration --no-pager
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