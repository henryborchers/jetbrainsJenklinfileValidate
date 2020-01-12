pipeline{
    agent {
        docker {
            image 'gradle'
        }
    }
    stages{
        stage("build"){
            steps{
                sh label: 'Building', script: 'gradle buildPlugin -w --warning-mode all | tee gradle.build.log'
            }
        }
        stage("Verify Plugin"){
            steps{
                catchError(buildResult: 'UNSTABLE') {
                    sh label: 'Verify Plugin', script: 'gradle verifyPlugin -w --warning-mode all'
                }
            }
        }
        stage("Create JAR"){
            steps{
                sh label: 'Building', script: 'gradle jar --warning-mode all | tee gradle.jar.log'
            }
            post{
                always{
                    sh "ls build/"
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