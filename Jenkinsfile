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
                    sh label: 'Verify Plugin', script: 'gradle verifyPlugin -w  --warning-mode all'
                }
            }
        }
        stage("Checkstyle"){
            steps{
                catchError(buildResult: 'UNSTABLE') {
                    sh label: 'Running Checkstyle', script: 'gradle checkstyleMain'
                }
            }
            post{
                always{
                    recordIssues(tools: [checkStyle(pattern: 'build/reports/checkstyle/*.xml')])
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