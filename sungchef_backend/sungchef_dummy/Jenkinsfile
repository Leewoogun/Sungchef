pipeline{
    agent any

    stages{
        stage('Build'){
            steps{
                script{
                    sh 'chmod +x gradlew'
                    sh './gradlew clean build'
                    sh 'chmod +x ./docker_install.sh'
                    sh './docker_install.sh'
                }
            }
        }
        stage('Deploy'){
            steps{
                script{
                    sh 'docker build -t jenkins-test .'
                    sh 'docker rm -f jenkins-test'
                    sh 'docker run -d --name jenkins-test -p 8080:8080 jenkins-test'
                }
            }
        }
    }
}