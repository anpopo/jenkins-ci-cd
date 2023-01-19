pipeline {
    agent any
//     tools {
//         gradle 'gradle_v7.5.1'
//         dockerTool 'docker'
//         git 'Default'
//     }

    stages {
        stage('checkout') {
            steps {
                git branch: 'master',
                    credentialsId: 'github_access_token',
                    url: 'https://github.com/anpopo/jenkins-ci-cd.git'
            }
            post {
                success {
                  echo 'Repository clone success !'
                }
                failure {
                  echo 'Repository clone failure !'
                }

            }
        }

//         stage("gradle init") {
//             steps{
//                 sh 'gradle init'
//             }
//         }

        stage("maven build") {
            stages {
                stage("mvn install") {
                    steps {
                        sh """
                            mvn install
                        """
                    }
                }
                stage("maven build") {
                    steps{
                        sh "mvn clean compile package"
                    }
                }
            }

        }
    }

}