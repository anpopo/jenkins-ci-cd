pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                git branch: 'master',
                    credentialsId: 'github_access_token',
                    url: 'https://github.com/anpopo/jenkins-ci-cd.git'
            }
        }
    }
}