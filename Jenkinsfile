pipeline{
    agent {
        docker {
            image 'gradle'
        }
    }
    stages{
        stage("build"){
            steps{
                sh label: 'Building', script: 'gradle build --warning-mode all | tee gradle.build.log'
            }
        }
        stage("Create JAR"){
            steps{
                sh label: 'Building', script: 'gradle jar --warning-mode all | tee gradle.jar.log'
            }
            post{
                always{
                    sh "ls build"
                }
            }
        }
    }
    post{
        always{
            sh "gradle clean"
        }
    }
}