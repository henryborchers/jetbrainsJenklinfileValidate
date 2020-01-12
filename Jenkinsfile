pipeline{
    agent {
        docker {
            image 'gradle'
        }
    }
    stages{
        stage("build"){
            steps{
                sh label: 'Building jar', script: 'gradle jar'
            }
            post{
                always{
                    sh "ls build"
                }
            }
        }
    }
}