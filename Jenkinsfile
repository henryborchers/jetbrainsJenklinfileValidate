pipeline{
    agent {
        docker {
            image 'gradle'
        }
    }
    stages{
        stage("build"){
            steps{
                sh label: 'Building jar', script: 'gradle build --warning-mode all | tee gradle.log'
            }

        }
    }
    post{
        always{
            sh "ls build"
            sh "gradle clean"
        }
    }
}