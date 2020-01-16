pipeline{
    agent {
        docker {
            image 'gradle'
        }
    }
    stages{
        stage("Init"){
            steps{
                sh label: 'Configure', script: 'gradle --info'
            }
        }
        stage("build"){
            steps{
                sh label: 'Building', script: 'gradle buildPlugin -w --warning-mode all | tee gradle.build.log'
            }
        }
        stage("Static Analysis"){
            parallel{
                stage("Checkstyle"){
                    steps{
                        catchError(buildResult: 'SUCCESS', stageResult: 'UNSTABLE') {
                            sh label: 'Running Checkstyle', script: 'gradle checkstyleMain'
                        }
                    }
                    post{
                        always{
                            recordIssues(tools: [checkStyle(pattern: 'build/reports/checkstyle/*.xml')])
                        }
                    }
                }
                stage("SpotBugs"){
                    steps{
                        catchError(buildResult: 'UNSTABLE', stageResult: 'UNSTABLE') {
                            sh label: 'Running Spotbugs', script: 'gradle spotbugsMain'
                        }
                    }
                    post{
                        always{
                            recordIssues(tools: [spotBugs(pattern: 'build/reports/spotbugs/*.xml')])
                        }
                    }
                }
            }
        }

        stage("Verify Plugin"){
            steps{
                catchError(buildResult: 'UNSTABLE') {
                    sh label: 'Verify Plugin', script: 'gradle verifyPlugin -w  --warning-mode all'
                }
            }
        }


    }
    post{
        always{
            sh "gradle clean"
            deleteDir()
        }
    }
}